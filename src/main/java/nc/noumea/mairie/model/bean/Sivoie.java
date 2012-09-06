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
@RooJpaActiveRecord(schema = "MAIRIE", table = "SIVOIE")
public class Sivoie {
	
	@Id
	@Column(name = "CDVOIE", columnDefinition = "numeric")
	private Integer idVoie;

	@NotNull
	@Column(name = "LIVOIE", columnDefinition = "char")
	private String liVoie;
}
