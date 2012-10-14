package nc.noumea.mairie.model.service;

import java.util.List;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.eae.Eae;
import nc.noumea.mairie.model.bean.eae.EaeEvaluateur;

import org.json.simple.JSONObject;

public interface IAgentService {

	/**
	 * Retourne l'état civil de l'agent passé en paramètre
	 * 
	 * @param id
	 *            : id de l'agent concerné
	 * @return
	 */
	public Agent getAgent(Integer id);

	public List<Agent> getAgentService(String servi, Integer idAgent,
			Integer idResponsable);

	public List<Agent> getAgentService(String servi, Integer idAgent);

	public JSONObject removeAll(JSONObject json);
	
	Eae fillEaeWithAgents(Eae eaeToFill);
	
	EaeEvaluateur fillEaeEvaluateurWithAgent(EaeEvaluateur eaeEvaluateurToFill);
}
