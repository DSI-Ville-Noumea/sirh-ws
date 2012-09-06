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
@RooJpaActiveRecord(schema = "MAIRIE", table = "SPGENG")
public class Spgeng {
	
	@Id
	@Column(name = "CDGENG", columnDefinition = "char")
	private String cdgeng;

	@NotNull
	@Column(name = "LIGRAD", columnDefinition = "char")
	private String liGrad;
}
