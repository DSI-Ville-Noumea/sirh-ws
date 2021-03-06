package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Activite;
import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Competence;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.repository.sirh.IFichePosteRepository;
import nc.noumea.mairie.service.IReportingService;
import nc.noumea.mairie.service.sirh.IAgentMatriculeConverterService;
import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.service.sirh.IFichePosteService;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.FichePosteDto;
import nc.noumea.mairie.web.dto.FichePosteTreeNodeDto;
import nc.noumea.mairie.web.dto.InfoEntiteDto;
import nc.noumea.mairie.web.dto.SpbhorDto;
import nc.noumea.mairie.ws.IADSWSConsumer;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/fichePostes")
public class FichePosteController {

	private Logger logger = LoggerFactory.getLogger(FichePosteController.class);

	@Autowired
	private IFichePosteService fpSrv;

	@Autowired
	private IAgentMatriculeConverterService agentMatriculeConverterService;

	@Autowired
	private IReportingService reportingService;

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private IADSWSConsumer adsWSConsumer;

	@Autowired
	private IFichePosteRepository fichePosteDao;

	/**
	 * Re-construit l arbre des fiches de poste
	 * Ce WS est appelé depuis :
	 *  - SIRH à la création/modification d une fiche de poste
	 * @return 200
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/rebuildFichePosteTree")
	@Transactional(readOnly = true)
	public ResponseEntity<String> rebuildFichePosteTree() throws ParseException {

		logger.debug("entered GET [fichePostes/rebuildFichePosteTree] => rebuildFichePosteTree");

		try {
			fpSrv.construitArbreFichePostes();
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.toString(), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	//TODO a verifier si utilisé et nettoyer code
	/**
	 * Ce WS n a plus l air de servir a un projet
	 *
	 * @param idAgent L ID de l agent
	 * @param maxDepth le nombre de niveau dans l arbre
	 * @return Les fiches de poste dependants d un agent 
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSubFichePostes", produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getSubFichePostes(@RequestParam("idAgent") int idAgent, @RequestParam(value = "maxDepth", required = false, defaultValue = "3") int maxDepth) throws ParseException {

		logger.debug("entered GET [fichePostes/getSubFichePostes] => getSubFichePostes with parameter idAgent = {}", idAgent);

		int newIdAgent = agentMatriculeConverterService.tryConvertFromADIdAgentToIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(newIdAgent);

		if (ag == null)
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

		List<Integer> fichePosteIds = fpSrv.getListSubFichePoste(newIdAgent, maxDepth);

		if (fichePosteIds.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<String>(new JSONSerializer().serialize(fichePosteIds), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadFichePoste", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> downloadFichePoste(@RequestParam("idFichePoste") int idFichePoste) throws ParseException {

		FichePoste fp = fpSrv.getFichePosteById(idFichePoste);

		if (fp == null)
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);

		byte[] responseData = null;

		try {
			int version = (fp.getFicheMetierPrimaire().isEmpty()) ? 1 : 2;
			responseData = reportingService.getFichePosteReportAsByteArray(idFichePoste, version);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/pdf");
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s.pdf\"", fp.getNumFP().replace('/', '_')));

		return new ResponseEntity<byte[]>(responseData, headers, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/xml/getFichePoste", produces = "application/xml", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView getXmlFichePoste(@RequestParam("idFichePoste") int idFichePoste) throws ParseException {

		FichePoste fp = fpSrv.getFichePosteById(idFichePoste);
		FichePosteDto dto = new FichePosteDto();
		if (fp != null && fp.getIdServiceADS() != null) {
			EntiteDto direction = adsWSConsumer.getAffichageDirection(fp.getIdServiceADS());
			EntiteDto service = adsWSConsumer.getAffichageService(fp.getIdServiceADS());
			EntiteDto section = adsWSConsumer.getAffichageSection(fp.getIdServiceADS());
			List<Activite> listActi = new ArrayList<Activite>();
			if (fp.getActivites() != null) {
				for (ActiviteFP actiFP : fp.getActivites()) {
					Activite acti = fichePosteDao.chercherActivite(actiFP.getActiviteFPPK().getIdActivite());
					if (acti != null)
						listActi.add(acti);
				}
			}
			List<Competence> listComp = new ArrayList<Competence>();
			if (fp.getCompetencesFDP() != null) {
				for (CompetenceFP compFP : fp.getCompetencesFDP()) {
					Competence comp = fichePosteDao.chercherCompetence(compFP.getCompetenceFPPK().getIdCompetence());
					if (comp != null)
						listComp.add(comp);
				}
			}

			dto = new FichePosteDto(fp, true, direction == null ? null : direction.getLabel(), service == null ? null : service.getLabel(), section == null ? null : section.getLabel(), null,
					listActi, listComp);
		} else {
			dto = new FichePosteDto(fp, true, "", null, null, null, new ArrayList<Activite>(), new ArrayList<Competence>());
		}

		return new ModelAndView("xmlView", "object", dto);
	}

	@ResponseBody
	@RequestMapping(value = "/xml/getFichePosteSIRH", produces = "application/xml", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView getXmlFichePosteSIRH(@RequestParam("idFichePoste") int idFichePoste) throws ParseException {

		FichePoste fp = fpSrv.getFichePosteDetailleSIRHByIdWithRefPrime(idFichePoste);
		FichePosteDto dto = new FichePosteDto();
		if (fp != null && fp.getIdServiceADS() != null) {
			EntiteDto direction = adsWSConsumer.getAffichageDirection(fp.getIdServiceADS());
			EntiteDto service = adsWSConsumer.getAffichageService(fp.getIdServiceADS());
			EntiteDto section = adsWSConsumer.getAffichageSection(fp.getIdServiceADS());
			List<Activite> listActi = new ArrayList<Activite>();
			if (fp.getActivites() != null) {
				for (ActiviteFP actiFP : fp.getActivites()) {
					Activite acti = fichePosteDao.chercherActivite(actiFP.getActiviteFPPK().getIdActivite());
					if (acti != null)
						listActi.add(acti);
				}
			}
			List<Competence> listComp = new ArrayList<Competence>();
			if (fp.getCompetencesFDP() != null) {
				for (CompetenceFP compFP : fp.getCompetencesFDP()) {
					Competence comp = fichePosteDao.chercherCompetence(compFP.getCompetenceFPPK().getIdCompetence());
					if (comp != null)
						listComp.add(comp);
				}
			}

			dto = new FichePosteDto(fp, true, direction == null ? null : direction.getLabel(), service == null ? null : service.getLabel(), section == null ? null : section.getLabel(), null,
					listActi, listComp);
		} else {
			dto = new FichePosteDto(fp, true, "", null, null, null, new ArrayList<Activite>(), new ArrayList<Competence>());
		}

		return new ModelAndView("xmlView", "object", dto);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadFichePosteSIRH", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> downloadFichePosteSIRH(@RequestParam("idFichePoste") int idFichePoste) throws ParseException {

		FichePoste fp = fpSrv.getFichePosteById(idFichePoste);

		if (fp == null)
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);

		byte[] responseData = null;

		try {
			int version = (fp.getFicheMetierPrimaire().isEmpty()) ? 1 : 2;
			responseData = reportingService.getFichePosteSIRHReportAsByteArray(idFichePoste, version);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/pdf");
		headers.add("Content-Disposition", String.format("attachment; filename=\"FP_%s.pdf\"", fp.getIdFichePoste()));

		return new ResponseEntity<byte[]>(responseData, headers, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/listeSpbhor", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListSpbhor() {

		List<SpbhorDto> result = fpSrv.getListSpbhorDto();

		if (result == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String jsonResult = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/spbhorById", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getSpbhorById(@RequestParam("idSpbhor") int idSpbhor) {

		SpbhorDto result = fpSrv.getSpbhorDtoById(idSpbhor);

		if (result == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String jsonResult = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/getFichePoste", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getFichePosteAgentOtherProject(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = sdf.parse(dateTemp);

		FichePoste fp = fpSrv.getFichePostePrimaireAgentAffectationEnCours(Integer.valueOf(newIdAgent), dateJour, true);

		if (fp == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		EntiteDto service = null;
		EntiteDto direction = null;
		EntiteDto section = null;
		if (fp.getIdServiceADS() != null) {
			direction = adsWSConsumer.getAffichageDirection(fp.getIdServiceADS());
			service = adsWSConsumer.getAffichageService(fp.getIdServiceADS());
			section = adsWSConsumer.getAffichageSection(fp.getIdServiceADS());
		}
		List<Activite> listActi = new ArrayList<Activite>();
		if (fp.getActivites() != null) {
			for (ActiviteFP actiFP : fp.getActivites()) {
				Activite acti = fichePosteDao.chercherActivite(actiFP.getActiviteFPPK().getIdActivite());
				if (acti != null)
					listActi.add(acti);
			}
		}
		List<Competence> listComp = new ArrayList<Competence>();
		if (fp.getCompetencesFDP() != null) {
			for (CompetenceFP compFP : fp.getCompetencesFDP()) {
				Competence comp = fichePosteDao.chercherCompetence(compFP.getCompetenceFPPK().getIdCompetence());
				if (comp != null)
					listComp.add(comp);
			}
		}

		FichePosteDto dto = new FichePosteDto(fp, direction == null ? "" : direction.getLabel(), service == null ? "" : service.getLabel(), section == null ? "" : section.getLabel(),
				service == null ? "" : service.getSigle(), listActi, listComp);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

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

	@RequestMapping(value = "/getFichePosteSecondaire", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getFichePosteSecondaireAgentOtherProject(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = sdf.parse(dateTemp);

		FichePoste fp = fpSrv.getFichePosteSecondaireAgentAffectationEnCours(Integer.valueOf(newIdAgent), dateJour);

		if (fp == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		EntiteDto service = null;
		EntiteDto direction = null;
		EntiteDto section = null;
		if (fp.getIdServiceADS() != null) {
			direction = adsWSConsumer.getAffichageDirection(fp.getIdServiceADS());
			service = adsWSConsumer.getAffichageService(fp.getIdServiceADS());
			section = adsWSConsumer.getAffichageSection(fp.getIdServiceADS());
		}
		List<Activite> listActi = new ArrayList<Activite>();
		if (fp.getActivites() != null) {
			for (ActiviteFP actiFP : fp.getActivites()) {
				Activite acti = fichePosteDao.chercherActivite(actiFP.getActiviteFPPK().getIdActivite());
				if (acti != null)
					listActi.add(acti);
			}
		}
		List<Competence> listComp = new ArrayList<Competence>();
		if (fp.getCompetencesFDP() != null) {
			for (CompetenceFP compFP : fp.getCompetencesFDP()) {
				Competence comp = fichePosteDao.chercherCompetence(compFP.getCompetenceFPPK().getIdCompetence());
				if (comp != null)
					listComp.add(comp);
			}
		}

		FichePosteDto dto = new FichePosteDto(fp, direction == null ? "" : direction.getLabel(), service == null ? "" : service.getLabel(), section == null ? "" : section.getLabel(),
				service == null ? "" : service.getSigle(), listActi, listComp);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile à ADS et organigramme
	 * 
	 * @param idEntite
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/listFichePosteByIdEntite", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> listFichePosteByIdEntite(@RequestParam(value = "idEntite", required = true) Integer idEntite,
			@RequestParam(value = "statutFDP", required = false) String listIdStatutFDP,
			@RequestParam(value = "withEntiteChildren", required = false, defaultValue = "false") boolean withEntiteChildren) throws ParseException {

		logger.debug("entered GET [fichePostes/listFichePosteByIdEntite/] => listFichePosteByIdEntite with idEntite = {} and statutFDP = {} and withEntiteChildren = {}", idEntite, listIdStatutFDP,
				withEntiteChildren);

		List<Integer> statutIds = new ArrayList<Integer>();
		if (listIdStatutFDP != null) {
			for (String id : listIdStatutFDP.split(",")) {
				statutIds.add(Integer.valueOf(id));
			}
		}

		List<FichePosteDto> result = fpSrv.getListFichePosteByIdServiceADSAndStatutFDP(idEntite, statutIds, withEntiteChildren);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		int size = null == result ? 0 : result.size();

		logger.debug("Finish GET [fichePostes/listFichePosteByIdEntite/] => listFichePosteByIdEntite with " + size + " results");

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile à ADS lors de la suppression d'une entité
	 * 
	 * @param idEntite
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/deleteFichePosteByIdEntite", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> deleteFichePosteByIdEntite(@RequestParam(value = "idEntite", required = true) Integer idEntite, @RequestParam(value = "idAgent", required = true) Long idAgent,
			@RequestParam(value = "sigle", required = true) String sigle) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		ReturnMessageDto result = fpSrv.deleteFichePosteByIdEntite(idEntite, new Integer(newIdAgent), sigle);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile à Organigramme
	 * 
	 * @param idEntite
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getInfoFDPByEntite", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getInfoFDPByEntite(@RequestParam(value = "idEntite", required = true) Integer idEntite,
			@RequestParam(value = "withEntiteChildren", required = false, defaultValue = "false") boolean withEntiteChildren) throws ParseException {

		InfoEntiteDto result = fpSrv.getInfoFDP(idEntite, withEntiteChildren, new Date());

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile à ADS lors de la duplication d'une entité
	 * 
	 * @param idEntite
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/dupliqueFichePosteByIdEntite", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> dupliqueFichePosteByIdEntite(@RequestParam(value = "idEntiteNew", required = true) Integer idEntiteNew,
			@RequestParam(value = "idEntiteOld", required = true) Integer idEntiteOld, @RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		ReturnMessageDto result = fpSrv.dupliqueFichePosteByIdEntite(idEntiteNew, idEntiteOld, new Integer(newIdAgent));

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile à JOBS lors de la suppression d'une entité, car il faut supprimer
	 * les FDP associées
	 * 
	 * Utile à SIRH lors de la suppression d'une FDP
	 * 
	 * @param idFichePoste
	 * @param idAgent
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/deleteFichePosteByIdFichePoste", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> deleteFichePosteByIdFichePoste(@RequestParam(value = "idFichePoste", required = true) Integer idFichePoste,
			@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		ReturnMessageDto result = fpSrv.deleteFichePosteByIdFichePoste(idFichePoste, new Integer(newIdAgent));

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile à JOBS lors de la duplication d'une entité, car il faut dupliquer
	 * les FDP associées sur la nouvelle entité
	 * 
	 * Utile à SIRH lors de la duplication d'une FDP
	 * 
	 * @param idFichePoste
	 * @param idAgent
	 * @param idEntite
	 *            : id de la nouvelle entite
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/dupliqueFichePosteByIdFichePoste", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> dupliqueFichePosteByIdFichePoste(@RequestParam(value = "idFichePoste", required = true) Integer idFichePoste,
			@RequestParam(value = "idEntite", required = true) Integer idEntite, @RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		logger.debug("entered GET [fichePostes/dupliqueFichePosteByIdFichePoste/] => dupliqueFichePosteByIdFichePoste with idEntite = {} and idFichePoste = {} and idAgent = {}", idEntite,
				idFichePoste, idAgent);

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		ReturnMessageDto result = fpSrv.dupliqueFichePosteByIdFichePoste(idFichePoste, idEntite, new Integer(newIdAgent));

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile à ADS lors de l'activation d'une entité
	 * 
	 * @param idEntite
	 * @param idAgent
	 *            : agent faisant l'action
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/activeFichesPosteByIdEntite", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> activeFichesPosteByIdEntite(@RequestParam(value = "idEntite", required = true) Integer idEntite, @RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		ReturnMessageDto result = fpSrv.activeFichesPosteByIdEntite(idEntite, new Integer(newIdAgent));

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile à JOBS lors de l'activation d'une entité, car il faut activer les
	 * FDP associées
	 * 
	 * @param idFichePoste
	 * @param idAgent
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/activeFichePosteByIdFichePoste", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> activeFichePosteByIdFichePoste(@RequestParam(value = "idFichePoste", required = true) Integer idFichePoste,
			@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		ReturnMessageDto result = fpSrv.activeFichePosteByIdFichePoste(idFichePoste, new Integer(newIdAgent));

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile a ORGANIGRAMME
	 * 
	 * @param idEntite 
	 * @param withFichesPosteNonReglemente
	 * @return Arbre de fiches de poste
	 */
	@RequestMapping(value = "/treeFichesPosteByIdEntite", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getTreeFichesPosteByIdEntite(@RequestParam(value = "idEntite") Integer idEntite,
			@RequestParam(value = "withFichesPosteNonReglemente") boolean withFichesPosteNonReglemente) {

		logger.debug("entered GET [fichePostes/treeFichesPosteByIdEntite/] => getTreeFichesPosteByIdEntite with idEntite = {} and withFichesPosteNonReglemente = {}", idEntite,
				withFichesPosteNonReglemente);

		List<FichePosteTreeNodeDto> result = fpSrv.getTreeFichesPosteByIdEntite(idEntite, withFichesPosteNonReglemente);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile à ADS lors du deplacement de fiches de poste d une entite a une
	 * autre : deplace les fiches de poste à l'état Validé, Gelé et Transitoire
	 * 
	 * @param idEntiteSource
	 *            Integer Entite source
	 * @param idEntiteCible
	 *            Integer Entite cible
	 * @param idAgent
	 *            Integer Agent effectuant l action
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/deplaceFichePosteFromEntityToOtherEntity", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> deplaceFichePosteFromEntityToOtherEntity(@RequestParam(value = "idEntiteSource", required = true) Integer idEntiteSource,
			@RequestParam(value = "idEntiteCible", required = true) Integer idEntiteCible, @RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		ReturnMessageDto result = fpSrv.deplaceFichePosteFromEntityToOtherEntity(idEntiteSource, idEntiteCible, new Integer(newIdAgent));

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile à ADS lors de l'inactivation de fiches de poste d une entite
	 * 
	 * @param idEntite
	 *            Integer Entite
	 * @param idAgent
	 *            Integer Agent effectuant l action
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/inactiveFichePosteFromEntity", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> inactiveFichePosteFromEntity(@RequestParam(value = "idEntite", required = true) Integer idEntite, @RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		ReturnMessageDto result = fpSrv.inactiveFichePosteFromEntity(idEntite, new Integer(newIdAgent));

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Utile à ADS lors du passage en transitoire de fiches de poste d une
	 * entite
	 * 
	 * @param idEntite
	 *            Integer Entite
	 * @param idAgent
	 *            Integer Agent effectuant l action
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/transiteFichePosteFromEntity", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> transiteFichePosteFromEntity(@RequestParam(value = "idEntite", required = true) Integer idEntite, @RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		ReturnMessageDto result = fpSrv.transiteFichePosteFromEntity(idEntite, new Integer(newIdAgent));

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
