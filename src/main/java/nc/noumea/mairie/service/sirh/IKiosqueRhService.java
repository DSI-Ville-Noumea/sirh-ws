package nc.noumea.mairie.service.sirh;

import java.util.List;

import nc.noumea.mairie.model.bean.sirh.AccueilRh;
import nc.noumea.mairie.model.bean.sirh.AlerteRh;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

public interface IKiosqueRhService {

	List<AccueilRh> getListeAccueilRh();

	ReferentRh getReferentRH(Integer idServiceADS);

	ReturnMessageDto getAlerteRHByAgent(Integer idAgent);

	List<AlerteRh> getListeAlerte(boolean approABS, boolean approPTG, boolean operateurABS, boolean operateurPTG,
			boolean viseurABS);

}
