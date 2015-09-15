package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Agent;

import org.springframework.stereotype.Repository;

@Repository
public class AgentRepository implements IAgentRepository {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public Agent getAgentWithListNomatr(Integer noMatr) {

		StringBuilder sb = new StringBuilder();

		sb.append("select ag.* from Agent ag where ag.nomatr = :noMatr ");

		Query query = sirhEntityManager.createNativeQuery(sb.toString(), Agent.class);
		query.setParameter("noMatr", noMatr);

		@SuppressWarnings("unchecked")
		List<Agent> list = query.getResultList();

		Agent agent = null;
		if (!list.isEmpty()) {
			agent = (Agent) list.get(0);
		}

		return agent;
	}

	@Override
	public Agent getAgentEligibleEAESansAffectes(Integer noMatr) {

		StringBuilder sb = new StringBuilder();

		sb.append("select a.* from Agent a ");
		sb.append(" inner join Affectation aff on a.id_agent = aff.id_agent ");
		sb.append(" inner join Fiche_Poste fp on aff.ID_FICHE_POSTE = fp.ID_FICHE_POSTE ");
		sb.append(" where aff.date_Debut_Aff <= :dateJourSIRH ");
		sb.append(" and (aff.date_Fin_Aff is null or aff.date_Fin_Aff >= :dateJourSIRH ) ");
		sb.append(" and a.nomatr = :noMatr ");

		Query query = sirhEntityManager.createNativeQuery(sb.toString(), Agent.class);

		query.setParameter("dateJourSIRH", new Date());
		query.setParameter("noMatr", noMatr);

		@SuppressWarnings("unchecked")
		List<Agent> list = query.getResultList();

		Agent agent = null;
		if (!list.isEmpty()) {
			agent = (Agent) list.get(0);
		}

		return agent;
	}

	@Override
	public Agent getAgent(Integer idAgent) {

		StringBuilder sb = new StringBuilder();

		sb.append("select a from Agent a ");
		sb.append(" WHERE a.idAgent = :idAgent ");

		Query query = sirhEntityManager.createQuery(sb.toString(), Agent.class);
		query.setParameter("idAgent", idAgent);

		@SuppressWarnings("unchecked")
		List<Agent> result = query.getResultList();
		if (null == result || result.size() == 0)
			return null;

		return result.get(0);
	}

	@Override
	public List<Agent> getListAgents(List<Integer> listIdsAgent) {

		TypedQuery<Agent> query = sirhEntityManager.createNamedQuery("getListAgents", Agent.class);

		query.setParameter("listIdsAgent", listIdsAgent);
		return query.getResultList();
	}
}
