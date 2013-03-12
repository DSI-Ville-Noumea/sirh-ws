package nc.noumea.mairie.model.bean;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", table = "SPGENG", versionField="")
@NamedQuery(name = "getSpgengFromCadreEmploi", query = "select s from Spgeng s JOIN FETCH s.filiere LEFT JOIN FETCH s.deliberationTerritoriale LEFT JOIN FETCH s.deliberationCommunale JOIN FETCH s.cadreEmploiGrade where s.cadreEmploiGrade.idCadreEmploi = :idCadreEmploi")
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
	
	@Column(name = "TEXTECAPCADREEMPLOI", columnDefinition = "char")
	private String texteCapCadreEmploi;
	
	@Column(name = "CDCADR", columnDefinition = "char")
	private String cdcadr;
	
	@OneToOne(optional=true)
	@JoinColumn(name = "CDFILI", referencedColumnName = "CDFILI")
	private Spfili filiere;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(schema = "SIRH", name = "CORPS_CAP", joinColumns = { @javax.persistence.JoinColumn(name = "CDGENG") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_CAP"))
	private Set<Cap> caps;
	
	@ManyToOne
	@JoinColumn(name = "IDDELIBTERR")
	private Deliberation deliberationTerritoriale;
	
	@ManyToOne
	@JoinColumn(name = "IDDELIBCOMM")
	private Deliberation deliberationCommunale;
}
