package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.service.sirh.IHolidayService;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.JourDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/utils")
public class UtilsController {

	private Logger logger = LoggerFactory.getLogger(UtilsController.class);

	@Autowired
	private IHolidayService holidayService;

	@ResponseBody
	@RequestMapping(value = "isHoliday", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> isHoliday(@RequestParam("date") @DateTimeFormat(pattern = "yyyyMMdd") Date date)
			throws ParseException {

		logger.debug("entered GET [utils/isHoliday] => isHoliday with parameter date = {}  ", date);

		boolean res = holidayService.isHoliday(date);

		if (!res) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>(HttpStatus.OK);
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "isJourFerie", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> isJourFerie(@RequestParam("date") @DateTimeFormat(pattern = "yyyyMMdd") Date date)
			throws ParseException {

		logger.debug("entered GET [utils/isJourFerie] => isHoliday with parameter date = {}  ", date);

		boolean res = holidayService.isJourFerie(date);

		if (!res) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>(HttpStatus.OK);
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "listeJoursFeries", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeJoursFeries(@RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyyMMdd") Date dateDebut,
			@RequestParam("dateFin") @DateTimeFormat(pattern = "yyyyMMdd") Date dateFin)
			throws ParseException {

		logger.debug("entered GET [utils/listeJoursFeries] => getListeJoursFeries with parameter dateDebut = {}, dateFin = {}  ", 
				dateDebut, dateFin);

		List<JourDto> result = holidayService.getListeJoursFeries(dateDebut, dateFin);

		if (result.size() == 0)
			new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
}
