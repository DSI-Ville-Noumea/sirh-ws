package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierColumn = "ID_NIVEAU_ETUDE", schema = "SIRH", identifierField = "idNiveauEtude", identifierType = Integer.class, table = "R_NIVEAU_ETUDE")
@RooSerializable
public class NiveauEtude {

	@NotNull
	@Column(name = "CODE_NIVEAU_ETUDE")
	private String libelleNiveauEtude;
}
