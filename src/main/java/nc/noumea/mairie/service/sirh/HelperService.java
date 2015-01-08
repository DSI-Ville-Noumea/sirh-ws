package nc.noumea.mairie.service.sirh;

import org.springframework.stereotype.Service;

@Service
public class HelperService {
	
	public Integer getMairieMatrFromIdAgent(Integer idAgent) {
		return idAgent - 9000000;
	}
	
}
