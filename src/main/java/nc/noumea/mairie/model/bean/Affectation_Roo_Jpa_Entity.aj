// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import nc.noumea.mairie.model.bean.Affectation;

privileged aspect Affectation_Roo_Jpa_Entity {
    
    declare @type: Affectation: @Entity;
    
    declare @type: Affectation: @Table(name = "AFFECTATION");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_AFFECTATION")
    private Integer Affectation.idAffectation;
    
    public Integer Affectation.getIdAffectation() {
        return this.idAffectation;
    }
    
    public void Affectation.setIdAffectation(Integer id) {
        this.idAffectation = id;
    }
    
}
