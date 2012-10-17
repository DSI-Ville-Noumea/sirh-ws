package nc.noumea.mairie.web.controller;

import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.eae.Eae;
import nc.noumea.mairie.model.bean.eae.EaeCampagne;
import nc.noumea.mairie.model.bean.eae.EaeEvaluateur;
import nc.noumea.mairie.model.bean.eae.EaeFichePoste;
import nc.noumea.mairie.model.service.IAgentService;
import nc.noumea.mairie.model.service.IEaeCampagneService;
import nc.noumea.mairie.model.service.IFichePosteService;
import nc.noumea.mairie.model.service.eae.EaeFichePosteServiceException;
import nc.noumea.mairie.model.service.eae.IEaeEvaluateurService;
import nc.noumea.mairie.model.service.eae.IEaeFichePosteService;
import nc.noumea.mairie.model.service.eae.IEaeService;

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
	private IFichePosteService fdpService;

	@Autowired
	private IEaeFichePosteService eaeFichePosteService;

	@Autowired
	private IEaeCampagneService eaeCampagneService;

	@Autowired
	private IEaeEvaluateurService eaeEvaluateurService;

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private IFichePosteService fpSrv;

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("listEaesByAgent") public ResponseEntity<String>
	 * listEaesByAgent(
	 * 
	 * @RequestParam("idAgent") int idAgent) {
	 * 
	 * HttpHeaders headers = new HttpHeaders(); headers.add("Content-Type",
	 * "application/json; charset=utf-8");
	 * 
	 * return new ResponseEntity<String>(Eae.getSerializerForEaeList()
	 * .serialize(result), headers, HttpStatus.OK); }
	 */

	@ResponseBody
	@RequestMapping("/estKiosqueOuvert")
	public ResponseEntity<String> getKiosqueOuvert() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		JSONObject jsonKiosqueOuvert = new JSONObject();
		jsonKiosqueOuvert.put("estKiosqueOuvert", false);

		EaeCampagne campagneEnCours = eaeCampagneService
				.getEaeCampagneOuverte();

		if (campagneEnCours != null)
			jsonKiosqueOuvert.put("estKiosqueOuvert", true);

		return new ResponseEntity<String>(jsonKiosqueOuvert.toJSONString(),
				headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/estHabiliteEAE", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getAgentHabilite(
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
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}
		JSONObject jsonAgentHabiliteEAE = new JSONObject();
		jsonAgentHabiliteEAE.put("estHabiliteEAE", false);

		EaeCampagne campagneEnCours = eaeCampagneService
				.getEaeCampagneOuverte();

		// si evaluateur
		List<EaeEvaluateur> listEval = eaeEvaluateurService
				.listerEaeEvaluateurAgent(Integer.valueOf(newIdAgent),
						campagneEnCours.getIdCampagneEae());

		if (listEval.size() > 0) {
			jsonAgentHabiliteEAE.put("estHabiliteEAE", true);
			return new ResponseEntity<String>(
					jsonAgentHabiliteEAE.toJSONString(), headers, HttpStatus.OK);
		}

		// si delegataire
		List<Eae> listEeaDelegataire = eaeService
				.listerEaeDelegataire(Integer.valueOf(newIdAgent),
						campagneEnCours.getIdCampagneEae());

		if (listEeaDelegataire.size() > 0) {
			jsonAgentHabiliteEAE.put("estHabiliteEAE", true);
			return new ResponseEntity<String>(
					jsonAgentHabiliteEAE.toJSONString(), headers, HttpStatus.OK);
		}

		// si SHD
		List<Eae> listEeaSHD = eaeService
				.listerEaeSHD(Integer.valueOf(newIdAgent),
						campagneEnCours.getIdCampagneEae());

		if (listEeaSHD.size() > 0) {
			jsonAgentHabiliteEAE.put("estHabiliteEAE", true);
			return new ResponseEntity<String>(
					jsonAgentHabiliteEAE.toJSONString(), headers, HttpStatus.OK);
		}

		// si chef on voit le service
		FichePoste fpAgent = fpSrv.getFichePosteAgentAffectationEnCours(Integer
				.valueOf(newIdAgent));
		if (fpAgent != null) {
			Integer idFichePosteAgent = fpAgent.getIdFichePoste();

			List<FichePoste> lfpAgentService = fpSrv.getFichePosteAgentService(
					fpAgent.getService().getServi(),
					Integer.valueOf(newIdAgent));

			// on parcours la liste des agents du service et on regarde si la
			// fiche
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
			for (int i = 0; i < jsonAr.size(); i++) {
				JSONObject json = (JSONObject) jsonAr.get(i);
				JSONObject responsable = (JSONObject) json.get("responsable");
				Long idResp = (Long) responsable.get("idFichePoste");
				if (idResp.toString().equals(idFichePosteAgent.toString())) {
					jsonAgentHabiliteEAE.put("estHabiliteEAE", true);
					break;
				}
			}
		}

		return new ResponseEntity<String>(jsonAgentHabiliteEAE.toJSONString(),
				headers, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping("/listEaesByCampagne")
	@Transactional(readOnly = true)
	public ResponseEntity<String> listEaesByCampagne(
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
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);
		}

		EaeCampagne campagneEnCours = eaeCampagneService
				.getEaeCampagneOuverte();

		List<Eae> listEaeCampagne = eaeService
				.listEaesByCampagne(campagneEnCours.getIdCampagneEae());

		if (listEaeCampagne.isEmpty())
			return new ResponseEntity<String>(headers, HttpStatus.NO_CONTENT);

		List<Eae> result = new ArrayList<Eae>();
		for (int i = 0; i < listEaeCampagne.size(); i++) {
			Eae eae = listEaeCampagne.get(i);
			// si l'agent connecté est delegataire
			/*
			 * if (eae.getAgentDelegataire() != null &&
			 * eae.getAgentDelegataire().getIdAgent().toString()
			 * .equals(ag.getIdAgent().toString())) { if (!result.contains(eae))
			 * { result.add(eae); } }
			 */
			// si l'agent connecté est évaluateur
			/*
			 * if (eae.getEaeEvaluateurs() != null) { for
			 * (Iterator<EaeEvaluateur> iterator = eae.getEaeEvaluateurs()
			 * .iterator(); iterator.hasNext();) { EaeEvaluateur eval =
			 * (EaeEvaluateur) iterator.next(); if
			 * (eval.getAgent().getIdAgent().toString()
			 * .equals(ag.getIdAgent().toString())) { if (!result.contains(eae))
			 * { result.add(eae); } } } }
			 */

			// si SHD
			/*
			 * if (eae.getEaeFichePoste() != null &&
			 * eae.getEaeFichePoste().getIdAgentShd() != null) { if
			 * (eae.getEaeFichePoste().getIdAgentShd().toString()
			 * .equals(ag.getIdAgent().toString())) { if (!result.contains(eae))
			 * { result.add(eae); }
			 * 
			 * } }
			 */
			if (eae.getEaeFichePoste() != null
					&& eae.getEaeFichePoste().getCodeService() != null) {
				EaeFichePoste eaeFDP = eae.getEaeFichePoste();
				try {
					eaeFichePosteService.setService(eaeFDP,
							eaeFDP.getCodeService());
					FichePoste fdpConnecte = fdpService
							.getFichePosteAgentAffectationEnCours(Integer
									.valueOf(newIdAgent));
					if (fdpConnecte.getService().getServi()
							.equals(eaeFDP.getCodeService())) {
						if (!result.contains(eae)) {
							result.add(eae);
						}
					}
				} catch (EaeFichePosteServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		String jsonResult = Eae.getSerializerForEaeList().serialize(result);

		return new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);
	}
}
