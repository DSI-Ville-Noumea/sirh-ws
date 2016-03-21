package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.web.dto.AgentGeneriqueDto;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.EntiteWithAgentWithServiceDto;

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

	public List<AgentWithServiceDto> listAgentsEnActiviteWithServiceAds(String nom, Integer idServiceADS);

	public EntiteDto getServiceAgent(Integer idAgent, Date dateDonnee);

	EntiteDto getDirectionOfAgent(Integer idAgent, Date dateDonnee);

	List<AgentGeneriqueDto> getListAgents(List<Integer> listIdsAgent);

	public List<AgentWithServiceDto> listAgentsOfServicesOldAffectation(List<Integer> idServiceADS, List<Integer> listIdsAgent);

	/**
	 * Retourne un arbre d entite avec les agents associés a chaque entite.
	 * On exclut l agent passe en parametre.
	 * On ajoute les agents en plus dans la liste en parametre listIdsAgentAInclure.
	 * 
	 * @param idServiceADS Integer L entite root que l on recherche
	 * @param idAgent Integer L agent a exclure de l arbre
	 * @param listIdsAgentAInclure List<Integer> Cette liste comprend la liste des agents que l on voir dans l arbre
	 * 		en plus des autres, MEME s ils ne font pas partis de l arbre des entites
	 * 		dans ce cas on rajoute les entites manquantes 
	 * @return EntiteWithAgentWithServiceDto un arbre d entite avec les agents associés a chaque entite
	 */
	EntiteWithAgentWithServiceDto getArbreServicesWithListAgentsByServiceWithoutAgentConnecteAndListAgentHorsService(
			Integer idServiceADS, Integer idAgent, List<Integer> listIdsAgentAInclure, Date dateJour);

	/**
	 * 
	 * Retourne un arbre d entite avec les agents associés a chaque entite.
	 * On exclut l agent passe en parametre
	 * 
	 * @param idServiceADS Integer L entite root que l on recherche
	 * @param idAgent Integer L agent a exclure de l arbre
	 * @return EntiteWithAgentWithServiceDto un arbre d entite avec les agents associés a chaque entite
	 */
	EntiteWithAgentWithServiceDto getArbreServicesWithListAgentsByServiceWithoutAgentConnecte(
			Integer idServiceADS, Integer idAgent, List<Integer> listIdsAgentAInclure, Date dateJour);

}
