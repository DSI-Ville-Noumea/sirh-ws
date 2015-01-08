package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.web.dto.InfosAlimAutoCongesAnnuelsDto;
import nc.noumea.mairie.web.dto.RefTypeSaisiCongeAnnuelDto;

public interface IAbsenceService {

	RefTypeSaisiCongeAnnuelDto getBaseHoraireAbsenceByAgent(Integer idAgent, Date date); 

	List<InfosAlimAutoCongesAnnuelsDto> getListPAPourAlimAutoCongesAnnuels(
			Integer idAgent, Date dateDebut, Date dateFin);

}
