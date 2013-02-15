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
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", table = "SPGENG",versionField="")
public class Spgeng {
	
	@Id
	@Column(name = "CDGENG", columnDefinition = "char")
	private String cdgeng;

	@NotNull
	@Column(name = "LIGRAD", columnDefinition = "char")
	private String liGrad;

	@OneToOne(optional=true)
	@JoinColumn(name = "IDCADREEMPLOI", referencedColumnName = "ID_CADRE_EMPLOI")
	private CadreEmploi cadreEmploiGrade;
	
	@OneToOne(optional=true)
	@JoinColumn(name = "CDFILI", referencedColumnName = "CDFILI")
	private Spfili Spfili;
}
