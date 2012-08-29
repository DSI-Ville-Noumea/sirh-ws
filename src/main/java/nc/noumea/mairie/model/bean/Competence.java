package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierColumn = "ID_COMPETENCE", schema = "SIRH", identifierField = "idCompetence", identifierType = Integer.class, table = "COMPETENCE")
@RooSerializable
public class Competence {

	@NotNull
	@Column(name = "NOM_COMPETENCE")
	private String nomCompetence;
}
