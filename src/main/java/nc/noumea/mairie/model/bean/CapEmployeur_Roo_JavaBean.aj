// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import nc.noumea.mairie.model.bean.Cap;
import nc.noumea.mairie.model.bean.CapEmployeur;
import nc.noumea.mairie.model.bean.CapEmployeurPK;
import nc.noumea.mairie.model.bean.Employeur;

privileged aspect CapEmployeur_Roo_JavaBean {
    
    public CapEmployeurPK CapEmployeur.getCapEmployeurPk() {
        return this.capEmployeurPk;
    }
    
    public void CapEmployeur.setCapEmployeurPk(CapEmployeurPK capEmployeurPk) {
        this.capEmployeurPk = capEmployeurPk;
    }
    
    public Cap CapEmployeur.getCap() {
        return this.cap;
    }
    
    public void CapEmployeur.setCap(Cap cap) {
        this.cap = cap;
    }
    
    public Employeur CapEmployeur.getEmployeur() {
        return this.employeur;
    }
    
    public void CapEmployeur.setEmployeur(Employeur employeur) {
        this.employeur = employeur;
    }
    
    public Integer CapEmployeur.getPosition() {
        return this.position;
    }
    
    public void CapEmployeur.setPosition(Integer position) {
        this.position = position;
    }
    
}