package nc.noumea.mairie.web.controller;

import java.util.List;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.service.IAgentMatriculeConverterService;
import nc.noumea.mairie.model.service.IFichePosteService;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/fichePostes")
public class FichePosteController {

	@Autowired
	private IFichePosteService fpSrv;
	
	@Autowired
	private IAgentMatriculeConverterService agentMatriculeConverterService;
	
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
	
}
