package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierType = Integer.class, identifierColumn = "ID_NATURE_CREDIT", identifierField = "idNatureCredit", table = "P_NATURE_CREDIT", versionField = "")
public class NatureCredit {

	@NotNull
	@Column(name = "LIB_NATURE_CREDIT")
	private String libNatureCredit;
}
