package nc.noumea.mairie.service.ads;

import java.util.List;
import java.util.Map;

import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.EntiteWithAgentWithServiceDto;

public interface IAdsService {

	EntiteDto getEntiteByIdEntiteOptimise(Integer idEntite, List<EntiteDto> listEntiteDto);

	String getSigleEntityInEntiteDtoTreeByIdEntite(EntiteDto entite, Integer idServiceAds);

	String getLibelleEntityInEntiteDtoTreeByIdEntite(EntiteDto entite, Integer idServiceAds);

	EntiteDto getInfoSiservByIdEntite(Integer idEntite);

	EntiteDto getEntiteByIdEntiteOptimiseWithWholeTree(Integer idEntite, EntiteDto root);

	EntiteDto getAffichageDirectionWithoutCallADS(EntiteDto entite);

	/**
	 * Retourne la liste des agents contenu dans l arbre des services
	 * en parametre.
	 * 
	 * @param tree EntiteWithAgentWithServiceDto
	 * @return List<AgentDto>
	 */
	List<AgentDto> getListeAgentsOfEntiteTree(EntiteWithAgentWithServiceDto tree);
	
	Map<String, Integer> getListIdsOfEntiteTree(Integer idService);
}
