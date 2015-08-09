package nc.noumea.mairie.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.ReferenceDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.ClientResponse;

@Service
public class ADSWSConsumer extends BaseWsConsumer implements IADSWSConsumer {

	private Logger logger = LoggerFactory.getLogger(ADSWSConsumer.class);

	@Autowired
	@Qualifier("adsWsBaseUrl")
	private String adsWsBaseUrl;

	private static final String sirhAdsListeTypeEntite = "/api/typeEntite";
	private static final String sirhAdsParentOfEntiteByTypeEntite = "/api/entite/parentOfEntiteByTypeEntite";
	private static final String sirhAdsGetEntiteUrl = "api/entite/";
	private static final String sirhAdsGetEntiteWithWildrenUrl = "/withChildren";
	private static final String sirhAdsGetEntiteByCodeServiceSISERVUrl = "api/entite/codeAs400/";
	private static final String sirhAdsGetInfoSiservUrl = "api/entite/infoSiserv/";

	@Override
	public EntiteDto getEntiteByIdEntite(Integer idEntite) {

		if (null == idEntite) {
			return null;
		}

		String url = String.format(adsWsBaseUrl + sirhAdsGetEntiteUrl + idEntite.toString());

		Map<String, String> parameters = new HashMap<String, String>();

		ClientResponse res = createAndFireGetRequest(parameters, url);
		try {
			return readResponse(EntiteDto.class, res, url);
		} catch (Exception e) {
			logger.error("L'application ADS ne repond pas." + e.getMessage());
		}

		return null;
	}

	@Override
	public EntiteDto getDirectionPourEAE(EntiteDto entiteDto) {
		if (!entiteDto.getCodeServi().substring(0, 3).equals("DAG")) {
			return getDirection(entiteDto.getIdEntite());
		} else {
			// cas de la DPM
			return getEntiteByCodeServiceSISERV("DAGA");
		}
	}

	@Override
	public EntiteDto getSection(Integer idEntite) {
		if (idEntite == null)
			return null;
		// on appel ADS pour connaitre la liste des types d'netité pour passer
		// en paramètre ensuite le type "section"
		List<ReferenceDto> listeType = getListTypeEntite();
		ReferenceDto type = null;
		for (ReferenceDto r : listeType) {
			if (r.getLabel().toUpperCase().equals("SECTION")) {
				type = r;
				break;
			}
		}
		if (type == null) {
			return null;
		}
		return getParentOfEntiteByTypeEntite(idEntite, type.getId());
	}

	@Override
	public EntiteDto getParentOfEntiteByTypeEntite(Integer idEntite, Integer idTypeEntite) {

		String url = String.format(adsWsBaseUrl + sirhAdsParentOfEntiteByTypeEntite);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idEntite", idEntite.toString());
		if (idTypeEntite != null)
			parameters.put("idTypeEntite", idTypeEntite.toString());

		ClientResponse res = createAndFireGetRequest(parameters, url);

		try {
			return readResponse(EntiteDto.class, res, url);
		} catch (Exception e) {
			logger.error("L'application ADS ne repond pas." + e.getMessage());
		}

		return null;
	}

	@Override
	public EntiteDto getEntiteByCodeServiceSISERV(String serviAS400) {

		if (null == serviAS400) {
			return null;
		}

		String url = String.format(adsWsBaseUrl + sirhAdsGetEntiteByCodeServiceSISERVUrl + serviAS400);

		Map<String, String> parameters = new HashMap<String, String>();

		ClientResponse res = createAndFireGetRequest(parameters, url);
		try {
			return readResponse(EntiteDto.class, res, url);
		} catch (Exception e) {
			logger.error("L'application ADS ne repond pas." + e.getMessage());
		}

		return null;
	}

	@Override
	public EntiteDto getDirection(Integer idEntite) {
		if (idEntite == null)
			return null;
		// on appel ADS pour connaitre la liste des types d'netité pour passer
		// en paramètre ensuite le type "direction"
		List<ReferenceDto> listeType = getListTypeEntite();
		ReferenceDto type = null;
		for (ReferenceDto r : listeType) {
			if (r.getLabel().toUpperCase().equals("DIRECTION")) {
				type = r;
				break;
			}
		}
		if (type == null) {
			return null;
		}
		return getParentOfEntiteByTypeEntite(idEntite, type.getId());
	}

	@Override
	public List<ReferenceDto> getListTypeEntite() {

		String url = String.format(adsWsBaseUrl + sirhAdsListeTypeEntite);

		Map<String, String> parameters = new HashMap<String, String>();

		ClientResponse res = createAndFireGetRequest(parameters, url);

		try {
			return readResponseAsList(ReferenceDto.class, res, url);
		} catch (Exception e) {
			logger.error("L'application ADS ne repond pas." + e.getMessage());
		}

		return new ArrayList<ReferenceDto>();
	}

	@Override
	public EntiteDto getEntiteWithChildrenByIdEntite(Integer idEntite) {

		if (null == idEntite) {
			return null;
		}

		String url = String.format(adsWsBaseUrl + sirhAdsGetEntiteUrl + idEntite.toString()
				+ sirhAdsGetEntiteWithWildrenUrl);

		Map<String, String> parameters = new HashMap<String, String>();

		ClientResponse res = createAndFireGetRequest(parameters, url);
		try {
			return readResponse(EntiteDto.class, res, url);
		} catch (Exception e) {
			logger.error("L'application ADS ne repond pas." + e.getMessage());
		}

		return null;
	}

	@Override
	public EntiteDto getInfoSiservByIdEntite(Integer idEntite) {

		if (null == idEntite) {
			return null;
		}

		String url = String.format(adsWsBaseUrl + sirhAdsGetInfoSiservUrl + idEntite);

		Map<String, String> parameters = new HashMap<String, String>();

		ClientResponse res = createAndFireGetRequest(parameters, url);
		try {
			return readResponse(EntiteDto.class, res, url);
		} catch (Exception e) {
			logger.error("L'application ADS ne repond pas." + e.getMessage());
		}

		return null;
	}

}
