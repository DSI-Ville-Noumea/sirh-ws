package nc.noumea.mairie.web.controller;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.service.sirh.IAffectationService;
import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.ws.dto.EasyVistaDto;

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
@RequestMapping("/easyVista")
public class EasyVistaController {

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private IAffectationService affectationSrv;

	/**
	 * Returns le chef de service d'un agent
	 * 
	 * @param nomatr
	 * @return
	 */
	@RequestMapping(value = "/agent", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentService(@RequestParam(value = "nomatr", required = true) Integer nomatr) {
		EasyVistaDto result = new EasyVistaDto();

		// on récupere l'idAgent à partir du nomatr
		Integer newIdAgent = getIdAgentWithNomatr(nomatr);

		// on verifie que l'agent existe
		Agent ag = agentSrv.getAgent(newIdAgent);
		if (ag == null) {
			result.getErrors().add("L'agent ne fait pas parti du système d'information.");
			return new ResponseEntity<String>(new JSONSerializer().serialize(result), HttpStatus.OK);
		}

		// on cherche si l'agent a une affectation active
		Affectation aff = affectationSrv.getAffectationActiveByIdAgent(newIdAgent);
		if (aff == null) {
			result.getErrors().add("L'agent n'a pas d'affectation active.");
			return new ResponseEntity<String>(new JSONSerializer().serialize(result), HttpStatus.OK);
		}

		// on traite le cas
		result = affectationSrv.getChefServiceAgent(aff, result);

		return new ResponseEntity<String>(new JSONSerializer().serialize(result), HttpStatus.OK);
	}

	private Integer getIdAgentWithNomatr(Integer nomatr) {
		return Integer.valueOf("900" + nomatr);
	}

}
