package nc.noumea.mairie.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.SuiviMedical;
import nc.noumea.mairie.model.bean.sirh.VisiteMedicale;
import nc.noumea.mairie.service.IReportingService;
import nc.noumea.mairie.service.sirh.IFichePosteService;
import nc.noumea.mairie.service.sirh.ISuiviMedicalService;
import nc.noumea.mairie.web.dto.AccompagnementVMDto;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.ConvocationVMDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.ListVMDto;
import nc.noumea.mairie.web.dto.MedecinDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

@Controller
@RequestMapping("/suiviMedical")
public class SuiviMedicalController {

	private Logger					logger	= LoggerFactory.getLogger(SuiviMedicalController.class);

	@Autowired
	private ISuiviMedicalService	smSrv;

	@Autowired
	private IADSWSConsumer			adsConsumer;

	@Autowired
	private IReportingService		reportingService;

	@Autowired
	private IFichePosteService		fichePosteService;

	@Autowired
	private IADSWSConsumer			adsWSConsumer;

	@ResponseBody
	@RequestMapping(value = "/xml/getConvocationSIRH", produces = "application/xml", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView getXmlConvocationSIRH(@RequestParam("csvIdSuiviMedical") String csvIdSuiviMedical,
			@RequestParam("typePopulation") String typePopulation) throws ParseException {

		logger.debug(
				"entered GET [suiviMedical/xml/getConvocationSIRH] => getXmlConvocationSIRH with parameter csvIdSuiviMedical = {} and typePopulation = {} ",
				csvIdSuiviMedical, typePopulation);

		ListVMDto listDto = new ListVMDto();
		listDto.setTypePopulation(typePopulation);
		if (csvIdSuiviMedical != null) {

			List<Integer> suiviMedIds = new ArrayList<Integer>();
			for (String id : csvIdSuiviMedical.split(",")) {
				suiviMedIds.add(Integer.valueOf(id));
			}

			for (Integer idSuivi : suiviMedIds) {
				SuiviMedical sm = smSrv.getSuiviMedicalById(idSuivi);

				EntiteDto service = adsConsumer.getEntiteByIdEntite(sm.getIdServiceADS());

				AgentWithServiceDto agDto = new AgentWithServiceDto(sm.getAgent(), service);
				if (service != null) {

					EntiteDto direction = adsConsumer.getAffichageDirection(sm.getIdServiceADS());
					agDto.setDirection(direction == null ? "" : direction.getLabel());
				}

				MedecinDto medDto = new MedecinDto(sm.getMedecinSuiviMedical());

				ConvocationVMDto dto = new ConvocationVMDto(sm, agDto, medDto);
				listDto.getConvocations().add(dto);
			}
		}

		return new ModelAndView("xmlView", "object", listDto);
	}

	@ResponseBody
	@RequestMapping(value = "/xml/getLettreAccompagnementSIRH", produces = "application/xml", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ModelAndView getXmlLettreAccompagnementSIRH(@RequestParam("csvIdSuiviMedical") String csvIdSuiviMedical,
			@RequestParam("typePopulation") String typePopulation) throws ParseException {

		logger.debug(
				"entered GET [suiviMedical/xml/getLettreAccompagnementSIRH] => getXmlLettreAccompagnementSIRH with parameter csvIdSuiviMedical = {} and typePopulation = {} ",
				csvIdSuiviMedical, typePopulation);

		ListVMDto listDto = new ListVMDto();
		listDto.setTypePopulation(typePopulation);
		if (csvIdSuiviMedical != null) {

			List<Integer> suiviMedIds = new ArrayList<Integer>();
			for (String id : csvIdSuiviMedical.split(",")) {
				suiviMedIds.add(Integer.valueOf(id));
			}

			for (Integer idSuivi : suiviMedIds) {
				SuiviMedical sm = smSrv.getSuiviMedicalById(idSuivi);

				EntiteDto service = adsConsumer.getEntiteByIdEntite(sm.getIdServiceADS());

				EntiteDto servResponsable = null;
				AgentWithServiceDto agRespDto = null;
				AgentWithServiceDto agDto = new AgentWithServiceDto(sm.getAgent(), service);
				if (service != null) {
					EntiteDto direction = adsConsumer.getAffichageDirection(sm.getIdServiceADS());
					agDto.setDirection(direction == null ? "" : direction.getLabel());
					if (direction != null && direction.getSigle().equals(service.getSigle())) {
						service = null;
					} else {
						servResponsable = adsConsumer.getParentOfEntiteByTypeEntite(service.getIdEntite(), null);

						agRespDto = new AgentWithServiceDto(null, servResponsable);
						EntiteDto directionResponsable = adsConsumer.getAffichageDirection(servResponsable.getIdEntite());
						agRespDto.setDirection(directionResponsable == null ? "" : directionResponsable.getLabel());
					}
				}

				AccompagnementVMDto dto = new AccompagnementVMDto(agRespDto);
				ConvocationVMDto convocDto = new ConvocationVMDto(sm, agDto, null);

				dto.getAgents().add(convocDto);
				listDto.getAccompagnements().add(dto);
			}
		}

		return new ModelAndView("xmlView", "object", listDto);
	}

	@ResponseBody
	@RequestMapping(value = "/downloadCertificatAptitudePDF", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> downloadCertificatAptitudePDF(@RequestParam("idVM") int idVisite) throws ParseException {

		logger.debug("entered GET [suiviMedical/downloadCertificatAptitudePDF] => downloadCertificatAptitudePDF with parameter idVisite = {} ",
				idVisite);

		if (smSrv.getVisiteMedicale(idVisite) == null)
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);

		// on recupere les infos
		VisiteMedicale vm = smSrv.getVisiteMedicale(idVisite);

		Integer fpId = fichePosteService.getIdFichePostePrimaireAgentAffectationEnCours(vm.getAgent().getIdAgent(), vm.getDateDerniereVisite());
		FichePoste fp = fichePosteService.getFichePosteById(fpId);
		if (fp == null || fp.getIdServiceADS() == null) {
			logger.error("L'agent id={} n'a pas d'affectation sur une entité ADS.", vm.getAgent().getIdAgent());
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		EntiteDto direction = adsWSConsumer.getAffichageDirection(fp.getIdServiceADS());
		EntiteDto service = adsWSConsumer.getAffichageSection(fp.getIdServiceADS());
		if (service == null)
			service = adsWSConsumer.getAffichageService(fp.getIdServiceADS());
		if (service == null)
			service = adsWSConsumer.getEntiteByIdEntite(fp.getIdServiceADS());

		byte[] responseData = null;

		try {

			responseData = reportingService.getCertificatAptitudePDF(vm, direction, service, fp.getTitrePoste());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/pdf");
		headers.add("Content-Disposition", String.format("attachment; filename=\"certificat_aptitude.pdf\""));

		return new ResponseEntity<byte[]>(responseData, headers, HttpStatus.OK);

	}

}
