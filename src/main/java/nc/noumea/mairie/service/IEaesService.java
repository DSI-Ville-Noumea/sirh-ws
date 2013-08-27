package nc.noumea.mairie.service;

import java.util.List;

import nc.noumea.mairie.model.bean.eae.Eae;

public interface IEaesService {

	public List<String> getEaesGedIdsForAgents(List<Integer> agentIds, int annee);

	Integer compterlistIdEaeByCampagneAndAgent(Integer idCampagneEae,
			List<Integer> idAgents, Integer idAgent);

	Eae findEaeByAgentAndYear(int idAgent, String annee);
}
