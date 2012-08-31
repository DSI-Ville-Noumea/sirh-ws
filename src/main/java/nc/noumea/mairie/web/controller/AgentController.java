package nc.noumea.mairie.web.controller;

import java.util.List;

import nc.noumea.mairie.model.bean.Activite;
import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.CadreEmploi;
import nc.noumea.mairie.model.bean.Competence;
import nc.noumea.mairie.model.bean.Contact;
import nc.noumea.mairie.model.bean.Enfant;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.NiveauEtude;
import nc.noumea.mairie.model.bean.SIVIET;
import nc.noumea.mairie.model.bean.Siguic;
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

	@RequestMapping(value = "/etatCivil", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getEtatCivil(
			@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2,
					idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		String res = "";
		if (ag.getCodeCommuneNaissFr() == null) {
			SIVIET siviet = sivietSrv.getLieuNaissEtr(ag.getCodePaysNaissEt(),
					ag.getCodeCommuneNaissEt());
			if (siviet != null) {
				res = siviet.lieuNaissancetoJson();
				// TODO
				// pour le moment on fait de la bidouille
				res = res.replace("{", "");
				res = res.replace("}", "");
				res = res.substring(res.indexOf(":") + 1, res.length());
				res = res.replace("\"", "");
				ag.setLieuNaissance(res);
			}
		}
		return new ResponseEntity<String>(ag.etatCivilToJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/enfants", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getEnfants(
			@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2,
					idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		String res = Enfant.enfantToJsonArray(ag.getEnfants(), sivietSrv);

		return new ResponseEntity<String>(res, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/couvertureSociale", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getCouvertureSociale(
			@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2,
					idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(ag.couvertureSocialeToJson(),
				headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/banque", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getBanque(
			@RequestParam(value = "idAgent", required = true) Long idAgent) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2,
					idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));
		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		String res = "";
		if (ag.getCodeBanque() != null && ag.getCodeGuichet() != null) {
			Siguic siguic = siguicSrv.getBanque(ag.getCodeBanque(),
					ag.getCodeGuichet());
			if (siguic != null) {
				res = siguic.banqueToJson();
				// TODO
				// pour le moment on fait de la bidouille
				res = res.replace("{", "");
				res = res.replace("}", "");
				res = res.substring(res.indexOf(":") + 1, res.length());
				res = res.replace("\"", "");
				ag.setBanque(res);
			}
		}
		return new ResponseEntity<String>(ag.banqueToJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/adresse", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getAdresse(
			@RequestParam(value = "idAgent", required = true) Long idAgent) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2,
					idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(ag.adresseToJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/contacts", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getContacts(
			@RequestParam(value = "idAgent", required = true) Long idAgent) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2,
					idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}

		List<Contact> lc = contactSrv
				.getContactsAgent(Long.valueOf(newIdAgent));

		if (lc == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		JSONArray jsonLfp = null;
		try {
			jsonLfp = (JSONArray) new JSONParser().parse(Contact
					.contactToJson(lc));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(jsonLfp.toJSONString(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/soldeConge", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getSoldeConge(
			@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent pour avoir le nomatr
		String nomatr;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			nomatr = idAgent.toString().substring(2,
					idAgent.toString().length());
		} else {
			nomatr = idAgent.toString().substring(3,
					idAgent.toString().length());
		}
		SpSold solde = soldeSrv.getSoldeConge(Integer.valueOf(nomatr));

		if (solde == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(solde.soldeToJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/histoCongeAll", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getToutHistoConge(
			@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent pour avoir le nomatr
		String nomatr;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			nomatr = idAgent.toString().substring(2,
					idAgent.toString().length());
		} else {
			nomatr = idAgent.toString().substring(3,
					idAgent.toString().length());
		}

		List<Spcong> lcong = congSrv.getToutHistoriqueConge(Long
				.valueOf(nomatr));

		if (lcong == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		JSONArray jsonLcong = null;
		try {
			jsonLcong = (JSONArray) new JSONParser().parse(Spcong
					.congeToJson(lcong));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(jsonLcong.toJSONString(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/histoConge", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getHistoConge(
			@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent pour avoir le nomatr
		String nomatr;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			nomatr = idAgent.toString().substring(2,
					idAgent.toString().length());
		} else {
			nomatr = idAgent.toString().substring(3,
					idAgent.toString().length());
		}

		List<Spcong> lcong = congSrv.getHistoriqueCongeAnnee(Long
				.valueOf(nomatr));

		if (lcong == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		JSONArray jsonLcong = null;
		try {
			jsonLcong = (JSONArray) new JSONParser().parse(Spcong
					.congeToJson(lcong));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (jsonLcong == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(jsonLcong.toJSONString(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/fichePoste", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getFichePosteAgent(
			@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2,
					idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}
		FichePoste fp = fpSrv.getFichePosteAgentAffectationEnCours(Integer
				.valueOf(newIdAgent));

		if (fp == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		String service = "";
		if (fp.getService().getServi() != null) {
			Siserv divisionService = siservSrv.getDivision(fp.getService()
					.getServi());
			if (divisionService != null) {
				service = divisionService.getLiServ();
			} else {
				Siserv servicePoste = siservSrv.getService(fp.getService()
						.getServi());
				if (servicePoste != null) {
					service = servicePoste.getLiServ();
				}
			}
		}
		String direction = "";
		if (fp.getService().getServi() != null) {
			Siserv directionService = siservSrv.getDirection(fp.getService()
					.getServi());
			if (directionService != null) {
				direction = directionService.getLiServ();
			}
		}
		String section = "";
		if (fp.getService().getServi() != null) {
			Siserv sectionService = siservSrv.getSection(fp.getService()
					.getServi());
			if (sectionService != null) {
				section = sectionService.getLiServ();
			}
		}
		String res = fp.fpToJson(
				Activite.activiteToJsonArray(fp.getActivites()),
				Competence.competenceToJsonArray(fp.getCompetences()),
				CadreEmploi.cadreEmploiToJsonArray(fp.getCardresEmploi()),
				NiveauEtude.niveauEtudeToJsonArray(fp.getNiveauEtude()),
				service, direction, section);

		return new ResponseEntity<String>(res, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/superieurHierarchique", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getSuperieurHierarchiqueAgent(
			@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2,
					idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}

		FichePoste fp = fpSrv.getFichePosteAgentAffectationEnCours(Integer
				.valueOf(newIdAgent));

		if (fp == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		Affectation affSuperieurHierarchique = affSrv.getAffectationFP(fp
				.getResponsable().getIdFichePoste());

		if (affSuperieurHierarchique == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		Agent superieurHierarchique = agentSrv
				.getAgent(affSuperieurHierarchique.getAgent().getIdAgent());

		if (superieurHierarchique == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		FichePoste fichePosteSuperieurHierarchique = fpSrv
				.getFichePoste(affSuperieurHierarchique.getFichePoste()
						.getIdFichePoste());

		if (fichePosteSuperieurHierarchique == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}

		String res = superieurHierarchique
				.responsableHierarchiqueToJson(fichePosteSuperieurHierarchique
						.getTitrePoste().getLibTitrePoste());

		return new ResponseEntity<String>(res, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/equipe", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getEquipeAgent(
			@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2,
					idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}

		FichePoste fpAgent = fpSrv.getFichePosteAgentAffectationEnCours(Integer
				.valueOf(newIdAgent));

		if (fpAgent == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		List<Agent> lagentService = agentSrv.getAgentService(fpAgent
				.getService().getServi(), Integer.valueOf(newIdAgent));

		if (lagentService == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}

		String test = new JSONSerializer().exclude("*.class").serialize(
				lagentService);
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
			String civiliteAgentEquipe = (String) json.get("civilite");
			// pour le titre du poste
			Long idAgentEquipe = (Long) json.get("idAgent");
			FichePoste fp = fpSrv
					.getFichePosteAgentAffectationEnCours(idAgentEquipe
							.intValue());
			json = agentSrv.removeAll(json);
			json.put("position", fp.getTitrePoste().getLibTitrePoste().trim());
			json.put("nom", nomAgentEquipe);
			json.put("prenom", prenomAgentEquipe);
			if (civiliteAgentEquipe.equals("0")) {
				json.put("titre", "Monsieur");
			} else if (civiliteAgentEquipe.equals("1")) {
				json.put("titre", "Madame");
			} else {
				json.put("titre", "Mademoiselle");
			}
			jsonAr.remove(i);
			jsonAr.add(i, json);
		}

		return new ResponseEntity<String>(jsonAr.toJSONString(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/estChef", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getAgentChef(
			@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			// on remanie l'idAgent
			String matr = idAgent.toString().substring(2,
					idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}

		FichePoste fpAgent = fpSrv.getFichePosteAgentAffectationEnCours(Integer
				.valueOf(newIdAgent));

		if (fpAgent == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		Long idFichePosteAgent = fpAgent.getIdFichePoste();

		List<FichePoste> lfpAgentService = fpSrv.getFichePosteAgentService(
				fpAgent.getService().getServi(), Integer.valueOf(newIdAgent));

		if (lfpAgentService == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}

		// on parcours la liste des agents du service et on regarde si la fiche
		// de poste de l'agent est responsable de au moins 1 personne de la
		// liste
		String test = new JSONSerializer().exclude("*.class").serialize(
				lfpAgentService);
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
		JSONObject json = new JSONObject();
		json.put("estResponsable", estResponsable);

		return new ResponseEntity<String>(json.toJSONString(), headers,
				HttpStatus.OK);
	}
}
