package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.Sibanq;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.SpSold;
import nc.noumea.mairie.model.bean.Spcong;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Contact;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.ParentEnfant;
import nc.noumea.mairie.service.ISibanqService;
import nc.noumea.mairie.service.ISiguicService;
import nc.noumea.mairie.service.ISiservService;
import nc.noumea.mairie.service.ISivietService;
import nc.noumea.mairie.service.ISoldeService;
import nc.noumea.mairie.service.ISpadmnService;
import nc.noumea.mairie.service.ISpcongService;
import nc.noumea.mairie.service.sirh.IAgentMatriculeConverterService;
import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.service.sirh.IContactService;
import nc.noumea.mairie.service.sirh.IFichePosteService;
import nc.noumea.mairie.tools.ServiceTreeNode;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.AgentGeneriqueDto;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.ContactAgentDto;
import nc.noumea.mairie.web.dto.EnfantDto;
import nc.noumea.mairie.web.dto.ProfilAgentDto;

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
@RequestMapping("/agents")
public class AgentController {

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private ISivietService sivietSrv;

	@Autowired
	private ISiguicService siguicSrv;

	@Autowired
	private ISibanqService sibanqSrv;

	@Autowired
	private IContactService contactSrv;

	@Autowired
	private ISoldeService soldeSrv;

	@Autowired
	private ISpcongService congSrv;

	@Autowired
	private IFichePosteService fpSrv;

	@Autowired
	private ISiservService siservSrv;

	@Autowired
	private ISpadmnService spadmnSrv;

	@Autowired
	private IAgentMatriculeConverterService agentMatriculeConverterService;

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

