// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.web.controller;

import java.util.List;
import nc.noumea.mairie.model.bean.eae.Eae;
import nc.noumea.mairie.web.controller.EaeController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

privileged aspect EaeController_Roo_Controller_Json {
    
    @RequestMapping(value = "/{idEae}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> EaeController.showJson(@PathVariable("idEae") Integer idEae) {
        Eae eae = Eae.findEae(idEae);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (eae == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(eae.toJson(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> EaeController.listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Eae> result = Eae.findAllEaes();
        return new ResponseEntity<String>(Eae.toJsonArray(result), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> EaeController.createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        Eae eae = Eae.fromJsonToEae(json);
        eae.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
        headers.add("Location",uriBuilder.path(a.value()[0]+"/"+eae.getId().toString()).build().toUriString());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> EaeController.createFromJsonArray(@RequestBody String json) {
        for (Eae eae: Eae.fromJsonArrayToEaes(json)) {
            eae.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/{idEae}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> EaeController.updateFromJson(@RequestBody String json, @PathVariable("idEae") Integer idEae) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Eae eae = Eae.fromJsonToEae(json);
        eae.setIdEae(idEae);
        if (eae.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{idEae}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> EaeController.deleteFromJson(@PathVariable("idEae") Integer idEae) {
        Eae eae = Eae.findEae(idEae);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (eae == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        eae.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
}
