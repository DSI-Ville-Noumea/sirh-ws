package nc.noumea.mairie.model.bean.sirh;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SUIVI_MEDICAL")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class SuiviMedical {

	@Id
	@Column(name = "ID_SUIVI_MED", columnDefinition = "numeric")
	private Integer idSuiviMedical;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AGENT", referencedColumnName = "ID_AGENT")
	private Agent agent;

	@NotNull
	@Column(name = "NB_VISITES_RATEES")
	private Integer nbVisitesRatees;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MEDECIN", referencedColumnName = "ID_MEDECIN")
	private Medecin medecinSuiviMedical;

	@Column(name = "DATE_PROCHAINE_VISITE")
	@Temporal(TemporalType.DATE)
	private Date dateProchaineVisite;

	@Column(name = "HEURE_PROCHAINE_VISITE")
	private String heureProchaineVisite;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MOTIF_VM", referencedColumnName = "ID_MOTIF_VM")
	private MotifVM motif;

	@Column(name = "ID_SERVICE_ADS")
	private Integer idServiceADS;

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Integer getIdSuiviMedical() {
		return idSuiviMedical;
	}

	public void setIdSuiviMedical(Integer idSuiviMedical) {
		this.idSuiviMedical = idSuiviMedical;
	}

	public Integer getNbVisitesRatees() {
		return nbVisitesRatees;
	}

	public void setNbVisitesRatees(Integer nbVisitesRatees) {
		this.nbVisitesRatees = nbVisitesRatees;
	}

	public Medecin getMedecinSuiviMedical() {
		return medecinSuiviMedical;
	}

	public void setMedecinSuiviMedical(Medecin medecinSuiviMedical) {
		this.medecinSuiviMedical = medecinSuiviMedical;
	}

	public Date getDateProchaineVisite() {
		return dateProchaineVisite;
	}

	public void setDateProchaineVisite(Date dateProchaineVisite) {
		this.dateProchaineVisite = dateProchaineVisite;
	}

	public String getHeureProchaineVisite() {
		return heureProchaineVisite;
	}

	public void setHeureProchaineVisite(String heureProchaineVisite) {
		this.heureProchaineVisite = heureProchaineVisite;
	}

	public MotifVM getMotif() {
		return motif;
	}

	public void setMotif(MotifVM motif) {
		this.motif = motif;
	}

	public Integer getIdServiceADS() {
		return idServiceADS;
	}

	public void setIdServiceADS(Integer idServiceADS) {
		this.idServiceADS = idServiceADS;
	}
}
