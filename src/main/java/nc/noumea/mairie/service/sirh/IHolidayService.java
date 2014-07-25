package nc.noumea.mairie.service.sirh;

import java.util.Date;

public interface IHolidayService {
	
	boolean isHoliday(Date day);

	boolean isJourFerie(Date day);
}
