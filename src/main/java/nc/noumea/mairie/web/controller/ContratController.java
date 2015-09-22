package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.Sibanq;
import nc.noumea.mairie.model.bean.sirh.Activite;
import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Competence;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.Contrat;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.repository.sirh.IFichePosteRepository;
import nc.noumea.mairie.service.IReportingService;
import nc.noumea.mairie.service.ISibanqService;
import nc.noumea.mairie.service.ISivietService;
import nc.noumea.mairie.service.eae.ICalculEaeService;
import nc.noumea.mairie.service.sirh.IAffectationService;
import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.service.sirh.IContratService;
import nc.noumea.mairie.service.sirh.IFichePosteService;
import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.CompteDto;
import nc.noumea.mairie.web.dto.ContratDto;
import nc.noumea.mairie.web.dto.DiplomeDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.FichePosteDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

import org.joda.time.DateTime;
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

@Controller
@RequestMapping("/contrat")
public class ContratController {

	private Logger logger = LoggerFactory.getLogger(ContratController.class);

	@Autowired
	private ICalculEaeService calculEaeSrv;

	@Autowired
	private ISibanqService sibanqSrv;

	@Autowired
	private ISivietService sivietSrv;

	@Autowired
	private IContratService contratSrv;

	@Autowired
	private IAffectationService affSrv;

	@Autowired
	private IFichePosteService fichePosteService;

	@Autowired
	private IAgentService agSrv;

	@Autowired
	private IReportingService reportingService;

	@Autowired
	private IADSWSConsumer adsWSConsumer;

	@Autowired
	private IFichePosteRepository fichePosteDao;

	@ResponseBody
	@RequestMapping(value = "/xml/getContratSIRH", produces = "application/xml", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView getXmlContratSIRH(@RequestParam("idAgent") Integer idAgent, @RequestParam("idContrat") Integer idContrat) throws ParseException {

		logger.debug("entered GET [contrat/xml/getContratSIRH] => getXmlContratSIRH with parameter idAgent = {} and idContrat = {} ", idAgent, idContrat);

		Contrat contrat = contratSrv.getContratById(idContrat);
		if (contrat == null) {
			logger.error("Le contrat id={} n'existe pas.", idContrat);
			return new ModelAndView("xmlView", "object", new ContratDto());
		}

		Agent ag = agSrv.getAgent(idAgent);
		if (ag == null) {
			logger.error("L'agent id={} n'existe pas.", idAgent);
			return new ModelAndView("xmlView", "object", new ContratDto());
		}

		Affectation affAgent = affSrv.getAffectationActiveByIdAgent(idAgent);
		if (affAgent == null) {
			logger.error("L'agent id={} n'a pas d'affectation active.", idAgent);
			return new ModelAndView("xmlView", "object", new ContratDto());
		}

		Integer fpId = fichePosteService.getIdFichePostePrimaireAgentAffectationEnCours(affAgent.getAgent().getIdAgent(), new DateTime().toDate());
		FichePoste fp = fichePosteService.getFichePosteById(fpId);
		if (fp == null || fp.getIdServiceADS() == null) {
			logger.error("L'agent id={} n'a pas d'affectation sur une entité ADS.", idAgent);
			return new ModelAndView("xmlView", "object", new ContratDto());
		}

		Sibanq banque = null;
		if (ag.getCodeBanque() != null) {
			banque = sibanqSrv.getBanque(ag.getCodeBanque());
		}

		Integer dureePeriodeEssai = null;
		if (contrat != null && contrat.getDateFinPeriodeEssai() != null) {
			dureePeriodeEssai = contratSrv.getNbJoursPeriodeEssai(contrat.getDateDebutContrat(), contrat.getDateFinPeriodeEssai());
		}

		// on construit le DTO

		List<DiplomeDto> listDiplomeDto = calculEaeSrv.getListDiplomeDto(idAgent);
		EntiteDto service = adsWSConsumer.getEntiteByIdEntite(fp.getIdServiceADS());
		EntiteDto direction = adsWSConsumer.getAffichageDirection(fp.getIdServiceADS());
		EntiteDto section = adsWSConsumer.getAffichageSection(fp.getIdServiceADS());
		List<Competence> listComp = new ArrayList<Competence>();
		if (fp.getCompetencesFDP() != null) {
			for (CompetenceFP compFP : fp.getCompetencesFDP()) {
				Competence comp = fichePosteDao.chercherCompetence(compFP.getCompetenceFPPK().getIdCompetence());
				if (comp != null)
					listComp.add(comp);
			}
		}
		List<Activite> listActi = new ArrayList<Activite>();
		if (fp.getActivites() != null) {
			for (ActiviteFP actiFP : fp.getActivites()) {
				Activite acti = fichePosteDao.chercherActivite(actiFP.getActiviteFPPK().getIdActivite());
				if (acti != null)
					listActi.add(acti);
			}
		}
		FichePosteDto fichePosteDto = new FichePosteDto(fp, direction == null ? null : direction.getLabel(), service.getLabel(), section == null ? null : section.getLabel(), service.getSigle(),
				listActi, listComp);
		CompteDto cptDto = new CompteDto(ag, banque);
		AgentDto agDto = new AgentDto(ag, cptDto);
		if (ag.getCodeCommuneNaissFr() == null) {
			agDto.setLieuNaissance(sivietSrv.getLieuNaissEtr(ag.getCodePaysNaissEt(), ag.getCodeCommuneNaissEt()).getLibCop());
		} else {
			agDto.setLieuNaissance(ag.getCodeCommuneNaissFr().getLibVil().trim());
		}
		ContratDto contratDto = new ContratDto(contrat, agDto, fichePosteDto, dureePeriodeEssai, listDiplomeDto);

		return new ModelAndView("xmlView", "object", contratDto);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadContratSIRH", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> downloadContratSIRH(@RequestParam("idAgent") Integer idAgent, @RequestParam("idContrat") Integer idContrat) throws ParseException {

		logger.debug("entered GET [contrat/downloadContratSIRH] => downloadContratSIRH with parameter idAgent = {} and idContrat = {}", idAgent, idContrat);

		if (idAgent == null || idContrat == null)
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);

		byte[] responseData = null;

		try {
			responseData = reportingService.getContratSIRHReportAsByteArray(idAgent, idContrat);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/doc");
		headers.add("Content-Disposition", String.format("attachment; filename=\"C_%s.doc\"", idContrat));

		return new ResponseEntity<byte[]>(responseData, headers, HttpStatus.OK);
	}

}
