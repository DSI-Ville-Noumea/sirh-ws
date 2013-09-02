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
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", table = "SPCLAS", versionField = "")
public class Spclas {

	@Id
	@Column(name = "CODCLA", columnDefinition = "char")
	private String codcla;

	@NotNull
	@Column(name = "LIBCLA", columnDefinition = "char")
	private String libCla;
}