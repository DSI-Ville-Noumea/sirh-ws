// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import nc.noumea.mairie.model.bean.Competence;
import nc.noumea.mairie.model.bean.TypeCompetence;

privileged aspect Competence_Roo_JavaBean {
    
    public String Competence.getNomCompetence() {
        return this.nomCompetence;
    }
    
    public void Competence.setNomCompetence(String nomCompetence) {
        this.nomCompetence = nomCompetence;
    }
    
    public TypeCompetence Competence.getTypeCompetence() {
        return this.typeCompetence;
    }
    
    public void Competence.setTypeCompetence(TypeCompetence typeCompetence) {
        this.typeCompetence = typeCompetence;
    }
    
}
