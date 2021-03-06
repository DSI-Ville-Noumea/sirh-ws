package nc.noumea.mairie.service.sirh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spclas;
import nc.noumea.mairie.model.bean.Speche;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.sirh.AvancementDetache;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.Cap;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.MotifAvct;
import nc.noumea.mairie.model.repository.sirh.IAvancementRepository;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.avancements.ArreteDto;
import nc.noumea.mairie.web.dto.avancements.ArreteListDto;
import nc.noumea.mairie.web.dto.avancements.AvancementEaeDto;
import nc.noumea.mairie.web.dto.avancements.AvancementItemDto;
import nc.noumea.mairie.web.dto.avancements.AvancementsDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementCorpsDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;
import nc.noumea.mairie.ws.IADSWSConsumer;
import nc.noumea.mairie.ws.ISirhEaeWSConsumer;
import nc.noumea.mairie.ws.dto.CampagneEaeDto;
import nc.noumea.mairie.ws.dto.EaeDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

@Service
public class AvancementsService implements IAvancementsService {

	private static List<Integer>	StatutsTerritoriaux	= Arrays.asList(18, 20);
	private static List<Integer>	StatutsCommunaux	= Arrays.asList(1, 2);

	private Logger					logger				= LoggerFactory.getLogger(AvancementsService.class);

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager			sirhEntityManager;

	@Autowired
	private IFichePosteService		fichePosteService;

	@Autowired
	private IAvancementRepository	avancementRepository;

	@Autowired
	private ISirhEaeWSConsumer		sirhEaeWSConsumer;

	@Autowired
	private IADSWSConsumer			adsWSConsumer;

	@Override
	public CommissionAvancementDto getCommissionsForCapAndCadreEmploi(int idCap, int idCadreEmploi, boolean isAvisShd, boolean capVDN,Integer idAgentConnecte) {

		CommissionAvancementDto result = new CommissionAvancementDto();
		Cap cap = getCap(idCap);

		if (cap == null)
			return result;

		List<Spgeng> corps = getCorpsForCadreEmploi(idCadreEmploi);
		if (corps == null)
			return result;

		for (Spgeng corp : corps) {
			List<AvancementFonctionnaire> avcts = getAvancementsForCommission(getAnnee(), cap.getIdCap(), corp.getCdgeng(), getStatutFromCap(cap),
					capVDN);

			if (avcts.size() == 0)
				continue;

			CommissionAvancementCorpsDto comCorps = createCommissionCorps(cap, corp, avcts, isAvisShd,idAgentConnecte);
			result.getCommissionsParCorps().add(comCorps);
		}

		return result;
	}

	@Override
	public ReturnMessageDto getAvancementsEaesForCapAndCadreEmploi(int idCap, int idCadreEmploi) {

		ReturnMessageDto result = new ReturnMessageDto();
		Cap cap = getCap(idCap);

		if (cap == null)
			return result;

		List<Spgeng> corps = getCorpsForCadreEmploi(idCadreEmploi);
		if (corps == null)
			return result;
		List<Integer> agentsIds = new ArrayList<Integer>();

		for (Spgeng corp : corps) {
			List<Integer> agents = getAgentsIdsForCommission(getAnnee(), cap.getIdCap(), corp.getCdgeng(), getStatutFromCap(cap));
			agentsIds.addAll(agents);
		}

		result = sirhEaeWSConsumer.getEaesGedIdsForAgents(agentsIds, getAnnee());

		return result;
	}

	@Override
	public List<AvancementFonctionnaire> getAvancementsForCommission(int annee, int idCap, String corps, List<Integer> codesCategories,
			boolean capVDN) {

		List<AvancementFonctionnaire> result = null;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT avct FROM AvancementFonctionnaire avct ");
		sb.append("INNER JOIN avct.grade AS spgradn ");
		sb.append("INNER JOIN spgradn.gradeGenerique AS spgeng ");
		sb.append("INNER JOIN spgeng.caps AS ca ");
		sb.append("JOIN FETCH avct.agent ");
		sb.append("JOIN FETCH avct.avisCap ");
		sb.append("JOIN FETCH avct.grade ");
		sb.append("where (avct.etat = 'C' or avct.etat = 'F') ");
		sb.append("and avct.codeCategporie IN (:codesCategories) ");
		sb.append("and avct.idMotifAvancement IN (4,6, 7) ");
		sb.append("and avct.anneeAvancement = :annee ");
		sb.append("and avct.agentVDN = :capVDN ");
		sb.append("and ca.idCap = :idCap ");
		sb.append("and spgeng.cdgeng = :cdgeng ");
		sb.append("order by avct.grade.gradeInitial desc , avct.agent.nomUsage asc ");

		TypedQuery<AvancementFonctionnaire> qA = sirhEntityManager.createQuery(sb.toString(), AvancementFonctionnaire.class);
		qA.setParameter("codesCategories", codesCategories);
		qA.setParameter("annee", annee);
		qA.setParameter("idCap", idCap);
		qA.setParameter("cdgeng", corps);
		qA.setParameter("capVDN", capVDN);

		result = qA.getResultList();

		return result;
	}