	@RequestMapping(value = "/etatCivil", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getEtatCivil(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		if (ag.getCodeCommuneNaissFr() == null) {
			ag.setLieuNaissance(sivietSrv.getLieuNaissEtr(ag.getCodePaysNaissEt(), ag.getCodeCommuneNaissEt())
					.getLibCop());
		} else {
			ag.setLieuNaissance(ag.getCodeCommuneNaissFr().getLibVil().trim());
		}

		String jsonResult = Agent.getSerializerForAgentEtatCivil().serialize(ag);
		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/getEtatCivil", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getEtatCivilOtherProject(
			@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		if (ag.getCodeCommuneNaissFr() == null) {
			ag.setLieuNaissance(sivietSrv.getLieuNaissEtr(ag.getCodePaysNaissEt(), ag.getCodeCommuneNaissEt())
					.getLibCop());
		} else {
			ag.setLieuNaissance(ag.getCodeCommuneNaissFr().getLibVil().trim());
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
			EnfantDto dtoEnfant = new EnfantDto(pe);
			listeEnfant.add(dtoEnfant);
		}

		Sibanq banque = null;
		if (ag.getCodeBanque() != null) {
			banque = sibanqSrv.getBanque(ag.getCodeBanque());
		}

		ProfilAgentDto dto = new ProfilAgentDto(ag, listeContact, listeEnfant, banque);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/enfants", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getEnfants(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		String jsonResult = Agent.getSerializerForEnfantAgent().serialize(ag.getParentEnfantsOrderByDateNaiss());

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/couvertureSociale", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getCouvertureSociale(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		String jsonResult = Agent.getSerializerForAgentCouvertureSociale().serialize(ag);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/banque", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getBanque(@RequestParam(value = "idAgent", required = true) Long idAgent) {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));
		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		if (ag.getCodeBanque() != null && ag.getCodeGuichet() != null) {
			ag.setBanque(siguicSrv.getBanque(ag.getCodeBanque(), ag.getCodeGuichet()).getLiGuic());
		}

		String jsonResult = Agent.getSerializerForAgentBanque().serialize(ag);
		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/adresse", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAdresse(@RequestParam(value = "idAgent", required = true) Long idAgent) {
		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		if (ag.getVoie() == null) {
			ag.setRue(ag.getRueNonNoumea());
		} else {
			ag.setRue(ag.getVoie().getLiVoie().trim());
		}

		String jsonResult = Agent.getSerializerForAgentAdresse().serialize(ag);
		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/contacts", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getContacts(@RequestParam(value = "idAgent", required = true) Long idAgent) {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		List<Contact> lc = contactSrv.getContactsAgent(Long.valueOf(newIdAgent));

		if (lc == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		String jsonResult = Contact.getSerializerForAgentContacts().serialize(lc);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/soldeConge", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getSoldeConge(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		// on remanie l'idAgent pour avoir le nomatr
		String nomatr = remanieNoMatrAgent(idAgent);

		SpSold solde = soldeSrv.getSoldeConge(Integer.valueOf(nomatr));

		if (solde == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		String jsonResult = SpSold.getSerializerForAgentSoldeConge().serialize(solde);
		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/histoCongeAll", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getToutHistoConge(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		// on remanie l'idAgent pour avoir le nomatr
		String nomatr = remanieNoMatrAgent(idAgent);

		List<Spcong> lcong = congSrv.getToutHistoriqueConge(Long.valueOf(nomatr));

		if (lcong == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		String jsonResult = Spcong.getSerializerForAgentHistoConge().serialize(lcong);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/histoConge", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getHistoConge(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

		// on remanie l'idAgent pour avoir le nomatr
		String nomatr = remanieNoMatrAgent(idAgent);

		List<Spcong> lcong = congSrv.getHistoriqueCongeAnnee(Long.valueOf(nomatr));

		if (lcong == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		String jsonResult = Spcong.getSerializerForAgentHistoConge().serialize(lcong);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/fichePoste", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getFichePosteAgent(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException, java.text.ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = sdf.parse(dateTemp);

		FichePoste fp = fpSrv.getFichePostePrimaireAgentAffectationEnCours(Integer.valueOf(newIdAgent), dateJour);

		if (fp == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		fp.getService().setDirection(
				siservSrv.getDirection(fp.getService().getServi()) == null ? "" : siservSrv.getDirection(
						fp.getService().getServi()).getLiServ());
		fp.getService().setDivision(
				siservSrv.getDivision(fp.getService().getServi()) == null ? fp.getService().getLiServ() : siservSrv
						.getDivision(fp.getService().getServi()).getLiServ());
		fp.getService().setSection(
				siservSrv.getSection(fp.getService().getServi()) == null ? "" : siservSrv.getSection(
						fp.getService().getServi()).getLiServ());

		String jsonResult = FichePoste.getSerializerForFichePoste().serialize(fp);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/fichePosteSecondaire", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getFichePosteSecondaireAgent(
			@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException,
			java.text.ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = sdf.parse(dateTemp);

		FichePoste fp = fpSrv.getFichePosteSecondaireAgentAffectationEnCours(Integer.valueOf(newIdAgent), dateJour);

		if (fp == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		fp.getService().setDirection(
				siservSrv.getDirection(fp.getService().getServi()) == null ? "" : siservSrv.getDirection(
						fp.getService().getServi()).getLiServ());
		fp.getService().setDivision(
				siservSrv.getDivision(fp.getService().getServi()) == null ? fp.getService().getLiServ() : siservSrv
						.getDivision(fp.getService().getServi()).getLiServ());
		fp.getService().setSection(
				siservSrv.getSection(fp.getService().getServi()) == null ? "" : siservSrv.getSection(
						fp.getService().getServi()).getLiServ());

		String jsonResult = FichePoste.getSerializerForFichePoste().serialize(fp);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/superieurHierarchique", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getSuperieurHierarchiqueAgent(
			@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException,
			java.text.ParseException {

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
		FichePoste fichePosteSuperieurHierarchique = fpSrv.getFichePostePrimaireAgentAffectationEnCours(
				agentSuperieurHierarchique.getIdAgent(), dateJour);

		if (fichePosteSuperieurHierarchique == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		agentSuperieurHierarchique.setPosition(fichePosteSuperieurHierarchique.getTitrePoste().getLibTitrePoste());

		String jsonResult = Agent.getSerializerForAgentSuperieurHierarchique().serialize(agentSuperieurHierarchique);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/getSuperieurHierarchique", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getSuperieurHierarchiqueAgentOtherProject(
			@RequestParam(value = "idAgent", required = true) Long idAgent) throws ParseException {

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
		FichePoste fichePosteSuperieurHierarchique = fpSrv.getFichePostePrimaireAgentAffectationEnCours(
				agentSuperieurHierarchique.getIdAgent(), dateJour);

		if (fichePosteSuperieurHierarchique == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		agentSuperieurHierarchique.setPosition(fichePosteSuperieurHierarchique.getTitrePoste().getLibTitrePoste());

		AgentWithServiceDto dto = new AgentWithServiceDto(agentSuperieurHierarchique);

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class)
				.deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/equipe", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getEquipeAgent(@RequestParam(value = "idAgent", required = true) Long idAgent,
			@RequestParam(value = "sigleService", required = false) String sigleService) throws ParseException,
			java.text.ParseException {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		if (ag == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		boolean estChef = fpSrv.estResponsable(ag.getIdAgent());

		List<String> listService = null;
		if (estChef) {
			// alors on regarde les sousService
			// listService = siservSrv.getListServiceAgent(ag.getIdAgent(),
			// sigleService);
			Siserv service = siservSrv.getServiceBySigle(sigleService);
			listService = new ArrayList<String>();
			if (service != null)
				listService.add(service.getServi());
		} else {
			Siserv serviceAgent = siservSrv.getServiceAgent(ag.getIdAgent());
			listService = new ArrayList<String>();
			if (serviceAgent != null)
				listService.add(serviceAgent.getServi());
		}

		if (listService.size() == 0) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		Agent agentSuperieurHierarchique = agentSrv.getSuperieurHierarchiqueAgent(ag.getIdAgent());

		Integer idAgentResp = 0;
		if (agentSuperieurHierarchique != null) {
			idAgentResp = agentSuperieurHierarchique.getIdAgent();
		}

		List<Agent> listAgentService = agentSrv.listAgentPlusieursServiceSansAgentSansSuperieur(ag.getIdAgent(),
				idAgentResp, listService);

		if (listAgentService == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = sdf.parse(dateTemp);
		for (Agent agentService : listAgentService) {
			String titrePoste = fpSrv.getTitrePosteAgent(agentService.getIdAgent(), dateJour);
			agentService.setPosition(titrePoste);
		}

		String jsonResult = Agent.getSerializerForAgentEquipeFichePoste().serialize(listAgentService);
		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/serviceArbre", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
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

		JSONSerializer serializer = new JSONSerializer().exclude("*.class").exclude("*.serviceParent")
				.exclude("*.sigleParent");

		return new ResponseEntity<String>(serializer.deepSerialize(treeHeadList), HttpStatus.OK);
	}

	@RequestMapping(value = "/estChef", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentChefPortail(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

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

	@RequestMapping(value = "/estHabiliteKiosqueRH", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentHabilitePortail(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

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

	@ResponseBody
	@RequestMapping(value = "/sousAgents", produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getSubAgents(@RequestParam("idAgent") int idAgent,
			@RequestParam(value = "maxDepth", required = false, defaultValue = "3") int maxDepth) throws ParseException {

		int newIdAgent = agentMatriculeConverterService.tryConvertFromADIdAgentToIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(newIdAgent);

		if (ag == null)
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

		List<Integer> agentIds = fpSrv.getListSubAgents(newIdAgent, maxDepth, null);

		if (agentIds.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<String>(new JSONSerializer().serialize(agentIds), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/agentsShd", produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getShdAgents(@RequestParam("idAgent") int idAgent,
			@RequestParam(value = "maxDepth", required = false, defaultValue = "3") int maxDepth) throws ParseException {

		int newIdAgent = agentMatriculeConverterService.tryConvertFromADIdAgentToIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(newIdAgent);

		if (ag == null)
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

		List<Integer> agentIds = fpSrv.getListShdAgents(newIdAgent, maxDepth);

		if (agentIds.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<String>(new JSONSerializer().serialize(agentIds), HttpStatus.OK);
	}

	@RequestMapping(value = "/direction", headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<String> getDirection(@RequestParam(value = "idAgent", required = true) Long idAgent) {

		// on remanie l'idAgent
		String newIdAgent = remanieIdAgent(idAgent);

		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));

		// Si l'agent n'existe pas, on ne retourne rien
		if (ag == null)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		// On récupère le noeud parent des services de la personne
		ServiceTreeNode direction = siservSrv.getAgentDirection(ag.getIdAgent());

		JSONSerializer serializer = new JSONSerializer().exclude("*.class").exclude("*.servicesEnfant")
				.exclude("*.serviceParent");

		return new ResponseEntity<String>(serializer.serialize(direction), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/agentsSubordonnes", produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgentsSubordonnes(@RequestParam("idAgent") int idAgent,
			@RequestParam(value = "nom", required = false, defaultValue = "") String nom,
			@RequestParam(value = "maxDepth", required = false, defaultValue = "3") int maxDepth) throws ParseException {

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

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(listeAgentSub),
				HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/listeAgentsMairie", produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeAgentsMairie(
			@RequestParam(value = "nom", required = false, defaultValue = "") String nom,
			@RequestParam(value = "codeService", required = false, defaultValue = "") String codeService)
			throws ParseException {

		List<AgentWithServiceDto> listeAgentActivite = agentSrv.listAgentsEnActivite(nom, codeService);

		return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(listeAgentActivite),
				HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "agent", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAgent(@RequestParam(value = "idAgent", required = true) Long idAgent)
			throws ParseException {

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
	public ResponseEntity<String> getAgentOtherProject(@RequestParam(value = "idAgent", required = true) Integer idAgent)
			throws ParseException {

		Agent agent = agentSrv.getAgent(idAgent);

		if (agent == null) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		AgentGeneriqueDto dto = new AgentGeneriqueDto(agent);

		String response = new JSONSerializer().exclude("*.class").deepSerialize(dto);

		return new ResponseEntity<String>(response, HttpStatus.OK);

	}
}
