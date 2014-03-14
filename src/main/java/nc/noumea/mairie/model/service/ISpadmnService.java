package nc.noumea.mairie.model.service;

import nc.noumea.mairie.web.dto.PositionAdmAgentDto;

public interface ISpadmnService {

	boolean estPAActive(Integer nomatr, Integer dateDeb);
	
	PositionAdmAgentDto chercherPositionAdmAgentAncienne(Integer noMatr);
	
	PositionAdmAgentDto chercherPositionAdmAgentEnCours(Integer noMatr);
}
