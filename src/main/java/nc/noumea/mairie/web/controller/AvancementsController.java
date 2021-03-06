package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Cap;
import nc.noumea.mairie.service.IReportingService;
import nc.noumea.mairie.service.sirh.IAvancementsService;
import nc.noumea.mairie.web.dto.avancements.ArreteListDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;
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
@RequestMapping("/avancements")
public class AvancementsController {

	private Logger logger = LoggerFactory.getLogger(AvancementsController.class);

	@Autowired
	private IAvancementsService avancementsService;

	@Autowired
	private IReportingService reportingService;

	@ResponseBody
	@RequestMapping(value = "/downloadTableauAvancementsPDF", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> downloadTableauAvancementsPDF(@RequestParam("idCap") int idCap, @RequestParam("idCadreEmploi") int idCadreEmploi, @RequestParam("isAvisShd") boolean isAvisShd,@RequestParam(value = "idAgent", required = false) Integer idAgentConnecte)
			throws ParseException {

		logger.debug("entered GET [avancements/downloadTableauAvancementsPDF] => downloadTableauAvancementsPDF with parameter idCap = {} and idCadreEmploi = {} and isAvisShd = {}", idCap,
				idCadreEmploi, isAvisShd);

		if (avancementsService.getCap(idCap) == null)
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);

		Cap cap = avancementsService.getCap(idCap);

		CommissionAvancementDto dto = avancementsService.getCommissionsForCapAndCadreEmploi(idCap, idCadreEmploi, isAvisShd, cap.isCapVDN(),idAgentConnecte);

		byte[] responseData = null;

		try {
			responseData = reportingService.getTableauAvancementPDF(dto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/pdf");
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s-%s.pdf\"", idCap, idCadreEmploi));

		return new ResponseEntity<byte[]>(responseData, headers, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/getEaesGedIds", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getEaesGedIds(@RequestParam("idCap") int idCap,
			@RequestParam("idCadreEmploi") int idCadreEmploi) {

		ReturnMessageDto eaeIds = avancementsService.getAvancementsEaesForCapAndCadreEmploi(idCap, idCadreEmploi);
		List<String> res = new ArrayList<String>();
		for (int i = 0; i < eaeIds.getInfos().size(); i++) {
			res.add(eaeIds.getInfos().get(i));
		}

		return new ResponseEntity<String>(new JSONSerializer().serialize(eaeIds), HttpStatus.OK);
	}

	/**
	 * bug #19585 : on recrée le WS /getEaesGedIds
	 * car on a un doute sur son utilisation par sharepoint
	 */
	@ResponseBody
	@RequestMapping(value = "/getEaesGedIdsForSIRHJobs", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getEaesGedIdsForSIRHJobs(@RequestParam("idCap") int idCap,
			@RequestParam("idCadreEmploi") int idCadreEmploi) {

		ReturnMessageDto eaeIds = avancementsService.getAvancementsEaesForCapAndCadreEmploi(idCap, idCadreEmploi);
		List<String> res = new ArrayList<String>();
		for (int i = 0; i < eaeIds.getInfos().size(); i++) {
			res.add(eaeIds.getInfos().get(i));
		}
		// bug #19585
		return new ResponseEntity<String>(new JSONSerializer().serialize(res), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/xml/getArretes", produces = "application/xml", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView getArretes(@RequestParam("csvIdAgents") String csvIdAgents,
			@RequestParam("isChangementClasse") boolean isChangementClasse, @RequestParam("annee") int year,
			@RequestParam("isDetache") boolean isDetache) throws Exception {

		logger.debug(
				"entered GET [avancements/xml/getArretes] => getArretes with parameter csvIdAgents = {} and isChangementClasse = {} and annee = {} and isDetache = {}",
				csvIdAgents, isChangementClasse, year, isDetache);

		ArreteListDto arretes = new ArreteListDto();
		if (!isDetache) {
			arretes = avancementsService.getArretesForUsers(csvIdAgents, isChangementClasse, year);
		} else {
			arretes = avancementsService.getArretesDetachesForUsers(csvIdAgents, isChangementClasse, year);
		}

		logger.debug("sortie GET [avancements/xml/getArretes] => getArretes result size = {}", arretes == null
				|| arretes.getArretes() == null ? null : arretes.getArretes().size());

		return new ModelAndView("xmlView", "object", arretes);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadArretes", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> downloadArretes(@RequestParam("csvIdAgents") String csvIdAgents,
			@RequestParam("isChangementClasse") boolean isChangementClasse, @RequestParam("annee") int year,
			@RequestParam("isDetache") boolean isDetache) throws ParseException {

		byte[] responseData = null;

		try {
			responseData = reportingService.getArretesReportAsByteArray(csvIdAgents, isChangementClasse, year,
					isDetache);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/msword");

		return new ResponseEntity<byte[]>(responseData, headers, HttpStatus.OK);
	}
}
