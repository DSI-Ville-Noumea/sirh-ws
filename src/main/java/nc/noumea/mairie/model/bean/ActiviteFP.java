package nc.noumea.mairie.model.bean;

import nc.noumea.mairie.model.pk.ActiviteFPPK;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierType = ActiviteFPPK.class, table = "ACTIVITE_FP", versionField = "")
public class ActiviteFP {
}
