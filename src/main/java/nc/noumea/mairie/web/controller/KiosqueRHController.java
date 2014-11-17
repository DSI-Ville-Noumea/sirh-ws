package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.AccueilRh;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;
import nc.noumea.mairie.service.ISiservService;
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

	@RequestMapping(value = "/getListeReferentRH", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeReferentRH() throws ParseException {

		List<ReferentRh> lc = kiosqueSrv.getListeReferentRH();
		List<ReferentRhDto> listeRef = new ArrayList<ReferentRhDto>();
		for (ReferentRh c : lc) {
			ReferentRhDto dto = new ReferentRhDto(c, agentSrv.getAgent(c.getIdAgentReferent()), siservSrv.getService(c
					.getServi()));
			listeRef.add(dto);
		}

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(listeRef);

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
}
