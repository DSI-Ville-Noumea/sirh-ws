package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.web.dto.AgentGeneriqueDto;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.EntiteDto;

public interface IAgentService {

	/**
	 * Retourne l'état civil de l'agent passé en paramètre
	 * 
	 * @param id
	 *            : id de l'agent concerné
	 * @return Agent
	 */
	public Agent getAgent(Integer id);

	public List<Agent> listAgentServiceSansAgent(Integer idServiceADS, Integer idAgent);

	public List<Agent> listAgentPlusieursServiceSansAgentSansSuperieur(Integer idAgent, Integer idAgentResponsable, List<Integer> listeIdServiceADS);

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
	public List<AgentWithServiceDto> listAgentsOfServices(List<Integer> idServiceADS, Date date, List<Integer> idAgents);

	public Agent findAgentWithName(Integer idAgent, String nom);

	public List<AgentWithServiceDto> listAgentsEnActivite(String nom, Integer idServiceADS);

	public EntiteDto getServiceAgent(Integer idAgent, Date dateDonnee);

	EntiteDto getDirectionOfAgent(Integer idAgent, Date dateDonnee);

	List<AgentGeneriqueDto> getListAgents(List<Integer> listIdsAgent);

	public List<AgentWithServiceDto> listAgentsOfServicesOldAffectation(List<Integer> idServiceADS, List<Integer> listIdsAgent);

}
