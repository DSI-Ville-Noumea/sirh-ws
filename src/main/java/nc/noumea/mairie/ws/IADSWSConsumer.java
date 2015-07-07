package nc.noumea.mairie.ws;

import java.util.List;

import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.ReferenceDto;

public interface IADSWSConsumer {

	EntiteDto getEntiteByIdEntite(Integer idEntite);

	EntiteDto getDirectionPourEAE(EntiteDto entiteDto);

	EntiteDto getSection(Integer idEntite);

	EntiteDto getEntiteByCodeServiceSISERV(String serviAS400);

	EntiteDto getDirection(Integer idEntite);

	List<ReferenceDto> getListTypeEntite();

	EntiteDto getParentOfEntiteByTypeEntite(Integer idEntite, Integer idTypeEntite);
}
