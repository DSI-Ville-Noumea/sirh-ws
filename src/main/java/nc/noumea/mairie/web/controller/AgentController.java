package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import nc.noumea.mairie.model.bean.Sibanq;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Contact;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.ParentEnfant;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
import nc.noumea.mairie.service.ISibanqService;
import nc.noumea.mairie.service.ISivietService;
import nc.noumea.mairie.service.ISpadmnService;
import nc.noumea.mairie.service.ads.IAdsService;
import nc.noumea.mairie.service.sirh.HelperService;
import nc.noumea.mairie.service.sirh.IAgentMatriculeConverterService;
import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.service.sirh.IContactService;
import nc.noumea.mairie.service.sirh.IContratService;
import nc.noumea.mairie.service.sirh.IFichePosteService;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.AgentGeneriqueDto;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.ContactAgentDto;
import nc.noumea.mairie.web.dto.EnfantDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.EntiteWithAgentWithServiceDto;
import nc.noumea.mairie.web.dto.ProfilAgentDto;

@Controller
@RequestMapping("/agents")
public class AgentController {

	private Logger							logger	= LoggerFactory.getLogger(AgentController.class);

	@Autowired
	private IAgentService					agentSrv;

	@Autowired
	private ISivietService					sivietSrv;

	@Autowired
	private ISibanqService					sibanqSrv;

	@Autowired
	private IContactService					contactSrv;

	@Autowired
	private IFichePosteService				fpSrv;

	@Autowired
	private ISpadmnService					spadmnSrv;

	@Autowired
	private IAgentMatriculeConverterService	agentMatriculeConverterService;

	@Autowired
	private IAffectationRepository			affRepo;

	@Autowired
	private IAdsService						adsService;

	@Autowired
	private HelperService					helperService;

	@Autowired
	private IContratService					contratService;

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

