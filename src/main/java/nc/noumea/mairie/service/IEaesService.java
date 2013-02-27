package nc.noumea.mairie.service;

import java.util.List;

public interface IEaesService {

	public List<String> getEaesGedIdsForAgents(List<Integer> agentIds, int annee);
}
