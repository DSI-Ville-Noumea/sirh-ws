package nc.noumea.mairie.model.service;

import java.util.List;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.FichePoste;

public interface IAgentService {
	
	/**
	 * Retourne l'état civil de l'agent passé en paramètre
	 * @param id : id de l'agent concerné
	 * @return
	 */
	public Agent getAgent(Integer id);
	
	/**
	 * Retourne l'agent passé en paramètre pour son affectationCourante
	 * @param id : id de l'agent concerné
	 * @return
	 */
	public Agent getAgentAffectationCourante(Integer id);

	
	/**
	 * Retourne les fiches de poste enfants de l'agent passé en paramètre
	 * @param id : id de l'agent concerné
	 * @return
	 */
	public List<FichePoste> getFichePosteEnfant(Integer id);
}
