package nc.noumea.mairie.model.service;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;

public interface IAgentService {

	/**
	 * Retourne l'état civil de l'agent passé en paramètre
	 * 
	 * @param id
	 *            : id de l'agent concerné
	 * @return Agent
	 */
	public Agent getAgent(Integer id);

	public List<Agent> listAgentServiceSansAgent(String servi, Integer idAgent);

	public List<Agent> listAgentPlusieursServiceSansAgentSansSuperieur(Integer idAgent, Integer idAgentResponsable,
			List<String> listeCodeService);

	public Agent getSuperieurHierarchiqueAgent(Integer idAgent);

	/**
	 * Retourne une liste d'agents (avec leur service)
	 * 
	 * @param servis
	 *            = liste services codes dans lesquels chercher
	 * @param date
	 *            = date à laquelle effectuer la recherche
	 * @param idAgent
	 *            = agents a restreindre
	 * @return
	 */
	public List<AgentWithServiceDto> listAgentsOfServices(List<String> servis, Date date, List<Integer> idAgents);

	public Agent findAgentWithName(Integer idAgent, String nom);

	public List<AgentWithServiceDto> listAgentsEnActivite(String nom, String codeService);

}
