package nc.noumea.mairie.service.ads;

import java.util.List;

import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.ws.ADSWSConsumer;
import nc.noumea.mairie.ws.IADSWSConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdsService implements IAdsService {

	@Autowired
	private IADSWSConsumer adsWSConsumer;

	/**
	 * Ce service optimise les appels à ADS.
	 * On passe en parametre une liste d entiteDto.
	 * A chaque appel ADS, on sauvegarde l entite retournee dans cette liste
	 * afin de ne pas appeler 2 fois la meme entite.
	 * 
	 * Dans le cas de tres grosse volumetrie, il vaut mieux utiliser le service 
	 * getEntiteByIdEntiteOptimiseWithWholeTree()
	 */
	@Override
	public EntiteDto getEntiteByIdEntiteOptimise(Integer idEntite, List<EntiteDto> listEntiteDtoExistant) {

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
	 * Retourne le sigle d une entite par rapport a l ID Entite passe en
	 * parametre dans un arbre d EntiteDto
	 */
	@Override
	public String getSigleEntityInEntiteDtoTreeByIdEntite(EntiteDto entite, Integer idServiceAds) {

		String result = null;

		if (null != entite && null != idServiceAds && null != entite.getIdEntite()) {
			if (entite.getIdEntite().equals(idServiceAds)) {
				return entite.getSigle();
			}
			return getListIdsEntiteEnfants(entite, idServiceAds, result);
		}

		return result;
	}

	private String getListIdsEntiteEnfants(EntiteDto entite, Integer idServiceAds, String result) {

		if (null != entite && null != entite.getEnfants()) {
			for (EntiteDto enfant : entite.getEnfants()) {
				if (null != enfant && null != enfant.getIdEntite() && enfant.getIdEntite().equals(idServiceAds)) {
					return enfant.getSigle();
				}
				result = getListIdsEntiteEnfants(enfant, idServiceAds, result);
				if (null != result) {
					return result;
				}
			}
		}
		return result;
	}

	@Override
	public EntiteDto getInfoSiservByIdEntite(Integer idEntite) {
		return adsWSConsumer.getInfoSiservByIdEntite(idEntite);
	}
	
	/**
	 * Ce service ne fait qu un seul appel à ADS qui retourne l arbre entier.
	 * Puis on recherche dans cet arbre l entite correspondant au parametre donne. 
	 */
	@Override
	public EntiteDto getEntiteByIdEntiteOptimiseWithWholeTree(Integer idEntite, EntiteDto root) {

		if (null == idEntite) {
			return null;
		}

		return getEntiteInWholeTree(root, idEntite);
	}
	
	private EntiteDto getEntiteInWholeTree(EntiteDto entite, Integer idEntite) {
		
		if(null == entite) 
			return null;
		
		if (entite.getIdEntite().equals(idEntite)) {
			return entite;
		}
		
		EntiteDto result = null;
		for(EntiteDto enfant : entite.getEnfants()) {
			result = getEntiteInWholeTree(enfant, idEntite);
			if(null != result) {
				return result;
			}
		}
		
		return result;
	}
	
	/**
	 * Retourne la direction d une entite sans appeler ADS.
	 * Pour cela, l entiteDto en parametre necessite d'avoir ses parents et grand parents
	 */
	@Override
	public EntiteDto getAffichageDirectionWithoutCallADS(EntiteDto entite) {
		
		if(null != entite
			&& null != entite.getEntiteParent()) {
			
			if(null != entite.getEntiteParent().getTypeEntite()
					&& null != entite.getEntiteParent().getTypeEntite().getLabel()
					&& ADSWSConsumer.AFFICHAGE_DIRECTION.equals(entite.getEntiteParent().getTypeEntite().getLabel())) {
				return entite.getEntiteParent();
			}else{
				return getAffichageDirectionWithoutCallADS(entite.getEntiteParent());
			}
		}
		
		return null;		
	}

}
