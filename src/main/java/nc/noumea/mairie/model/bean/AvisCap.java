package nc.noumea.mairie.model.bean;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_AVIS_CAP", schema = "SIRH", identifierField = "idAvisCap", identifierType = Integer.class, table = "R_AVIS_CAP", versionField = "")
public class AvisCap {

}
