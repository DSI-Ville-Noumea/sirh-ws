package nc.noumea.mairie.model.service.eae;

import java.util.List;

public interface IEaeService {

	List<Integer> listIdEaeByCampagneAndAgent(Integer idCampagneEae, Integer idAgent, List<String> listService);
}
