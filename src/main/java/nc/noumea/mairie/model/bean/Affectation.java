package nc.noumea.mairie.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AFFECTATION")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
@NamedQuery(name = "getCurrentAffectation", query = "select a.fichePoste.idFichePoste from Affectation a where a.agent.idAgent = :idAgent and a.dateDebutAff <= :today and (a.dateFinAff = '01/01/0001' or a.dateFinAff is null or a.dateFinAff >= :today)")
public class Affectation {

	@Id
	@Column(name = "ID_AFFECTATION")
	private Integer idAffectation;

	@NotNull
	@OneToOne
	@JoinColumn(name = "ID_AGENT", referencedColumnName = "ID_AGENT", insertable = false, updatable = false)
	private Agent agent;

	@NotNull
	@OneToOne
	@JoinColumn(name = "ID_AGENT", referencedColumnName = "ID_AGENT", insertable = false, updatable = false)
	private AgentRecherche agentrecherche;

	@NotNull
	@OneToOne
	@JoinColumn(name = "ID_FICHE_POSTE", referencedColumnName = "ID_FICHE_POSTE")
	private FichePoste fichePoste;

	@OneToOne
	@JoinColumn(name = "ID_FICHE_POSTE_SECONDAIRE", referencedColumnName = "ID_FICHE_POSTE")
	private FichePoste fichePosteSecondaire;

	@NotNull
	@Column(name = "DATE_DEBUT_AFF")
	@Temporal(TemporalType.DATE)
	private Date dateDebutAff;

	@Column(name = "DATE_FIN_AFF")
	@Temporal(TemporalType.DATE)
	private Date dateFinAff;

	@Column(name = "TEMPS_TRAVAIL")
	private String tempsTravail;

	public Integer getIdAffectation() {
		return idAffectation;
	}

	public void setIdAffectation(Integer idAffectation) {
		this.idAffectation = idAffectation;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public AgentRecherche getAgentrecherche() {
		return agentrecherche;
	}

	public void setAgentrecherche(AgentRecherche agentrecherche) {
		this.agentrecherche = agentrecherche;
	}

	public FichePoste getFichePoste() {
		return fichePoste;
	}

	public void setFichePoste(FichePoste fichePoste) {
		this.fichePoste = fichePoste;
	}

	public Date getDateDebutAff() {
		return dateDebutAff;
	}

	public void setDateDebutAff(Date dateDebutAff) {
		this.dateDebutAff = dateDebutAff;
	}

	public Date getDateFinAff() {
		return dateFinAff;
	}

	public void setDateFinAff(Date dateFinAff) {
		this.dateFinAff = dateFinAff;
	}

	public String getTempsTravail() {
		return tempsTravail;
	}

	public void setTempsTravail(String tempsTravail) {
		this.tempsTravail = tempsTravail;
	}

	public FichePoste getFichePosteSecondaire() {
		return fichePosteSecondaire;
	}

	public void setFichePosteSecondaire(FichePoste fichePosteSecondaire) {
		this.fichePosteSecondaire = fichePosteSecondaire;
	}

}
