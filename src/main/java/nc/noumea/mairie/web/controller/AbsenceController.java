package nc.noumea.mairie.web.controller;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.service.sirh.IAbsenceService;
import nc.noumea.mairie.web.dto.InfosAlimAutoCongesAnnuelsDto;
import nc.noumea.mairie.web.dto.RefTypeSaisiCongeAnnuelDto;

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
@RequestMapping("/absences")
public class AbsenceController {

	@Autowired
	private IAbsenceService absenceSrv;

	@ResponseBody
	@RequestMapping(value = "/baseHoraire", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getBaseHoraireOfAgent(
			@RequestParam(value = "idAgent", required = true) Integer idAgent,
			@RequestParam(value = "date", required = true) @DateTimeFormat(pattern = "YYYYMMdd") Date date) {

		RefTypeSaisiCongeAnnuelDto result = absenceSrv.getBaseHoraireAbsenceByAgent(idAgent, date);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listPAPourAlimAutoCongesAnnuels", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListPAPourAlimAutoCongesAnnuels(
			@RequestParam(value = "idAgent", required = true) Integer idAgent,
			@RequestParam(value = "dateDebut", required = true) @DateTimeFormat(pattern = "YYYYMMdd") Date dateDebut,
			@RequestParam(value = "dateFin", required = true) @DateTimeFormat(pattern = "YYYYMMdd") Date dateFin) {

		List<InfosAlimAutoCongesAnnuelsDto> result = absenceSrv.getListPAPourAlimAutoCongesAnnuels(idAgent, dateDebut, dateFin);

		String json = new JSONSerializer().exclude("*.class").serialize(result);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

}
