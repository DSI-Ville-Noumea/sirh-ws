// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.pk.SpcarrId;

privileged aspect Spcarr_Roo_JavaBean {
    
    public SpcarrId Spcarr.getId() {
        return this.id;
    }
    
    public void Spcarr.setId(SpcarrId id) {
        this.id = id;
    }
    
    public Integer Spcarr.getDateFin() {
        return this.dateFin;
    }
    
    public void Spcarr.setDateFin(Integer dateFin) {
        this.dateFin = dateFin;
    }
    
    public Integer Spcarr.getDateArrete() {
        return this.dateArrete;
    }
    
    public void Spcarr.setDateArrete(Integer dateArrete) {
        this.dateArrete = dateArrete;
    }
    
    public Integer Spcarr.getReferenceArrete() {
        return this.referenceArrete;
    }
    
    public void Spcarr.setReferenceArrete(Integer referenceArrete) {
        this.referenceArrete = referenceArrete;
    }
    
    public String Spcarr.getModReg() {
        return this.modReg;
    }
    
    public void Spcarr.setModReg(String modReg) {
        this.modReg = modReg;
    }
    
}
