// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import javax.persistence.Entity;
import javax.persistence.Table;
import nc.noumea.mairie.model.bean.CapRepresentant;

privileged aspect CapRepresentant_Roo_Jpa_Entity {
    
    declare @type: CapRepresentant: @Entity;
    
    declare @type: CapRepresentant: @Table(schema = "SIRH", name = "REPRESENTANT_CAP");
    
}
