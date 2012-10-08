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
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", table = "SILIEU",versionField="")
public class Silieu {
	@Id
	@Column(name = "CDLIEU", columnDefinition = "decimal")
	private Long codeLieu;

	@NotNull
	@Column(name = "LILIEU", columnDefinition = "char")
	private String libelleLieu;
}
