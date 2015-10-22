package nc.noumea.mairie.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.service.sirh.HelperService;
import nc.noumea.mairie.service.sirh.IAffectationService;
import nc.noumea.mairie.service.sirh.IPointageService;
import nc.noumea.mairie.web.dto.AffectationDto;
import nc.noumea.mairie.web.dto.BaseHorairePointageDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Controller
@RequestMapping("/pointages")
public class PointageController {

	@Autowired
	private IPointageService pointageSrv;
	
	@Autowired
	private IAffectationService affectationService;
	
	@Autowired
	private HelperService helperService;

	@ResponseBody
	@RequestMapping(value = "/listPrimePointages", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getPrimePointageAffOfAgent(
			@RequestParam(value = "idAgent", required = true) Integer idAgent,
			@RequestParam(value = "dateDebut", required = true) @DateTimeFormat(pattern = "yyyyMMdd") Date dateDebut,
			@RequestParam(value = "dateFin", required = true) @DateTimeFormat(pattern = "yyyyMMdd") Date dateFin) {

		List<Integer> result = pointageSrv.getPrimePointagesByAgent(idAgent, dateDebut, dateFin);

		if (result.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/baseHoraire", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getBaseHoraireOfAgent(
			@RequestParam(value = "idAgent", required = true) Integer idAgent,
			@RequestParam(value = "dateDebut", required = true) @DateTimeFormat(pattern = "yyyyMMdd") Date dateDebut,
			@RequestParam(value = "dateFin", required = true) @DateTimeFormat(pattern = "yyyyMMdd") Date dateFin) {

		List<BaseHorairePointageDto> result = pointageSrv.getBaseHorairePointageByAgent(idAgent, dateDebut, dateFin);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/listAgentsWithPrimeTIDOnAffectation", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListAgentsWithPrimeTIDOnAffectation(
			@RequestParam(value = "dateDebut", required = true) @DateTimeFormat(pattern = "yyyyMMdd") Date dateDebut,
			@RequestParam(value = "dateFin", required = true) @DateTimeFormat(pattern = "yyyyMMdd") Date dateFin) {

		List<Integer> result = pointageSrv.getListAgentsWithPrimeTIDOnAffectation(dateDebut, dateFin);

		if (result.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	/**
	 * Retourne une liste d'affectation avec la base congé et les primes pointages sur l'affectation 
	 * pour une liste d agents donne en parametre
	 * et entre deux dates
	 * 
	 * @param dateDebut Date yyyyMMdd
	 * @param dateFin Date yyyyMMdd
	 * @param agentsApprouvesJson List<Integer> Liste d ID agent
	 * @return List<AffectationDto>
	 */
	@ResponseBody
	@RequestMapping(value = "/listeAffectationDtosForListAgentByPeriode", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAffectationDtosForListAgentByPeriode(
			@RequestParam(value = "dateDebut", required = true) @DateTimeFormat(pattern = "yyyyMMdd") Date dateDebut,
			@RequestParam(value = "dateFin", required = true) @DateTimeFormat(pattern = "yyyyMMdd") Date dateFin,
			@RequestBody String agentsApprouvesJson) {
		
		List<Integer> listIdsAgent = new JSONDeserializer<List<Integer>>()
				.use(null, ArrayList.class)
				.use("values", Integer.class)
				.deserialize(agentsApprouvesJson);

		if (null == listIdsAgent || listIdsAgent.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		// on remanie l'idAgent
		List<Integer> listIdsAgentRemanie = new ArrayList<Integer>();
		for (Integer idAgent : listIdsAgent) {
			listIdsAgentRemanie.add(helperService.remanieIdAgent(idAgent));
		}
			
		List<AffectationDto> result = affectationService.getListeAffectationDtosForListAgentByPeriode(listIdsAgent, dateDebut, dateFin);

		if (result.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

}
