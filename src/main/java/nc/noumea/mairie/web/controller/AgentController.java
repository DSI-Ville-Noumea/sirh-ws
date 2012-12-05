package nc.noumea.mairie.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import nc.noumea.mairie.model.service.ISpadmnService;
import nc.noumea.mairie.model.service.ISpcongService;
import nc.noumea.mairie.tools.ServiceTreeNode;

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

import flexjson.JSONSerializer;

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

	@Autowired
	ISpadmnService spadmnSrv;

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

		String jsonResult = Agent.getSerializerForEnfantAgent().serialize(ag.getParentEnfantsOrderByDateNaiss());

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
	public ResponseEntity<String> getFichePosteAgent(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException,
			java.text.ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = sdf.parse(dateTemp);

		FichePoste fp = fpSrv.getFichePostePrimaireAgentAffectationEnCours(Integer.valueOf(newIdAgent), dateJour);

		if (fp == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		String jsonResult = FichePoste.getSerializerForFichePoste().serialize(fp);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/fichePosteSecondaire", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getFichePosteSecondaireAgent(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException,
			java.text.ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = sdf.parse(dateTemp);

		FichePoste fp = fpSrv.getFichePosteSecondaireAgentAffectationEnCours(Integer.valueOf(newIdAgent), dateJour);

		if (fp == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		String jsonResult = FichePoste.getSerializerForFichePoste().serialize(fp);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/superieurHierarchique", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getSuperieurHierarchiqueAgent(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException, java.text.ParseException {
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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = sdf.parse(dateTemp);
		FichePoste fichePosteSuperieurHierarchique = fpSrv.getFichePostePrimaireAgentAffectationEnCours(agentSuperieurHierarchique.getIdAgent(),
				dateJour);

		if (fichePosteSuperieurHierarchique == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		agentSuperieurHierarchique.setPosition(fichePosteSuperieurHierarchique.getTitrePoste().getLibTitrePoste());

		String jsonResult = Agent.getSerializerForAgentSuperieurHierarchique().serialize(agentSuperieurHierarchique);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/equipe", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getEquipeAgent(@RequestParam(value = "idAgent", required = true) Long idAgent, @RequestParam(value = "sigleService", required = false) String sigleService) throws ParseException,
			java.text.ParseException {
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
			listService = siservSrv.getListServiceAgent(ag.getIdAgent(), sigleService);
		} else {
			Siserv serviceAgent = siservSrv.getServiceAgent(ag.getIdAgent());
			listService = new ArrayList<String>();
			listService.add(serviceAgent.getServi());
		}
		
		if (listService.size() == 0) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = sdf.parse(dateTemp);
		for (Agent agentService : listAgentService) {
			String titrePoste = fpSrv.getTitrePosteAgent(agentService.getIdAgent(), dateJour);
			agentService.setPosition(titrePoste);
		}

		String jsonResult = Agent.getSerializerForAgentEquipeFichePoste().serialize(listAgentService);
		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/serviceArbre", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getServiceArbre(@RequestParam(value = "idAgent", required = true) Long idAgent) {
		
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		// Si l'agent n'existe pas ou n'est pas chef, on ne retourne rien
		if (ag == null || !fpSrv.estResponsable(ag.getIdAgent()))
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		// On récupère le noeud parent des services de la personne
		ServiceTreeNode treeHead = siservSrv.getAgentServiceTree(ag.getIdAgent());
		List<ServiceTreeNode> treeHeadList = new ArrayList<ServiceTreeNode>();
		treeHeadList.add(treeHead);
		
		JSONSerializer serializer = new JSONSerializer()
			.exclude("*.class")	
			.exclude("*.serviceParent")
			.exclude("*.sigleParent");

		return new ResponseEntity<String>(serializer.deepSerialize(treeHeadList), HttpStatus.OK);
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

	@RequestMapping(value = "/estHabiliteKiosqueRH", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getAgentHabilitePortail(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newNomatrAgent = remanieNoMatrAgent(idAgent);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Integer dateJour = Integer.valueOf(sdf.format(new Date()));

		boolean estHabilite = spadmnSrv.estPAActive(Integer.valueOf(newNomatrAgent), dateJour);

		JSONObject jsonHabiliteKiosque = new JSONObject();
		jsonHabiliteKiosque.put("estHabiliteKiosqueRH", estHabilite);

		return new ResponseEntity<String>(jsonHabiliteKiosque.toJSONString(), headers, HttpStatus.OK);
	}
}
