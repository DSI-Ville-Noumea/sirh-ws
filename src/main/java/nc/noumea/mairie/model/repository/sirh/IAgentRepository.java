package nc.noumea.mairie.model.repository.sirh;

import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Agent;

public interface IAgentRepository {

	Agent getAgentWithListNomatr(Integer noMatr);

	Agent getAgentEligibleEAESansAffectes(Integer noMatr);

	Agent getAgent(Integer idAgent);

	List<Agent> getListAgents(List<Integer> listIdsAgent);
}
