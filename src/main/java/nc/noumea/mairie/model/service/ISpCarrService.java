package nc.noumea.mairie.model.service;

import nc.noumea.mairie.web.dto.CarriereDto;

public interface ISpCarrService {

	CarriereDto getCarriereFonctionnaireAncienne(Integer noMatr);
	
	CarriereDto getCarriereActive(Integer noMatr);
}
