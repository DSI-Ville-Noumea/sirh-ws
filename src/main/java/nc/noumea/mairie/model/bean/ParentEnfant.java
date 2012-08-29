package nc.noumea.mairie.model.bean;

import nc.noumea.mairie.model.pk.ParentEnfantPK;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(identifierType = ParentEnfantPK.class, table = "PARENT_ENFANT", schema="SIRH")
public class ParentEnfant {
}
