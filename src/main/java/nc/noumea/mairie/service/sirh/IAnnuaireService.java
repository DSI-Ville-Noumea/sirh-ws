package nc.noumea.mairie.service.sirh;

import java.util.List;

import nc.noumea.mairie.web.dto.AgentAnnuaireDto;

public interface IAnnuaireService {

	List<Integer> listAgentActiviteAnnuaire();

	AgentAnnuaireDto getInfoAgent(Integer idAgent);
}
