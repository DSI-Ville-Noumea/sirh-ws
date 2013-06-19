package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", table = "SPTYCO", versionField = "")
public class Sptyco {

	@Id
	@Column(name = "TYPE2", columnDefinition = "numeric")
	private Integer idType;

	@NotNull
	@Column(name = "LICONG", columnDefinition = "char")
	private String libTypeConge;
}
