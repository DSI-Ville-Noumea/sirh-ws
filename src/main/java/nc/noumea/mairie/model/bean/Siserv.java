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
@RooJpaActiveRecord(identifierType = String.class,identifierColumn = "SERVI", identifierField = "servi", schema = "MAIRIE", table = "SISERV")
public class Siserv {

	@NotNull
	@Column(name = "LISERV")
	private String liServ;
}
