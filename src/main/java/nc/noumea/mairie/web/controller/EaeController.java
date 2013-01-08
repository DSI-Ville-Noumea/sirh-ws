package nc.noumea.mairie.web.controller;

import java.util.List;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.eae.Eae;
import nc.noumea.mairie.model.bean.eae.EaeCampagne;
import nc.noumea.mairie.model.service.IAgentService;
import nc.noumea.mairie.model.service.IEaeCampagneService;
import nc.noumea.mairie.model.service.IFichePosteService;
import nc.noumea.mairie.model.service.ISiservService;
import nc.noumea.mairie.model.service.eae.IEaeService;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@RooWebJson(jsonObject = Eae.class)
@Controller
@RequestMapping("/eaes")
public class EaeController {

	@Autowired
	private IEaeService eaeService;

	@Autowired
	private IEaeCampagneService eaeCampagneService;

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private IFichePosteService fpSrv;

	@Autowired
	private ISiservService siservSrv;

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

	@RequestMapping(value = "/estHabiliteEAE", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getAgentHabilite(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.UNAUTHORIZED);
		}

		EaeCampagne campagneEnCours = eaeCampagneService.getEaeCampagneOuverte();
		if (campagneEnCours == null)
			return new ResponseEntity<String>(headers, HttpStatus.UNAUTHORIZED);

		// on regarde si la personne connectée est chef
		boolean estChef = fpSrv.estResponsable(ag.getIdAgent());
		List<String> listService = null;
		if (estChef) {
			// alors on regarde les sousService
			listService = siservSrv.getListServiceAgent(ag.getIdAgent());
		}

		Integer nbEae = eaeService.compterlistIdEaeByCampagneAndAgent(campagneEnCours.getIdCampagneEae(), ag.getIdAgent(), listService);

		if (nbEae == 0) {
			return new ResponseEntity<String>(headers, HttpStatus.UNAUTHORIZED);
		}

		JSONObject jsonHabiliteEAE = new JSONObject();
		return new ResponseEntity<String>(jsonHabiliteEAE.toJSONString(), headers, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping("/listEaesByCampagne")
	@Transactional(readOnly = true)
	public ResponseEntity<String> listEaesByCampagne(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}
		EaeCampagne campagneEnCours = null;
		try {
			campagneEnCours = eaeCampagneService.getEaeCampagneOuverte();
		} catch (Exception e) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}
		if (campagneEnCours == null)
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);

		// on regarde si la personne connectée est chef
		boolean estChef = fpSrv.estResponsable(ag.getIdAgent());
		List<String> listService = null;
		//Pour le moment suppression de cette partie en attendant l'arbre des fiche de poste
		/*if (estChef) {
			// alors on regarde les sousService
			listService = siservSrv.getListServiceAgent(ag.getIdAgent());
		}*/

		List<Integer> listIdEaeCampagne = eaeService.listIdEaeByCampagneAndAgent(campagneEnCours.getIdCampagneEae(), ag.getIdAgent(), listService);

		if (listIdEaeCampagne.isEmpty())
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);

		String jsonResult = new JSONSerializer().serialize(listIdEaeCampagne);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping("/listDelegataire")
	@Transactional(readOnly = true)
	public ResponseEntity<String> listDelegataireByAgent(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		EaeCampagne campagneEnCours = eaeCampagneService.getEaeCampagneOuverte();
		if (campagneEnCours == null)
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);

		// on veut la liste des agents du service
		Siserv serviceAgent = siservSrv.getServiceAgent(ag.getIdAgent());
		if (serviceAgent == null)
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);

		List<Agent> listAgentService = agentSrv.listAgentServiceSansAgent(serviceAgent.getServi(), ag.getIdAgent());
		String jsonResult = Agent.getSerializerForAgentDelegataire().serialize(listAgentService);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}
}
