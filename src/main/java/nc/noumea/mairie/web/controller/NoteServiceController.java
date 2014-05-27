package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.Date;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Contrat;
import nc.noumea.mairie.service.IReportingService;
import nc.noumea.mairie.service.ISiservService;
import nc.noumea.mairie.service.sirh.IAffectationService;
import nc.noumea.mairie.service.sirh.IContratService;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.NoteServiceDto;
import nc.noumea.mairie.web.dto.TitrePosteDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/noteService")
public class NoteServiceController {

	private Logger logger = LoggerFactory.getLogger(NoteServiceController.class);

	@Autowired
	private IAffectationService affSrv;

	@Autowired
	private IContratService contratSrv;

	@Autowired
	private IReportingService reportingService;

	@Autowired
	private ISiservService siservSrv;

	@ResponseBody
	@RequestMapping(value = "/xml/getNoteServiceSIRH", produces = "application/xml", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView getXmlNoteServiceSIRH(@RequestParam("idAffectation") int idAffectation,
			@RequestParam("typeNoteService") String typeNoteService) throws ParseException {

		logger.debug(
				"entered GET [noteService/xml/getNoteServiceSIRH] => getNoteServiceSIRH with parameter idAffectation = {} ",
				idAffectation);

		Affectation aff = affSrv.getAffectationById(idAffectation);
		NoteServiceDto dto = new NoteServiceDto();
		if (aff != null) {
			AgentWithServiceDto agDto = new AgentWithServiceDto(aff.getAgent(), aff.getFichePoste().getService());
			agDto.setDirection(siservSrv.getDirection(aff.getFichePoste().getService().getServi()) == null ? ""
					: siservSrv.getDirection(aff.getFichePoste().getService().getServi()).getLiServ());
			TitrePosteDto titrePoste = new TitrePosteDto(aff.getFichePoste());

			Contrat contrat = contratSrv.getContratBetweenDate(aff.getAgent().getIdAgent(), new Date());
			Integer dureePeriodeEssai = null;
			if (contrat != null && contrat.getDateFinPeriodeEssai() != null) {
				dureePeriodeEssai = contratSrv.getNbJoursPeriodeEssai(contrat.getDateDebutContrat(),
						contrat.getDateFinPeriodeEssai());
			}

			dto = new NoteServiceDto(aff, agDto, titrePoste, dureePeriodeEssai, contrat, typeNoteService);
		}

		return new ModelAndView("xmlView", "object", dto);
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/downloadNoteServiceSIRH", method =
	 * RequestMethod.GET)
	 * 
	 * @Transactional(readOnly = true) public ResponseEntity<byte[]>
	 * downloadNoteServiceSIRH(@RequestParam("idAffectation") int idAffectation)
	 * throws ParseException {
	 * 
	 * Affectation aff = affSrv.getAffectationById(idAffectation);
	 * 
	 * if (aff == null) return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
	 * 
	 * byte[] responseData = null;
	 * 
	 * try { responseData =
	 * reportingService.getNoteServiceSIRHReportAsByteArray(idFichePoste); }
	 * catch (Exception e) { logger.error(e.getMessage(), e); return new
	 * ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR); }
	 * 
	 * HttpHeaders headers = new HttpHeaders(); headers.add("Content-Type",
	 * "application/doc"); headers.add("Content-Disposition",
	 * String.format("attachment; filename=\"FP_%s.doc\"",
	 * fp.getIdFichePoste()));
	 * 
	 * return new ResponseEntity<byte[]>(responseData, headers, HttpStatus.OK);
	 * }
	 */
}
