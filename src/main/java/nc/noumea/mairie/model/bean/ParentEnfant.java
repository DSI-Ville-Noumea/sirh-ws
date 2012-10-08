package nc.noumea.mairie.model.bean;

import javax.persistence.Column;

import nc.noumea.mairie.model.pk.ParentEnfantPK;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierType = ParentEnfantPK.class, table = "PARENT_ENFANT", schema = "SIRH", versionField = "")
@RooJson
public class ParentEnfant {

	@Column(name = "ENFANT_A_CHARGE")
	private Boolean enfantACharge;
}
