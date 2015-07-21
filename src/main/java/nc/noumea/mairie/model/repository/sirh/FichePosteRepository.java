package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.tools.FichePosteTreeNode;

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
	public List<FichePoste> getListFichePosteByIdServiceADSAndStatutFDP(Integer idEntite, List<Integer> listStatutFDP) {
		StringBuilder sb = new StringBuilder();
		sb.append("select fp from FichePoste fp where fp.idServiceADS=:idServiceADS ");
		if (listStatutFDP != null && listStatutFDP.size() > 0) {
			sb.append(" and fp.statutFP.idStatutFp in (:listStatut) ");
		}

		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(sb.toString(), FichePoste.class);
		query.setParameter("idServiceADS", idEntite);
		if (listStatutFDP != null && listStatutFDP.size() > 0) {
			query.setParameter("listStatut", listStatutFDP);
		}

		List<FichePoste> res = query.getResultList();

		return res;
	}

	@Override
	public void persisEntity(Object obj) {
		sirhEntityManager.persist(obj);
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
}
