// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import nc.noumea.mairie.model.bean.Diplome;

privileged aspect Diplome_Roo_Jpa_Entity {
    
    declare @type: Diplome: @Entity;
    
    declare @type: Diplome: @Table(name = "P_DIPLOME_GENERIQUE");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_DIPLOME_GENERIQUE")
    private Integer Diplome.idDiplomeGenerique;
    
    public Integer Diplome.getIdDiplomeGenerique() {
        return this.idDiplomeGenerique;
    }
    
    public void Diplome.setIdDiplomeGenerique(Integer id) {
        this.idDiplomeGenerique = id;
    }
    
}
