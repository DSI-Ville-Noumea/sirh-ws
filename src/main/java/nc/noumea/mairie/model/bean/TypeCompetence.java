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
@RooJpaActiveRecord(identifierColumn = "ID_TYPE_COMPETENCE", schema = "SIRH", identifierField = "idTypeCompetence", identifierType = Integer.class, table = "R_TYPE_COMPETENCE", versionField = "")
public class TypeCompetence {

	@NotNull
	@Column(name = "LIB_TYPE_COMPETENCE")
	private String libTypeCompetence;
}
