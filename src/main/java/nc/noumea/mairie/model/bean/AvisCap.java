package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_AVIS_CAP", identifierField = "idAvisCap", identifierType = Integer.class, table = "R_AVIS_CAP", versionField = "")
public class AvisCap {


	@NotNull
	@Column(name = "LIB_LONG_AVIS_CAP")
	private String libLong;
}
