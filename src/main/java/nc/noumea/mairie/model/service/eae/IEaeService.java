package nc.noumea.mairie.model.service.eae;

import java.util.List;

public interface IEaeService {

	Integer compterlistIdEaeByCampagneAndAgent(Integer idCampagneEae, List<Integer> idAgents, Integer idAgent);
}
