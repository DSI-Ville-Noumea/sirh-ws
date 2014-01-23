package nc.noumea.mairie.web.controller;

import nc.noumea.mairie.model.service.IUtilisateurService;
import nc.noumea.mairie.web.dto.ReturnMessageDto;

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
}
