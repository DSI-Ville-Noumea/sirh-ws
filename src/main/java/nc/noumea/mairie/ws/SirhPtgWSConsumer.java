package nc.noumea.mairie.ws;

import java.util.HashMap;
import java.util.Map;

import nc.noumea.mairie.ws.dto.RefPrimeDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.ClientResponse;

@Service
public class SirhPtgWSConsumer extends BaseWsConsumer implements ISirhPtgWSConsumer {

	@Autowired
	@Qualifier("ptgWsBaseUrl")
	private String ptgWsBaseUrl;

	private static final String sirhPtgPrimeDetail = "primes/getPrime";
	private static final String sirhPtgUserApprobateur = "droits/isUserApprobateur";
	private static final String sirhPtgUserOperateur = "droits/isUserOperateur";

	@Override
	public RefPrimeDto getPrime(Integer noRubr) {

		String url = String.format(ptgWsBaseUrl + sirhPtgPrimeDetail);

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("noRubr", String.valueOf(noRubr));

		ClientResponse res = createAndFireGetRequest(parameters, url);

		return readResponse(RefPrimeDto.class, res, url);
	}

	@Override
	public boolean isUserApprobateur(Integer idAgent) {

		String url = String.format(ptgWsBaseUrl + sirhPtgUserApprobateur);

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("idAgent", String.valueOf(idAgent));

		ClientResponse res = createAndFireGetRequest(parameters, url);

		if (res.getStatus() == HttpStatus.OK.value())
			return true;
		return false;
	}

	@Override
	public boolean isUserOperateur(Integer idAgent) {

		String url = String.format(ptgWsBaseUrl + sirhPtgUserOperateur);

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("idAgent", String.valueOf(idAgent));

		ClientResponse res = createAndFireGetRequest(parameters, url);

		if (res.getStatus() == HttpStatus.OK.value())
			return true;
		return false;
	}
}
