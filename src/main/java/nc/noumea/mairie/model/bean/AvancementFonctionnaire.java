package nc.noumea.mairie.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_AVCT", schema = "SIRH", identifierField = "idAvct", identifierType = Integer.class, table = "AVCT_FONCT", versionField = "")
public class AvancementFonctionnaire {

	@ManyToOne(optional=true)
	@JoinColumn(name = "ID_AGENT")
	private Agent agent;
	
	@OneToOne(optional=true)
	@JoinColumn(name = "ID_AVIS_CAP")
	private AvisCap avisCap;
	
	@Column(name = "ID_MOTIF_AVCT")
	private Integer idModifAvancement;
	
	@Column(name = "ETAT", columnDefinition = "char")
	private String etat;
	
	@Column(name = "CODE_CATEGORIE")
	private int codeCategporie;
	
	@Column(name = "FILIERE")
	private String filiere;
	
	@ManyToOne
	@JoinColumn(name = "GRADE")
	private Spgradn grade;
	
	@Column(name = "DATE_GRADE")
	@Temporal(TemporalType.DATE)
	private Date gradeDate;

	@ManyToOne
	@JoinColumn(name = "ID_NOUV_GRADE")
	private Spgradn gradeNouveau;
	
	@Column(name = "DATE_AVCT_MINI")
	@Temporal(TemporalType.DATE)
	private Date dateAvctMini;
	
	@Column(name = "DATE_AVCT_MOY")
	@Temporal(TemporalType.DATE)
	private Date dateAvctMoy;
	
	@Column(name = "DATE_AVCT_MAXI")
	@Temporal(TemporalType.DATE)
	private Date dateAvctMaxi;

	@Column(name = "ACC_ANNEE")
	private Integer accAnnee;
	
	@Column(name = "ACC_MOIS")
	private Integer accMois;
	
	@Column(name = "ACC_JOUR")
	private Integer accJour;

	@Column(name = "ANNEE")
	private int anneeAvancement;
	
	@Column(name = "ORDRE_MERITE")
	private String ordreMerite;
	
	@Column(name = "REGULARISATION")
	private boolean regularisation;
	
	@Column(name = "DATE_CAP")
	@Temporal(TemporalType.DATE)
	private Date dateCap;
	
	@OneToOne(optional=true)
	@JoinColumn(name = "ID_AVIS_EMP")
	private AvisCap avisCapEmployeur;
}
