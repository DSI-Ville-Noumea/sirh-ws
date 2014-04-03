package nc.noumea.mairie.service.eae;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.eae.Eae;

import org.springframework.stereotype.Service;

@Service
public class EaesService implements IEaesService {

	@PersistenceContext(unitName = "eaePersistenceUnit")
	private EntityManager eaeEntityManager;

	@Override
	public List<String> getEaesGedIdsForAgents(List<Integer> agentIds, int annee) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT fin.idGedDocument ");
		sb.append("FROM EaeFinalisation fin ");
		sb.append("INNER JOIN fin.eae AS e ");
		sb.append("INNER JOIN e.eaeCampagne AS c ");
		sb.append("INNER JOIN e.eaeEvalue AS ev ");
		sb.append("WHERE c.annee = :annee ");
		sb.append("AND ev.idAgent IN :agentIds");

		TypedQuery<String> qEaesIds = eaeEntityManager.createQuery(sb.toString(), String.class);
		qEaesIds.setParameter("annee", annee);
		qEaesIds.setParameter("agentIds", agentIds.size() == 0 ? null : agentIds);

		List<String> result = qEaesIds.getResultList();

		return result;
	}

	@Override
	public Integer compterlistIdEaeByCampagneAndAgent(Integer idCampagneEae, List<Integer> idAgents, Integer idAgent) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(e) from Eae e ");
		sb.append("JOIN e.eaeEvalue ");
		sb.append("where (e.eaeEvalue.idAgent in :idAgents ");
		sb.append("OR e.idAgentDelegataire = :idAgent ");
		sb.append("OR e.idEae in (select eva.eae.idEae from EaeEvaluateur eva where eva.idAgent = :idAgent) ) ");
		sb.append("and e.eaeCampagne.idCampagneEae = :idCampagne");

		TypedQuery<Long> eaeQuery = eaeEntityManager.createQuery(sb.toString(), Long.class);
		eaeQuery.setParameter("idAgents", idAgents.size() == 0 ? null : idAgents);
		eaeQuery.setParameter("idCampagne", idCampagneEae);
		eaeQuery.setParameter("idAgent", idAgent);

		Long nbRes = eaeQuery.getSingleResult();

		return nbRes.intValue();
	}

	@Override
	public Eae findEaeByAgentAndYear(int idAgent, String annee) {
		// Query
		StringBuilder sb = new StringBuilder();
		sb.append("select e.* from eae e ");
		sb.append("inner join eae_campagne_eae c on e.id_campagne_eae=c.id_campagne_eae ");
		sb.append("inner join eae_evalue ev on e.id_eae=ev.id_eae ");
		sb.append("where c.annee=:annee and ev.id_agent=:idAgent and e.etat != 'S' ");

		Query q = eaeEntityManager.createNativeQuery(sb.toString(), Eae.class);
		q.setParameter("idAgent", idAgent);
		q.setParameter("annee", annee);

		@SuppressWarnings("unchecked")
		List<Eae> result = q.getResultList();

		if (result.isEmpty())
			return null;
		else
			return result.get(0);
	}
}
