package nc.noumea.mairie.web.controller;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.service.ISibanqService;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.web.dto.BanqueGuichetDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/reference")
public class ReferenceController {

	@Autowired
	private ISibanqService sibanqSrv;

	@ResponseBody
	@RequestMapping(value = "/listeBanqueGuichet", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getListeBanqueGuichet() {

		List<BanqueGuichetDto> result = sibanqSrv.getListBanqueAvecGuichet();

		String response = new JSONSerializer().exclude("*.class").transform(new MSDateTransformer(), Date.class).deepSerialize(result);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
