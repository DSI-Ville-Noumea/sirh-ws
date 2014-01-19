package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.SiidmaId;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", table = "SIIDMA", versionField = "")
public class Siidma {

	@EmbeddedId
	private SiidmaId id;

	@NotNull
	@Column(name = "NOMATR", columnDefinition = "numeric")
	private Integer nomatr;

}
