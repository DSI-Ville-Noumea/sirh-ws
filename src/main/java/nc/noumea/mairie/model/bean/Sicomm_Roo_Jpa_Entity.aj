// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import nc.noumea.mairie.model.bean.Sicomm;

privileged aspect Sicomm_Roo_Jpa_Entity {
    
    declare @type: Sicomm: @Entity;
    
    declare @type: Sicomm: @Table(schema = "MAIRIE", name = "SICOMM");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CODCOM")
    private BigDecimal Sicomm.codeCommune;
    
    public BigDecimal Sicomm.getCodeCommune() {
        return this.codeCommune;
    }
    
    public void Sicomm.setCodeCommune(BigDecimal id) {
        this.codeCommune = id;
    }
    
}
