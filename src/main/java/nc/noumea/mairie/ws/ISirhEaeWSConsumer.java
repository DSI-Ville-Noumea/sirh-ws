package nc.noumea.mairie.ws;

import java.util.List;

import nc.noumea.mairie.ws.dto.CampagneEaeDto;
import nc.noumea.mairie.ws.dto.EaeDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

public interface ISirhEaeWSConsumer {

	CampagneEaeDto getEaeCampagneOuverte();

	ReturnMessageDto getAvisSHDEae(Integer idEae);

	ReturnMessageDto findEaeByAgentAndYear(Integer idAgent, Integer annee);

	ReturnMessageDto compterlistIdEaeByCampagneAndAgent(Integer idCampagneEae, List<Integer> listAgentsId, Integer idAgent);

	ReturnMessageDto getEaesGedIdsForAgents(List<Integer> agentsIds, int annee);

	EaeDto getDetailsEae(Integer idAgentSirh, Integer idEae);
}
