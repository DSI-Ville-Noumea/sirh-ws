// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import nc.noumea.mairie.model.bean.Activite;

privileged aspect Activite_Roo_Jpa_Entity {
    
    declare @type: Activite: @Entity;
    
    declare @type: Activite: @Table(schema = "SIRH", name = "ACTIVITE");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ACTIVITE")
    private Integer Activite.idActivite;
    
    @Version
    @Column(name = "version")
    private Integer Activite.version;
    
    public Integer Activite.getIdActivite() {
        return this.idActivite;
    }
    
    public void Activite.setIdActivite(Integer id) {
        this.idActivite = id;
    }
    
    public Integer Activite.getVersion() {
        return this.version;
    }
    
    public void Activite.setVersion(Integer version) {
        this.version = version;
    }
    
}
