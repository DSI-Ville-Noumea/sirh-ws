package nc.noumea.mairie.model.bean.sirh;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.bean.Silieu;
import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spgradn;

import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

@Entity
@Table(name = "FICHE_POSTE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class FichePoste {

	@Id
	@Column(name = "ID_FICHE_POSTE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idFichePoste;

	@NotNull
	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TITRE_POSTE", referencedColumnName = "ID_TITRE_POSTE")
	private TitrePoste titrePoste;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ENTITE_GEO", referencedColumnName = "CDLIEU")
	private Silieu lieuPoste;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
	private Budget budget;

	@Column(name = "OPI")
	private String opi;

	public String getOpi() {
		if (this.opi == null)
			return "";
		return this.opi;
	}

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

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "ACTIVITE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_ACTIVITE"))
	@OrderBy("idActivite asc")
	private Set<Activite> activites = new HashSet<Activite>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "COMPETENCE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_COMPETENCE"))
	@OrderBy("nomCompetence asc")
	private Set<Competence> competencesFDP = new HashSet<Competence>();

	@OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "NIVEAU_ETUDE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_NIVEAU_ETUDE"))
	private NiveauEtude niveauEtude;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "AVANTAGE_NATURE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_AVANTAGE"))
	@OrderBy("numRubrique asc")
	private Set<AvantageNature> avantagesNature = new HashSet<AvantageNature>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "DELEGATION_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_DELEGATION"))
	@OrderBy("libDelegation asc")
	private Set<Delegation> delegations = new HashSet<Delegation>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "REG_INDEMN_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_REGIME"))
	@OrderBy("numRubrique asc")
	private Set<RegimeIndemnitaire> regimesIndemnitaires = new HashSet<RegimeIndemnitaire>();

	@OneToMany(mappedBy = "fichePoste", fetch = FetchType.LAZY)
	@OrderBy("primePointageFPPK.numRubrique asc")
	private Set<PrimePointageFP> primePointageFP = new HashSet<PrimePointageFP>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "FE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_FICHE_EMPLOI"))
	@WhereJoinTable(clause = "FE_PRIMAIRE = 1")
	private Set<FicheEmploi> ficheEmploiPrimaire = new HashSet<FicheEmploi>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "FE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_FICHE_EMPLOI"))
	@WhereJoinTable(clause = "FE_PRIMAIRE = 0")
	private Set<FicheEmploi> ficheEmploiSecondaire = new HashSet<FicheEmploi>();

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RESPONSABLE", referencedColumnName = "ID_FICHE_POSTE")
	private FichePoste superieurHierarchique;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_REMPLACEMENT", referencedColumnName = "ID_FICHE_POSTE")
	private FichePoste remplace;

	@OneToMany(mappedBy = "fichePoste", fetch = FetchType.LAZY)
	@Where(clause = "DATE_DEBUT_AFF <= CURRENT_DATE and (DATE_FIN_AFF is null or DATE_FIN_AFF >= CURRENT_DATE)")
	private Set<Affectation> agent = new HashSet<Affectation>();
	
	@OneToMany(mappedBy = "fichePoste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<FeFp> ficheEmploi = new HashSet<FeFp>();

	@Transient
	public HashMap<String, List<String>> getCompetences() {
		HashMap<String, List<String>> res = new HashMap<String, List<String>>();
		for (Competence comp : getCompetencesFDP()) {
			if (!res.containsKey(comp.getTypeCompetence().getLibTypeCompetence())) {
				List<String> list = new ArrayList<String>();
				res.put(comp.getTypeCompetence().getLibTypeCompetence(), list);
			}
			res.get(comp.getTypeCompetence().getLibTypeCompetence()).add(comp.getNomCompetence());
		}
		return res;
	}

	@OneToOne(optional = true, fetch = FetchType.LAZY)
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

	public Set<Activite> getActivites() {
		return activites;
	}

	public void setActivites(Set<Activite> activites) {
		this.activites = activites;
	}

	public Set<Competence> getCompetencesFDP() {
		return competencesFDP;
	}

	public void setCompetencesFDP(Set<Competence> competencesFDP) {
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

	public void setOpi(String opi) {
		this.opi = opi;
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
	
}
