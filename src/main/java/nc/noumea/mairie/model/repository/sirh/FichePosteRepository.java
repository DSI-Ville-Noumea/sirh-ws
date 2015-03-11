package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.TitrePoste;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<TitrePoste> getListeTitrePosteChefService() {
		String sql = "select t.* from TitrePoste t where t.libTitrePoste like :lib";

		Query query = sirhEntityManager.createNativeQuery(sql, TitrePoste.class);
		query.setParameter("lib", "%CHEF%SERVICE%");

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TitrePoste> getListeTitrePosteDirecteur() {
		String sql = "select t.* from TitrePoste t where t.libTitrePoste like :lib or t.libTitrePoste like :lib2";

		Query query = sirhEntityManager.createNativeQuery(sql, TitrePoste.class);
		query.setParameter("lib", "%DIRECTEUR%");
		query.setParameter("lib2", "%DIRECTRICE%");

		return query.getResultList();
	}
}
