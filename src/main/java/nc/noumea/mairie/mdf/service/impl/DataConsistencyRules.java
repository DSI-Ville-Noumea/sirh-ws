package nc.noumea.mairie.mdf.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import nc.noumea.mairie.mdf.domain.vdn.FdsMutDet;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutEnt;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutTot;
import nc.noumea.mairie.mdf.dto.AlimenteBordereauBean;
import nc.noumea.mairie.mdf.dto.CotisationBean;
import nc.noumea.mairie.mdf.dto.EffectifBean;
import nc.noumea.mairie.mdf.dto.RemunerationBean;
import nc.noumea.mairie.mdf.service.IDataConsistencyRules;

@Service
public class DataConsistencyRules implements IDataConsistencyRules {

	protected Logger logger = LoggerFactory.getLogger(DataConsistencyRules.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
	
	final static String REMUNERATION = "SAL";

	@Override
	public void verifyInputDatas(FdsMutEnt entete, List<FdsMutDet> details, FdsMutTot total) {
		
		// En tête
		Date moisPrecedent =  new DateTime().minusMonths(1).toDate();
		if (!entete.getPeriodeCourante().toString().equals(sdf.format(moisPrecedent)))
			throw new IllegalArgumentException("Le mois du fichier d'en-tête ne correspond pas au mois précédent.");
		
		// Total
		if (!total.getNombreLignesDetail().equals(details.size()))
			throw new IllegalArgumentException("Le nombre d'enregistrement ne correspond pas à la valeur renseignée dans le total.");
		if (!total.getCodeCollectivité().equals(entete.getCodeCollectivité()))
			throw new IllegalArgumentException("Le code collectivité diffère entre l'en-tête et le total.");
		if (!total.getDateFichier().equals(entete.getDateFichier()))
			throw new IllegalArgumentException("La date du fichier diffère entre l'en-tête et le total.");
		
		// Données
		String codeCol = details.get(0).getCodeCollectivite();
		for (FdsMutDet det : details) {
			if (!det.getCodeCollectivite().equals(codeCol))
				throw new IllegalArgumentException("Plusieurs code de collectivité différents se trouvent dans les details.");
			// TODO : Check sur valeurs positives ?
			// TODO : Taux supérieurs à 1 ?
			// TODO : Demander à Christine quels sont les cas à tester (dates, nombres, montants, numéro cafat obligatoire, ...)
		}
	}
	
	public AlimenteBordereauBean alimenteDatas(FdsMutEnt enTete, List<FdsMutDet> details, FdsMutTot total) {
		
		AlimenteBordereauBean bean = new AlimenteBordereauBean();
		
		bean.setCodeCollectivité(enTete.getCodeCollectivité());
		bean.setMoisPaye(enTete.getDateFichier());
		
		EffectifBean effectif = mapEffectifs(details);
		bean.setEffectif(effectif);
		
		RemunerationBean remuneration = mapRemuneration(details);
		bean.setRemuneration(remuneration);

		CotisationBean cotisationPatr = mapCotisations(details, true);
		bean.setCotisationPatronale(cotisationPatr);
		
		CotisationBean cotisationSal = mapCotisations(details, false);
		bean.setCotisationSalariale(cotisationSal);
		
		logger.debug("Les données du bordereau ont bien été générées.");
		
		return bean;
	}

	private EffectifBean mapEffectifs(List<FdsMutDet> details) {
		EffectifBean effectif = new EffectifBean();
		List<Integer> effectifDistinc = Lists.newArrayList();
		
		for (FdsMutDet det : details) {
			if (!effectifDistinc.contains(det.getId().getNumeroPers()))
				effectifDistinc.add(det.getId().getNumeroPers());
		}
		
		// Attention : différence entre matcaf : 1220 res. et nopers : 1194 res. 
		// TODO : Quelle est la diff, et lequel prendre ?
		effectif.setEffectifRemunere(effectifDistinc.size());
		// TODO : setEffectif()
		effectif.setEffectif(1300);
		
		return effectif;
	}

	private RemunerationBean mapRemuneration(List<FdsMutDet> details) {
		RemunerationBean remuneration = new RemunerationBean();
		
		Integer montantTotal = 0;
		Integer montantRemuneration = 0;
		Integer montantRegularisation = 0;
		
		for (FdsMutDet det : details) {
			montantTotal += det.getMontantBrut();
			if (det.getId().getTypeMouvement().equals(REMUNERATION))
				montantRemuneration += det.getMontantBrut();
			else
				montantRegularisation += det.getMontantBrut();
		}
		
		remuneration.setTotalBrut(montantTotal);
		remuneration.setMontantRegularisation(montantRegularisation);
		remuneration.setMontantRemuneration(montantRemuneration);
		
		return remuneration;
	}
	
	private CotisationBean mapCotisations(List<FdsMutDet> details, boolean isPatronal) {
		CotisationBean cotisation = new CotisationBean();
		
		Integer montantTotal = 0;
		Integer montantRemuneration = 0;
		Integer montantRegularisation = 0;
		
		for (FdsMutDet det : details) {
			// Pour les parts patronales
			if (isPatronal) {
				montantTotal += det.getMontantPP();
				if (det.getId().getTypeMouvement().equals(REMUNERATION))
					montantRemuneration += det.getMontantPP();
				else
					montantRegularisation += det.getMontantPP();
			}
			// Pour les parts salariales
			else {
				montantTotal += det.getMontantPS();
				if (det.getId().getTypeMouvement().equals(REMUNERATION))
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
		
//		if (bean.getRemuneration() == null || bean.getRemuneration().getTotalBrut() == null 
//				|| bean.getRemuneration().getMontantRemuneration() == null
//				|| bean.getRemuneration().getMontantRegularisation() == null)
//			throw new IllegalArgumentException("Les données concernant les rémunérations ne sont pas complètes");
		
		return;
	}
}
