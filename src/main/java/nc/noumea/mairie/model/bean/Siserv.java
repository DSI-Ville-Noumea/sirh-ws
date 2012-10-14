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
@RooJpaActiveRecord(identifierColumn = "SERVI", identifierField = "servi", identifierType = String.class, persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", table = "SISERV", versionField = "")
public class Siserv {

	@NotNull
	@Column(name = "LISERV", columnDefinition = "char")
	private String liServ;
}
