package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import nc.noumea.mairie.model.pk.SpadmnId;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", table = "SPADMN", versionField = "")
public class Spadmn {
	

	@EmbeddedId
	private SpadmnId id;

	@Column(name = "DATFIN", columnDefinition = "numeric")
	private Integer datfin;

	@Column(name = "CDPADM", columnDefinition = "char")
	private String cdpadm;
}
