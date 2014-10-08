package nc.noumea.mairie.service.eae;

import java.util.List;

import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.AutreAdministrationAgentDto;
import nc.noumea.mairie.web.dto.CalculEaeInfosDto;
import nc.noumea.mairie.web.dto.DateAvctDto;
import nc.noumea.mairie.web.dto.DiplomeDto;

public interface ICalculEaeService {

	CalculEaeInfosDto getAffectationActiveByAgent(Integer idAgent, Integer anneeFormation);

	List<CalculEaeInfosDto> getListeAffectationsAgentAvecService(Integer idAgent, String idService);

	List<CalculEaeInfosDto> getListeAffectationsAgentAvecFP(Integer idAgent, Integer idFichePoste);

	List<AgentDto> getListeAgentEligibleEAESansAffectes();

	List<AgentDto> getListeAgentEligibleEAEAffectes();

	AutreAdministrationAgentDto chercherAutreAdministrationAgentAncienne(Integer idAgent, boolean isFonctionnaire);

	List<AutreAdministrationAgentDto> getListeAutreAdministrationAgent(Integer idAgent);

	List<DiplomeDto> getListDiplomeDto(Integer idAgent);

	DateAvctDto getCalculDateAvancement(Integer idAgent);

}
