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
	
	/**
	 * Retourne le sigle d une entite par rapport a l ID Entite passe en parametre
	 * dans un arbre d EntiteDto
	 */
	@Override
	public String getSigleEntityInEntiteDtoTreeByIdEntite(EntiteDto entite, Integer idServiceAds) {
		
		String result = null;
		
		if(null != entite
				&& null != idServiceAds
				&& null != entite.getIdEntite()) {
			if(entite.getIdEntite().equals(idServiceAds)) {
				return entite.getSigle();
			}
			return getListIdsEntiteEnfants(entite, idServiceAds, result);
		}
		
		return result;
	}
	
	private String getListIdsEntiteEnfants(EntiteDto entite, Integer idServiceAds, String result) {

		
		if (null != entite && null != entite.getEnfants()) {
			for (EntiteDto enfant : entite.getEnfants()) {
				if(null != enfant
						&& null != enfant.getIdEntite()
						&& enfant.getIdEntite().equals(idServiceAds)) {
					return enfant.getSigle();
				}
				result = getListIdsEntiteEnfants(enfant, idServiceAds, result);
				if(null != result) {
					return result;
				}
			}
		}
		return result;
	}

}
