// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.Set;
import nc.noumea.mairie.model.bean.CadreEmploi;
import nc.noumea.mairie.model.bean.Cap;
import nc.noumea.mairie.model.bean.Deliberation;
import nc.noumea.mairie.model.bean.Spfili;
import nc.noumea.mairie.model.bean.Spgeng;

privileged aspect Spgeng_Roo_JavaBean {
    
    public String Spgeng.getCdgeng() {
        return this.cdgeng;
    }
    
    public void Spgeng.setCdgeng(String cdgeng) {
        this.cdgeng = cdgeng;
    }
    
    public String Spgeng.getLiGrad() {
        return this.liGrad;
    }
    
    public void Spgeng.setLiGrad(String liGrad) {
        this.liGrad = liGrad;
    }
    
    public CadreEmploi Spgeng.getCadreEmploiGrade() {
        return this.cadreEmploiGrade;
    }
    
    public void Spgeng.setCadreEmploiGrade(CadreEmploi cadreEmploiGrade) {
        this.cadreEmploiGrade = cadreEmploiGrade;
    }
    
    public String Spgeng.getTexteCapCadreEmploi() {
        return this.texteCapCadreEmploi;
    }
    
    public void Spgeng.setTexteCapCadreEmploi(String texteCapCadreEmploi) {
        this.texteCapCadreEmploi = texteCapCadreEmploi;
    }
    
    public String Spgeng.getCdcadr() {
        return this.cdcadr;
    }
    
    public void Spgeng.setCdcadr(String cdcadr) {
        this.cdcadr = cdcadr;
    }
    
    public Spfili Spgeng.getSpfili() {
        return this.Spfili;
    }
    
    public void Spgeng.setSpfili(Spfili Spfili) {
        this.Spfili = Spfili;
    }
    
    public Set<Cap> Spgeng.getCaps() {
        return this.caps;
    }
    
    public void Spgeng.setCaps(Set<Cap> caps) {
        this.caps = caps;
    }
    
    public Deliberation Spgeng.getDeliberationTerritoriale() {
        return this.deliberationTerritoriale;
    }
    
    public void Spgeng.setDeliberationTerritoriale(Deliberation deliberationTerritoriale) {
        this.deliberationTerritoriale = deliberationTerritoriale;
    }
    
    public Deliberation Spgeng.getDeliberationCommunale() {
        return this.deliberationCommunale;
    }
    
    public void Spgeng.setDeliberationCommunale(Deliberation deliberationCommunale) {
        this.deliberationCommunale = deliberationCommunale;
    }
    
}
