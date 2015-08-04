package nc.noumea.mairie.model.bean.sirh;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ACTION_FDP_JOB")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class ActionFdpJob {

	public ActionFdpJob() {
		super();
	}

	@Id
	@Column(name = "ID_ACTION_FDP_JOB")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idActionFdpJob;

	@Column(name = "ID_AGENT")
	@NotNull
	private Integer idAgent;

	@Column(name = "ID_FICHE_POSTE")
	@NotNull
	private Integer idFichePoste;

	@Column(name = "ID_NEW_SERVICE_ADS")
	private Integer idNewServiceAds;

	@Column(name = "TYPE_ACTION")
	@NotNull
	private String typeAction;

	@Column(name = "STATUT")
	private String statut;

	@Column(name = "DATE_SUBMISSION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSubmission;

	@Column(name = "DATE_STATUT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateStatut;

	public ActionFdpJob(Integer idFichePoste, Integer idAgent, String typeAction, Integer idNewEntite) {
		this.idFichePoste = idFichePoste;
		this.idAgent = idAgent;
		this.typeAction = typeAction;
		this.idNewServiceAds = idNewEntite;
	}

	public Integer getIdActionFdpJob() {
		return idActionFdpJob;
	}

	public void setIdActionFdpJob(Integer idActionFdpJob) {
		this.idActionFdpJob = idActionFdpJob;
	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}

	public String getTypeAction() {
		return typeAction;
	}

	public void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Date getDateSubmission() {
		return dateSubmission;
	}

	public void setDateSubmission(Date dateSubmission) {
		this.dateSubmission = dateSubmission;
	}

	public Date getDateStatut() {
		return dateStatut;
	}

	public void setDateStatut(Date dateStatut) {
		this.dateStatut = dateStatut;
	}

	public Integer getIdNewServiceAds() {
		return idNewServiceAds;
	}

	public void setIdNewServiceAds(Integer idNewServiceAds) {
		this.idNewServiceAds = idNewServiceAds;
	}
}
