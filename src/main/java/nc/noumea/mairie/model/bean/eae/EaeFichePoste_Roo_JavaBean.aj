// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean.eae;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.eae.Eae;
import nc.noumea.mairie.model.bean.eae.EaeFichePoste;

privileged aspect EaeFichePoste_Roo_JavaBean {
    
    public Integer EaeFichePoste.getIdAgentShd() {
        return this.idAgentShd;
    }
    
    public void EaeFichePoste.setIdAgentShd(Integer idAgentShd) {
        this.idAgentShd = idAgentShd;
    }
    
    public Eae EaeFichePoste.getEae() {
        return this.eae;
    }
    
    public void EaeFichePoste.setEae(Eae eae) {
        this.eae = eae;
    }
    
    public String EaeFichePoste.getCodeService() {
        return this.codeService;
    }
    
    public void EaeFichePoste.setCodeService(String codeService) {
        this.codeService = codeService;
    }
    
    public Siserv EaeFichePoste.getService() {
        return this.service;
    }
    
    public void EaeFichePoste.setService(Siserv service) {
        this.service = service;
    }
    
    public Agent EaeFichePoste.getAgentShd() {
        return this.agentShd;
    }
    
    public void EaeFichePoste.setAgentShd(Agent agentShd) {
        this.agentShd = agentShd;
    }
    
}
