package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.sirh.SuiviMedical;
import nc.noumea.mairie.service.IReportingService;
import nc.noumea.mairie.service.ISiservService;
import nc.noumea.mairie.service.sirh.ISuiviMedicalService;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.ConvocationVMDto;
import nc.noumea.mairie.web.dto.ListConvocationVMDto;
import nc.noumea.mairie.web.dto.MedecinDto;

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
@RequestMapping("/suiviMedical")
public class SuiviMedicalController {

	private Logger logger = LoggerFactory.getLogger(SuiviMedicalController.class);

	@Autowired
	private ISuiviMedicalService smSrv;

	@Autowired
	private ISiservService siservSrv;

	@Autowired
	private IReportingService reportingService;

	@ResponseBody
	@RequestMapping(value = "/xml/getConvocationSIRH", produces = "application/xml", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView getXmlConvocationSIRH(@RequestParam("csvIdSuiviMedical") String csvIdSuiviMedical,
			@RequestParam("typePopulation") String typePopulation) throws ParseException {

		logger.debug(
				"entered GET [suiviMedical/xml/getConvocationSIRH] => getXmlConvocationSIRH with parameter csvIdSuiviMedical = {} and typePopulation = {} ",
				csvIdSuiviMedical, typePopulation);

		ListConvocationVMDto listDto = new ListConvocationVMDto();
		listDto.setTypePopulation(typePopulation);
		if (csvIdSuiviMedical != null) {

			List<Integer> suiviMedIds = new ArrayList<Integer>();
			for (String id : csvIdSuiviMedical.split(",")) {
				suiviMedIds.add(Integer.valueOf(id));
			}

			for (Integer idSuivi : suiviMedIds) {
				SuiviMedical sm = smSrv.getSuiviMedicalById(idSuivi);

				Siserv service = siservSrv.getService(sm.getCodeService());

				AgentWithServiceDto agDto = new AgentWithServiceDto(sm.getAgent(), service);
				agDto.setDirection(siservSrv.getDirection(service.getServi()) == null ? "" : siservSrv.getDirection(
						service.getServi()).getLiServ());

				MedecinDto medDto = new MedecinDto(sm.getMedecinSuiviMedical());

				ConvocationVMDto dto = new ConvocationVMDto(sm, agDto, medDto);
				listDto.getConvocations().add(dto);
			}
		}

		return new ModelAndView("xmlView", "object", listDto);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadConvocationSIRH", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> downloadConvocationSIRH(@RequestParam("csvIdSuiviMedical") String csvIdSuiviMedical,
			@RequestParam("typePopulation") String typePopulation, @RequestParam("mois") String mois,
			@RequestParam("annee") String annee) throws ParseException {

		logger.debug(
				"entered GET [suiviMedical/downloadConvocationSIRH] => downloadConvocationSIRH with parameter csvIdSuiviMedical = {} and typePopulation = {} and mois = {} and annee = {} ",
				csvIdSuiviMedical, typePopulation, mois, annee);

		if (csvIdSuiviMedical == null)
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);

		byte[] responseData = null;

		try {
			responseData = reportingService.getConvocationSIRHReportAsByteArray(csvIdSuiviMedical, typePopulation);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/doc");
		headers.add("Content-Disposition",
				String.format("attachment; filename=\"SM_Convocation_%s_%s_%s.doc\"", typePopulation, mois, annee));

		return new ResponseEntity<byte[]>(responseData, headers, HttpStatus.OK);
	}

	// @ResponseBody
	// @RequestMapping(value = "/xml/getLettreAccompagnementSIRH", produces =
	// "application/xml", method = RequestMethod.GET)
	// @Transactional(readOnly = true)
	// public ModelAndView
	// getXmlLettreAccompagnementSIRH(@RequestParam("idAffectation") int
	// idAffectation,
	// @RequestParam("typeNoteService") String typeNoteService) throws
	// ParseException {
	//
	// logger.debug(
	// "entered GET [suiviMedical/xml/getLettreAccompagnementSIRH] => getXmlLettreAccompagnementSIRH with parameter idAffectation = {} and typeNoteService = {} ",
	// idAffectation, typeNoteService);
	//
	// Affectation aff = affSrv.getAffectationById(idAffectation);
	// NoteServiceDto dto = new NoteServiceDto();
	// if (aff != null) {
	// AgentWithServiceDto agDto = new AgentWithServiceDto(aff.getAgent(),
	// aff.getFichePoste().getService());
	// agDto.setDirection(siservSrv.getDirection(aff.getFichePoste().getService().getServi())
	// == null ? ""
	// :
	// siservSrv.getDirection(aff.getFichePoste().getService().getServi()).getLiServ());
	// TitrePosteDto titrePoste = new TitrePosteDto(aff.getFichePoste());
	//
	// Contrat contrat =
	// contratSrv.getContratBetweenDate(aff.getAgent().getIdAgent(), new
	// Date());
	// Integer dureePeriodeEssai = null;
	// if (contrat != null && contrat.getDateFinPeriodeEssai() != null) {
	// dureePeriodeEssai =
	// contratSrv.getNbJoursPeriodeEssai(contrat.getDateDebutContrat(),
	// contrat.getDateFinPeriodeEssai());
	// }
	//
	// dto = new NoteServiceDto(aff, agDto, titrePoste, dureePeriodeEssai,
	// contrat, typeNoteService);
	// }
	//
	// return new ModelAndView("xmlView", "object", dto);
	// }
	//
	//
	// @ResponseBody
	// @RequestMapping(value = "/downloadLettreAccompagnementSIRH", method =
	// RequestMethod.GET)
	// @Transactional(readOnly = true)
	// public ResponseEntity<byte[]>
	// downloadLettreAccompagnementSIRH(@RequestParam("idAffectation") int
	// idAffectation)
	// throws ParseException {
	//
	// logger.debug(
	// "entered GET [suiviMedical/downloadLettreAccompagnementSIRH] => downloadLettreAccompagnementSIRHs with parameter idAffectation = {} ",
	// idAffectation);
	//
	// Affectation aff = affSrv.getAffectationById(idAffectation);
	//
	// if (aff == null)
	// return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
	//
	// byte[] responseData = null;
	//
	// try {
	// responseData =
	// reportingService.getNoteServiceSIRHReportAsByteArray(idAffectation,
	// null);
	// } catch (Exception e) {
	// logger.error(e.getMessage(), e);
	// return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
	// }
	//
	// HttpHeaders headers = new HttpHeaders();
	// headers.add("Content-Type", "application/doc");
	// headers.add("Content-Disposition",
	// String.format("attachment; filename=\"NS_%s_%s.doc\"",
	// aff.getIdAffectation(), "INTERNE"));
	//
	// return new ResponseEntity<byte[]>(responseData, headers, HttpStatus.OK);
	// }

}
