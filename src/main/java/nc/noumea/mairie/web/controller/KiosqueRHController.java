package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.AccueilRh;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;
import nc.noumea.mairie.service.ISiservService;
import nc.noumea.mairie.service.sirh.IAffectationService;
import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.service.sirh.IKiosqueRhService;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.AccueilRhDto;
import nc.noumea.mairie.web.dto.ReferentRhDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/kiosqueRH")
public class KiosqueRHController {

	@Autowired
	private IKiosqueRhService kiosqueSrv;

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private ISiservService siservSrv;

	@Autowired
	private IAffectationService affSrv;

	@RequestMapping(value = "/getReferentRH", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getReferentRH(Integer idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		// on cherche le service de l'agent
		Affectation aff = affSrv.getAffectationActiveByIdAgent(idAgent);
		if (aff == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		ReferentRh lc = kiosqueSrv.getReferentRH(aff.getFichePoste().getService().getServi());
		ReferentRhDto dto = new ReferentRhDto();
		if (lc != null) {
			dto = new ReferentRhDto(lc, agentSrv.getAgent(lc.getIdAgentReferent()), siservSrv.getService(lc.getServi()));
		}

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getListeAccueilRH", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAccueilRH() throws ParseException {

		List<AccueilRh> lc = kiosqueSrv.getListeAccueilRh();
		List<AccueilRhDto> listeRef = new ArrayList<AccueilRhDto>();
		for (AccueilRh c : lc) {
			AccueilRhDto dto = new AccueilRhDto(c);
			listeRef.add(dto);
		}

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(listeRef);

		return new ResponseEntity<String>(response, HttpStatus.OK);
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
