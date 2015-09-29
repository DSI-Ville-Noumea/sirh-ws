package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.AccueilRh;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.service.sirh.IKiosqueRhService;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.AccueilRhDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.ReferentRhDto;
import nc.noumea.mairie.ws.IADSWSConsumer;
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
	private IAffectationRepository affectationRepository;

	@Autowired
	private IADSWSConsumer adsWSConsumer;

	private Logger logger = LoggerFactory.getLogger(KiosqueRHController.class);

	@RequestMapping(value = "/getListReferentRH", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListReferentRH(Integer idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		// on cherche le service de l'agent
		Affectation aff = affectationRepository.getAffectationActiveByAgent(idAgent);
		if (aff == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		List<ReferentRh> lc = kiosqueSrv.getListReferentRH(aff.getFichePoste().getIdServiceADS());
		List<ReferentRhDto> dto = new ArrayList<ReferentRhDto>();
		for (ReferentRh ref : lc) {
			EntiteDto service = adsWSConsumer.getEntiteByIdEntite(aff.getFichePoste().getIdServiceADS());
			dto.add(new ReferentRhDto(ref, agentSrv.getAgent(ref.getIdAgentReferent()), service));
		}

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(dto);

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

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(listeRef);

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

	@RequestMapping(value = "/getAlerteRHByAgent", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAlerteRHByAgent(Integer idAgent) {

		logger.debug("entered GET [kiosqueRH/getAlerteRHByAgent] => getAlerteRHByAgent with parameter idAgent = {}  ", idAgent);

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		ReturnMessageDto res = kiosqueSrv.getAlerteRHByAgent(new Integer(newIdAgent));

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(res);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
