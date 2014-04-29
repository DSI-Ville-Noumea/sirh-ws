package nc.noumea.mairie.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.noumea.mairie.web.dto.LightUserDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.ClientResponse;

@Service
public class RadiWSConsumer extends BaseWsConsumer implements IRadiWSConsumer {
	private Logger logger = LoggerFactory.getLogger(RadiWSConsumer.class);

	@Autowired
	@Qualifier("radiWsBaseUrl")
	private String radiWsBaseUrl;

	private static final String searchAgentRadi = "users";

	@Override
	public boolean asAgentCompteAD(Integer nomatr) {
		String url = String.format(radiWsBaseUrl + searchAgentRadi);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("employeenumber", getEmployeeNumberWithNomatr(nomatr));

		ClientResponse res = createAndFireGetRequest(parameters, url);
		if (res.getStatus() == HttpStatus.NO_CONTENT.value()) {
			return false;
		} else if (res.getStatus() == HttpStatus.OK.value()) {
			return true;
		}
		return false;
	}

	@Override
	public LightUserDto getAgentCompteAD(Integer nomatr) {

		String url = String.format(radiWsBaseUrl + searchAgentRadi);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("employeenumber", getEmployeeNumberWithNomatr(nomatr));
		logger.debug("Call " + url + " with employeenumber=" + getEmployeeNumberWithNomatr(nomatr));

		ClientResponse res = createAndFireGetRequest(parameters, url);
		List<LightUserDto> list = readResponseAsList(LightUserDto.class, res, url);
		return list.size() == 0 ? null : list.get(0);
	}

	@Override
	public String getEmployeeNumberWithNomatr(Integer nomatr) {
		return "90" + nomatr;
	}

	@Override
	public String getIdAgentWithNomatr(Integer nomatr) {
		return "900" + nomatr;
	}

	@Override
	public String getNomatrWithIdAgent(Integer idAgent) {
		return idAgent.toString().substring(3, idAgent.toString().length());
	}

	@Override
	public LightUserDto getAgentCompteADByLogin(String login) {

		String url = String.format(radiWsBaseUrl + searchAgentRadi);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("sAMAccountName", login);
		logger.debug("Call " + url + " with sAMAccountName=" + login);

		ClientResponse res = createAndFireGetRequest(parameters, url);
		List<LightUserDto> list = readResponseAsList(LightUserDto.class, res, url);
		return list.size() == 0 ? null : list.get(0);
	}

	@Override
	public String getNomatrWithEmployeeNumber(Integer employeeNumber) {
		return employeeNumber.toString().substring(2, employeeNumber.toString().length());
	}
}
