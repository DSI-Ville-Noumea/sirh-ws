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
@RooJpaActiveRecord(identifierColumn = "CDTHOR", identifierField = "cdThor", schema = "MAIRIE", table = "SPBHOR")
public class Spbhor {

	@NotNull
	@Column(name = "LIBHOR")
	private String libHor;
}
