package nc.noumea.mairie.service.sirh;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class HelperService {

	private static SimpleDateFormat mairieDateFormat = new SimpleDateFormat("yyyyMMdd");
	
	public Integer getMairieMatrFromIdAgent(Integer idAgent) {
		return idAgent - 9000000;
	}

	public Integer remanieIdAgent(Integer idAgent) {
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2, idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			return idAgent;
		}
		return new Integer(newIdAgent);
	}
	
	public Integer getIntegerDateMairieFromDate(Date date) {
		return date == null ? 0 : Integer.parseInt(mairieDateFormat.format(date));
	}
	
}
