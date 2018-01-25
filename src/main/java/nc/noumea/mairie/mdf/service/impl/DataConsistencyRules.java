package nc.noumea.mairie.mdf.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import nc.noumea.mairie.mdf.dto.AlimenteBordereauBean;
import nc.noumea.mairie.mdf.dto.CotisationBean;
import nc.noumea.mairie.mdf.dto.DetailDto;
import nc.noumea.mairie.mdf.dto.EffectifBean;
import nc.noumea.mairie.mdf.dto.EnTeteDto;
import nc.noumea.mairie.mdf.dto.RemunerationBean;
import nc.noumea.mairie.mdf.dto.TotalDto;
import nc.noumea.mairie.mdf.service.IDataConsistencyRules;
import nc.noumea.mairie.model.repository.ISpcarrRepository;

@Service
public class DataConsistencyRules implements IDataConsistencyRules {

	protected Logger logger = LoggerFactory.getLogger(DataConsistencyRules.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
	private SimpleDateFormat sdfPerCou = new SimpleDateFormat("yyyyMM");
	private SimpleDateFormat sdfDateEnvoi = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdfDateTraitement = new SimpleDateFormat("MMMM YYYY", new Locale("fr"));
	
	final static String REMUNERATION = "SAL";
	
	private static final String VDN = "VDN";
	private static final String PERS = "PERS";
	private static final String ADM = "ADM";

	@Autowired
	private ISpcarrRepository spcarrRepository;

	@Override
	public void verifyInputDatas(EnTeteDto enTete, List<DetailDto> details, TotalDto total) {
		
		if (enTete == null || total == null || details == null || details.isEmpty())
			throw new IllegalArgumentException("Impossible de générer le bordereau : certaines données n'ont pas été récupérées.");
		
		// En tête
		Date moisPrecedent =  new DateTime().minusMonths(1).toDate();
		if (!enTete.getPeriodeCourante().toString().equals(sdf.format(moisPrecedent)))
			throw new IllegalArgumentException("Le mois du fichier d'en-tête ne correspond pas au mois précédent.");
		
		// Total
		if (!total.getNombreLignesDetail().equals(details.size()))
			throw new IllegalArgumentException("Le nombre d'enregistrement ne correspond pas à la valeur renseignée dans le total.");
		if (!total.getCodeCollectivité().equals(enTete.getCodeCollectivité()))
			throw new IllegalArgumentException("Le code collectivité diffère entre l'en-tête et le total.");
		if (!total.getDateFichier().equals(enTete.getDateFichier()))
			throw new IllegalArgumentException("La date du fichier diffère entre l'en-tête et le total.");
		
		// Données
		String codeCol = details.get(0).getCodeCollectivite();
		for (DetailDto det : details) {
			if (!det.getCodeCollectivite().equals(codeCol))
				throw new IllegalArgumentException("Plusieurs code de collectivité différents se trouvent dans les details.");
		}
	}
	
	@Override
	public AlimenteBordereauBean alimenteDatas(EnTeteDto enTete, List<DetailDto> details, TotalDto total) throws ParseException {
		
		AlimenteBordereauBean bean = new AlimenteBordereauBean();
		
		Date moisTraitement = sdfPerCou.parse(enTete.getPeriodeCourante().toString());
		
		bean.setCodeCollectivité(enTete.getCodeCollectivité());
		bean.setMoisPaye(sdfDateTraitement.format(moisTraitement));
		bean.setDenominationEmployeur("MAIRIE DE NOUMEA");
		bean.setDateEmission(sdfDateEnvoi.format(new Date()));
		
		EffectifBean effectif = mapEffectifs(details);
		bean.setEffectif(effectif);
		
		RemunerationBean remuneration = mapRemuneration(details);
		bean.setRemuneration(remuneration);

		CotisationBean cotisationPatr = mapCotisations(details, true);
		bean.setCotisationPatronale(cotisationPatr);
		
		CotisationBean cotisationSal = mapCotisations(details, false);
		bean.setCotisationSalariale(cotisationSal);
		
		logger.debug("Les données du bordereau récapitulatif ont bien été générées.");
		
		return bean;
	}

	private EffectifBean mapEffectifs(List<DetailDto> details) {
		EffectifBean effectif = new EffectifBean();
		List<String> effectifDistinc = Lists.newArrayList();
		
		for (DetailDto det : details) {
			if (!effectifDistinc.contains(det.getMatriculeCafat()))
				effectifDistinc.add(det.getMatriculeCafat());
		}

		// Pour des explications concernant les effectifs, se réferrer à cette redmine : https://redmine.ville-noumea.nc/issues/39378#note-9
		//     Rubrique « Effectif pour lesquels une rémunération est déclarée pour le mois m 
		effectif.setEffectifRemunere(effectifDistinc.size());
		
		//     Rubrique « Effectif de l'employeur au 1er jour du mois + entrants au cours du mois m » 
		Date finMoisPrecedent = new DateTime().withDayOfMonth(1).minusDays(1).toDate();
		Date debutMoisPrecedent = new DateTime(finMoisPrecedent).withDayOfMonth(1).toDate();
		Integer effectifTotal = spcarrRepository.getListeAgentsActifsPourGenerationBordereauMDF(debutMoisPrecedent, finMoisPrecedent);
		effectif.setEffectif(effectifTotal);
		
		return effectif;
	}

	private RemunerationBean mapRemuneration(List<DetailDto> details) {
		RemunerationBean remuneration = new RemunerationBean();
		
		Integer montantTotal = 0;
		Integer montantRemuneration = 0;
		Integer montantRegularisation = 0;
		
		for (DetailDto det : details) {
			montantTotal += det.getMontantBrut();
			if (det.getTypeMouvement().equals(REMUNERATION))
				montantRemuneration += det.getMontantBrut();
			else
				montantRegularisation += det.getMontantBrut();
		}
		
		remuneration.setTotalBrut(montantTotal);
		remuneration.setMontantRegularisation(montantRegularisation);
		remuneration.setMontantRemuneration(montantRemuneration);
		
		return remuneration;
	}
	
	private CotisationBean mapCotisations(List<DetailDto> details, boolean isPatronal) {
		CotisationBean cotisation = new CotisationBean();
		
		Integer montantTotal = 0;
		Integer montantRemuneration = 0;
		Integer montantRegularisation = 0;
		
		for (DetailDto det : details) {
			// Pour les parts patronales
			if (isPatronal) {
				montantTotal += det.getMontantPP();
				if (det.getTypeMouvement().equals(REMUNERATION))
					montantRemuneration += det.getMontantPP();
				else
					montantRegularisation += det.getMontantPP();
			}
			// Pour les parts salariales
			else {
				montantTotal += det.getMontantPS();
				if (det.getTypeMouvement().equals(REMUNERATION))
					montantRemuneration += det.getMontantPS();
				else
					montantRegularisation += det.getMontantPS();
			}
		}
		
		cotisation.setCotisationCalculee(montantTotal);
		cotisation.setSurRemuneration(montantRemuneration);
		cotisation.setSurRegularisation(montantRegularisation);
		
		return cotisation;
	}
	
	public void verifyConsistency(AlimenteBordereauBean bean) {
		if (bean.getCodeCollectivité() == null || bean.getMoisPaye() == null)
			throw new IllegalArgumentException("Les données ne sont pas complètes");
		
		if (bean.getEffectif() == null || bean.getEffectif().getEffectif() == null || bean.getEffectif().getEffectifRemunere() == null)
			throw new IllegalArgumentException("Les données concernant les effectifs ne sont pas complètes");
		
		if (bean.getRemuneration() == null || bean.getRemuneration().getTotalBrut() == null 
				|| bean.getRemuneration().getMontantRemuneration() == null
				|| bean.getRemuneration().getMontantRegularisation() == null)
			throw new IllegalArgumentException("Les données concernant les rémunérations ne sont pas complètes");
		
		if (bean.getCotisationPatronale() == null || bean.getCotisationPatronale().getCotisationCalculee() == null 
				|| bean.getCotisationPatronale().getSurRegularisation() == null
				|| bean.getCotisationPatronale().getSurRemuneration() == null)
			throw new IllegalArgumentException("Les données concernant les cotisations patronales ne sont pas complètes");
		
		if (bean.getCotisationSalariale() == null || bean.getCotisationSalariale().getCotisationCalculee() == null 
				|| bean.getCotisationSalariale().getSurRegularisation() == null
				|| bean.getCotisationSalariale().getSurRemuneration() == null)
			throw new IllegalArgumentException("Les données concernant les cotisations salariales ne sont pas complètes");
		
		return;
	}
}
