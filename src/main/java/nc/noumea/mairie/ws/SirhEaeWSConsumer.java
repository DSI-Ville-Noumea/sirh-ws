package nc.noumea.mairie.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.noumea.mairie.ws.dto.CampagneEaeDto;
import nc.noumea.mairie.ws.dto.EaeDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.ClientResponse;

@Service
public class SirhEaeWSConsumer extends BaseWsConsumer implements ISirhEaeWSConsumer {

	@Autowired
	@Qualifier("eaeWsBaseUrl")
	private String				eaeWsBaseUrl;

	private static final String	sirhEaeCampagneOuverte					= "eaes/getEaeCampagneOuverte";
	private static final String	sirhAvisSHDEae							= "eaes/getAvisSHD";
	private static final String	sirhFindEaeByAgentAndYear				= "eaes/findEaeByAgentAndYear";
	private static final String	sirhCompterlistIdEaeByCampagneAndAgent	= "eaes/compterlistIdEaeByCampagneAndAgent";
	private static final String	sirhGetEaesGedIdsForAgents				= "eaes/getEaesGedIdsForAgents";
	private static final String	sirhDetailsEaeUrl						= "sirhEaes/detailsEae";

	@Override
	public CampagneEaeDto getEaeCampagneOuverte() {

		String url = String.format(eaeWsBaseUrl + sirhEaeCampagneOuverte);

		Map<String, String> parameters = new HashMap<String, String>();

		ClientResponse res = createAndFireGetRequest(parameters, url);

		return readResponse(CampagneEaeDto.class, res, url);
	}

	@Override
	public ReturnMessageDto getAvisSHDEae(Integer idEae) {

		String url = String.format(eaeWsBaseUrl + sirhAvisSHDEae);

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("idEae", String.valueOf(idEae));

		ClientResponse res = createAndFireGetRequest(parameters, url);

		return readResponse(ReturnMessageDto.class, res, url);
	}

	@Override
	public ReturnMessageDto findEaeByAgentAndYear(Integer idAgent, Integer annee) {

		String url = String.format(eaeWsBaseUrl + sirhFindEaeByAgentAndYear);

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("idAgent", String.valueOf(idAgent));
		parameters.put("annee", String.valueOf(annee));

		ClientResponse res = createAndFireGetRequest(parameters, url);

		return readResponse(ReturnMessageDto.class, res, url);
	}

	@Override
	public ReturnMessageDto compterlistIdEaeByCampagneAndAgent(Integer idCampagneEae, List<Integer> listAgentsId, Integer idAgent) {

		String url = String.format(eaeWsBaseUrl + sirhCompterlistIdEaeByCampagneAndAgent);

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("idCampagneEae", String.valueOf(idCampagneEae));
		parameters.put("idAgent", String.valueOf(idAgent));

		ClientResponse res = createAndFireRequest(parameters, url, true, listAgentsId.toString());

		return readResponse(ReturnMessageDto.class, res, url);
	}

	@Override
	public ReturnMessageDto getEaesGedIdsForAgents(List<Integer> agentsIds, int annee) {

		String url = String.format(eaeWsBaseUrl + sirhGetEaesGedIdsForAgents);

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("annee", String.valueOf(annee));

		ClientResponse res = createAndFireRequest(parameters, url, true, agentsIds.toString());

		return readResponse(ReturnMessageDto.class, res, url);
	}

	@Override
	public EaeDto getDetailsEae(Integer idAgentSirh, Integer idEae) {

		String url = String.format(eaeWsBaseUrl + sirhDetailsEaeUrl);

		HashMap<String, String> params = new HashMap<>();
		params.put("idAgentSirh", idAgentSirh.toString());
		params.put("idEae", idEae.toString());

		ClientResponse res = createAndFireGetRequest(params, url);
		return readResponse(EaeDto.class, res, url);
	}
}
