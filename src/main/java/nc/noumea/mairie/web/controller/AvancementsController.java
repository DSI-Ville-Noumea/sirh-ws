package nc.noumea.mairie.web.controller;

import nc.noumea.mairie.model.bean.Cap;
import nc.noumea.mairie.model.service.IReportingService;
import nc.noumea.mairie.service.IAvancementsService;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;

import org.json.simple.parser.ParseException;
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
@RequestMapping("/avancements")
public class AvancementsController {
	
	private Logger logger = LoggerFactory.getLogger(AvancementsController.class);
	
	@Autowired
	private IAvancementsService avancementsService;
	
	@Autowired
	private IReportingService reportingService;
	
	@ResponseBody
	@RequestMapping(value = "/xml/getTableauAvancements",  produces = "application/xml", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView getTableauAvancements(@RequestParam("idCap") int idCap, @RequestParam("idCadreEmploi") int idCadreEmploi) throws ParseException {
		
		CommissionAvancementDto dto = avancementsService.getCommissionsForCapAndCadreEmploi(idCap, idCadreEmploi);
		
		return new ModelAndView("xmlView", "object", dto);
	}
	
	@ResponseBody
	@RequestMapping(value = "/downloadTableauAvancements", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> downloadTableauAvancements(@RequestParam("idCap") int idCap, @RequestParam("idCadreEmploi") int idCadreEmploi) throws ParseException {
		
		Cap cap = Cap.findCap(idCap);
		
		if (cap == null)
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		
		byte[] responseData = null;
		
		try {
			responseData = reportingService.getTableauAvancementsReportAsByteArray(idCap, idCadreEmploi);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<byte []>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/pdf");
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s-%s.pdf\"", idCap, idCadreEmploi));
		
		return new ResponseEntity<byte []>(responseData, headers, HttpStatus.OK);
	}
}
