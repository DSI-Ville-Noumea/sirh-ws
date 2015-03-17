package nc.noumea.mairie.service;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.ws.dto.EasyVistaDto;

public interface ISpprimService {

	public EasyVistaDto getChefServiceAgent(Affectation aff, EasyVistaDto result);
}
