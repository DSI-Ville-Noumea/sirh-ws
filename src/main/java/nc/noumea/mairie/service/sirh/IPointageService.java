package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.web.dto.BaseHorairePointageDto;

public interface IPointageService {

	List<Integer> getPrimePointagesByAgent(Integer idAgent, Date dateDebut, Date dateFin);

	List<BaseHorairePointageDto> getBaseHorairePointageByAgent(Integer idAgent, Date dateDebut, Date dateFin);

}
