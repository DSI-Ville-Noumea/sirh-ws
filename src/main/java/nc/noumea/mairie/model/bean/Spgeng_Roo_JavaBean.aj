// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import nc.noumea.mairie.model.bean.CadreEmploi;
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
    
    public Spfili Spgeng.getSpfili() {
        return this.Spfili;
    }
    
    public void Spgeng.setSpfili(Spfili Spfili) {
        this.Spfili = Spfili;
    }
    
}
