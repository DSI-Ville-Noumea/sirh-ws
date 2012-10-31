package nc.noumea.mairie.model.service;

import java.util.List;

import nc.noumea.mairie.model.bean.Agent;

public interface IAgentService {

	/**
	 * Retourne l'état civil de l'agent passé en paramètre
	 * 
	 * @param id
	 *            : id de l'agent concerné
	 * @return
	 */
	public Agent getAgent(Integer id);

	public List<Agent> listAgentServiceSansAgent(String servi, Integer idAgent);

	public List<Agent> listAgentPlusieursServiceSansAgentSansSuperieur(Integer idAgent,Integer idAgentResponsable, List<String> listeCodeService);

	public Agent getSuperieurHierarchiqueAgent(Integer idAgent);

}
