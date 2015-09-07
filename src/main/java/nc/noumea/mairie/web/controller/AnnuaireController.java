package nc.noumea.mairie.web.controller;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.service.sirh.IAnnuaireService;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.AgentAnnuaireDto;

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
@RequestMapping("/annuaire")
public class AnnuaireController {

	@Autowired
	private IAnnuaireService annuaireSrv;

	/**
	 * Returns les agents en activité pour l'annuaire
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listAgentActiviteAnnuaire", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> listAgentActiviteAnnuaire() {

		// on cherche si l'agent a une affectation active
		List<Integer> listIdAgent = annuaireSrv.listAgentActiviteAnnuaire();

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").deepSerialize(listIdAgent),
				HttpStatus.OK);
	}

	/**
	 * Returns les info d'un agent pour l'annuaire
	 * 
	 * @param idAgent
	 *            sur 6 ou 7 digits
	 * @return
	 */
	@RequestMapping(value = "/agent", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentAnnuaire(@RequestParam(value = "idAgent", required = true) Integer idAgent) {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		// on recup les infos de l'agent pour l'annuaire
		AgentAnnuaireDto res = annuaireSrv.getInfoAgent(new Integer(newIdAgent));

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(res);

		if (res == null || res.getIdAgent() == null) {
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

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

}
