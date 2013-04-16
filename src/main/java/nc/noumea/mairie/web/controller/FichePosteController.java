package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.List;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.service.IAgentMatriculeConverterService;
import nc.noumea.mairie.model.service.IFichePosteService;
import nc.noumea.mairie.model.service.IReportingService;
import nc.noumea.mairie.web.dto.FichePosteDto;

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
@RequestMapping("/fichePostes")
public class FichePosteController {

	private Logger logger = LoggerFactory.getLogger(FichePosteController.class);
	
	@Autowired
	private IFichePosteService fpSrv;
	
	@Autowired
	private IAgentMatriculeConverterService agentMatriculeConverterService;
	
	@Autowired
	private IReportingService reportingService;
	
	@ResponseBody
	@RequestMapping(value = "/rebuildFichePosteTree")
	@Transactional(readOnly = true)
	public ResponseEntity<String> rebuildFichePosteTree() throws ParseException {

		try {
			fpSrv.construitArbreFichePostes();
		}
		catch (Exception ex) {
			return new ResponseEntity<String>(ex.toString(), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getSubFichePostes", produces = "application/json; charset=utf-8")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getSubFichePostes(@RequestParam("idAgent") int idAgent, @RequestParam(value = "maxDepth", required = false, defaultValue = "3") int maxDepth) throws ParseException {

		int newIdAgent = agentMatriculeConverterService.tryConvertFromADIdAgentToEAEIdAgent(idAgent);
		
		Agent ag = Agent.findAgent(newIdAgent);		
		
		if (ag == null)
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		
		List<Integer> fichePosteIds = fpSrv.getListSubFichePoste(idAgent, maxDepth);
		
		if (fichePosteIds.size() == 0)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT); 
		
		return new ResponseEntity<String>(new JSONSerializer().serialize(fichePosteIds), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/downloadFichePoste", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> downloadFichePoste(@RequestParam("idFichePoste") int idFichePoste) throws ParseException {
		
		FichePoste fp = FichePoste.findFichePoste(idFichePoste);
		
		if (fp == null)
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		
		byte[] responseData = null;
		
		try {
			responseData = reportingService.getFichePosteReportAsByteArray(idFichePoste);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<byte []>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/pdf");
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s.pdf\"", fp.getNumFP().replace('/', '_')));
		
		return new ResponseEntity<byte []>(responseData, headers, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/xml/getFichePoste",  produces = "application/xml", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView getXmlFichePoste(@RequestParam("idFichePoste") int idFichePoste) throws ParseException {
		
		FichePoste fp = FichePoste.findFichePoste(idFichePoste);
		
		FichePosteDto dto = new FichePosteDto(fp);
		
		return new ModelAndView("xmlView", "object", dto);
	}
}
