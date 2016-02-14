package nc.noumea.mairie.web.controller;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.service.sirh.IUtilisateurService;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.AccessRightOrganigrammeDto;
import nc.noumea.mairie.ws.dto.LightUserDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

	private Logger logger = LoggerFactory.getLogger(UtilisateurController.class);

	@Autowired
	private IUtilisateurService utilisateurSrv;

	@Autowired
	private IAgentService agentSrv;

	private String remanieIdAgent(Integer idAgent) {
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

	@ResponseBody
	@RequestMapping(value = "/isUtilisateurSIRH", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> isUtilisateurSIRH(@RequestParam(value = "idAgent", required = true) Integer idAgent) {

		logger.debug("entered GET [utilisateur/isUtilisateurSIRH] => isUtilisateurSIRH with parameters idAgent = {}",
				idAgent);

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		ReturnMessageDto srm = utilisateurSrv.isUtilisateurSIRH(newIdAgent);

		String response = new JSONSerializer().exclude("*.class").deepSerialize(srm);

		if (!srm.getErrors().isEmpty()) {
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/listeUtilisateurSIRH", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeUtilisateurSIRH(@RequestParam(value = "idAgent", required = false) Integer idAgent) {

		logger.debug("entered GET [utilisateur/listeUtilisateurSIRH] => getListeUtilisateurSIRH with parameters idAgent = {}",
				idAgent);

		// on remanie l'idAgent
		Integer newIdAgent = null;
		
		if(null != idAgent) {
			newIdAgent = new Integer(remanieIdAgent(idAgent));
		}
		
		List<LightUserDto> srm = utilisateurSrv.getListeUtilisateurSIRH(newIdAgent);

		String response = new JSONSerializer().exclude("*.class").deepSerialize(srm);

		if (null == srm || srm.isEmpty()) {
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	/**
	 * Retourne un AccessRightOrganigrammeDto avec les droits de l agent pour l organigramme
	 * 
	 * @param idAgent Identifiant de l agent
	 * @return AccessRightOrganigrammeDto
	 */
	@ResponseBody
	@RequestMapping(value = "/getAutorisationOrganigramme", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAutorisationOrganigramme(@RequestParam(value = "idAgent", required = true) Integer idAgent) {

		logger.debug("entered GET [utilisateur/getAutorisationOrganigramme] => getAutorisationOrganigramme with parameters idAgent = {}",
				idAgent);

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		AccessRightOrganigrammeDto res = utilisateurSrv.getOrganigrammeAccessRight(new Integer(newIdAgent));

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(res);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
