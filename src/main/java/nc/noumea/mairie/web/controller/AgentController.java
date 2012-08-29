package nc.noumea.mairie.web.controller;

import java.util.List;
import java.util.Set;

import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Contact;
import nc.noumea.mairie.model.bean.Enfant;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.SIVIET;
import nc.noumea.mairie.model.bean.Siguic;
import nc.noumea.mairie.model.bean.SpSold;
import nc.noumea.mairie.model.bean.Spcong;
import nc.noumea.mairie.model.service.IAffectationService;
import nc.noumea.mairie.model.service.IAgentService;
import nc.noumea.mairie.model.service.IContactService;
import nc.noumea.mairie.model.service.IFichePosteService;
import nc.noumea.mairie.model.service.ISiguicService;
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

	@RequestMapping(value = "/tst", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> tst() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Agent> result = Agent.findAllAgents();
		return new ResponseEntity<String>(Agent.toJsonArray(result), headers,
				HttpStatus.OK);
	}

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

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
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

		return new ResponseEntity<String>(Enfant.toJsonArray(ag.getEnfants()), headers,
				HttpStatus.OK);
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
		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
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
		FichePoste fp = fpSrv.getFichePosteAgent(Integer.valueOf(newIdAgent));

		if (fp == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(fp.fpToJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/titulaireFichePoste", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getTitulaireFichePosteAgent(
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
		Agent ag = agentSrv.getAgentAffectationCourante(Integer
				.valueOf(newIdAgent));
		Affectation aff = affSrv.getAffectationCouranteAgent(Integer
				.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		if (aff == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		//String res = ag.titulaireFPToJson() + aff.infoTitulaire();
		String res = ag.titulaireFPToJson() ;
		return new ResponseEntity<String>(res, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/fichePosteEnfant", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getFichePosteEnfants(
			@RequestParam(value = "idAgent", required = true) Long idAgent) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// on remanie l'idAgent
		String newIdAgent;
		if (idAgent.toString().length() == 6) {
			String matr = idAgent.toString().substring(2,
					idAgent.toString().length());
			String prefixe = idAgent.toString().substring(0, 2);
			newIdAgent = prefixe + "0" + matr;
		} else {
			newIdAgent = idAgent.toString();
		}

		List<FichePoste> lfp = agentSrv.getFichePosteEnfant(Integer
				.valueOf(newIdAgent));

		if (lfp == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}

		// On supprimer du résultat json le champ responsable
		JSONArray jsonLfp = null;
		try {
			jsonLfp = (JSONArray) new JSONParser().parse(FichePoste
					.toJsonArray(lfp));

			for (int i = 0; i < jsonLfp.size(); i++) {
				JSONObject ob = (JSONObject) jsonLfp.get(i);
				ob.remove("responsable");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(headers,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(jsonLfp.toJSONString(), headers,
				HttpStatus.OK);
	}
}
