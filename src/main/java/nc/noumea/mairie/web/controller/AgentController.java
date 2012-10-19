package nc.noumea.mairie.web.controller;

import java.util.List;

import nc.noumea.mairie.model.bean.Activite;
import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.CadreEmploi;
import nc.noumea.mairie.model.bean.Competence;
import nc.noumea.mairie.model.bean.Contact;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.NiveauEtude;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.SpSold;
import nc.noumea.mairie.model.bean.Spcong;
import nc.noumea.mairie.model.service.IAffectationService;
import nc.noumea.mairie.model.service.IAgentService;
import nc.noumea.mairie.model.service.IContactService;
import nc.noumea.mairie.model.service.IFichePosteService;
import nc.noumea.mairie.model.service.ISiguicService;
import nc.noumea.mairie.model.service.ISiservService;
import nc.noumea.mairie.model.service.ISivietService;
import nc.noumea.mairie.model.service.ISoldeService;
import nc.noumea.mairie.model.service.ISpcongService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
	IAffectationService affSrv;

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
		String service = "";
		if (fp.getService().getServi() != null) {
			Siserv divisionService = siservSrv.getDivision(fp.getService().getServi());
			if (divisionService != null) {
				service = divisionService.getLiServ();
			} else {
				Siserv servicePoste = siservSrv.getService(fp.getService().getServi());
				if (servicePoste != null) {
					service = servicePoste.getLiServ();
				}
			}
		}
		String direction = "";
		if (fp.getService().getServi() != null) {
			Siserv directionService = siservSrv.getDirection(fp.getService().getServi());
			if (directionService != null) {
				direction = directionService.getLiServ();
			}
		}
		String section = "";
		if (fp.getService().getServi() != null) {
			Siserv sectionService = siservSrv.getSection(fp.getService().getServi());
			if (sectionService != null) {
				section = sectionService.getLiServ();
			}
		}
		String res = fp.fpToJson(Activite.getSerializerForActivite().serialize(fp.getActivites()),
				Competence.competenceToJsonArray(fp.getCompetences()), CadreEmploi.getSerializerForCadreEmploi().serialize(fp.getCardresEmploi()),
				NiveauEtude.getSerializerForNiveauEtude().serialize(fp.getNiveauEtude()), service, direction, section);

		return new ResponseEntity<String>(res.replace("\"[", "[").replace("]\"", "]"), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/superieurHierarchique", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getSuperieurHierarchiqueAgent(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		FichePoste fp = fpSrv.getFichePosteAgentAffectationEnCours(Integer.valueOf(newIdAgent));

		if (fp == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}
		Affectation affSuperieurHierarchique = affSrv.getAffectationFP(fp.getResponsable().getIdFichePoste());

		if (affSuperieurHierarchique == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}
		Agent superieurHierarchique = agentSrv.getAgent(affSuperieurHierarchique.getAgent().getIdAgent());

		if (superieurHierarchique == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		FichePoste fichePosteSuperieurHierarchique = fpSrv.getFichePoste(affSuperieurHierarchique.getFichePoste().getIdFichePoste());

		if (fichePosteSuperieurHierarchique == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}
		superieurHierarchique.setPosition(fichePosteSuperieurHierarchique.getTitrePoste().getLibTitrePoste());

		String jsonResult = Agent.getSerializerForAgentSuperieurHierarchique().serialize(superieurHierarchique);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/equipe", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getEquipeAgent(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		// si la personne est chef alors on affiche aussi les FDP de l'équipe
		boolean estAgentChef = getAgentChef(idAgent);

		FichePoste fpAgent = fpSrv.getFichePosteAgentAffectationEnCours(Integer.valueOf(newIdAgent));

		if (fpAgent == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		// on regarde qui est le chef de la personne
		Affectation affSuperieurHierarchique = affSrv.getAffectationFP(fpAgent.getResponsable().getIdFichePoste());

		if (affSuperieurHierarchique == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		String codeService = fpAgent.getService().getServi();
		while (codeService.endsWith("A")) {
			codeService = codeService.substring(0, codeService.length() - 1);
		}
		List<Agent> lagentService = agentSrv.getAgentService(codeService, Integer.valueOf(newIdAgent), affSuperieurHierarchique.getAgent()
				.getIdAgent());

		if (lagentService == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		String test = new JSONSerializer().exclude("*.class").serialize(lagentService);
		JSONArray jsonAr = null;
		try {
			jsonAr = (JSONArray) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonAr.size(); i++) {
			JSONObject json = (JSONObject) jsonAr.get(i);
			String nomAgentEquipe = (String) json.get("nomUsage");
			String prenomAgentEquipe = (String) json.get("prenomUsage");
			String civiliteAgentEquipe = (String) json.get("titre");
			// pour le titre du poste
			Long idAgentEquipe = (Long) json.get("idAgent");
			String objetFichePoste = getFichePosteAgentEquipe(idAgentEquipe);
			FichePoste fp = fpSrv.getFichePosteAgentAffectationEnCours(idAgentEquipe.intValue());
			json = agentSrv.removeAll(json);
			json.put("position", fp.getTitrePoste().getLibTitrePoste().trim());
			json.put("nom", nomAgentEquipe);
			json.put("prenom", prenomAgentEquipe);
			json.put("titre", civiliteAgentEquipe);
			if (estAgentChef) {
				json.put("fichePoste", objetFichePoste);

			}
			jsonAr.remove(i);
			jsonAr.add(i, json);
		}

		return new ResponseEntity<String>(jsonAr.toJSONString().replace("\\", "").replace("\"[", "[").replace("]\"", "]").replace("\"{", "{")
				.replace("}\"", "}"), headers, HttpStatus.OK);
	}

	public boolean getAgentChef(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		FichePoste fpAgent = fpSrv.getFichePosteAgentAffectationEnCours(Integer.valueOf(newIdAgent));

		if (fpAgent == null) {
			return false;
		}
		Integer idFichePosteAgent = fpAgent.getIdFichePoste();

		List<FichePoste> lfpAgentService = fpSrv.getFichePosteAgentService(fpAgent.getService().getServi(), Integer.valueOf(newIdAgent));

		if (lfpAgentService == null) {
			return false;
		}

		// on parcours la liste des agents du service et on regarde si la fiche
		// de poste de l'agent est responsable de au moins 1 personne de la
		// liste
		String test = new JSONSerializer().exclude("*.class").serialize(lfpAgentService);
		JSONArray jsonAr = null;
		try {
			jsonAr = (JSONArray) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		boolean estResponsable = false;
		for (int i = 0; i < jsonAr.size(); i++) {
			JSONObject json = (JSONObject) jsonAr.get(i);
			JSONObject responsable = (JSONObject) json.get("responsable");
			Long idResp = (Long) responsable.get("idFichePoste");
			if (idResp.toString().equals(idFichePosteAgent.toString())) {
				estResponsable = true;
				break;
			}
		}

		return estResponsable;
	}

	public String getFichePosteAgentEquipe(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		FichePoste fp = fpSrv.getFichePosteAgentAffectationEnCours(Integer.valueOf(newIdAgent));

		if (fp == null) {
			return null;
		}
		String service = "";
		if (fp.getService().getServi() != null) {
			Siserv divisionService = siservSrv.getDivision(fp.getService().getServi());
			if (divisionService != null) {
				service = divisionService.getLiServ();
			} else {
				Siserv servicePoste = siservSrv.getService(fp.getService().getServi());
				if (servicePoste != null) {
					service = servicePoste.getLiServ();
				}
			}
		}
		String direction = "";
		if (fp.getService().getServi() != null) {
			Siserv directionService = siservSrv.getDirection(fp.getService().getServi());
			if (directionService != null) {
				direction = directionService.getLiServ();
			}
		}
		String section = "";
		if (fp.getService().getServi() != null) {
			Siserv sectionService = siservSrv.getSection(fp.getService().getServi());
			if (sectionService != null) {
				section = sectionService.getLiServ();
			}
		}
		String res = fp.fpToJson(Activite.getSerializerForActivite().serialize(fp.getActivites()),
				Competence.competenceToJsonArray(fp.getCompetences()), CadreEmploi.getSerializerForCadreEmploi().serialize(fp.getCardresEmploi()),
				NiveauEtude.getSerializerForNiveauEtude().serialize(fp.getNiveauEtude()), service, direction, section);

		return res;
	}

	@RequestMapping(value = "/estChef", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getAgentChefPortail(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		JSONObject jsonChef = new JSONObject();
		boolean estResponsable = false;
		jsonChef.put("estResponsable", estResponsable);

		FichePoste fpAgent = fpSrv.getFichePosteAgentAffectationEnCours(Integer.valueOf(newIdAgent));

		if (fpAgent == null) {
			return new ResponseEntity<String>(jsonChef.toJSONString(), headers, HttpStatus.OK);
		}
		Integer idFichePosteAgent = fpAgent.getIdFichePoste();

		List<FichePoste> lfpAgentService = fpSrv.getFichePosteAgentService(fpAgent.getService().getServi(), Integer.valueOf(newIdAgent));

		if (lfpAgentService == null) {
			return new ResponseEntity<String>(jsonChef.toJSONString(), headers, HttpStatus.OK);
		}

		// on parcours la liste des agents du service et on regarde si la fiche
		// de poste de l'agent est responsable de au moins 1 personne de la
		// liste
		String test = new JSONSerializer().exclude("*.class").serialize(lfpAgentService);
		JSONArray jsonAr = null;
		try {
			jsonAr = (JSONArray) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonAr.size(); i++) {
			JSONObject json = (JSONObject) jsonAr.get(i);
			JSONObject responsable = (JSONObject) json.get("responsable");
			Long idResp = (Long) responsable.get("idFichePoste");
			if (idResp.toString().equals(idFichePosteAgent.toString())) {
				estResponsable = true;
				break;
			}
		}
		jsonChef.put("estResponsable", estResponsable);

		return new ResponseEntity<String>(jsonChef.toJSONString(), headers, HttpStatus.OK);
	}
}
