package nc.noumea.mairie.model.repository.sirh;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.AvantageNatureFP;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.DelegationFP;
import nc.noumea.mairie.model.bean.sirh.FeFp;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.NiveauEtudeFP;
import nc.noumea.mairie.model.bean.sirh.PrimePointageFP;
import nc.noumea.mairie.model.bean.sirh.RegIndemFP;
import nc.noumea.mairie.tools.FichePosteTreeNode;
import nc.noumea.mairie.web.dto.InfoFichePosteDto;

import org.springframework.stereotype.Repository;

@Repository
public class FichePosteRepository implements IFichePosteRepository {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@SuppressWarnings("unchecked")
	public Hashtable<Integer, FichePosteTreeNode> getAllFichePosteAndAffectedAgents(Date today) {

		Hashtable<Integer, FichePosteTreeNode> result = new Hashtable<Integer, FichePosteTreeNode>();

		String sqlQuery = "select distinct fp.ID_FICHE_POSTE, fp.ID_RESPONSABLE, case when aff.DATE_DEBUT_AFF <= :today AND (aff.DATE_FIN_AFF is null OR aff.DATE_FIN_AFF >= :today) then aff.ID_AGENT else null end as ID_AGENT from FICHE_POSTE fp left join AFFECTATION aff on fp.ID_FICHE_POSTE = aff.ID_FICHE_POSTE where fp.ID_STATUT_FP = 2 order by ID_FICHE_POSTE asc, ID_AGENT asc";
		Query q = sirhEntityManager.createNativeQuery(sqlQuery);
		q.setParameter("today", today);
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
	public List<FichePoste> getListFichePosteByIdServiceADSAndStatutFDP(List<Integer> listIdsEntite,
			List<Integer> listStatutFDP) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FichePoste fp where fp.idServiceADS in :listIdsServiceADS ");
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
	public List<FichePoste> getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(
			List<Integer> listIdsEntite, List<Integer> listStatutFDP) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FichePoste fp " + "left join fetch fp.statutFP statut "
				+ "left join fetch fp.titrePoste titre " + "left join fetch fp.reglementaire reg "
				+ "left join fetch fp.gradePoste gradePoste " + "left join fetch gradePoste.barem "
				+ "left join fetch gradePoste.classe " + "left join fetch gradePoste.echelon "
				+ "left join fetch gradePoste.gradeGenerique " + "where fp.idServiceADS in :listIdsServiceADS ");
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
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FichePoste fp where fp.idFichePoste=:idFichePoste ");

		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(sb.toString(), FichePoste.class);
		query.setParameter("idFichePoste", idFichePoste);
		FichePoste res = null;
		try {
			res = query.getSingleResult();
		} catch (Exception e) {

		}
		return res;
	}

	@Override
	public List<InfoFichePosteDto> getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(
			List<Integer> idEntiteEnfant) {

		List<InfoFichePosteDto> res = new ArrayList<InfoFichePosteDto>();

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
			InfoFichePosteDto info = new InfoFichePosteDto();
			info.setNbFDP(nbFDP.intValue());
			info.setTauxETP(tauxETP);
			info.setTitreFDP(titrePoste);
			res.add(info);
		}

		return res;
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
}
