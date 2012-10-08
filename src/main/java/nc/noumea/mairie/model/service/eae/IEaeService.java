package nc.noumea.mairie.model.service.eae;

import java.util.List;

import nc.noumea.mairie.model.bean.eae.Eae;

public interface IEaeService {

	List<Eae> listEaesByAgentId(int agentId);
}
