package nc.noumea.mairie.web.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.NoeudDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/services")
public class ServiceController {

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private IADSWSConsumer adsWSConsumer;

	/**
	 * Returns the list of agents in a service and its sub services
	 * 
	 * @param idAgent
	 * @param date
	 *            (optional)
	 * @return
	 */
	@RequestMapping(value = "/agent", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentService(@RequestParam(value = "idAgent", required = true) Integer idAgent,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date date) {

		// Si la date n'est pas spécifiée, prendre la date du jour
		if (date == null)
			date = new Date();

		List<AgentWithServiceDto> result = agentSrv.listAgentsOfServices(null, date, Arrays.asList(idAgent));

		if (result.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").serialize(result.get(0));

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	/**
	 * Returns the list of agents in a service and its sub services
	 * 
	 * @param idServiceADS
	 * @param date
	 *            (optional)
	 * @return
	 */
	@RequestMapping(value = "/agents", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getServiceAgents(
			@RequestParam(value = "idServiceADS", required = true) Integer idServiceADS,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date date) {

		NoeudDto service = adsWSConsumer.getNoeudByIdService(idServiceADS);
		// Si le service n'existe pas, on ne retourne rien
		if (service == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		List<Integer> services = adsWSConsumer.getListIdsServiceWithEnfantsOfService(idServiceADS);

		// Si la date n'est pas spécifiée, prendre la date du jour
		if (date == null)
			date = new Date();

		List<AgentWithServiceDto> result = agentSrv.listAgentsOfServices(services, date, null);

		if (result.size() == 0)
			new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

}
