package nc.noumea.mairie.model.bean;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", identifierColumn = "CODCOM", identifierField = "codeCommune", table = "SICOMM", identifierType = BigDecimal.class, versionField = "")
public class Sicomm {

	@NotNull
	@Column(name = "LIBVIL", columnDefinition = "char")
	private String libVil;
}
