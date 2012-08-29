package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(identifierColumn = "CDVOIE", schema = "MAIRIE", identifierField = "idVoie", identifierType = Integer.class, table = "SIVOIE")
public class Sivoie {

	@NotNull
	@Column(name = "LIVOIE")
	private String liVoie;
}
