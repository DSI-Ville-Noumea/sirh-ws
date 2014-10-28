package nc.noumea.mairie.web.controller;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.service.sirh.IPointageService;
import nc.noumea.mairie.web.dto.BaseHorairePointageDto;

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
@RequestMapping("/pointages")
public class PointageController {

	@Autowired
	private IPointageService pointageSrv;

	@ResponseBody
	@RequestMapping(value = "/listPrimePointages", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getPrimePointageAffOfAgent(
			@RequestParam(value = "idAgent", required = true) Integer idAgent,
			@RequestParam(value = "date", required = true) @DateTimeFormat(pattern = "YYYYMMdd") Date date) {

		List<Integer> result = pointageSrv.getPrimePointagesByAgent(idAgent, date);

		if (result.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/baseHoraire", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getBaseHoraireOfAgent(
			@RequestParam(value = "idAgent", required = true) Integer idAgent,
			@RequestParam(value = "date", required = true) @DateTimeFormat(pattern = "YYYYMMdd") Date date) {

		BaseHorairePointageDto result = pointageSrv.getBaseHorairePointageByAgent(idAgent, date);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

}
