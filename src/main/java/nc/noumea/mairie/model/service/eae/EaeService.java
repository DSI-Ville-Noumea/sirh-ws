package nc.noumea.mairie.model.service.eae;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

@Service
public class EaeService implements IEaeService {

	@PersistenceContext(unitName = "eaePersistenceUnit")
	private EntityManager eaeEntityManager;

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
}
