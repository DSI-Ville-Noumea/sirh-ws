// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import nc.noumea.mairie.model.bean.Siidma;
import nc.noumea.mairie.model.pk.SiidmaId;

privileged aspect Siidma_Roo_JavaBean {
    
    public SiidmaId Siidma.getId() {
        return this.id;
    }
    
    public void Siidma.setId(SiidmaId id) {
        this.id = id;
    }
    
    public Integer Siidma.getNomatr() {
        return this.nomatr;
    }
    
    public void Siidma.setNomatr(Integer nomatr) {
        this.nomatr = nomatr;
    }
    
}
