package nc.noumea.mairie.model.bean.sirh;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.bean.Silieu;
import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spgradn;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;
import org.springframework.core.annotation.Order;

@Entity
@Table(name = "FICHE_POSTE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class FichePoste {

	@Id
	@Column(name = "ID_FICHE_POSTE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idFichePoste;

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(optional = true, fetch = FetchType.LAZY, orphanRemoval = false)
	@JoinColumn(name = "ID_TITRE_POSTE", referencedColumnName = "ID_TITRE_POSTE")
	@LazyToOne(value = LazyToOneOption.NO_PROXY)
	private TitrePoste titrePoste;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ENTITE_GEO", referencedColumnName = "CDLIEU")
	private Silieu lieuPoste;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
	private Budget budget;

	@Column(name = "OPI")
	private String opi;
	@NotNull
	@Column(name = "NFA")
	private String nfa;
	@NotNull
	@Column(name = "ANNEE_CREATION", columnDefinition = "numeric")
	private Integer annee;
	@NotNull
	@Column(name = "NUM_FP")
	private String numFP;
	@NotNull
	@Column(name = "MISSIONS", columnDefinition = "clob")
	private String missions;
	@Column(name = "OBSERVATION", columnDefinition = "clob")
	private String observation;
	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_STATUT_FP", referencedColumnName = "ID_STATUT_FP")
	private StatutFichePoste statutFP;
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CDTHOR_BUD", referencedColumnName = "CDTHOR")
	private Spbhor budgete;
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CDTHOR_REG", referencedColumnName = "CDTHOR")
	private Spbhor reglementaire;
	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "CODE_GRADE", referencedColumnName = "CDGRAD")
	private Spgradn gradePoste;
	@OneToMany(mappedBy = "fichePoste", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@OrderBy("activiteFPPK.idActivite asc")
	private Set<ActiviteFP> activites = new HashSet<ActiviteFP>();
	@OneToMany(mappedBy = "fichePoste", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@OrderBy("competenceFPPK.idCompetence asc")
	private Set<CompetenceFP> competencesFDP = new HashSet<CompetenceFP>();
	@OneToOne(optional = true, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "NIVEAU_ETUDE_FP", joinColumns = { @JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @JoinColumn(name = "ID_NIVEAU_ETUDE"))
	private NiveauEtude niveauEtude;
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "AVANTAGE_NATURE_FP", joinColumns = { @JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @JoinColumn(name = "ID_AVANTAGE"))
	@OrderBy("numRubrique asc")
	private Set<AvantageNature> avantagesNature = new HashSet<AvantageNature>();
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "DELEGATION_FP", joinColumns = { @JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @JoinColumn(name = "ID_DELEGATION"))
	@OrderBy("libDelegation asc")
	private Set<Delegation> delegations = new HashSet<Delegation>();
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "REG_INDEMN_FP", joinColumns = { @JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @JoinColumn(name = "ID_REGIME"))
	@OrderBy("numRubrique asc")
	private Set<RegimeIndemnitaire> regimesIndemnitaires = new HashSet<RegimeIndemnitaire>();
	@OneToMany(mappedBy = "fichePoste", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@OrderBy("primePointageFPPK.numRubrique asc")
	private Set<PrimePointageFP> primePointageFP = new HashSet<PrimePointageFP>();
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "FE_FP", joinColumns = { @JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @JoinColumn(name = "ID_FICHE_EMPLOI"))
	@WhereJoinTable(clause = "FE_PRIMAIRE = 1")
	private Set<FicheEmploi> ficheEmploiPrimaire = new HashSet<FicheEmploi>();
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "FE_FP", joinColumns = { @JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @JoinColumn(name = "ID_FICHE_EMPLOI"))
	@WhereJoinTable(clause = "FE_PRIMAIRE = 0")
	private Set<FicheEmploi> ficheEmploiSecondaire = new HashSet<FicheEmploi>();
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "FM_FP", joinColumns = { @JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @JoinColumn(name = "ID_FICHE_METIER"))
	@WhereJoinTable(clause = "FM_PRIMAIRE = 1")
	private Set<FicheMetier> ficheMetierPrimaire = new HashSet<>();
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "FM_FP", joinColumns = { @JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @JoinColumn(name = "ID_FICHE_METIER"))
	@WhereJoinTable(clause = "FM_PRIMAIRE = 0")
	private Set<FicheMetier> ficheMetierSecondaire = new HashSet<>();
	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RESPONSABLE", referencedColumnName = "ID_FICHE_POSTE")
	private FichePoste superieurHierarchique;
	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_REMPLACEMENT", referencedColumnName = "ID_FICHE_POSTE")
	private FichePoste remplace;
	@OneToMany(mappedBy = "fichePoste", fetch = FetchType.LAZY)
	@Where(clause = "DATE_DEBUT_AFF <= CURRENT_DATE and (DATE_FIN_AFF is null or DATE_FIN_AFF >= CURRENT_DATE)")
	private Set<Affectation> agent = new HashSet<Affectation>();
	@OneToMany(mappedBy = "fichePoste", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<FeFp> ficheEmploi = new HashSet<FeFp>();
	@OneToOne(cascade = CascadeType.MERGE, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_NATURE_CREDIT", referencedColumnName = "ID_NATURE_CREDIT")
	private NatureCredit natureCredit;
	@Column(name = "ID_SERVICE_ADS")
	private Integer idServiceADS;
	@Column(name = "ID_SERVI", columnDefinition = "char")
	private String idServi;
	@Column(name = "ID_BASE_HORAIRE_POINTAGE")
	private Integer idBaseHorairePointage;
	@Column(name = "ID_BASE_HORAIRE_ABSENCE")
	private Integer idBaseHoraireAbsence;
	@Column(name = "NUM_DELIBERATION")
	private String numDeliberation;
	@Column(name = "DATE_FIN_VALIDITE_FP")
	@Temporal(TemporalType.DATE)
	private Date dateFinValiditeFp;
	@Column(name = "DATE_DEBUT_VALIDITE_FP")
	@Temporal(TemporalType.DATE)
	private Date dateDebutValiditeFp;
	@Column(name = "DATE_DEB_APPLI_SERV")
	@Temporal(TemporalType.DATE)
	private Date dateDebAppliServ;
	@Column(name = "INFORMATIONS_COMPLEMENTAIRES", columnDefinition = "clob")
	private String informationsComplementaires;
	@Column(name = "SPECIALISATION", columnDefinition = "clob")
	private String specialisation;
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_NIVEAU_MANAGEMENT")
	private NiveauManagement niveauManagement;

	@OneToMany(mappedBy = "idFichePoste", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@OrderBy("ordre")
	private List<ActiviteMetierSavoirFp> activiteMetier = new ArrayList<>();

	@OneToMany(mappedBy = "idFichePoste", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@OrderBy("ordre")
	private List<SavoirFaireFp> savoirFaire = new ArrayList<>();

	@OneToMany(mappedBy = "idFichePoste", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@OrderBy("ordre")
	private List<ActiviteGeneraleFp> activitesGenerales = new ArrayList<>();

	@OneToMany(mappedBy = "idFichePoste", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@OrderBy("ordre")
	private List<ConditionExerciceFp> conditionsExercice = new ArrayList<>();

	public String getOpi() {
		if (this.opi == null)
			return "";
		return this.opi;
	}

	public void setOpi(String opi) {
		this.opi = opi;
	}

	public TitrePoste getTitrePoste() {
		return titrePoste;
	}

	public void setTitrePoste(TitrePoste titrePoste) {
		this.titrePoste = titrePoste;
	}

	public Silieu getLieuPoste() {
		return lieuPoste;
	}

	public void setLieuPoste(Silieu lieuPoste) {
		this.lieuPoste = lieuPoste;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public String getNfa() {
		return nfa;
	}

	public void setNfa(String nfa) {
		this.nfa = nfa;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public String getNumFP() {
		return numFP;
	}

	public void setNumFP(String numFP) {
		this.numFP = numFP;
	}

	public String getMissions() {
		return missions;
	}

	public void setMissions(String missions) {
		this.missions = missions;
	}

	public StatutFichePoste getStatutFP() {
		return statutFP;
	}

	public void setStatutFP(StatutFichePoste statutFP) {
		this.statutFP = statutFP;
	}

	public Spbhor getBudgete() {
		return budgete;
	}

	public void setBudgete(Spbhor budgete) {
		this.budgete = budgete;
	}

	public Spbhor getReglementaire() {
		return reglementaire;
	}

	public void setReglementaire(Spbhor reglementaire) {
		this.reglementaire = reglementaire;
	}

	public Spgradn getGradePoste() {
		return gradePoste;
	}

	public void setGradePoste(Spgradn gradePoste) {
		this.gradePoste = gradePoste;
	}

	public Set<ActiviteFP> getActivites() {
		return activites;
	}

	public void setActivites(Set<ActiviteFP> activites) {
		this.activites = activites;
	}

	public Set<CompetenceFP> getCompetencesFDP() {
		return competencesFDP;
	}

	public void setCompetencesFDP(Set<CompetenceFP> competencesFDP) {
		this.competencesFDP = competencesFDP;
	}

	public NiveauEtude getNiveauEtude() {
		return niveauEtude;
	}

	public void setNiveauEtude(NiveauEtude niveauEtude) {
		this.niveauEtude = niveauEtude;
	}

	public Set<AvantageNature> getAvantagesNature() {
		return avantagesNature;
	}

	public void setAvantagesNature(Set<AvantageNature> avantagesNature) {
		this.avantagesNature = avantagesNature;
	}

	public Set<Delegation> getDelegations() {
		return delegations;
	}

	public void setDelegations(Set<Delegation> delegations) {
		this.delegations = delegations;
	}

	public Set<RegimeIndemnitaire> getRegimesIndemnitaires() {
		return regimesIndemnitaires;
	}

	public void setRegimesIndemnitaires(Set<RegimeIndemnitaire> regimesIndemnitaires) {
		this.regimesIndemnitaires = regimesIndemnitaires;
	}

	public Set<PrimePointageFP> getPrimePointageFP() {
		return primePointageFP;
	}

	public void setPrimePointageFP(Set<PrimePointageFP> primePointageFP) {
		this.primePointageFP = primePointageFP;
	}

	public Set<FicheEmploi> getFicheEmploiPrimaire() {
		return ficheEmploiPrimaire;
	}

	public void setFicheEmploiPrimaire(Set<FicheEmploi> ficheEmploiPrimaire) {
		this.ficheEmploiPrimaire = ficheEmploiPrimaire;
	}

	public Set<FicheEmploi> getFicheEmploiSecondaire() {
		return ficheEmploiSecondaire;
	}

	public void setFicheEmploiSecondaire(Set<FicheEmploi> ficheEmploiSecondaire) {
		this.ficheEmploiSecondaire = ficheEmploiSecondaire;
	}

	public Set<FicheMetier> getFicheMetierPrimaire() {
		return ficheMetierPrimaire;
	}

	public void setFicheMetierPrimaire(Set<FicheMetier> ficheMetierPrimaire) {
		this.ficheMetierPrimaire = ficheMetierPrimaire;
	}

	public Set<FicheMetier> getFicheMetierSecondaire() {
		return ficheMetierSecondaire;
	}

	public void setFicheMetierSecondaire(Set<FicheMetier> ficheMetierSecondaire) {
		this.ficheMetierSecondaire = ficheMetierSecondaire;
	}

	public FichePoste getSuperieurHierarchique() {
		return superieurHierarchique;
	}

	public void setSuperieurHierarchique(FichePoste superieurHierarchique) {
		this.superieurHierarchique = superieurHierarchique;
	}

	public FichePoste getRemplace() {
		return remplace;
	}

	public void setRemplace(FichePoste remplace) {
		this.remplace = remplace;
	}

	public Set<Affectation> getAgent() {
		return agent;
	}

	public void setAgent(Set<Affectation> agent) {
		this.agent = agent;
	}

	public NatureCredit getNatureCredit() {
		return natureCredit;
	}

	public void setNatureCredit(NatureCredit natureCredit) {
		this.natureCredit = natureCredit;
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}

	public Integer getIdServiceADS() {
		return idServiceADS;
	}

	public void setIdServiceADS(Integer idServiceADS) {
		this.idServiceADS = idServiceADS;
	}

	public String getIdServi() {
		return idServi;
	}

	public void setIdServi(String idServi) {
		this.idServi = idServi;
	}

	public Integer getIdBaseHorairePointage() {
		return idBaseHorairePointage;
	}

	public void setIdBaseHorairePointage(Integer idBaseHorairePointage) {
		this.idBaseHorairePointage = idBaseHorairePointage;
	}

	public Integer getIdBaseHoraireAbsence() {
		return idBaseHoraireAbsence;
	}

	public void setIdBaseHoraireAbsence(Integer idBaseHoraireAbsence) {
		this.idBaseHoraireAbsence = idBaseHoraireAbsence;
	}

	public String getNumDeliberation() {
		return numDeliberation;
	}

	public void setNumDeliberation(String numDeliberation) {
		this.numDeliberation = numDeliberation;
	}

	public Date getDateFinValiditeFp() {
		return dateFinValiditeFp;
	}

	public void setDateFinValiditeFp(Date dateFinValiditeFp) {
		this.dateFinValiditeFp = dateFinValiditeFp;
	}

	public Date getDateDebutValiditeFp() {
		return dateDebutValiditeFp;
	}

	public void setDateDebutValiditeFp(Date dateDebutValiditeFp) {
		this.dateDebutValiditeFp = dateDebutValiditeFp;
	}

	public Date getDateDebAppliServ() {
		return dateDebAppliServ;
	}

	public void setDateDebAppliServ(Date dateDebAppliServ) {
		this.dateDebAppliServ = dateDebAppliServ;
	}

	public Set<FeFp> getFicheEmploi() {
		return ficheEmploi;
	}

	public void setFicheEmploi(Set<FeFp> ficheEmploi) {
		this.ficheEmploi = ficheEmploi;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Basic
	@Column(name = "INFORMATIONS_COMPLEMENTAIRES", nullable = true)
	public String getInformationsComplementaires() {
		return informationsComplementaires;
	}

	public void setInformationsComplementaires(String informationsComplementaires) {
		this.informationsComplementaires = informationsComplementaires;
	}

	@Basic
	@Column(name = "SPECIALISATION", nullable = true, length = 255)
	public String getSpecialisation() {
		return specialisation;
	}

	public void setSpecialisation(String specialisation) {
		this.specialisation = specialisation;
	}

	public NiveauManagement getNiveauManagement() {
		return niveauManagement;
	}

	public void setNiveauManagement(NiveauManagement niveauManagement) {
		this.niveauManagement = niveauManagement;
	}

	public List<SavoirFaireFp> getSavoirFaire() {
		return savoirFaire;
	}

	public void setSavoirFaire(List<SavoirFaireFp> savoirFaire) {
		this.savoirFaire = savoirFaire;
	}

	public List<ActiviteGeneraleFp> getActivitesGenerales() {
		return activitesGenerales;
	}

	public void setActivitesGenerales(List<ActiviteGeneraleFp> activitesGenerales) {
		this.activitesGenerales = activitesGenerales;
	}

	public List<ConditionExerciceFp> getConditionsExercice() {
		return conditionsExercice;
	}

	public void setConditionsExercice(List<ConditionExerciceFp> conditionsExercice) {
		this.conditionsExercice = conditionsExercice;
	}

	public List<ActiviteMetierSavoirFp> getActiviteMetier() {
		return activiteMetier;
	}

	public void setActiviteMetier(List<ActiviteMetierSavoirFp> activiteMetier) {
		this.activiteMetier = activiteMetier;
	}
}
