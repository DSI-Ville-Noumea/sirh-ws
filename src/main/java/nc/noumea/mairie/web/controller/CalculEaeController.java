package nc.noumea.mairie.web.controller;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.service.ICalculEaeService;
import nc.noumea.mairie.model.service.ISpCarrService;
import nc.noumea.mairie.model.service.ISpadmnService;
import nc.noumea.mairie.service.IAvancementsService;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.AutreAdministrationAgentDto;
import nc.noumea.mairie.web.dto.CalculEaeInfosDto;
import nc.noumea.mairie.web.dto.avancements.AvancementEaeDto;

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
@RequestMapping("/calculEae")
public class CalculEaeController {

	private Logger logger = LoggerFactory.getLogger(CalculEaeController.class);
	
	@Autowired
	private ICalculEaeService calculEaeService;
	
	@Autowired
	private IAvancementsService avancementsService;
	
	@Autowired
	private ISpCarrService spCarrService;
	
	@Autowired
	private ISpadmnService spadmnService;
	
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
	@RequestMapping(value = "/avancement", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAvancement (
			@RequestParam("idAgent") int idAgent, @RequestParam("anneeAvancement") int anneeAvancement, @RequestParam("isFonctionnaire") boolean isFonctionnaire) {
		
		logger.debug("entered GET [calculEae/avancement] => getAvancement");
		
		AvancementEaeDto result = avancementsService.getAvancement(idAgent, anneeAvancement, isFonctionnaire);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).serialize(result), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/avancementDetache", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAvancementDetache (
			@RequestParam("idAgent") int idAgent, @RequestParam("anneeAvancement") int anneeAvancement) {
		
		logger.debug("entered GET [calculEae/avancementDetache] => getAvancementDetache");
		
		AvancementEaeDto result = avancementsService.getAvancementDetache(idAgent, anneeAvancement);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).serialize(result), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/affectationActiveByAgent", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getDetailAffectationActiveByAgent(@RequestParam("idAgent") int idAgent, @RequestParam("anneeFormation") int anneeFormation) {
		
		logger.debug("entered GET [calculEae/affectationActiveByAgent] => getAffectationActiveByAgent ");
		
		CalculEaeInfosDto result = calculEaeService.getAffectationActiveByAgent(idAgent, anneeFormation);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(result), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listeAffectationsAgentAvecService", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAffectationsAgentAvecService(
			@RequestParam("idAgent") int idAgent,
			@RequestParam("idService") String idService) {
		
		logger.debug("entered GET [calculEae/listeAffectationsAgentAvecService] => getListeAffectationsAgentAvecService ");
		
		List<CalculEaeInfosDto> result = calculEaeService.getListeAffectationsAgentAvecService(idAgent, idService);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(result), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listeAffectationsAgentAvecFP", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAffectationsAgentAvecFP(
			@RequestParam("idAgent") int idAgent,
			@RequestParam("idFichePoste") int idFichePoste) {
		
		logger.debug("entered GET [calculEae/listeAffectationsAgentAvecFP] => getListeAffectationsAgentAvecFP ");
		
		List<CalculEaeInfosDto> result = calculEaeService.getListeAffectationsAgentAvecFP(idAgent, idFichePoste);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(result), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/autreAdministrationAgentAncienne", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> chercherAutreAdministrationAgentAncienne(@RequestParam("idAgent") int idAgent, @RequestParam("isFonctionnaire") boolean isFonctionnaire) {
		
		logger.debug("entered GET [calculEae/autreAdministrationAgentAncienne] => chercherAutreAdministrationAgentAncienne ");
		
		AutreAdministrationAgentDto result = calculEaeService.chercherAutreAdministrationAgentAncienne(idAgent, isFonctionnaire);
		
		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(result), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listeAutreAdministrationAgent", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAutreAdministrationAgent(@RequestParam("idAgent") int idAgent) {
		
		logger.debug("entered GET [calculEae/listeAutreAdministrationAgent] => getListeAutreAdministrationAgent ");
		
		List<AutreAdministrationAgentDto> result = calculEaeService.getListeAutreAdministrationAgent(idAgent);
		
		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(result), HttpStatus.OK);
	}
}
