package nc.noumea.mairie.service.ads;

import java.util.List;

import nc.noumea.mairie.web.dto.EntiteDto;

public interface IAdsService {

	EntiteDto getEntiteByIdEntiteOptimise(Integer idEntite, List<EntiteDto> listEntiteDto);

	String getSigleEntityInEntiteDtoTreeByIdEntite(EntiteDto entite,
			Integer idServiceAds);

	EntiteDto getInfoSiservByIdEntite(Integer idEntite);
}
