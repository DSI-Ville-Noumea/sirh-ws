package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", table = "SPGRADN",versionField="")
public class Spgradn {
	
	@Id
	@Column(name = "CDGRAD", columnDefinition = "char")
	private String cdgrad;

	@NotNull
	@Column(name = "LIGRAD", columnDefinition = "char")
	private String liGrad;

	@NotNull
	@Column(name = "GRADE", columnDefinition = "char")
	private String gradeInitial;

	@OneToOne(optional=true)
	@JoinColumn(name = "CODGRG", referencedColumnName = "CDGENG")
	private Spgeng gradeGenerique;
}
