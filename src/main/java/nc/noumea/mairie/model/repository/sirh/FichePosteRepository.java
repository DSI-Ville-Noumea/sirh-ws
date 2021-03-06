package nc.noumea.mairie.model.repository.sirh;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spmtsr;
import nc.noumea.mairie.model.bean.Sppost;
import nc.noumea.mairie.model.bean.sirh.ActionFdpJob;
import nc.noumea.mairie.model.bean.sirh.Activite;
import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.AvantageNature;
import nc.noumea.mairie.model.bean.sirh.AvantageNatureFP;
import nc.noumea.mairie.model.bean.sirh.Competence;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.Delegation;
import nc.noumea.mairie.model.bean.sirh.DelegationFP;
import nc.noumea.mairie.model.bean.sirh.EnumStatutFichePoste;
import nc.noumea.mairie.model.bean.sirh.FeFp;
import nc.noumea.mairie.model.bean.sirh.FicheEmploi;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.NiveauEtude;
import nc.noumea.mairie.model.bean.sirh.NiveauEtudeFP;
import nc.noumea.mairie.model.bean.sirh.PrimePointageFP;
import nc.noumea.mairie.model.bean.sirh.RegIndemFP;
import nc.noumea.mairie.model.bean.sirh.RegimeIndemnitaire;
import nc.noumea.mairie.model.bean.sirh.StatutFichePoste;
import nc.noumea.mairie.tools.FichePosteTreeNode;
import nc.noumea.mairie.web.dto.GroupeInfoFichePosteDto;
import nc.noumea.mairie.web.dto.InfoFichePosteDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FichePosteRepository implements IFichePosteRepository {

	private Logger logger = LoggerFactory.getLogger(FichePosteRepository.class);

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@SuppressWarnings("unchecked")
	public Hashtable<Integer, FichePosteTreeNode> getAllFichePosteAndAffectedAgents(Date today,List<Integer> listStatutFDP) {

		Hashtable<Integer, FichePosteTreeNode> result = new Hashtable<Integer, FichePosteTreeNode>();

		String sqlQuery = "select distinct fp.ID_FICHE_POSTE, fp.ID_RESPONSABLE, case when aff.DATE_DEBUT_AFF <= :today AND (aff.DATE_FIN_AFF is null OR aff.DATE_FIN_AFF >= :today) then aff.ID_AGENT else null end as ID_AGENT "
				+ "from FICHE_POSTE fp left join AFFECTATION aff on fp.ID_FICHE_POSTE = aff.ID_FICHE_POSTE "
				+ "where fp.ID_STATUT_FP in (:listStatut) order by ID_FICHE_POSTE asc, ID_AGENT asc";
		Query q = sirhEntityManager.createNativeQuery(sqlQuery);
		q.setParameter("today", today);
		q.setParameter("listStatut", listStatutFDP);
		List<Object[]> l = q.getResultList();

		for (Object[] r : l) {
			Integer idFichePoste = (Integer) r[0];

			if (result.containsKey(idFichePoste))
				continue;

			FichePosteTreeNode node = new FichePosteTreeNode(idFichePoste, (Integer) r[1], (Integer) r[2]);
			result.put(idFichePoste, node);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TreeMap<Integer, FichePosteTreeNode> getAllFichePoste(Date today,List<Integer> listStatutFDP) {

		TreeMap<Integer, FichePosteTreeNode> result = new TreeMap<Integer, FichePosteTreeNode>();

		String sqlQuery = "select distinct fp.ID_FICHE_POSTE, fp.ID_RESPONSABLE, "
				+ "case when aff.DATE_DEBUT_AFF <= :today AND (aff.DATE_FIN_AFF is null OR aff.DATE_FIN_AFF >= :today) then aff.ID_AGENT else null end as ID_AGENT "
				+ "from FICHE_POSTE fp left outer join AFFECTATION aff on fp.ID_FICHE_POSTE = aff.ID_FICHE_POSTE and (aff.DATE_FIN_AFF is null OR aff.DATE_FIN_AFF >= :today) "
				+ "where fp.ID_STATUT_FP in (:listStatut) order by ID_FICHE_POSTE asc, ID_AGENT asc";
		Query q = sirhEntityManager.createNativeQuery(sqlQuery);
		q.setParameter("today", today);
		q.setParameter("listStatut", listStatutFDP);
		List<Object[]> l = q.getResultList();

		for (Object[] r : l) {
			Integer idFichePoste = (Integer) r[0];

			if (result.containsKey(idFichePoste))
				continue;

			FichePosteTreeNode node = new FichePosteTreeNode(idFichePoste, (Integer) r[1], (Integer) r[2]);
			result.put(idFichePoste, node);
		}

		return result;
	}

	@Override
	public List<FichePoste> getListFichePosteByIdServiceADSAndStatutFDP(List<Integer> listIdsEntite, List<Integer> listStatutFDP) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FichePoste fp where fp.idServiceADS in :listIdsServiceADS ");
		if (listStatutFDP != null && listStatutFDP.size() > 0) {
			sb.append(" and fp.statutFP.idStatutFp in (:listStatut) ");
		}
		sb.append("order by fp.superieurHierarchique.idFichePoste ");

		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(sb.toString(), FichePoste.class);
		query.setParameter("listIdsServiceADS", listIdsEntite);

		if (listStatutFDP != null && listStatutFDP.size() > 0) {
			query.setParameter("listStatut", listStatutFDP);
		}

		List<FichePoste> res = query.getResultList();

		return res;
	}

	@Override
	public List<FichePoste> getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(List<Integer> listIdsEntite, List<Integer> listStatutFDP) {

		logger.debug("getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation with parameter listIdsEntite = {}, listStatutFDP = {}", listIdsEntite, listStatutFDP);

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FichePoste fp ");
		sb.append("left join fetch fp.statutFP statut ");
		sb.append("left join fetch fp.titrePoste titre ");
		sb.append("left join fetch fp.reglementaire reg ");
		sb.append("left join fetch fp.gradePoste gradePoste ");
		sb.append("left join fetch gradePoste.barem ");
		sb.append("left join fetch gradePoste.classe ");
		sb.append("left join fetch gradePoste.echelon ");
		sb.append("left join fetch gradePoste.gradeGenerique ");
		sb.append("where fp.idServiceADS in :listIdsServiceADS ");
		if (listStatutFDP != null && listStatutFDP.size() > 0) {
			sb.append(" and fp.statutFP.idStatutFp in (:listStatut) ");
		}

		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(sb.toString(), FichePoste.class);
		query.setParameter("listIdsServiceADS", listIdsEntite);

		if (listStatutFDP != null && listStatutFDP.size() > 0) {
			query.setParameter("listStatut", listStatutFDP);
		}

		List<FichePoste> res = query.getResultList();

		return res;
	}

	@Override
	public FichePoste chercherFichePoste(Integer idFichePoste) {

		logger.debug("chercherFichePoste with parameter idFichePoste = {}", idFichePoste);

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FichePoste fp ");
		sb.append("left join fetch fp.statutFP statut ");
		sb.append("left join fetch fp.budgete budgete ");
		sb.append("left join fetch fp.reglementaire reglementaire ");
		sb.append("left join fetch fp.titrePoste titrePoste ");
		sb.append("where fp.idFichePoste=:idFichePoste ");
		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(sb.toString(), FichePoste.class);
		query.setParameter("idFichePoste", idFichePoste);
		FichePoste res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherFichePoste with parameter idFichePoste = {} and Exception = {}", idFichePoste, e.getMessage());
		}
		return res;
	}

	@Override
	public List<FichePoste> chercherListFichesPosteByListIdsFichePoste(List<Integer> listIdsFichePoste) {

		logger.debug("chercherListFichesPosteByListIdsFichePoste with parameter listIdsFichePoste = {}", listIdsFichePoste);

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FichePoste fp ");
		sb.append("left join fetch fp.statutFP statut ");
		sb.append("left join fetch fp.budgete budgete ");
		sb.append("left join fetch fp.reglementaire reglementaire ");
		sb.append("left join fetch fp.titrePoste titrePoste ");
		sb.append("left join fetch fp.gradePoste gradePoste ");
		sb.append("left join fetch gradePoste.barem ");
		sb.append("left join fetch gradePoste.classe ");
		sb.append("left join fetch gradePoste.echelon ");
		sb.append("left join fetch gradePoste.gradeGenerique ");
		sb.append("where fp.idFichePoste in :listIdsFichePoste ");

		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(sb.toString(), FichePoste.class);
		query.setParameter("listIdsFichePoste", listIdsFichePoste);

		return query.getResultList();
	}

	@Override
	public List<GroupeInfoFichePosteDto> getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(List<Integer> idEntiteEnfant) {

		List<GroupeInfoFichePosteDto> res = new ArrayList<GroupeInfoFichePosteDto>();

		StringBuilder sb = new StringBuilder();

		sb.append("select fp.titrePoste.libTitrePoste as titrePoste, count(fp.idFichePoste) as nbFiche, sum(fp.reglementaire.taux) as tauxETP ");
		sb.append(" from FichePoste fp where fp.idServiceADS in (:idServiceADS) ");
		// on ne prend que les FDP en statut "gelée" ou "validée
		// #16786
		sb.append(" and fp.statutFP.idStatutFp in (2,6) ");
		// on ne prend que les FDP reglementaire != non
		// #16786
		sb.append(" and fp.reglementaire.cdThor != 0 ");

		// on groupe par titrePoste
		// #16786
		sb.append(" group by fp.titrePoste.libTitrePoste ");

		Query query = sirhEntityManager.createQuery(sb.toString());
		query.setParameter("idServiceADS", idEntiteEnfant);

		@SuppressWarnings("unchecked")
		List<Object[]> result1 = query.getResultList();
		for (Object[] resultElement : result1) {
			String titrePoste = (String) resultElement[0];
			Long nbFDP = (Long) resultElement[1];
			Double tauxETP = (Double) resultElement[2];
			GroupeInfoFichePosteDto info = new GroupeInfoFichePosteDto();
			info.setNbFDP(nbFDP.intValue());
			info.setTauxETP(tauxETP);
			info.setTitreFDP(titrePoste);
			res.add(info);
		}

		return res;
	}

	@Override
	public List<InfoFichePosteDto> getListInfoFichePosteDtoByIdServiceADSAndTitrePoste(List<Integer> idEntiteEnfant, String titrePoste, Date today) {

		StringBuilder sb = new StringBuilder();

		sb.append("select fp.NUM_FP ");
		sb.append(" , ag.NOMATR, ag.NOM_USAGE, ag.PRENOM_USAGE, ag.ID_AGENT ");
		sb.append(" from FICHE_POSTE fp ");
		sb.append(" left outer join AFFECTATION aff on fp.ID_FICHE_POSTE = aff.ID_FICHE_POSTE and (aff.DATE_FIN_AFF is null OR aff.DATE_FIN_AFF >= :today) ");
		sb.append(" left outer join AGENT ag on aff.ID_AGENT = ag.ID_AGENT ");
		sb.append(" left outer join P_TITRE_POSTE tr on tr.ID_TITRE_POSTE = fp.ID_TITRE_POSTE ");
		sb.append(" where fp.id_Service_ADS in (:idServiceADS) ");
		// on ne prend que les FDP en statut "gelée" ou "validée
		// #16786
		sb.append(" and fp.id_Statut_Fp in (2,6) ");
		// on ne prend que les FDP reglementaire != non
		// #16786
		sb.append(" and fp.Id_CDTHOR_REG != 0 ");

		// on groupe par titrePoste
		// #16786
		sb.append(" and tr.LIB_TITRE_POSTE = :titrePoste ");

		Query query = sirhEntityManager.createNativeQuery(sb.toString());
		query.setParameter("idServiceADS", idEntiteEnfant);
		query.setParameter("titrePoste", titrePoste);
		query.setParameter("today", today);

		List<InfoFichePosteDto> result = new ArrayList<InfoFichePosteDto>();

		@SuppressWarnings("unchecked")
		List<Object[]> result1 = query.getResultList();
		for (Object[] resultElement : result1) {
			String numFP = (String) resultElement[0];
			Integer noMatr = (Integer) resultElement[1];
			String nomAgent = (String) resultElement[2];
			String prenomAgent = (String) resultElement[3];
			Integer idAgent = (Integer) resultElement[4];

			InfoFichePosteDto info = new InfoFichePosteDto();
			info.setIdAgent(idAgent);
			info.setPrenomAgent(prenomAgent);
			info.setNomAgent(nomAgent);
			info.setNumFP(numFP);
			info.setNoMatr(noMatr);
			FichePoste fichePoste = chercherFichePosteByNumFP(numFP);
			if (fichePoste != null && null != fichePoste.getGradePoste() && null != fichePoste.getGradePoste().getGradeGenerique()
					&& null != fichePoste.getGradePoste().getGradeGenerique().getCdcadr()) {
				info.setCategorie(fichePoste.getGradePoste().getGradeGenerique().getCdcadr().trim());
			} else {
				info.setCategorie("");
			}
			info.setGradePoste(fichePoste == null || fichePoste.getGradePoste() == null || fichePoste.getGradePoste().getGradeInitial() == null ? "" : fichePoste.getGradePoste().getGradeInitial()
					.trim());

			result.add(info);
		}

		return result;
	}

	@Override
	public void persisEntity(Object obj) {
		sirhEntityManager.persist(obj);
	}

	@Override
	public void removeEntity(Object obj) {
		sirhEntityManager.remove(obj);
	}

	@Override
	public List<FeFp> listerFEFPAvecFP(Integer idFichePoste) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FeFp fp where fp.id.idFichePoste = :idFichePoste ");

		TypedQuery<FeFp> query = sirhEntityManager.createQuery(sb.toString(), FeFp.class);
		query.setParameter("idFichePoste", idFichePoste);

		List<FeFp> res = query.getResultList();

		return res;
	}

	@Override
	public List<NiveauEtudeFP> listerNiveauEtudeFPAvecFP(Integer idFichePoste) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from NiveauEtudeFP fp where fp.niveauEtudeFPPK.idFichePoste = :idFichePoste ");

		TypedQuery<NiveauEtudeFP> query = sirhEntityManager.createQuery(sb.toString(), NiveauEtudeFP.class);
		query.setParameter("idFichePoste", idFichePoste);

		List<NiveauEtudeFP> res = query.getResultList();

		return res;
	}

	@Override
	public List<ActiviteFP> listerActiviteFPAvecFP(Integer idFichePoste) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from ActiviteFP fp where fp.activiteFPPK.idFichePoste = :idFichePoste ");

		TypedQuery<ActiviteFP> query = sirhEntityManager.createQuery(sb.toString(), ActiviteFP.class);
		query.setParameter("idFichePoste", idFichePoste);

		List<ActiviteFP> res = query.getResultList();

		return res;
	}

	@Override
	public List<CompetenceFP> listerCompetenceFPAvecFP(Integer idFichePoste) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from CompetenceFP fp where fp.competenceFPPK.idFichePoste = :idFichePoste ");

		TypedQuery<CompetenceFP> query = sirhEntityManager.createQuery(sb.toString(), CompetenceFP.class);
		query.setParameter("idFichePoste", idFichePoste);

		List<CompetenceFP> res = query.getResultList();

		return res;
	}

	@Override
	public List<AvantageNatureFP> listerAvantageNatureFPAvecFP(Integer idFichePoste) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from AvantageNatureFP fp where fp.avantageNaturePK.idFichePoste = :idFichePoste ");

		TypedQuery<AvantageNatureFP> query = sirhEntityManager.createQuery(sb.toString(), AvantageNatureFP.class);
		query.setParameter("idFichePoste", idFichePoste);

		List<AvantageNatureFP> res = query.getResultList();

		return res;
	}

	@Override
	public List<DelegationFP> listerDelegationFPAvecFP(Integer idFichePoste) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from DelegationFP fp where fp.delegationFPPK.idFichePoste = :idFichePoste ");

		TypedQuery<DelegationFP> query = sirhEntityManager.createQuery(sb.toString(), DelegationFP.class);
		query.setParameter("idFichePoste", idFichePoste);

		List<DelegationFP> res = query.getResultList();

		return res;
	}

	@Override
	public List<PrimePointageFP> listerPrimePointageFP(Integer idFichePoste) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from PrimePointageFP fp where fp.fichePoste.idFichePoste = :idFichePoste ");

		TypedQuery<PrimePointageFP> query = sirhEntityManager.createQuery(sb.toString(), PrimePointageFP.class);
		query.setParameter("idFichePoste", idFichePoste);

		List<PrimePointageFP> res = query.getResultList();

		return res;
	}

	@Override
	public List<RegIndemFP> listerRegIndemFPFPAvecFP(Integer idFichePoste) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from RegIndemFP fp where fp.regIndemFPPK.idFichePoste = :idFichePoste ");

		TypedQuery<RegIndemFP> query = sirhEntityManager.createQuery(sb.toString(), RegIndemFP.class);
		query.setParameter("idFichePoste", idFichePoste);

		List<RegIndemFP> res = query.getResultList();

		return res;
	}

	@Override
	public Sppost chercherSppost(Integer poanne, Integer ponuor) {

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from Sppost fp where fp.id.poanne = :poanne and fp.id.ponuor = :ponuor ");

		TypedQuery<Sppost> query = sirhEntityManager.createQuery(sb.toString(), Sppost.class);
		query.setParameter("poanne", poanne);
		query.setParameter("ponuor", ponuor);

		Sppost res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherSppost with parameter poanne = {} and ponuor = {} and Exception = {}", poanne, ponuor, e.getMessage());
		}
		return res;
	}

	@Override
	public void flush() {
		sirhEntityManager.flush();

	}

	@Override
	public String chercherDernierNumFichePosteByYear(Integer annee) {

		StringBuilder sb = new StringBuilder();
		sb.append("select fp.NUM_FP from Fiche_Poste fp where fp.ANNEE_CREATION = :annee ");
		sb.append("order by cast(substr(fp.NUM_FP,1,4) as Integer) desc,cast(substr(fp.NUM_FP,6) as Integer) desc");

		// #34482 : on passe en native query sinon ca plante
		Query query = sirhEntityManager.createNativeQuery(sb.toString());
		query.setParameter("annee", annee);

		String res = null;
		try {
			@SuppressWarnings("unchecked")
			List<String> resTemp = query.getResultList();
			// comme on a ordonnée en ordre decroissant on ne renvoi que le
			// 1er enregistrement
			if (resTemp.size() > 0) {
				return resTemp.get(0);
			}
		} catch (Exception e) {
			logger.debug("Exception chercherDernierNumFichePosteByYear with parameter annee = {} and Exception = {}", annee, e.getMessage());
		}
		return res;
	}

	@Override
	public FeFp chercherFEFPAvecFP(Integer idFichePoste, Integer isPrimaire) {

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FeFp fp where fp.id.idFichePoste = :idFichePoste and fp.fePrimaire = :fePrimaire ");

		TypedQuery<FeFp> query = sirhEntityManager.createQuery(sb.toString(), FeFp.class);
		query.setParameter("idFichePoste", idFichePoste);
		query.setParameter("fePrimaire", isPrimaire);

		FeFp res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherFEFPAvecFP with parameter idFichePoste = {} and isPrimaire = {} and Exception = {}", idFichePoste, isPrimaire, e.getMessage());
		}
		return res;
	}

	@Override
	public FicheEmploi chercherFicheEmploi(Integer idFicheEmploi) {

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FicheEmploi fp where fp.idFicheEmploi = :idFicheEmploi ");

		TypedQuery<FicheEmploi> query = sirhEntityManager.createQuery(sb.toString(), FicheEmploi.class);
		query.setParameter("idFicheEmploi", idFicheEmploi);

		FicheEmploi res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherFicheEmploi with parameter idFicheEmploi = {} and Exception = {}", idFicheEmploi, e.getMessage());
		}
		return res;
	}

	@Override
	public NiveauEtude chercherNiveauEtude(Integer idNiveauEtude) {

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from NiveauEtude fp where fp.idNiveauEtude = :idNiveauEtude ");

		TypedQuery<NiveauEtude> query = sirhEntityManager.createQuery(sb.toString(), NiveauEtude.class);
		query.setParameter("idNiveauEtude", idNiveauEtude);

		NiveauEtude res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherNiveauEtude with parameter idNiveauEtude = {} and Exception = {}", idNiveauEtude, e.getMessage());
		}
		return res;
	}

	@Override
	public Activite chercherActivite(Integer idActivite) {

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from Activite fp where fp.idActivite = :idActivite ");

		TypedQuery<Activite> query = sirhEntityManager.createQuery(sb.toString(), Activite.class);
		query.setParameter("idActivite", idActivite);

		Activite res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherActivite with parameter idActivite = {} and Exception = {}", idActivite, e.getMessage());
		}
		return res;
	}

	@Override
	public Competence chercherCompetence(Integer idCompetence) {

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from Competence fp where fp.idCompetence = :idCompetence ");

		TypedQuery<Competence> query = sirhEntityManager.createQuery(sb.toString(), Competence.class);
		query.setParameter("idCompetence", idCompetence);

		Competence res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherCompetence with parameter idCompetence = {} and Exception = {}", idCompetence, e.getMessage());
		}
		return res;
	}

	@Override
	public AvantageNature chercherAvantageNature(Integer idAvantage) {

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from AvantageNature fp where fp.idAvantage = :idAvantage ");

		TypedQuery<AvantageNature> query = sirhEntityManager.createQuery(sb.toString(), AvantageNature.class);
		query.setParameter("idAvantage", idAvantage);

		AvantageNature res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherAvantageNature with parameter idAvantage = {} and Exception = {}", idAvantage, e.getMessage());
		}
		return res;
	}

	@Override
	public Delegation chercherDelegation(Integer idDelegation) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from Delegation fp where fp.idDelegation = :idDelegation ");

		TypedQuery<Delegation> query = sirhEntityManager.createQuery(sb.toString(), Delegation.class);
		query.setParameter("idDelegation", idDelegation);
		Delegation res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherDelegation with parameter idDelegation = {} and Exception = {}", idDelegation, e.getMessage());
		}
		return res;
	}

	@Override
	public RegimeIndemnitaire chercherRegimeIndemnitaire(Integer idRegime) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from RegimeIndemnitaire fp where fp.idRegimeIndemnitaire = :idRegimeIndemnitaire ");

		TypedQuery<RegimeIndemnitaire> query = sirhEntityManager.createQuery(sb.toString(), RegimeIndemnitaire.class);
		query.setParameter("idRegimeIndemnitaire", idRegime);
		RegimeIndemnitaire res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherRegimeIndemnitaire with parameter idRegime = {} and Exception = {}", idRegime, e.getMessage());
		}
		return res;
	}

	@Override
	public StatutFichePoste chercherStatutFPByIdStatut(Integer idStatut) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from StatutFichePoste fp where fp.idStatutFp = :idStatutFp ");

		TypedQuery<StatutFichePoste> query = sirhEntityManager.createQuery(sb.toString(), StatutFichePoste.class);
		query.setParameter("idStatutFp", idStatut);
		StatutFichePoste res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherStatutFPByIdStatut with parameter idStatut = {} and Exception = {}", idStatut, e.getMessage());
		}
		return res;
	}

	@Override
	public FichePoste chercherFichePosteByNumFP(String numFP) {

		logger.debug("chercherFichePosteByNumFP with parameter numFP = {}", numFP);

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FichePoste fp ");
		sb.append("left join fetch fp.statutFP statut ");
		sb.append("left join fetch fp.budgete budgete ");
		sb.append("left join fetch fp.reglementaire reglementaire ");
		sb.append("left join fetch fp.titrePoste titrePoste ");
		sb.append("where fp.numFP=:numFP ");

		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(sb.toString(), FichePoste.class);
		query.setParameter("numFP", numFP);

		FichePoste res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherFichePosteByNumFP with parameter numFP = {} and Exception = {}", numFP, e.getMessage());
		}

		return res;
	}

	@Override
	public ActionFdpJob chercherActionFDPParentDuplication(Integer idFichePoste) {

		StringBuilder sb = new StringBuilder();
		sb.append("select fp from ActionFdpJob fp ");
		sb.append("where fp.idFichePoste=:idFichePoste ");
		sb.append("and fp.typeAction='DUPLICATION' ");
		sb.append("order by fp.idActionFdpJob desc ");

		TypedQuery<ActionFdpJob> query = sirhEntityManager.createQuery(sb.toString(), ActionFdpJob.class);
		query.setParameter("idFichePoste", idFichePoste);

		ActionFdpJob res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Exception chercherActionFDPParentDuplication with parameter idFichePoste = {} and Exception = {}", idFichePoste, e.getMessage());
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getListFichePosteAffecteeInPresentAndFutureByIdServiceADS(Integer idEntite) {

		StringBuilder sb = new StringBuilder();

		sb.append("select fp.id_fiche_poste from Fiche_Poste fp ");
		sb.append(" inner join Affectation aff on fp.id_fiche_poste = aff.id_fiche_poste ");
		sb.append(" where ( aff.date_Fin_Aff is null or aff.date_Fin_Aff >= :dateJourSIRH ) ");
		sb.append(" and fp.ID_SERVICE_ADS = :idEntite ");

		Query query = sirhEntityManager.createNativeQuery(sb.toString());

		query.setParameter("dateJourSIRH", new Date());
		query.setParameter("idEntite", idEntite);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getListFichePosteNonAffecteeEtPasInactiveByIdServiceADS(Integer idEntite) {

		StringBuilder sb = new StringBuilder();

		sb.append("select fp.id_fiche_poste from Fiche_Poste fp where fp.ID_SERVICE_ADS = :idEntite and fp.ID_STATUT_FP <> :inactif ");
		sb.append(" and fp.id_fiche_poste not in ( ");
			sb.append(" select fp2.id_fiche_poste from Fiche_Poste fp2 inner join Affectation aff on fp2.id_fiche_poste = aff.id_fiche_poste ");
			sb.append(" where aff.date_Debut_Aff <= :dateJourSIRH ");
			sb.append(" and (aff.date_Fin_Aff is null or aff.date_Fin_Aff >= :dateJourSIRH ) ");
			sb.append(" and fp2.ID_SERVICE_ADS = :idEntite ) ");

		Query query = sirhEntityManager.createNativeQuery(sb.toString());

		query.setParameter("dateJourSIRH", new Date());
		query.setParameter("idEntite", idEntite);
		query.setParameter("inactif", EnumStatutFichePoste.INACTIVE.getId());

		return query.getResultList();
	}

	@Override
	public void modifierSpmtsrWithId(Spmtsr spmtsr) {

		String jpql = "update spmtsr set SERVI= :servi  where nomatr = :nomatr and datdeb = :datdeb ";
		Query q = sirhEntityManager.createNativeQuery(jpql);
		q.setParameter("nomatr", spmtsr.getId().getNomatr());
		q.setParameter("servi", spmtsr.getId().getServi());
		q.setParameter("datdeb", spmtsr.getId().getDatdeb());

		@SuppressWarnings("unused")
		int res = q.executeUpdate();

	}

}