	public List<Integer> getAgentsIdsForCommission(int annee, int idCap, String corps, List<Integer> codesCategories) {

		List<Integer> result = null;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT avct.agent.idAgent FROM AvancementFonctionnaire avct ");
		sb.append("INNER JOIN avct.grade AS spgradn ");
		sb.append("INNER JOIN spgradn.gradeGenerique AS spgeng ");
		sb.append("INNER JOIN spgeng.caps AS ca ");
		sb.append("where (avct.etat = 'C' or avct.etat = 'F') ");
		sb.append("and avct.codeCategporie IN (:codesCategories) ");
		sb.append("and avct.idMotifAvancement IN (4,6, 7) ");
		sb.append("and avct.anneeAvancement = :annee ");
		sb.append("and ca.idCap = :idCap ");
		sb.append("and spgeng.cdgeng = :cdgeng");

		TypedQuery<Integer> qA = sirhEntityManager.createQuery(sb.toString(), Integer.class);
		qA.setParameter("codesCategories", codesCategories);
		qA.setParameter("annee", annee);
		qA.setParameter("idCap", idCap);
		qA.setParameter("cdgeng", corps);

		result = qA.getResultList();

		return result;
	}

	@Override
	public List<Spgeng> getCorpsForCadreEmploi(int idCadreEmploi) {

		List<Spgeng> result = null;

		TypedQuery<Spgeng> qCorps = sirhEntityManager.createNamedQuery("getSpgengFromCadreEmploi", Spgeng.class);
		qCorps.setParameter("idCadreEmploi", idCadreEmploi);
		result = qCorps.getResultList();

		if (result.size() == 0)
			result = null;

		return result;
	}

	public CommissionAvancementCorpsDto createCommissionCorps(Cap cap, Spgeng spgeng, List<AvancementFonctionnaire> avancements, boolean isAvisShd,
			Integer idAgentConnecte) {

		CommissionAvancementCorpsDto result = new CommissionAvancementCorpsDto(spgeng);
		result.setAvancementsDifferencies(new AvancementsDto(cap, spgeng, getAnnee()));
		result.setChangementClasses(new AvancementsDto(cap, spgeng, getAnnee()));

		for (AvancementFonctionnaire avct : avancements) {
			Integer valeurAvisEAE = null;
			// Si on veut l'avis SHD, on prend la valeur de l'avis de l'EAE
			if (isAvisShd) {
				CampagneEaeDto campagneEnCours = sirhEaeWSConsumer.getEaeCampagneOuverte();

				ReturnMessageDto eaeAgent = sirhEaeWSConsumer.findEaeByAgentAndYear(avct.getAgent().getIdAgent(), campagneEnCours.getAnnee());
				try {
					Integer idEae = Integer.valueOf(eaeAgent.getInfos().get(0));
					String avisSHD = sirhEaeWSConsumer.getAvisSHDEae(idEae).getInfos().get(0);
					switch (avisSHD) {
						case "Durée minimale":
							valeurAvisEAE = 1;
							break;
						case "Durée moyenne":
							valeurAvisEAE = 2;
							break;
						case "Durée maximale":
							valeurAvisEAE = 3;
							break;
						case "Favorable":
							valeurAvisEAE = 4;
							break;
						case "Défavorable":
							valeurAvisEAE = 5;
							break;
					}
				} catch (Exception e) {
					// aucune evaluation trouvé
				}
			}

			// redmine #19991 : on cherche le derniere avancement minimale
			AvancementItemDto aItem = new AvancementItemDto(avct, isAvisShd, valeurAvisEAE,
					avancementRepository.getDateAvancementsMinimaleAncienne(avct.getAgent().getIdAgent()));
			// on regarde si l'EAE est finalisé pour le considérer comme réalisé
			if (idAgentConnecte != null) {
				try {
					aItem.setEaeRealise(false);
					CampagneEaeDto campagneEnCours = sirhEaeWSConsumer.getEaeCampagneOuverte();
					ReturnMessageDto eaeAgent = sirhEaeWSConsumer.findEaeByAgentAndYear(avct.getAgent().getIdAgent(), campagneEnCours.getAnnee());
					Integer idEae = Integer.valueOf(eaeAgent.getInfos().get(0));
					EaeDto eaeCourant = sirhEaeWSConsumer.getDetailsEae(idAgentConnecte, idEae);
					if (eaeCourant.getEtat().equals("F") || eaeCourant.getEtat().equals("CO")) {
						aItem.setEaeRealise(true);
					} else {
						aItem.setEaeRealise(false);
					}
				} catch (Exception e) {
					aItem.setEaeRealise(false);
				}
			}
			switch (avct.getIdMotifAvancement()) {

				case 7:
					result.getAvancementsDifferencies().getAvancementsItems().add(aItem);
					break;

				case 6:
					result.getAvancementsDifferencies().getAvancementsItems().add(aItem);
					break;

				case 4:
					result.getChangementClasses().getAvancementsItems().add(aItem);
					break;
			}
		}
		result.getAvancementsDifferencies().updateNbAgents();
		result.getChangementClasses().updateNbAgents();

		return result;
	}

