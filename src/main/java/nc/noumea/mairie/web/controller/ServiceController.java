package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.service.ISiservService;
import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.tools.ServiceTreeNode;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;

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
	private ISiservService siservSrv;

	private String remanieIdAgent(Long idAgent) {
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2, idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}
		return newIdAgent;
	}

	/**
	 * Returns the list of agents in a service and its sub services
	 * 
	 * @param codeService
	 * @param date
	 *            (optional)
	 * @return
	 */
	@RequestMapping(value = "/agent", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentService(@RequestParam(value = "idAgent", required = true) Integer idAgent,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "YYYYMMdd") Date date) {

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
	 * @param codeService
	 * @param date
	 *            (optional)
	 * @return
	 */
	@RequestMapping(value = "/agents", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getServiceAgents(
			@RequestParam(value = "codeService", required = true) String codeService,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "YYYYMMdd") Date date) {

		Siserv service = siservSrv.getService(codeService);

		// Si le service n'existe pas, on ne retourne rien
		if (service == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		List<String> services = siservSrv.getListSubServicesSigles(codeService);

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
	 * Returns the list of services contained in the direction service of the
	 * given agent
	 * 
	 * @param idAgent
	 * @return
	 */
	@RequestMapping(value = "/servicesDirectionAgent", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentsDirectionServices(
			@RequestParam(value = "idAgent", required = true) Long idAgent) {

		String newIdAgent = remanieIdAgent(idAgent);
		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		ServiceTreeNode direction = siservSrv.getAgentDirection(ag.getIdAgent(), null);
		List<ServiceTreeNode> services = siservSrv.getListSubServices(direction.getService());

		String json = new JSONSerializer().exclude("*.class").exclude("*.serviceParent").exclude("*.sigleParent")
				.serialize(services);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	/**
	 * Returns the list of sub services for a given service
	 * 
	 * @param codeService
	 * @return
	 */
	@RequestMapping(value = "/sousServices", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getSubServices(
			@RequestParam(value = "codeService", required = true) String codeService) {

		Siserv service = siservSrv.getService(codeService);

		// Si le service n'existe pas, on ne retourne rien
		if (service == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		List<ServiceTreeNode> services = siservSrv.getListSubServices(service.getServi());

		if (services.size() == 0)
			new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").exclude("*.serviceParent").exclude("*.sigleParent")
				.serialize(services);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/rebuildServiceTree")
	@Transactional(readOnly = true)
	public ResponseEntity<String> rebuildServiceTree() throws ParseException {

		try {
			siservSrv.construitArbreServices();
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.toString(), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
