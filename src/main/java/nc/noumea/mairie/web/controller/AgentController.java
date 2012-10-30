package nc.noumea.mairie.web.controller;

import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Contact;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.SpSold;
import nc.noumea.mairie.model.bean.Spcong;
import nc.noumea.mairie.model.service.IAgentService;
import nc.noumea.mairie.model.service.IContactService;
import nc.noumea.mairie.model.service.IFichePosteService;
import nc.noumea.mairie.model.service.ISiguicService;
import nc.noumea.mairie.model.service.ISiservService;
import nc.noumea.mairie.model.service.ISivietService;
import nc.noumea.mairie.model.service.ISoldeService;
import nc.noumea.mairie.model.service.ISpcongService;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RooWebJson(jsonObject = Agent.class)
@Controller
@RequestMapping("/agents")
public class AgentController {

	@Autowired
	IAgentService agentSrv;

	@Autowired
	ISivietService sivietSrv;

	@Autowired
	ISiguicService siguicSrv;

	@Autowired
	IContactService contactSrv;

	@Autowired
	ISoldeService soldeSrv;

	@Autowired
	ISpcongService congSrv;

	@Autowired
	IFichePosteService fpSrv;

	@Autowired
	ISiservService siservSrv;

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

	private String remanieNoMatrAgent(Long idAgent) {
		String nomatr;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			nomatr = idAgent.toString().substring(2, idAgent.toString().length());
		} else {
			nomatr = idAgent.toString().substring(3, idAgent.toString().length());
		}
		return nomatr;
	}

	@RequestMapping(value = "/etatCivil", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getEtatCivil(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		if (ag.getCodeCommuneNaissFr() == null) {
			ag.setLieuNaissance(sivietSrv.getLieuNaissEtr(ag.getCodePaysNaissEt(), ag.getCodeCommuneNaissEt()).getLibCop());
		} else {
			ag.setLieuNaissance(ag.getCodeCommuneNaissFr().getLibVil().trim());
		}

		String jsonResult = Agent.getSerializerForAgentEtatCivil().serialize(ag);
		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/enfants", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getEnfants(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		String jsonResult = Agent.getSerializerForEnfantAgent().serialize(ag.getEnfants());

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/couvertureSociale", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getCouvertureSociale(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		String jsonResult = Agent.getSerializerForAgentCouvertureSociale().serialize(ag);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/banque", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getBanque(@RequestParam(value = "idAgent", required = true) Long idAgent) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));
		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}
		if (ag.getCodeBanque() != null && ag.getCodeGuichet() != null) {
			ag.setBanque(siguicSrv.getBanque(ag.getCodeBanque(), ag.getCodeGuichet()).getLiGuic());
		}

		String jsonResult = Agent.getSerializerForAgentBanque().serialize(ag);
		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/adresse", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getAdresse(@RequestParam(value = "idAgent", required = true) Long idAgent) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		if (ag.getVoie() == null) {
			ag.setRue(ag.getRueNonNoumea());
		} else {
			ag.setRue(ag.getVoie().getLiVoie().trim());
		}

		String jsonResult = Agent.getSerializerForAgentAdresse().serialize(ag);
		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/contacts", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getContacts(@RequestParam(value = "idAgent", required = true) Long idAgent) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		List<Contact> lc = contactSrv.getContactsAgent(Long.valueOf(newIdAgent));

		if (lc == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		String jsonResult = Contact.getSerializerForAgentContacts().serialize(lc);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/soldeConge", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getSoldeConge(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent pour avoir le nomatr
		String nomatr = remanieNoMatrAgent(idAgent);

		SpSold solde = soldeSrv.getSoldeConge(Integer.valueOf(nomatr));

		if (solde == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		String jsonResult = SpSold.getSerializerForAgentSoldeConge().serialize(solde);
		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/histoCongeAll", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getToutHistoConge(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent pour avoir le nomatr
		String nomatr = remanieNoMatrAgent(idAgent);

		List<Spcong> lcong = congSrv.getToutHistoriqueConge(Long.valueOf(nomatr));

		if (lcong == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		String jsonResult = Spcong.getSerializerForAgentHistoConge().serialize(lcong);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/histoConge", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getHistoConge(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent pour avoir le nomatr
		String nomatr = remanieNoMatrAgent(idAgent);

		List<Spcong> lcong = congSrv.getHistoriqueCongeAnnee(Long.valueOf(nomatr));

		if (lcong == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		String jsonResult = Spcong.getSerializerForAgentHistoConge().serialize(lcong);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/fichePoste", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getFichePosteAgent(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		FichePoste fp = fpSrv.getFichePosteAgentAffectationEnCours(Integer.valueOf(newIdAgent));

		if (fp == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		String jsonResult = FichePoste.getSerializerForFichePoste().serialize(fp);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/superieurHierarchique", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getSuperieurHierarchiqueAgent(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		Agent agentSuperieurHierarchique = agentSrv.getSuperieurHierarchiqueAgent(ag.getIdAgent());

		if (agentSuperieurHierarchique == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		FichePoste fichePosteSuperieurHierarchique = fpSrv.getFichePosteAgentAffectationEnCours(agentSuperieurHierarchique.getIdAgent());

		if (fichePosteSuperieurHierarchique == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		agentSuperieurHierarchique.setPosition(fichePosteSuperieurHierarchique.getTitrePoste().getLibTitrePoste());

		String jsonResult = Agent.getSerializerForAgentSuperieurHierarchique().serialize(agentSuperieurHierarchique);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/equipe", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getEquipeAgent(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		boolean estChef = fpSrv.estResponsable(ag.getIdAgent());
		List<String> listService = null;
		if (estChef) {
			// alors on regarde les sousService
			listService = siservSrv.getListServiceAgent(ag.getIdAgent());
		} else {
			Siserv serviceAgent = siservSrv.getServiceAgent(ag.getIdAgent());
			listService = new ArrayList<String>();
			listService.add(serviceAgent.getServi());
		}
		Agent agentSuperieurHierarchique = agentSrv.getSuperieurHierarchiqueAgent(ag.getIdAgent());

		Integer idAgentResp = 0;
		if (agentSuperieurHierarchique != null) {
			idAgentResp = agentSuperieurHierarchique.getIdAgent();
		}

		List<Agent> listAgentService = agentSrv.listAgentPlusieursServiceSansAgentSansSuperieur(ag.getIdAgent(), idAgentResp, listService);

		if (listAgentService == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}
		for (Agent agentService : listAgentService) {
			FichePoste fpAgentService = fpSrv.getFichePosteAgentAffectationEnCours(agentService.getIdAgent());
			agentService.setPosition(fpAgentService.getTitrePoste().getLibTitrePoste());
			if (estChef) {
				agentService.setFichePoste(fpAgentService);
			}
		}

		String jsonResult = Agent.getSerializerForAgentEquipeFichePoste().serialize(listAgentService);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/estChef", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getAgentChefPortail(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		boolean estChef = fpSrv.estResponsable(ag.getIdAgent());

		JSONObject jsonChef = new JSONObject();
		jsonChef.put("estResponsable", estChef);

		return new ResponseEntity<String>(jsonChef.toJSONString(), headers, HttpStatus.OK);
	}
}