	public static List<Integer> getStatutFromCap(Cap cap) {

		if (cap.getTypeCap().equals("COMMUNAL"))
			return StatutsCommunaux;
		else
			return StatutsTerritoriaux;
	}

	@Override
	public Cap getCap(int idCap) {

		TypedQuery<Cap> qCap = sirhEntityManager.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class);
		qCap.setParameter("idCap", idCap);
		List<Cap> qR = qCap.getResultList();

		if (qR.size() == 0)
			return null;

		return qR.get(0);
	}

	@Override
	public ArreteListDto getArretesForUsers(String csvIdAgents, boolean isChangmentClasse, int year) throws ParseException {

		logger.debug("Entrée fonction getArretesForUsers");
		List<Integer> agentIds = new ArrayList<Integer>();

		for (String id : csvIdAgents.split(",")) {
			agentIds.add(Integer.valueOf(id));
		}

		// requete
		List<AvancementFonctionnaire> avcts = getAvancementsForArretes(agentIds, year);

		ArreteListDto arretes = new ArreteListDto();

		for (AvancementFonctionnaire avct : avcts) {

			Integer fpId = fichePosteService.getIdFichePostePrimaireAgentAffectationEnCours(avct.getAgent().getIdAgent(), new DateTime().toDate());
			FichePoste fp = fichePosteService.getFichePosteById(fpId);

			Spclas classeGrade = avct.getGradeNouveau() == null || avct.getGradeNouveau().getClasse() == null ? null
					: avct.getGradeNouveau().getClasse();
			Speche echelonGrade = avct.getGradeNouveau() == null || avct.getGradeNouveau().getEchelon() == null ? null
					: avct.getGradeNouveau().getEchelon();
			EntiteDto direction = null;
			EntiteDto service = null;
			if (fp != null && fp.getIdServiceADS() != null) {
				direction = adsWSConsumer.getAffichageDirection(fp.getIdServiceADS());
				service = adsWSConsumer.getEntiteByIdEntite(fp.getIdServiceADS());
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int dateFormatMairie = Integer.valueOf(sdf.format(new DateTime().toDate()));
			TypedQuery<Spcarr> qCarr = sirhEntityManager.createNamedQuery("getCurrentCarriere", Spcarr.class);
			qCarr.setParameter("nomatr", avct.getAgent().getNomatr());
			qCarr.setParameter("todayFormatMairie", dateFormatMairie);
			Spcarr carr = qCarr.getSingleResult();

			ArreteDto dto = new ArreteDto(avct, fp, carr, classeGrade, echelonGrade, direction, service);
			arretes.getArretes().add(dto);
		}

		return arretes;
	}

	@Override
	public List<AvancementFonctionnaire> getAvancementsForArretes(List<Integer> agentIds, int year) {

		List<AvancementFonctionnaire> result = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT avct FROM AvancementFonctionnaire avct ");
		sb.append("JOIN FETCH avct.agent ag ");
		sb.append("JOIN FETCH avct.gradeNouveau gr ");
		sb.append("JOIN FETCH gr.gradeGenerique gn ");
		sb.append("JOIN FETCH gn.filiere ");
		sb.append("JOIN FETCH gn.deliberationCommunale ");
		sb.append("JOIN FETCH gr.barem ");
		sb.append("where avct.anneeAvancement = :year ");
		sb.append("and ag.idAgent IN (:agentIds) ");

		TypedQuery<AvancementFonctionnaire> qA = sirhEntityManager.createQuery(sb.toString(), AvancementFonctionnaire.class);
		qA.setParameter("agentIds", agentIds);
		qA.setParameter("year", year);

		result = qA.getResultList();

		logger.debug("Entrée requete getAvancementsForArretes size ={}", result.size());

		return result;
	}

	public static int getAnnee() {
		return new DateTime().getYear();
	}

	@Override
	public ArreteListDto getArretesDetachesForUsers(String csvIdAgents, boolean isChangementClasse, int year) throws ParseException {

		List<Integer> agentIds = new ArrayList<Integer>();

		for (String id : csvIdAgents.split(",")) {
			agentIds.add(Integer.valueOf(id));
		}

		// requete
		List<AvancementDetache> avcts = getAvancementsDetacheForArretes(agentIds, year);

		ArreteListDto arretes = new ArreteListDto();

		for (AvancementDetache avct : avcts) {

			Integer fpId = fichePosteService.getIdFichePostePrimaireAgentAffectationEnCours(avct.getAgent().getIdAgent(), new DateTime().toDate());
			FichePoste fp = fichePosteService.getFichePosteById(fpId);
			Spclas classeGrade = avct.getGradeNouveau() == null || avct.getGradeNouveau().getClasse() == null ? null
					: avct.getGradeNouveau().getClasse();
			Speche echelonGrade = avct.getGradeNouveau() == null || avct.getGradeNouveau().getEchelon() == null ? null
					: avct.getGradeNouveau().getEchelon();

			EntiteDto direction = null;
			EntiteDto service = null;
			if (fp != null && fp.getIdServiceADS() != null) {
				direction = adsWSConsumer.getAffichageDirection(fp.getIdServiceADS());
				service = adsWSConsumer.getEntiteByIdEntite(fp.getIdServiceADS());
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int dateFormatMairie = Integer.valueOf(sdf.format(new DateTime().toDate()));
			TypedQuery<Spcarr> qCarr = sirhEntityManager.createNamedQuery("getCurrentCarriere", Spcarr.class);
			qCarr.setParameter("nomatr", avct.getAgent().getNomatr());
			qCarr.setParameter("todayFormatMairie", dateFormatMairie);
			Spcarr carr = qCarr.getSingleResult();

			ArreteDto dto = new ArreteDto(avct, fp, carr, classeGrade, echelonGrade, direction, service);
			arretes.getArretes().add(dto);
		}

		return arretes;
	}

	@Override
	public List<AvancementDetache> getAvancementsDetacheForArretes(List<Integer> agentIds, int year) {

		List<AvancementDetache> result = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT avct FROM AvancementDetache avct ");
		sb.append("JOIN FETCH avct.agent ag ");
		sb.append("JOIN FETCH avct.gradeNouveau gr ");
		sb.append("JOIN FETCH gr.gradeGenerique gn ");
		sb.append("JOIN FETCH gn.filiere ");
		sb.append("JOIN FETCH gr.barem ");
		sb.append("where avct.anneeAvancement = :year ");
		sb.append("and ag.idAgent IN (:agentIds) ");

		TypedQuery<AvancementDetache> qA = sirhEntityManager.createQuery(sb.toString(), AvancementDetache.class);
		qA.setParameter("agentIds", agentIds);
		qA.setParameter("year", year);

		result = qA.getResultList();

		return result;
	}

	@Override
	public AvancementEaeDto getAvancement(Integer idAgent, Integer anneeAvancement, boolean isFonctionnaire) {

		AvancementFonctionnaire avct = avancementRepository.getAvancement(idAgent, anneeAvancement, isFonctionnaire);

		if (null == avct)
			return null;

		AvancementEaeDto dto = new AvancementEaeDto(avct);
		if (null != dto.getGrade() && null != avct.getGrade() && null != avct.getGrade().getCdTava()
				&& !"".equals(avct.getGrade().getCdTava().trim())) {
			MotifAvct motifAvct = avancementRepository.getMotifAvct(new Integer(avct.getGrade().getCdTava().trim()));
			if (null != motifAvct) {
				dto.getGrade().setCodeMotifAvancement(motifAvct.getCodeAvct());
			}
		}
		return dto;
	}

	@Override
	public AvancementEaeDto getAvancementDetache(Integer idAgent, Integer anneeAvancement) {

		AvancementDetache avct = avancementRepository.getAvancementDetache(idAgent, anneeAvancement);

		if (null == avct)
			return null;

		AvancementEaeDto dto = new AvancementEaeDto(avct);
		if (null != dto.getGrade() && null != avct.getGrade() && null != avct.getGrade().getCdTava()
				&& !"".equals(avct.getGrade().getCdTava().trim())) {
			MotifAvct motifAvct = avancementRepository.getMotifAvct(new Integer(avct.getGrade().getCdTava().trim()));
			if (null != motifAvct) {
				dto.getGrade().setCodeMotifAvancement(motifAvct.getCodeAvct());
			}
		}
		return dto;
	}
}
