package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.web.dto.JourDto;

public interface IHolidayService {
	
	boolean isHoliday(Date day);

	boolean isJourFerie(Date day);

	List<JourDto> getListeJoursFeries(Date dateDebut, Date dateFin);
}
