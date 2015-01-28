package nc.noumea.mairie.service;

import nc.noumea.mairie.web.dto.CarriereDto;

public interface ISpCarrService {

	CarriereDto getCarriereFonctionnaireAncienne(Integer noMatr);

	CarriereDto getCarriereActive(Integer noMatr);

	CarriereDto getCarriereAvecGrade(Integer noMatr, String codeGrade, Integer categorie);
}
