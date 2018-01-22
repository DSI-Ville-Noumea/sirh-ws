package nc.noumea.mairie.mdf.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nc.noumea.mairie.mdf.service.IBordereauRecapService;

@Controller
@RequestMapping("/edition")
public class EditionController {

	private Logger logger = LoggerFactory.getLogger(EditionController.class);

	@Autowired
	private IBordereauRecapService bordereauRecapService;

	@ResponseBody
	@RequestMapping(value = "/downloadRecapMdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadRecapMdf() {

		byte[] responseData = null;

		try {
			responseData = bordereauRecapService.getRecapMDFAsByteArray();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/pdf");
		headers.add("Content-Disposition", String.format("attachment; filename=\"titreDemande.pdf\""));

		return new ResponseEntity<byte[]>(responseData, headers, HttpStatus.OK);
	}

}
