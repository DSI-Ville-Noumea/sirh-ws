// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.pk;

import nc.noumea.mairie.model.pk.ParentEnfantPK;

privileged aspect ParentEnfantPK_Roo_Identifier {
    
    public ParentEnfantPK.new(Integer idAgent, Integer idEnfant) {
        super();
        this.idAgent = idAgent;
        this.idEnfant = idEnfant;
    }

    private ParentEnfantPK.new() {
        super();
    }

    public Integer ParentEnfantPK.getIdAgent() {
        return idAgent;
    }
    
    public Integer ParentEnfantPK.getIdEnfant() {
        return idEnfant;
    }
    
}
