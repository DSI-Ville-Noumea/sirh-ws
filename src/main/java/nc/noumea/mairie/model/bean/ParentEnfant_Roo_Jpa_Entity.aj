// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nc.noumea.mairie.model.bean.ParentEnfant;
import nc.noumea.mairie.model.pk.ParentEnfantPK;

privileged aspect ParentEnfant_Roo_Jpa_Entity {
    
    declare @type: ParentEnfant: @Entity;
    
    declare @type: ParentEnfant: @Table(schema = "SIRH", name = "PARENT_ENFANT");
    
    @EmbeddedId
    private ParentEnfantPK ParentEnfant.id;
    
    public ParentEnfantPK ParentEnfant.getId() {
        return this.id;
    }
    
    public void ParentEnfant.setId(ParentEnfantPK id) {
        this.id = id;
    }
    
}
