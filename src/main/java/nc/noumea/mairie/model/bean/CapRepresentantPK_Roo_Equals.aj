// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import nc.noumea.mairie.model.bean.CapRepresentantPK;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect CapRepresentantPK_Roo_Equals {
    
    public boolean CapRepresentantPK.equals(Object obj) {
        if (!(obj instanceof CapRepresentantPK)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        CapRepresentantPK rhs = (CapRepresentantPK) obj;
        return new EqualsBuilder().append(idCap, rhs.idCap).append(idRepresentant, rhs.idRepresentant).isEquals();
    }
    
    public int CapRepresentantPK.hashCode() {
        return new HashCodeBuilder().append(idCap).append(idRepresentant).toHashCode();
    }
    
}
