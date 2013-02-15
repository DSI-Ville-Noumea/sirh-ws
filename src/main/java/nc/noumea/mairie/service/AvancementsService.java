package nc.noumea.mairie.service;

import java.util.List;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
public class AvancementsService implements IAvancementsService {

	@Override
	public CommissionAvancementDto getCommissionsForCapAndCadreEmploi(int idCap, int idCadreEmploi) {

		throw new NotImplementedException();
	}
	
	@Override
	public List<Agent> getAgentsForCommission(int idCap, int idCadreEmploi) {
		
		List<Agent> result = null;
		
		
		return result;
	}

}
