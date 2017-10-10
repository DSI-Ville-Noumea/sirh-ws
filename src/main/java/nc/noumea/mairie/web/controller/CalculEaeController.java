package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
import nc.noumea.mairie.service.eae.ICalculEaeService;
import nc.noumea.mairie.service.sirh.IAvancementsService;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.AutreAdministrationAgentDto;
import nc.noumea.mairie.web.dto.CalculEaeInfosDto;
import nc.noumea.mairie.web.dto.DateAvctDto;
import nc.noumea.mairie.web.dto.avancements.AvancementEaeDto;

@Controller
@RequestMapping("/calculEae")
public class CalculEaeController {

	private Logger logger = LoggerFactory.getLogger(CalculEaeController.class);

	@Autowired
	private ICalculEaeService calculEaeService;

	@Autowired
	private IAvancementsService avancementsService;
	@ResponseBody
	@RequestMapping(value = "/listeAgentEligibleEAESansAffectes", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAgentEligibleEAESansAffectes() {

		logger.debug("entered GET [calculEae/listeAgentEligibleEAESansAffectes] => getListeAgentEligibleEAESansAffectes ");

		List<AgentDto> result = calculEaeService.getListeAgentEligibleEAESansAffectes();

		logger.debug("FIN [calculEae/listeAgentEligibleEAESansAffectes] : " + result.size());

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(result), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/listeAgentEligibleEAEAffectes", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAgentEligibleEAEAffectes() {

		logger.debug("entered GET [calculEae/listeAgentEligibleEAEAffectes] => getListeAgentEligibleEAEAffectes ");

		List<AgentDto> result = calculEaeService.getListeAgentEligibleEAEAffectes();

		logger.debug("FIN [calculEae/listeAgentEligibleEAEAffectes] : " + result.size());

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(result), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/avancement", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAvancement(@RequestParam("idAgent") int idAgent,
			@RequestParam("anneeAvancement") int anneeAvancement,
			@RequestParam("isFonctionnaire") boolean isFonctionnaire) {

		logger.debug(
				"entered GET [calculEae/avancement] => getAvancement with parameter idAgent = {}, anneeAvancement = {}, isFonctionnaire = {}",
				idAgent, anneeAvancement, isFonctionnaire);

		AvancementEaeDto result = avancementsService.getAvancement(idAgent, anneeAvancement, isFonctionnaire);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class")
				.transform(new MSDateTransformer(), Date.class).serialize(result), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/avancementDetache", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAvancementDetache(@RequestParam("idAgent") int idAgent,
			@RequestParam("anneeAvancement") int anneeAvancement) {

		logger.debug(
				"entered GET [calculEae/avancementDetache] => getAvancementDetache with parameter idAgent = {}, anneeAvancement = {}",
				idAgent, anneeAvancement);

		AvancementEaeDto result = avancementsService.getAvancementDetache(idAgent, anneeAvancement);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class")
				.transform(new MSDateTransformer(), Date.class).serialize(result), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/affectationActiveByAgent", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getDetailAffectationActiveByAgent(@RequestParam("idAgent") int idAgent,
			@RequestParam("anneeFormation") int anneeFormation) throws NumberFormatException, ParseException {

		logger.debug(
				"entered GET [calculEae/affectationActiveByAgent] => getAffectationActiveByAgent with parameter idAgent = {}, anneeFormation = {}",
				idAgent, anneeFormation);

		CalculEaeInfosDto result = calculEaeService.getAffectationActiveByAgent(idAgent, anneeFormation);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class")
				.transform(new MSDateTransformer(), Date.class).deepSerialize(result), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/listeAffectationsAgentAvecService", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAffectationsAgentAvecService(@RequestParam("idAgent") int idAgent,
			@RequestParam("idServiceADS") Integer idServiceADS) {

		logger.debug(
				"entered GET [calculEae/listeAffectationsAgentAvecService] => getListeAffectationsAgentAvecService with parameter idAgent = {}, idServiceADS = {}",
				idAgent, idServiceADS);

		List<CalculEaeInfosDto> result = calculEaeService.getListeAffectationsAgentAvecService(idAgent, idServiceADS);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class")
				.transform(new MSDateTransformer(), Date.class).deepSerialize(result), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/listeAffectationsAgentAvecFP", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAffectationsAgentAvecFP(@RequestParam("idAgent") int idAgent,
			@RequestParam("idFichePoste") int idFichePoste) {

		logger.debug(
				"entered GET [calculEae/listeAffectationsAgentAvecFP] => getListeAffectationsAgentAvecFP  with parameter idAgent = {}, idFichePoste = {}",
				idAgent, idFichePoste);

		List<CalculEaeInfosDto> result = calculEaeService.getListeAffectationsAgentAvecFP(idAgent, idFichePoste);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class")
				.transform(new MSDateTransformer(), Date.class).deepSerialize(result), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/autreAdministrationAgentAncienne", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> chercherAutreAdministrationAgentAncienne(@RequestParam("idAgent") int idAgent,
			@RequestParam("isFonctionnaire") boolean isFonctionnaire) {

		logger.debug(
				"entered GET [calculEae/autreAdministrationAgentAncienne] => chercherAutreAdministrationAgentAncienne with parameter idAgent = {}, isFonctionnaire = {}",
				idAgent, isFonctionnaire);

		AutreAdministrationAgentDto result = calculEaeService.chercherAutreAdministrationAgentAncienne(idAgent,
				isFonctionnaire);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class")
				.transform(new MSDateTransformer(), Date.class).deepSerialize(result), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/listeAutreAdministrationAgent", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAutreAdministrationAgent(@RequestParam("idAgent") int idAgent) {

		logger.debug(
				"entered GET [calculEae/listeAutreAdministrationAgent] => getListeAutreAdministrationAgent with parameter idAgent = {}",
				idAgent);

		List<AutreAdministrationAgentDto> result = calculEaeService.getListeAutreAdministrationAgent(idAgent);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class")
				.transform(new MSDateTransformer(), Date.class).deepSerialize(result), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/calculDateAvancement", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getCalculDateAvancement(@RequestParam("idAgent") int idAgent) {

		logger.debug(
				"entered GET [calculEae/calculDateAvancement] => getCalculDateAvancement with parameter idAgent = {} ",
				idAgent);

		DateAvctDto dto = calculEaeService.getCalculDateAvancement(idAgent);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDernierAvancement", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<Integer> getDernierAvancement(@RequestParam("idAgent") int idAgent, @RequestParam("anneeAvancement") int anneeAvancement) {

		logger.debug("entered GET [calculEae/getDernierAvancement] => with parameter idAgent = {}, annee = {} ",
				idAgent, anneeAvancement);

		Integer avct = calculEaeService.getDernierAvancement(idAgent, anneeAvancement);
		
		HttpStatus status = avct == null ? HttpStatus.NO_CONTENT : HttpStatus.OK;

		return new ResponseEntity<Integer>(avct, status);
	}
}
