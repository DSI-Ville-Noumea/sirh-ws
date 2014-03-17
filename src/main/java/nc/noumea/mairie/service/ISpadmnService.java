package nc.noumea.mairie.service;

import nc.noumea.mairie.web.dto.PositionAdmAgentDto;

public interface ISpadmnService {

	boolean estPAActive(Integer nomatr, Integer dateDeb);
	
	PositionAdmAgentDto chercherPositionAdmAgentAncienne(Integer noMatr);
	
	PositionAdmAgentDto chercherPositionAdmAgentEnCours(Integer noMatr);
}
