package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;

public interface IAgentRepository {

	Agent getAgentWithListNomatr(Integer noMatr);

	Agent getAgentEligibleEAESansAffectes(Integer noMatr);

	Agent getAgent(Integer idAgent);

	List<Agent> getListAgents(List<Integer> listIdsAgent);

	List<Object[]> getListAgentsEnActivite(String nom, Integer idServiceADS);

	/**
	 * Retoune une liste d affectation par rapport
	 * au parametres passes.
	 * 
	 * @param idServiceADS List<Integer>
	 * @param date Date NOT NULL
	 * @param idAgents List<Integer>
	 * @return List<Affectation>
	 */
	List<Affectation> getListAgentsByServicesAndListAgentsAndDate(
			List<Integer> idServiceADS, Date date, List<Integer> idAgents);

	/**
	 * Retoune la derniere affectation de chaque agent passe en parametre.
	 * 
	 * @param idServiceADS List<Integer>
	 * @param idAgents List<Integer>
	 * @return List<Affectation>
	 */
	List<Affectation> getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(List<Integer> idServiceADS, List<Integer> idAgents);
}
