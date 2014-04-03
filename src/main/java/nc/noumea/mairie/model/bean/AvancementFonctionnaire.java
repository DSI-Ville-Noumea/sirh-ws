package nc.noumea.mairie.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "AVCT_FONCT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class AvancementFonctionnaire {

	@Id
	@Column(name = "ID_AVCT")
	private Integer idAvct;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AGENT")
	private Agent agent;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRADE")
	private Spgradn grade;

	@Column(name = "DATE_GRADE")
	@Temporal(TemporalType.DATE)
	private Date gradeDate;

	@ManyToOne(fetch = FetchType.LAZY)
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

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AVIS_EMP")
	private AvisCap avisCapEmployeur;

	@Column(name = "NOUV_ACC_ANNEE")
	private Integer nouvAccAnnee;

	@Column(name = "NOUV_ACC_MOIS")
	private Integer nouvAccMois;

	@Column(name = "NOUV_ACC_JOUR")
	private Integer nouvAccJour;

	public String getAccRestant() {
		if (this.nouvAccAnnee.intValue() == 0 && this.nouvAccMois.intValue() == 0 && this.nouvAccJour.intValue() == 0) {
			if (this.accAnnee.intValue() != 0 || this.accMois.intValue() != 0 || this.accJour.intValue() != 0) {
				return "épuisée";
			}
			return "néant";
		}
		String anneeAcc = this.nouvAccAnnee.intValue() == 0 ? "" : this.nouvAccAnnee.intValue() > 1 ? this.nouvAccAnnee
				.intValue() + " ans, " : this.nouvAccAnnee.intValue() + " an, ";
		String moisAcc = this.nouvAccMois.intValue() == 0 ? "" : this.nouvAccMois.intValue() + " mois, ";
		String jourAcc = this.nouvAccJour.intValue() == 0 ? "" : this.nouvAccJour.intValue() > 1 ? this.nouvAccJour
				.intValue() + " jours " : this.nouvAccJour.intValue() + " jour ";
		String res = anneeAcc + moisAcc + jourAcc;
		if (res.endsWith(", "))
			res = res.substring(0, res.length() - 2);
		return res;
	}

	@Column(name = "AGENT_VDN")
	private boolean agentVDN;

	public Integer getIdAvct() {
		return idAvct;
	}

	public void setIdAvct(Integer idAvct) {
		this.idAvct = idAvct;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public AvisCap getAvisCap() {
		return avisCap;
	}

	public void setAvisCap(AvisCap avisCap) {
		this.avisCap = avisCap;
	}

	public Integer getIdModifAvancement() {
		return idModifAvancement;
	}

	public void setIdModifAvancement(Integer idModifAvancement) {
		this.idModifAvancement = idModifAvancement;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public int getCodeCategporie() {
		return codeCategporie;
	}

	public void setCodeCategporie(int codeCategporie) {
		this.codeCategporie = codeCategporie;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public Spgradn getGrade() {
		return grade;
	}

	public void setGrade(Spgradn grade) {
		this.grade = grade;
	}

	public Date getGradeDate() {
		return gradeDate;
	}

	public void setGradeDate(Date gradeDate) {
		this.gradeDate = gradeDate;
	}

	public Spgradn getGradeNouveau() {
		return gradeNouveau;
	}

	public void setGradeNouveau(Spgradn gradeNouveau) {
		this.gradeNouveau = gradeNouveau;
	}

	public Date getDateAvctMini() {
		return dateAvctMini;
	}

	public void setDateAvctMini(Date dateAvctMini) {
		this.dateAvctMini = dateAvctMini;
	}

	public Date getDateAvctMoy() {
		return dateAvctMoy;
	}

	public void setDateAvctMoy(Date dateAvctMoy) {
		this.dateAvctMoy = dateAvctMoy;
	}

	public Date getDateAvctMaxi() {
		return dateAvctMaxi;
	}

	public void setDateAvctMaxi(Date dateAvctMaxi) {
		this.dateAvctMaxi = dateAvctMaxi;
	}

	public Integer getAccAnnee() {
		return accAnnee;
	}

	public void setAccAnnee(Integer accAnnee) {
		this.accAnnee = accAnnee;
	}

	public Integer getAccMois() {
		return accMois;
	}

	public void setAccMois(Integer accMois) {
		this.accMois = accMois;
	}

	public Integer getAccJour() {
		return accJour;
	}

	public void setAccJour(Integer accJour) {
		this.accJour = accJour;
	}

	public int getAnneeAvancement() {
		return anneeAvancement;
	}

	public void setAnneeAvancement(int anneeAvancement) {
		this.anneeAvancement = anneeAvancement;
	}

	public String getOrdreMerite() {
		return ordreMerite;
	}

	public void setOrdreMerite(String ordreMerite) {
		this.ordreMerite = ordreMerite;
	}

	public boolean isRegularisation() {
		return regularisation;
	}

	public void setRegularisation(boolean regularisation) {
		this.regularisation = regularisation;
	}

	public Date getDateCap() {
		return dateCap;
	}

	public void setDateCap(Date dateCap) {
		this.dateCap = dateCap;
	}

	public AvisCap getAvisCapEmployeur() {
		return avisCapEmployeur;
	}

	public void setAvisCapEmployeur(AvisCap avisCapEmployeur) {
		this.avisCapEmployeur = avisCapEmployeur;
	}

	public Integer getNouvAccAnnee() {
		return nouvAccAnnee;
	}

	public void setNouvAccAnnee(Integer nouvAccAnnee) {
		this.nouvAccAnnee = nouvAccAnnee;
	}

	public Integer getNouvAccMois() {
		return nouvAccMois;
	}

	public void setNouvAccMois(Integer nouvAccMois) {
		this.nouvAccMois = nouvAccMois;
	}

	public Integer getNouvAccJour() {
		return nouvAccJour;
	}

	public void setNouvAccJour(Integer nouvAccJour) {
		this.nouvAccJour = nouvAccJour;
	}

	public boolean isAgentVDN() {
		return agentVDN;
	}

	public void setAgentVDN(boolean agentVDN) {
		this.agentVDN = agentVDN;
	}
}
