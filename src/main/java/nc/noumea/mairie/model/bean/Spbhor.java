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
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", table = "SPBHOR", versionField = "")
public class Spbhor {

	@Id
	@Column(name = "CDTHOR", columnDefinition = "decimal")
	private String cdThor;

	@NotNull
	@Column(name = "LIBHOR", columnDefinition = "char")
	private String libHor;
}
