package nc.noumea.mairie.service.sirh;

import java.util.Date;

import nc.noumea.mairie.web.dto.RefTypeSaisiCongeAnnuelDto;

public interface IAbsenceService {

	RefTypeSaisiCongeAnnuelDto getBaseHoraireAbsenceByAgent(Integer idAgent, Date date);

}
