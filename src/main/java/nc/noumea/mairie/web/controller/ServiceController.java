package nc.noumea.mairie.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Controller
@RequestMapping("/services")
public class ServiceController {

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private IADSWSConsumer adsWSConsumer;

	/**
	 * Returns the list of agents in a service and its sub services Attention,
	 * verifier si SHAREPOINT APPELLE ce WS
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
	 * Returns the list of agents with theirs services
	 * 
	 * Le parametre withoutLibelleService permet de faire appel ou non a ADS
	 * Dans un souci de performances, nous n avons pas toujours besoin des libelles de service mais uniquement de idServiceADS
	 * (utile a SIRH-ABS-WS/filtre/services)
	 * 
	 * @param idsAgent
	 *            List<Integer>
	 * @param date
	 *            (optional)
	 * @param withoutLibelleService si true alors pas d appel de ADS pour recuperer les libelles des services
	 * @return List<AgentWithServiceDto> liste des agents passes en parametre
	 *         avec leur service
	 */
	@RequestMapping(value = "/listAgentsWithService", headers = "Accept=application/json", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListAgentsWithService(@RequestBody String agentsApprouvesJson, @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date date,
			@RequestParam(value = "withoutLibelleService", required = false) Boolean withoutLibelleService) {

		List<Integer> listIdsAgent = new JSONDeserializer<List<Integer>>().use(null, ArrayList.class).use("values", Integer.class).deserialize(agentsApprouvesJson);

		// Si la date n'est pas spécifiée, prendre la date du jour
		if (date == null)
			date = new Date();

		if (null == listIdsAgent || listIdsAgent.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		List<AgentWithServiceDto> result = null;
		
		if(null != withoutLibelleService 
				&& withoutLibelleService) {
			result = agentSrv.listAgentsOfServicesWithoutLibelleService(null, date, listIdsAgent);
		} else {
			result = agentSrv.listAgentsOfServices(null, date, listIdsAgent);
		}
		
		if (null == result || result.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

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
	public ResponseEntity<String> getServiceAgents(@RequestParam(value = "idServiceADS", required = true) Integer idServiceADS,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date date) {

		EntiteDto serviceAgent = adsWSConsumer.getEntiteWithChildrenByIdEntite(idServiceADS);
		// Si le service n'existe pas, on ne retourne rien
		if (serviceAgent == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		List<Integer> services = new ArrayList<Integer>();

		for (EntiteDto enfant : serviceAgent.getEnfants()) {
			if (!services.contains(enfant.getIdEntite()))
				services.add(enfant.getIdEntite());
		}

		// Si la date n'est pas spécifiée, prendre la date du jour
		if (date == null)
			date = new Date();

		List<AgentWithServiceDto> result = agentSrv.listAgentsOfServices(services, date, null);

		if (result.size() == 0)
			new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

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
	@RequestMapping(value = "/agentsWithEntiteParent", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getServiceAgentsWithEntiteParent(@RequestParam(value = "idServiceADS", required = true) Integer idServiceADS,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date date) {

		EntiteDto serviceAgent = adsWSConsumer.getEntiteWithChildrenByIdEntite(idServiceADS);
		// Si le service n'existe pas, on ne retourne rien
		if (serviceAgent == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		List<Integer> services = new ArrayList<Integer>();
		services.add(serviceAgent.getIdEntite());

		for (EntiteDto enfant : serviceAgent.getEnfants()) {
			if (!services.contains(enfant.getIdEntite()))
				services.add(enfant.getIdEntite());
		}

		// Si la date n'est pas spécifiée, prendre la date du jour
		if (date == null)
			date = new Date();

		List<AgentWithServiceDto> result = agentSrv.listAgentsOfServices(services, date, null);

		if (result.size() == 0)
			new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	/**
	 * Returns the list of agents with theirs services
	 * 
	 * Le parametre withoutLibelleService permet de faire appel ou non a ADS
	 * Dans un souci de performances, nous n avons pas toujours besoin des libelles de service mais uniquement de idServiceADS
	 * (utile a SIRH-ABS-WS/filtre/services)
	 * 
	 * @param idsAgent
	 *            List<Integer>
	 * @param withoutLibelleService 
	 * 			  si true alors pas d appel de ADS pour recuperer les libelles des services
	 * @return List<AgentWithServiceDto> liste des agents passes en parametre
	 *         avec leur service
	 */
	@RequestMapping(value = "/listAgentsWithServiceOldAffectation", headers = "Accept=application/json", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListAgentsWithServiceOldAffectation(@RequestParam(value = "withoutLibelleService", required = false) Boolean withoutLibelleService,
			@RequestBody String agentsApprouvesJson) {

		List<Integer> listIdsAgent = new JSONDeserializer<List<Integer>>().use(null, ArrayList.class).use("values", Integer.class).deserialize(agentsApprouvesJson);

		if (null == listIdsAgent || listIdsAgent.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		List<AgentWithServiceDto> result = null;
		
		if(null != withoutLibelleService 
				&& withoutLibelleService) {
			result = agentSrv.listAgentsOfServicesOldAffectationWithoutLibelleService(null, listIdsAgent);
		} else {
			result = agentSrv.listAgentsOfServicesOldAffectation(null, listIdsAgent);
		}
		
		if (null == result 
				|| result.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

}
