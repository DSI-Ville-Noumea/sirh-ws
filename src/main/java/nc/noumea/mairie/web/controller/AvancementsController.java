package nc.noumea.mairie.web.controller;

import nc.noumea.mairie.service.IAvancementsService;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;

import org.json.simple.parser.ParseException;
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
@RequestMapping("/avancements/xml")
public class AvancementsController {

	private Logger logger = LoggerFactory.getLogger(AvancementsController.class);
	
	@Autowired
	private IAvancementsService avancementsService;
	
	@ResponseBody
	@RequestMapping(value = "/getTableauAvancements",  produces = "application/xml", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView getTableauAvancements(@RequestParam("idCap") int idCap, @RequestParam("idCadreEmploi") int idCadreEmploi) throws ParseException {
		
		CommissionAvancementDto dto = avancementsService.getCommissionsForCapAndCadreEmploi(0, 0);
		
		return new ModelAndView("xmlView", "object", dto);
	}
}
