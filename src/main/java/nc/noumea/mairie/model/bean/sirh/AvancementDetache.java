package nc.noumea.mairie.model.bean.sirh;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nc.noumea.mairie.model.bean.Spgradn;

@Entity
@Table(name = "AVCT_DETACHE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class AvancementDetache {

	@Id
	@Column(name = "ID_AVCT")
	private Integer idAvct;

	@ManyToOne(optional = true)
	@JoinColumn(name = "ID_AGENT")
	private Agent agent;

	@Column(name = "ID_MOTIF_AVCT")
	private Integer idMotifAvancement;

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

	@Column(name = "DATE_AVCT_MOY")
	@Temporal(TemporalType.DATE)
	private Date dateAvctMoy;

	@Column(name = "ACC_ANNEE")
	private Integer accAnnee;

	@Column(name = "ACC_MOIS")
	private Integer accMois;

	@Column(name = "ACC_JOUR")
	private Integer accJour;

	@Column(name = "ANNEE")
	private int anneeAvancement;

	@Column(name = "REGULARISATION")
	private boolean regularisation;

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

	public Integer getIdMotifAvancement() {
		return idMotifAvancement;
	}

	public void setIdMotifAvancement(Integer idMotifAvancement) {
		this.idMotifAvancement = idMotifAvancement;
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

	public Date getDateAvctMoy() {
		return dateAvctMoy;
	}

	public void setDateAvctMoy(Date dateAvctMoy) {
		this.dateAvctMoy = dateAvctMoy;
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

	public boolean isRegularisation() {
		return regularisation;
	}

	public void setRegularisation(boolean regularisation) {
		this.regularisation = regularisation;
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
