package nc.noumea.mairie.model.bean.sirh;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AFFECTATION")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
@NamedQueries({
		@NamedQuery(name = "getCurrentAffectation", query = "select a.fichePoste.idFichePoste from Affectation a where a.agent.idAgent = :idAgent and a.dateDebutAff <= :today and (a.dateFinAff is null or a.dateFinAff >= :today)"),
		@NamedQuery(name = "getAffectationActiveByAgent", query = "select a from Affectation a where a.agent.idAgent = :idAgent and a.dateDebutAff <= :today and (a.dateFinAff is null or a.dateFinAff >= :today)"),
		// #30357 on retire les join FETCH car ca retourne trop de ligne
		// et le setMaxResult de hibernate ne fonctionne pas avec
		@NamedQuery(name = "getAffectationActiveByAgentPourCalculEAE", query = "select a "
				+ " from Affectation a "
				+ " join a.fichePoste fp "
				+ " where a.agent.idAgent = :idAgent and a.dateDebutAff <= :today and (a.dateFinAff is null or a.dateFinAff >= :today)") })
public class Affectation {

	@Id
	@Column(name = "ID_AFFECTATION")
	private Integer idAffectation;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AGENT", referencedColumnName = "ID_AGENT")
	private Agent agent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AGENT", referencedColumnName = "ID_AGENT", insertable = false, updatable = false)
	private AgentRecherche agentrecherche;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FICHE_POSTE", referencedColumnName = "ID_FICHE_POSTE")
	private FichePoste fichePoste;

	@OneToOne(fetch = FetchType.LAZY)
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

	@Column(name = "ID_BASE_HORAIRE_ABSENCE")
	private Integer idBaseHoraireAbsence;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BASE_HORAIRE_POINTAGE", referencedColumnName = "ID_BASE_HORAIRE_POINTAGE")
	private BaseHorairePointage baseHorairePointage;
	
	@OneToMany(mappedBy = "affectation", fetch = FetchType.LAZY)
	@OrderBy("primePointageAffPK.numRubrique asc")
	private Set<PrimePointageAff> primePointageAff = new HashSet<PrimePointageAff>();
	
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

	public Integer getIdBaseHoraireAbsence() {
		return idBaseHoraireAbsence;
	}

	public void setIdBaseHoraireAbsence(Integer idBaseHoraireAbsence) {
		this.idBaseHoraireAbsence = idBaseHoraireAbsence;
	}

	public BaseHorairePointage getBaseHorairePointage() {
		return baseHorairePointage;
	}

	public void setBaseHorairePointage(BaseHorairePointage baseHorairePointage) {
		this.baseHorairePointage = baseHorairePointage;
	}

	public Set<PrimePointageAff> getPrimePointageAff() {
		return primePointageAff;
	}

	public void setPrimePointageAff(Set<PrimePointageAff> primePointageAff) {
		this.primePointageAff = primePointageAff;
	}
	
}
