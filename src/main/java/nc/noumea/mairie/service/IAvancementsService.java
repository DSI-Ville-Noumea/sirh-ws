package nc.noumea.mairie.service;

import java.util.List;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;


public interface IAvancementsService {

	public CommissionAvancementDto getCommissionsForCapAndCadreEmploi(int idCap, int idCadreEmploi);
	
	public  List<Agent> getAgentsForCommission(int idCap, int idCadreEmploi);
}
