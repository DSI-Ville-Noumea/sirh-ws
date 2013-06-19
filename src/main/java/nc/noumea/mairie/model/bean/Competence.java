package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_COMPETENCE", identifierField = "idCompetence", identifierType = Integer.class, table = "COMPETENCE", versionField = "")
@RooSerializable
public class Competence {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "NOM_COMPETENCE")
	private String nomCompetence;

	@OneToOne
	@JoinColumn(name = "ID_TYPE_COMPETENCE", referencedColumnName = "ID_TYPE_COMPETENCE")
	private TypeCompetence typeCompetence;

}
