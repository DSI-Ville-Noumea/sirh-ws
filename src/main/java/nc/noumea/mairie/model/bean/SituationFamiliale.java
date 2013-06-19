package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_SITUATION", identifierField = "idSituationFamiliale", identifierType = Integer.class, table = "R_SITUATION_FAMILIALE", versionField = "")
public class SituationFamiliale {

	@NotNull
	@Column(name = "LIB_SITUATION")
	private String libSituationFamiliale;
}
