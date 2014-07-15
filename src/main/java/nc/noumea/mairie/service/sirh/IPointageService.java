package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

public interface IPointageService {

	List<Integer> getPrimePointagesByAgent(Integer idAgent, Date date);

}
