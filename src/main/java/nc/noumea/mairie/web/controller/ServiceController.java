package nc.noumea.mairie.web.controller;

import java.util.List;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.service.IAgentService;
import nc.noumea.mairie.model.service.ISiservService;
import nc.noumea.mairie.tools.ServiceTreeNode;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value = "/agents", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getServiceAgents(@RequestParam(value = "codeService", required = true) String codeService) {
		
		Siserv service = Siserv.findSiserv(codeService);
		
		// Si le service n'existe pas, on ne retourne rien
		if (service == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		List<String> services = siservSrv.getListSubServicesSigles(codeService);
		
		List<Integer> result = agentSrv.listAgentIdsOfServices(services);
		
		if (result.size() == 0)
			new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<String>(new JSONSerializer().serialize(result), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sousServices", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getSubServices(@RequestParam(value = "codeService", required = true) String codeService) {
		
		Siserv service = Siserv.findSiserv(codeService);
		
		// Si le service n'existe pas, on ne retourne rien
		if (service == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		List<ServiceTreeNode> services = siservSrv.getListSubServices(service.getServi());
		
		if (services.size() == 0)
			new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		
		String json = new JSONSerializer().exclude("*.class").exclude("*.serviceParent").serialize(services);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
}
