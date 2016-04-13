package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.service.sirh.IFichePosteService;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.ws.ISirhEaeWSConsumer;
import nc.noumea.mairie.ws.dto.CampagneEaeDto;
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
@RequestMapping("/eaes")
public class EaeController {
	
	private Logger logger = LoggerFactory.getLogger(EaeController.class);

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private IFichePosteService fpSrv;

	@Autowired
	private ISirhEaeWSConsumer sirhEaeWSConsumer;

	private String remanieIdAgent(Long idAgent) {
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

	@RequestMapping(value = "/estHabiliteEAE", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentHabilite(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		logger.debug("entered GET [eaes/estHabiliteEAE] => getAgentHabilite with parameter idAgent = {}", idAgent);
		
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		CampagneEaeDto campagneEnCours = sirhEaeWSConsumer.getEaeCampagneOuverte();
		if (campagneEnCours == null || campagneEnCours.getIdCampagneEae() == null)
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

		List<Integer> listAgentsId = fpSrv.getListSubAgents(ag.getIdAgent(), 3, null);
		ReturnMessageDto nbEae = sirhEaeWSConsumer.compterlistIdEaeByCampagneAndAgent(
				campagneEnCours.getIdCampagneEae(), listAgentsId, ag.getIdAgent());

		if (nbEae == null || nbEae.getInfos().get(0).equals("0")) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<String>("{}", HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/listDelegataire", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> listDelegataireByAgent(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		CampagneEaeDto campagneEnCours = sirhEaeWSConsumer.getEaeCampagneOuverte();
		if (campagneEnCours == null || campagneEnCours.getIdCampagneEae() == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		// on veut la liste des agents du service
		EntiteDto serviceAgent = agentSrv.getServiceAgent(ag.getIdAgent(), null);
		if (serviceAgent == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		List<Agent> listAgentService = agentSrv.listAgentServiceSansAgent(serviceAgent.getIdEntite(), ag.getIdAgent());
		String jsonResult = Agent.getSerializerForAgentDelegataire().serialize(listAgentService);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/getCampagneEnCours", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAnneCampagne() throws ParseException {

		CampagneEaeDto campagneEnCours = sirhEaeWSConsumer.getEaeCampagneOuverte();
		if (campagneEnCours == null || campagneEnCours.getIdCampagneEae() == null)
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

		String jsonResult = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.serialize(campagneEnCours);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}
}
