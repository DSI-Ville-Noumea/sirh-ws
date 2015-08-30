package nc.noumea.mairie.web.controller;

import java.util.List;

import nc.noumea.mairie.service.sirh.IAnnuaireService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * @param nomatr
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

}
