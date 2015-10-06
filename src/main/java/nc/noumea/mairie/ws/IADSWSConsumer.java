package nc.noumea.mairie.ws;

import java.util.List;

import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.ReferenceDto;

public interface IADSWSConsumer {

	EntiteDto getEntiteByIdEntite(Integer idEntite);

	EntiteDto getEntiteWithChildrenByIdEntite(Integer idEntite);

	EntiteDto getAffichageSection(Integer idEntite);

	EntiteDto getAffichageService(Integer idEntite);

	EntiteDto getAffichageDirection(Integer idEntite);

	EntiteDto getEntiteByCodeServiceSISERV(String serviAS400);

	List<ReferenceDto> getListTypeEntite();

	EntiteDto getParentOfEntiteByTypeEntite(Integer idEntite, Integer idTypeEntite);

	EntiteDto getInfoSiservByIdEntite(Integer idEntite);

	EntiteDto getWholeTree();
}
