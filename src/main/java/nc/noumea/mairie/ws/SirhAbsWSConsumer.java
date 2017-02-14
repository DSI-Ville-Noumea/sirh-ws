package nc.noumea.mairie.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.ClientResponse;

import nc.noumea.mairie.web.dto.RefTypeAbsenceDto;
import nc.noumea.mairie.web.dto.RefTypeSaisiCongeAnnuelDto;

@Service
public class SirhAbsWSConsumer extends BaseWsConsumer implements ISirhAbsWSConsumer {

	@Autowired
	@Qualifier("absWsBaseUrl")
	private String absWsBaseUrl;

	private static final String sirhAbsUserApprobateur = "droits/isUserApprobateur";
	private static final String sirhAbsUserOperateur = "droits/isUserOperateur";
	private static final String sirhAbsUserViseur = "droits/isUserViseur";
	private static final String getListeTypeAbsenceUrl = "typeAbsence/getListeTypeAbsence";
	private static final String	getTypeAbsenceUrl		= "typeAbsence/getTypeAbsence";

	@Override
	public boolean isUserApprobateur(Integer idAgent) {

		String url = String.format(absWsBaseUrl + sirhAbsUserApprobateur);

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("idAgent", String.valueOf(idAgent));

		ClientResponse res = createAndFireGetRequest(parameters, url);

		if (res.getStatus() == HttpStatus.OK.value())
			return true;
		return false;
	}

	@Override
	public boolean isUserOperateur(Integer idAgent) {

		String url = String.format(absWsBaseUrl + sirhAbsUserOperateur);

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("idAgent", String.valueOf(idAgent));

		ClientResponse res = createAndFireGetRequest(parameters, url);

		if (res.getStatus() == HttpStatus.OK.value())
			return true;
		return false;
	}

	@Override
	public boolean isUserViseur(Integer idAgent) {

		String url = String.format(absWsBaseUrl + sirhAbsUserViseur);

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("idAgent", String.valueOf(idAgent));

		ClientResponse res = createAndFireGetRequest(parameters, url);

		if (res.getStatus() == HttpStatus.OK.value())
			return true;
		return false;
	}

	@Override
	public List<RefTypeSaisiCongeAnnuelDto> getListeTypAbsenceCongeAnnuel() {

		String url = String.format(absWsBaseUrl + getListeTypeAbsenceUrl);
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idRefGroupeAbsence", "5"); // CONGES ANNUELS
		
		ClientResponse response = createAndFireGetRequest(parameters, url);
		return readResponseAsList(RefTypeSaisiCongeAnnuelDto.class, response, url);
	}

	@Override
	public RefTypeAbsenceDto getTypAbsenceCongeAnnuelById(Integer idBaseHoraireConge) {

		String url = String.format(absWsBaseUrl + getTypeAbsenceUrl);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idBaseHoraireAbsence", idBaseHoraireConge.toString());

		ClientResponse response = createAndFireGetRequest(parameters, url);
		return readResponse(RefTypeAbsenceDto.class, response, url);
	}
}
