package nc.noumea.mairie.model.bean.sirh;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "HISTO_FICHE_POSTE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class HistoFichePoste {

	@Id
	@NotNull
	@Column(name = "ID_FICHE_POSTE")
	private Integer idFichePoste;

	@Column(name = "ID_TITRE_POSTE")
	private Integer idTitrePoste;

	@Column(name = "ID_ENTITE_GEO", columnDefinition = "decimal")
	private Integer idEntiteGeo;

	@Column(name = "ID_BUDGET")
	private Integer idBudget;

	@Column(name = "ID_STATUT_FP")
	private Integer idStatutFp;

	@Column(name = "ID_RESPONSABLE")
	private Integer idResponsable;

	@Column(name = "ID_REMPLACEMENT")
	private Integer idRemplacement;

	@NotNull
	@Column(name = "ID_CDTHOR_BUD", columnDefinition = "decimal")
	private Integer idCdthorBud;

	@NotNull
	@Column(name = "ID_CDTHOR_REG", columnDefinition = "decimal")
	private Integer idCdthorReg;

	@Column(name = "ID_SERVI", columnDefinition = "char")
	private String idServi;

	@Column(name = "DATE_FIN_VALIDITE_FP")
	@Temporal(TemporalType.DATE)
	private Date dateFinValiditeFp;

	@Column(name = "OPI")
	private String opi;

	@NotNull
	@Column(name = "NFA")
	private String nfa;

	@Lob
	@NotNull
	@Column(name = "MISSIONS")
	private String missions;

	@NotNull
	@Column(name = "ANNEE_CREATION", columnDefinition = "numeric")
	private Integer anneeCreation;

	@Column(name = "CODE_GRADE", columnDefinition = "char")
	private String codeGrade;

	@NotNull
	@Column(name = "NUM_FP")
	private String numFp;

	@Column(name = "DATE_HISTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateHisto;

	@Column(name = "TYPE_HISTO")
	private String typeHisto;

	@Column(name = "USER_HISTO")
	private String userHisto;

	@Column(name = "DATE_DEBUT_VALIDITE_FP")
	@Temporal(TemporalType.DATE)
	private Date dateDebutValiditeFp;

	@Column(name = "DATE_DEB_APPLI_SERV")
	@Temporal(TemporalType.DATE)
	private Date dateDebAppliServ;

	@Column(name = "DATE_FIN_APPLI_SERV")
	@Temporal(TemporalType.DATE)
	private Date dateFinAppliServ;

	@Column(name = "ID_NATURE_CREDIT")
	private Integer idNatureCredit;

	@Column(name = "NUM_DELIBERATION")
	private String numDeliberation;

	@Column(name = "ID_BASE_HORAIRE_POINTAGE")
	private Integer idBaseHorairePointage;

	@Column(name = "ID_BASE_HORAIRE_ABSENCE")
	private Integer idBaseHoraireAbsence;

	@Column(name = "ID_SERVICE_ADS")
	private Integer idServiceADS;

	public HistoFichePoste() {
		super();
	}

	/**
	 * Constructeur HistoFichePoste.
	 */
	public HistoFichePoste(FichePoste fichePoste) {
		super();

		this.idFichePoste = fichePoste.getIdFichePoste();
		this.idTitrePoste = fichePoste.getTitrePoste().getIdTitrePoste();
		this.idEntiteGeo = fichePoste.getLieuPoste().getCodeLieu().intValue();
		this.idBudget = fichePoste.getBudget().getIdBudget();
		this.idStatutFp = fichePoste.getStatutFP().getIdStatutFp();
		this.idResponsable = fichePoste.getSuperieurHierarchique().getIdFichePoste();
		this.idRemplacement = fichePoste.getRemplace().getIdFichePoste();
		this.idCdthorBud = fichePoste.getBudgete().getCdThor();
		this.idCdthorReg = fichePoste.getReglementaire().getCdThor();
		this.idServi = fichePoste.getIdServi();
		this.dateFinValiditeFp = fichePoste.getDateFinValiditeFp();
		this.dateDebutValiditeFp = fichePoste.getDateDebutValiditeFp();
		this.opi = fichePoste.getOpi();
		this.nfa = fichePoste.getNfa();
		this.missions = fichePoste.getMissions();
		this.anneeCreation = fichePoste.getAnnee();
		this.codeGrade = fichePoste.getGradePoste().getCdgrad();
		this.numFp = fichePoste.getNumFP();
		this.dateDebAppliServ = fichePoste.getDateDebAppliServ();
		this.idNatureCredit = fichePoste.getNatureCredit().getIdNatureCredit();
		this.numDeliberation = fichePoste.getNumDeliberation();
		this.idBaseHorairePointage = fichePoste.getIdBaseHorairePointage();
		this.idBaseHoraireAbsence = fichePoste.getIdBaseHoraireAbsence();
		this.idServiceADS = fichePoste.getIdServiceADS();

	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}

	public Integer getIdTitrePoste() {
		return idTitrePoste;
	}

	public void setIdTitrePoste(Integer idTitrePoste) {
		this.idTitrePoste = idTitrePoste;
	}

	public Integer getIdEntiteGeo() {
		return idEntiteGeo;
	}

	public void setIdEntiteGeo(Integer idEntiteGeo) {
		this.idEntiteGeo = idEntiteGeo;
	}

	public Integer getIdBudget() {
		return idBudget;
	}

	public void setIdBudget(Integer idBudget) {
		this.idBudget = idBudget;
	}

	public Integer getIdStatutFp() {
		return idStatutFp;
	}

	public void setIdStatutFp(Integer idStatutFp) {
		this.idStatutFp = idStatutFp;
	}

	public Integer getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
	}

	public Integer getIdRemplacement() {
		return idRemplacement;
	}

	public void setIdRemplacement(Integer idRemplacement) {
		this.idRemplacement = idRemplacement;
	}

	public Integer getIdCdthorBud() {
		return idCdthorBud;
	}

	public void setIdCdthorBud(Integer idCdthorBud) {
		this.idCdthorBud = idCdthorBud;
	}

	public Integer getIdCdthorReg() {
		return idCdthorReg;
	}

	public void setIdCdthorReg(Integer idCdthorReg) {
		this.idCdthorReg = idCdthorReg;
	}

	public String getIdServi() {
		return idServi;
	}

	public void setIdServi(String idServi) {
		this.idServi = idServi;
	}

	public Date getDateFinValiditeFp() {
		return dateFinValiditeFp;
	}

	public void setDateFinValiditeFp(Date dateFinValiditeFp) {
		this.dateFinValiditeFp = dateFinValiditeFp;
	}

	public String getOpi() {
		return opi;
	}

	public void setOpi(String opi) {
		this.opi = opi;
	}

	public String getNfa() {
		return nfa;
	}

	public void setNfa(String nfa) {
		this.nfa = nfa;
	}

	public String getMissions() {
		return missions;
	}

	public void setMissions(String missions) {
		this.missions = missions;
	}

	public Integer getAnneeCreation() {
		return anneeCreation;
	}

	public void setAnneeCreation(Integer anneeCreation) {
		this.anneeCreation = anneeCreation;
	}

	public String getCodeGrade() {
		return codeGrade;
	}

	public void setCodeGrade(String codeGrade) {
		this.codeGrade = codeGrade;
	}

	public String getNumFp() {
		return numFp;
	}

	public void setNumFp(String numFp) {
		this.numFp = numFp;
	}

	public String getTypeHisto() {
		return typeHisto;
	}

	public void setTypeHisto(String typeHisto) {
		this.typeHisto = typeHisto;
	}

	public String getUserHisto() {
		return userHisto;
	}

	public void setUserHisto(String userHisto) {
		this.userHisto = userHisto;
	}

	public Integer getIdNatureCredit() {
		return idNatureCredit;
	}

	public void setIdNatureCredit(Integer idNatureCredit) {
		this.idNatureCredit = idNatureCredit;
	}

	public String getNumDeliberation() {
		return numDeliberation;
	}

	public void setNumDeliberation(String numDeliberation) {
		this.numDeliberation = numDeliberation;
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

	public Integer getIdServiceADS() {
		return idServiceADS;
	}

	public void setIdServiceADS(Integer idServiceADS) {
		this.idServiceADS = idServiceADS;
	}

	public Date getDateHisto() {
		return dateHisto;
	}

	public void setDateHisto(Date dateHisto) {
		this.dateHisto = dateHisto;
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

	public Date getDateFinAppliServ() {
		return dateFinAppliServ;
	}

	public void setDateFinAppliServ(Date dateFinAppliServ) {
		this.dateFinAppliServ = dateFinAppliServ;
	}
}
