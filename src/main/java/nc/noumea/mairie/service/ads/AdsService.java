package nc.noumea.mairie.service.ads;

import java.util.List;

import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdsService implements IAdsService {


	@Autowired
	private IADSWSConsumer adsWSConsumer;
	
	@Override
	public EntiteDto getEntiteByIdEntiteOptimise(Integer idEntite,
			List<EntiteDto> listEntiteDtoExistant) {
		
		if (null == idEntite) {
			return null;
		}

		// on regarde dans les agents deja retournes par sirh-ws
		for (EntiteDto entiteExistant : listEntiteDtoExistant) {
			if (entiteExistant.getIdEntite().equals(idEntite)) {
				return entiteExistant;
			}
		}

		EntiteDto result = adsWSConsumer.getEntiteByIdEntite(idEntite);
		if (result != null && result.getIdEntite() != null) {
			listEntiteDtoExistant.add(result);
		}

		return result;
	}

}