	@RequestMapping(value = "/getEtatCivil", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getEtatCivilOtherProject(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		if (ag.getCodeCommuneNaissFr() == null) {
			ag.setLieuNaissance(sivietSrv.getLieuNaissEtr(ag.getCodePaysNaissEt(), ag.getCodeCommuneNaissEt()).getLibCop());
		} else {
			ag.setLieuNaissance(ag.getCodeCommuneNaissFr().getLibVil().trim());
		}

		if (ag.getVoie() == null) {
			ag.setRue(ag.getRueNonNoumea());
		} else {
			ag.setRue(ag.getVoie().getLiVoie().trim());
		}

		List<Contact> lc = contactSrv.getContactsAgent(Long.valueOf(newIdAgent));
		List<ContactAgentDto> listeContact = new ArrayList<ContactAgentDto>();
		for (Contact c : lc) {
			ContactAgentDto dtoContact = new ContactAgentDto(c);
			listeContact.add(dtoContact);
		}

		List<ParentEnfant> lpe = ag.getParentEnfantsOrderByDateNaiss();
		List<EnfantDto> listeEnfant = new ArrayList<EnfantDto>();
		for (ParentEnfant pe : lpe) {
			EnfantDto dtoEnfant = new EnfantDto(pe, sivietSrv);
			listeEnfant.add(dtoEnfant);
		}

		Sibanq banque = null;
		if (ag.getCodeBanque() != null) {
			banque = sibanqSrv.getBanque(ag.getCodeBanque());
		}

		ProfilAgentDto dto = new ProfilAgentDto(ag, listeContact, listeEnfant, banque);

		// id service ADS pour le kiosque RH
		EntiteDto entite = agentSrv.getServiceAgent(new Integer(newIdAgent), new Date());
		dto.setIdServiceAds(null != entite ? entite.getIdEntite() : null);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getSuperieurHierarchique", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getSuperieurHierarchiqueAgentOtherProject(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		Agent agentSuperieurHierarchique = agentSrv.getSuperieurHierarchiqueAgent(ag.getIdAgent());

		if (agentSuperieurHierarchique == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = sdf.parse(dateTemp);
		FichePoste fichePosteSuperieurHierarchique = fpSrv.getFichePostePrimaireAgentAffectationEnCours(agentSuperieurHierarchique.getIdAgent(),
				dateJour, false);

		if (fichePosteSuperieurHierarchique == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		agentSuperieurHierarchique.setPosition(fichePosteSuperieurHierarchique.getTitrePoste().getLibTitrePoste());

		AgentWithServiceDto dto = new AgentWithServiceDto(agentSuperieurHierarchique);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getEquipe", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getEquipeAgentOtherProject(@RequestParam(value = "idAgent", required = true) Long idAgent,
			@RequestParam(value = "idService", required = false) Integer idService) throws ParseException, java.text.ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		List<AgentWithServiceDto> listAgent = agentSrv.listAgentsOfServices(null, new Date(), Arrays.asList(Integer.valueOf(newIdAgent)));

		if (listAgent == null || listAgent.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		AgentWithServiceDto ag = listAgent.get(0);

		List<Integer> listService = new ArrayList<Integer>();
		if (null != idService) {
			listService.add(idService);
		} else if (null != ag.getIdServiceADS()) {
			listService.add(ag.getIdServiceADS());
		}

		if (listService.size() == 0) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		Agent agentSuperieurHierarchique = agentSrv.getSuperieurHierarchiqueAgent(ag.getIdAgent());

		Integer idAgentResp = 0;
		if (agentSuperieurHierarchique != null) {
			idAgentResp = agentSuperieurHierarchique.getIdAgent();
		}

		List<Agent> listAgentService = agentSrv.listAgentPlusieursServiceSansAgentSansSuperieur(ag.getIdAgent(), idAgentResp, listService);

		if (listAgentService == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		Date dateJour = new Date();

		List<AgentWithServiceDto> dto = new ArrayList<AgentWithServiceDto>();
		List<EntiteDto> listEntitesDto = new ArrayList<EntiteDto>();
		for (Agent agentService : listAgentService) {
			FichePoste fichePoste = fpSrv.getFichePostePrimaireAgentAffectationEnCours(agentService.getIdAgent(), dateJour, false);

			AgentWithServiceDto dtoAgent = new AgentWithServiceDto(agentService);
			dtoAgent.setPosition(null == fichePoste.getTitrePoste() ? "" : fichePoste.getTitrePoste().getLibTitrePoste());

			EntiteDto entite = adsService.getEntiteByIdEntiteOptimise(fichePoste.getIdServiceADS(), listEntitesDto);
			if (null != entite) {
				dtoAgent.setIdServiceADS(fichePoste.getIdServiceADS());
				dtoAgent.setService(entite.getLabel());
				dtoAgent.setSigleService(entite.getSigle());
			}

			dto.add(dtoAgent);
		}

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/estChef", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentChefPortail(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		final boolean estChef = fpSrv.estResponsable(ag.getIdAgent());

		/* Fix for using FlexJson and getting rid of google-json dependency */
		Object o = new Object() {
			@SuppressWarnings("unused")
			public boolean isEstResponsable() {
				return estChef;
			}
		};

		String json = new JSONSerializer().exclude("*.class").serialize(o);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	/**
	 * Est utilisé par kioque-RH et Alfresco : webscript
	 * SynchroniseAgentWebScript
	 * 
	 * @param idAgent
	 *            Long ID de l agent
	 * @return ResponseEntity avec boolean
	 * @throws ParseException
	 */
	@RequestMapping(value = "/estHabiliteKiosqueRH", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentHabilitePortail(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		// on remanie l'idAgent
		String newNomatrAgent = remanieNoMatrAgent(idAgent);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Integer dateJour = Integer.valueOf(sdf.format(new Date()));

		final boolean estHabilite = spadmnSrv.estPAActive(Integer.valueOf(newNomatrAgent), dateJour);

		/* Fix for using FlexJson and getting rid of google-json dependency */
		Object o = new Object() {
			@SuppressWarnings("unused")
			public boolean isEstHabiliteKiosqueRH() {
				return estHabilite;
			}
		};

		String json = new JSONSerializer().exclude("*.class").serialize(o);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	/**
	 * Retourne la liste des agents subordonnés de l agent en parametre
	 * 
	 * Ce WS est appelé par : - SIRH-EAE-WS > utilisé par plusieurs services
	 * 
	 * @param idAgent
	 *            L ID de l agent
	 * @param maxDepth
	 *            Le nombre de niveau de recherche dans l arbre des fiches de
	 *            poste
	 * @return List<Integer> La liste des agents subordonnés
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/sousAgents", produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getSubAgents(@RequestParam("idAgent") int idAgent,
			@RequestParam(value = "maxDepth", required = false, defaultValue = "3") int maxDepth) throws ParseException {

		logger.debug("entered GET [agents/sousAgents] => getSubAgents with parameter idAgent = {}", idAgent);

		int newIdAgent = agentMatriculeConverterService.tryConvertFromADIdAgentToIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(newIdAgent);

		if (ag == null)
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

		List<Integer> agentIds = fpSrv.getListSubAgents(newIdAgent, maxDepth, null);

		if (agentIds.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<String>(new JSONSerializer().serialize(agentIds), HttpStatus.OK);
	}

	/**
	 * Retourne la liste des Supérieurs Hierarchique d un agent
	 * 
	 * Ce WS est appelé par : - SIRH-EAE-WS >
	 * EaeController.getFinalizationInformation - Alfresco >
	 * SynchroniseDroitsSHDWebScript
	 * 
	 * @param idAgent
	 *            L ID de l agent
	 * @param maxDepth
	 *            Le nombre de niveau à remonter dans l arbre des fiches de
	 *            poste
	 * @return La liste des SHD selon maxDepth
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/agentsShd", produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getShdAgents(@RequestParam("idAgent") int idAgent,
			@RequestParam(value = "maxDepth", required = false, defaultValue = "3") int maxDepth) throws ParseException {

		logger.debug("entered GET [agents/agentsShd] => getShdAgents with parameter idAgent = {}", idAgent);

		int newIdAgent = agentMatriculeConverterService.tryConvertFromADIdAgentToIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(newIdAgent);

		if (ag == null)
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

		List<Integer> agentIds = fpSrv.getListShdAgents(newIdAgent, maxDepth);

		if (agentIds.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<String>(new JSONSerializer().serialize(agentIds), HttpStatus.OK);
	}

	/**
	 * Retourne une liste List<AgentDto> Une liste d agents subordonnées selon
	 * les parametres en entrée
	 * 
	 * Ce WS est appelé depuis : - SIRH > OeSMConvocation > Recherche -
	 * Kiosque-RH > Gestion des droits ABS > Gestion des viseurs
	 * 
	 * @param idAgent
	 *            L ID de l agent
	 * @param nom
	 *            Affine la recherche sur le nom des agents subordonnés
	 * @param maxDepth
	 *            Le niveau de recherche dans l arbre des fiches de poste
	 * @return List<AgentDto> Une liste d agents subordonnées de l agent en
	 *         parametre
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/agentsSubordonnes", produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentsSubordonnes(@RequestParam("idAgent") int idAgent,
			@RequestParam(value = "nom", required = false, defaultValue = "") String nom,
			@RequestParam(value = "maxDepth", required = false, defaultValue = "3") int maxDepth) throws ParseException {

		logger.debug("entered GET [agents/agentsSubordonnes] => getAgentsSubordonnes with parameter idAgent = {}", idAgent);

		int newIdAgent = agentMatriculeConverterService.tryConvertFromADIdAgentToIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(newIdAgent);

		if (ag == null)
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

		List<Integer> agentIds = fpSrv.getListSubAgents(newIdAgent, maxDepth, nom);

		if (agentIds.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		List<AgentDto> listeAgentSub = new ArrayList<AgentDto>();
		for (Integer idAgentSub : agentIds) {

			Agent agSub = agentSrv.getAgent(idAgentSub);
			AgentDto agDto = new AgentDto(agSub);
			if (!listeAgentSub.contains(agDto))
				listeAgentSub.add(agDto);
		}

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(listeAgentSub), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/listeAgentsMairie", produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAgentsMairie(@RequestParam(value = "nom", required = false, defaultValue = "") String nom,
			@RequestParam(value = "idServiceADS", required = false) Integer idServiceADS) throws ParseException {

		logger.debug("DEBUT getListeAgentsMairie [agents/listeAgentsMairie]");

		List<AgentWithServiceDto> listeAgentActivite = agentSrv.listAgentsEnActiviteWithServiceAds(nom, idServiceADS);

		logger.debug("FIN getListeAgentsMairie [agents/listeAgentsMairie]");

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(listeAgentActivite), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/arbreServicesWithListAgentsByServiceWithoutAgentConnecte", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getArbreServicesWithListAgentsByService(
			@RequestParam(value = "idServiceADS", required = false) Integer idServiceADS,
			@RequestParam(value = "idAgent", required = false) Integer idAgent, @RequestBody(required = false) String agentsAInclureJson)
			throws ParseException {

		logger.debug("DEBUT getArbreServicesWithListAgentsByService [agents/arbreServicesWithListAgentsByServiceWithoutAgentConnecte]");

		List<Integer> listIdsAgentRemanie = new ArrayList<Integer>();
		if (null != agentsAInclureJson && !"".equals(agentsAInclureJson)) {
			List<Integer> listIdsAgent = new JSONDeserializer<List<Integer>>().use(null, ArrayList.class).use("values", Integer.class)
					.deserialize(agentsAInclureJson);

			// on remanie l'idAgent
			for (Integer idAgentAInclure : listIdsAgent) {
				listIdsAgentRemanie.add(helperService.remanieIdAgent(idAgentAInclure));
			}
		}

		EntiteWithAgentWithServiceDto result = agentSrv.getArbreServicesWithListAgentsByServiceWithoutAgentConnecteAndListAgentHorsService(
				idServiceADS, idAgent, listIdsAgentRemanie, new Date());

		logger.debug("FIN getArbreServicesWithListAgentsByService [agents/arbreServicesWithListAgentsByServiceWithoutAgentConnecte]");

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").include("*.entiteEnfantWithAgents.*")
				.include("*.listAgentWithServiceDto.*").transform(new MSDateTransformer(), Date.class).serialize(result), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "agent", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgent(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent agent = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (agent == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		String jsonResult = Agent.getSerializerAgentForEae().serialize(agent);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "getAgent", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentOtherProject(@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent agent = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (agent == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		AgentGeneriqueDto dto = new AgentGeneriqueDto(agent);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "getListAgents", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListAgents(@RequestBody String agentsApprouvesJson) throws ParseException {

		List<Integer> listIdsAgent = new JSONDeserializer<List<Integer>>().use(null, ArrayList.class).use("values", Integer.class)
				.deserialize(agentsApprouvesJson);

		if (null == listIdsAgent || listIdsAgent.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		// on remanie l'idAgent
		List<Integer> listIdsAgentRemanie = new ArrayList<Integer>();
		for (Integer idAgent : listIdsAgent) {
			listIdsAgentRemanie.add(helperService.remanieIdAgent(idAgent));
		}

		List<AgentGeneriqueDto> result = agentSrv.getListAgents(listIdsAgentRemanie);

		if (null == result || result.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/direction", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getDirection(@RequestParam(value = "idAgent", required = true) Integer idAgent,
			@RequestParam(value = "dateAffectation", required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date dateAffectation) {

		EntiteDto direction = agentSrv.getDirectionOfAgent(idAgent, dateAffectation);

		// Si l'agent n'existe pas, on ne retourne rien
		if (direction == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		JSONSerializer serializer = new JSONSerializer().exclude("*.class").exclude("*.servicesEnfant").exclude("*.serviceParent")
				.transform(new MSDateTransformer(), Date.class);

		return new ResponseEntity<String>(serializer.serialize(direction), HttpStatus.OK);
	}

	@RequestMapping(value = "/affectationAgent", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAffectationAgent(@RequestParam(value = "idAgent", required = true) Long idAgent,
			@RequestParam(value = "dateAffectation", required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date dateAffectation) {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		// on cherche l'affectation active
		Agent agent = affRepo.getAffectationAgent(Integer.valueOf(newIdAgent), dateAffectation);

		if (agent == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		AgentGeneriqueDto dto = new AgentGeneriqueDto(agent);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * WS qui retourne TRUE ou FALSE selon que l agent est en periode d essai ou
	 * non
	 * 
	 * @param idAgent
	 *            Long ID de l agent
	 * @param dateJour
	 *            Date date de la recherche
	 * @return TRUE ou FALSE
	 */
	@RequestMapping(value = "/isPeriodeEssai", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> isPeriodeEssai(@RequestParam(value = "idAgent", required = true) Long idAgent,
			@RequestParam(value = "dateAffectation", required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date dateJour) {

		logger.debug(String.format("DEBUT isPeriodeEssai [agents/isPeriodeEssai] with idAgent = {} and dateJour = {}", idAgent, dateJour));
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		if (contratService.isPeriodeEssai(new Integer(newIdAgent), dateJour)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Retourne la liste des agents ayant la prime pointage Indemnité
	 * forfaitaire travail DPM sur leur affectation active. Filtre également
	 * avec les agents passés en parametre. #30544
	 * 
	 * @param agentsJson
	 *            Liste des agents pour filtre
	 * @return List<AgentWithServiceDto> La liste des agents avec la prime
	 *         Indemnité forfaitaire travail DPM
	 */
	@RequestMapping(value = "/listeAgentWithIndemniteForfaitTravailDPM", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAgentWithIndemniteForfaitTravailDPM(@RequestBody String agentsJson) {

		logger.debug("DEBUT getListeAgentWithIndemniteForfaitTravailDPM [agents/listeAgentWithIndemniteForfaitTravailDPM] with parameter json = {}",
				agentsJson);

		List<Integer> listIdsAgent = new JSONDeserializer<List<Integer>>().use(null, ArrayList.class).use("values", Integer.class)
				.deserialize(agentsJson);

		List<AgentWithServiceDto> listeAgentActivite = agentSrv.getListeAgentWithIndemniteForfaitTravailDPM(listIdsAgent);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(listeAgentActivite), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/listeAgentsMairieSurPeriode", produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAgentsMairieSurPeriode(
			@RequestParam(value = "dateDebutPeriode", required = true) @DateTimeFormat(pattern = "yyyyMMdd") Date dateDebutPeriode,
			@RequestParam(value = "dateFinPeriode", required = true) @DateTimeFormat(pattern = "yyyyMMdd") Date dateFinPeriode)
			throws ParseException {

		logger.debug("DEBUT getListeAgentsMairieSurPeriode [agents/listeAgentsMairieSurPeriode]");

		List<AgentWithServiceDto> listeAgentActivite = agentSrv.listAgentsEnActiviteSurPeriode(dateDebutPeriode, dateFinPeriode);

		logger.debug("FIN getListeAgentsMairieSurPeriode [agents/listeAgentsMairieSurPeriode]");

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(listeAgentActivite), HttpStatus.OK);
	}

}
