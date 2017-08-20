package nc.noumea.mairie.mdf.domain.vdn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "FDSMUTDET")
@PersistenceUnit(unitName = "mdfVdnPersistenceUnit")
public class FdsMutDet {
	
	@Id
	private FdsMutDetId id;

	@Column(name = "TYPEN2", columnDefinition = "numeric")
	private Integer typeEnregistrement;

	@Column(name = "RANGEM", columnDefinition = "numeric")
	private Integer rangGemellaire;

	@Column(name = "MONTBRUT", columnDefinition = "numeric")
	private Integer montantBrut;

	@Column(name = "NBJOURS", columnDefinition = "numeric")
	private Integer nombreJours;

	@Column(name = "MONTPP", columnDefinition = "numeric")
	private Integer montantPP;

	@Column(name = "MONTPS", columnDefinition = "numeric")
	private Integer montantPS;

	@Column(name = "ANCMONT", columnDefinition = "numeric")
	private Integer ancienMontant;

	@Column(name = "ANCNBJR", columnDefinition = "numeric")
	private Integer ancienNombreJours;

	@Column(name = "TAUXPP", columnDefinition = "char")
	private String tauxPP;

	@Column(name = "TAUXPS", columnDefinition = "char")
	private String tauxPS;

	@Column(name = "MATCAFA", columnDefinition = "char")
	private String matriculeCafat;

	@Column(name = "ANCTAUPS", columnDefinition = "char")
	private String ancienTauxPS;

	@Column(name = "ANCTAUPP", columnDefinition = "char")
	private String ancienTauxPP;
	
	@Column(name = "COLLEC2", columnDefinition = "char")
	private String codeCollectivite;
	
	@Column(name = "NOMNAIS", columnDefinition = "char")
	private String nomNaissance;
	
	@Column(name = "NOMMAR", columnDefinition = "char")
	private String nomMarital;
	
	@Column(name = "PRENUM", columnDefinition = "char")
	private String prenom;
	
	@Column(name = "DATNAIS", columnDefinition = "char")
	private String dateNaissance;
	
	@Column(name = "DATEDEB", columnDefinition = "char")
	private String dateDebut;
	
	@Column(name = "DATEFIN", columnDefinition = "char")
	private String dateFin;
	
	@Column(name = "NOREGCOL", columnDefinition = "char")
	private String numeroRegulColl;
	
	@Column(name = "NOREGMDF", columnDefinition = "char")
	private String numeroRegulMDF;
	
	@Column(name = "DATEEMB", columnDefinition = "char")
	private String dateEmbauche;
	
	@Column(name = "CODPROD", columnDefinition = "char")
	private String codeProduit;
	
	@Column(name = "CODCOLL", columnDefinition = "char")
	private String codeCollege;

	public Integer getTypeEnregistrement() {
		return typeEnregistrement;
	}

	public void setTypeEnregistrement(Integer typeEnregistrement) {
		this.typeEnregistrement = typeEnregistrement;
	}

	public String getMatriculeCafat() {
		return matriculeCafat;
	}

	public void setMatriculeCafat(String matriculeCafat) {
		this.matriculeCafat = matriculeCafat;
	}

	public Integer getRangGemellaire() {
		return rangGemellaire;
	}

	public void setRangGemellaire(Integer rangGemellaire) {
		this.rangGemellaire = rangGemellaire;
	}

	public Integer getMontantBrut() {
		return montantBrut;
	}

	public void setMontantBrut(Integer montantBrut) {
		this.montantBrut = montantBrut;
	}

	public Integer getNombreJours() {
		return nombreJours;
	}

	public void setNombreJours(Integer nombreJours) {
		this.nombreJours = nombreJours;
	}

	public String getTauxPP() {
		return tauxPP;
	}

	public void setTauxPP(String tauxPP) {
		this.tauxPP = tauxPP;
	}

	public Integer getMontantPP() {
		return montantPP;
	}

	public void setMontantPP(Integer montantPP) {
		this.montantPP = montantPP;
	}

	public String getTauxPS() {
		return tauxPS;
	}

	public void setTauxPS(String tauxPS) {
		this.tauxPS = tauxPS;
	}

	public Integer getMontantPS() {
		return montantPS;
	}

	public void setMontantPS(Integer montantPS) {
		this.montantPS = montantPS;
	}

	public Integer getAncienMontant() {
		return ancienMontant;
	}

	public void setAncienMontant(Integer ancienMontant) {
		this.ancienMontant = ancienMontant;
	}

	public Integer getAncienNombreJours() {
		return ancienNombreJours;
	}

	public void setAncienNombreJours(Integer ancienNombreJours) {
		this.ancienNombreJours = ancienNombreJours;
	}

	public String getAncienTauxPP() {
		return ancienTauxPP;
	}

	public void setAncienTauxPP(String ancienTauxPP) {
		this.ancienTauxPP = ancienTauxPP;
	}

	public String getAncienTauxPS() {
		return ancienTauxPS;
	}

	public void setAncienTauxPS(String ancienTauxPS) {
		this.ancienTauxPS = ancienTauxPS;
	}

	public String getCodeCollectivite() {
		return codeCollectivite;
	}

	public void setCodeCollectivite(String codeCollectivite) {
		this.codeCollectivite = codeCollectivite;
	}

	public String getNomNaissance() {
		return nomNaissance;
	}

	public void setNomNaissance(String nomNaissance) {
		this.nomNaissance = nomNaissance;
	}

	public String getNomMarital() {
		return nomMarital;
	}

	public void setNomMarital(String nomMarital) {
		this.nomMarital = nomMarital;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	public String getNumeroRegulColl() {
		return numeroRegulColl;
	}

	public void setNumeroRegulColl(String numeroRegulColl) {
		this.numeroRegulColl = numeroRegulColl;
	}

	public String getNumeroRegulMDF() {
		return numeroRegulMDF;
	}

	public void setNumeroRegulMDF(String numeroRegulMDF) {
		this.numeroRegulMDF = numeroRegulMDF;
	}

	public String getDateEmbauche() {
		return dateEmbauche;
	}

	public void setDateEmbauche(String dateEmbauche) {
		this.dateEmbauche = dateEmbauche;
	}

	public String getCodeProduit() {
		return codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}

	public String getCodeCollege() {
		return codeCollege;
	}

	public void setCodeCollege(String codeCollege) {
		this.codeCollege = codeCollege;
	}

	public FdsMutDetId getId() {
		return id;
	}

	public void setId(FdsMutDetId id) {
		this.id = id;
	}
}
