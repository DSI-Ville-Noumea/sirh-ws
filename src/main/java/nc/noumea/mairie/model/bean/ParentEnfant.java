package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import nc.noumea.mairie.model.pk.ParentEnfantPK;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", table = "PARENT_ENFANT", versionField = "")
@RooJson
public class ParentEnfant {

	@EmbeddedId
	private ParentEnfantPK parentEnfantPK;

	@OneToOne
	@JoinColumn(name = "ID_AGENT", referencedColumnName = "ID_AGENT", insertable = false, updatable = false)
	private Agent parent;

	@OneToOne
	@JoinColumn(name = "ID_ENFANT", referencedColumnName = "ID_ENFANT", insertable = false, updatable = false)
	private Enfant enfant;

	@Column(name = "ENFANT_A_CHARGE")
	private Boolean enfantACharge;
    
    public String getEnfantACharge() {
    	if(this.enfantACharge==true){
    		return "oui";
    	}
        return "non";
    }
}
