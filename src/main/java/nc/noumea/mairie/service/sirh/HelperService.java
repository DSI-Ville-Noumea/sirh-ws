package nc.noumea.mairie.service.sirh;

import org.springframework.stereotype.Service;

@Service
public class HelperService {
	
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
	
}
