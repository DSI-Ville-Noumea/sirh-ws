package nc.noumea.mairie.ws;

import java.util.List;

import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.ReferenceDto;

public interface IADSWSConsumer {

	EntiteDto getEntiteByIdEntite(Integer idEntite);

	EntiteDto getDirectionPourEAE(Integer idEntite);

	EntiteDto getSection(Integer idEntite);

	EntiteDto getEntiteFromCodeServiceAS400(String servi);

	EntiteDto getDirection(Integer idEntite);

	List<Integer> getListIdsServiceWithEnfantsOfService(Integer idEntite);

	EntiteDto getParentOfEntiteByIdEntite(Integer idEntite);

	List<ReferenceDto> getListTypeEntite();
}
