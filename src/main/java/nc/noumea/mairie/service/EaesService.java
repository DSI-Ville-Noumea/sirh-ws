package nc.noumea.mairie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
}
