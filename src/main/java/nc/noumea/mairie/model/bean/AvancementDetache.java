package nc.noumea.mairie.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_AVCT", identifierField = "idAvct", identifierType = Integer.class, table = "AVCT_DETACHE", versionField = "")
public class AvancementDetache {

	@ManyToOne(optional = true)
	@JoinColumn(name = "ID_AGENT")
	private Agent agent;

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
}
