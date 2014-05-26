package nc.noumea.mairie.model.bean.sirh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.bean.Sicomm;
import nc.noumea.mairie.model.bean.Sivoie;
import nc.noumea.mairie.tools.transformer.AgentDelegataireTransformer;
import nc.noumea.mairie.tools.transformer.AgentToAdresseTransformer;
import nc.noumea.mairie.tools.transformer.AgentToBanqueTransformer;
import nc.noumea.mairie.tools.transformer.AgentToEquipeTransformer;
import nc.noumea.mairie.tools.transformer.AgentToHierarchiqueTransformer;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.tools.transformer.NullableIntegerTransformer;
import nc.noumea.mairie.tools.transformer.ParentEnfantTransformer;
import nc.noumea.mairie.tools.transformer.SituationFamilialeTransformer;
import nc.noumea.mairie.tools.transformer.StringTrimTransformer;
import flexjson.JSONSerializer;

@Entity
@Table(name = "AGENT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Agent {

	@Id
	@Column(name = "ID_AGENT")
	private Integer idAgent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ParentEnfant> parentEnfants = new HashSet<ParentEnfant>();

	@Transient
	public List<ParentEnfant> getParentEnfantsOrderByDateNaiss() {
		List<ParentEnfant> res = new ArrayList<ParentEnfant>();
		res.addAll(getParentEnfants());

		Comparator<ParentEnfant> comp = new Comparator<ParentEnfant>() {
			@Override
			public int compare(ParentEnfant o1, ParentEnfant o2) {
				return o1.getEnfant().getDateNaissance().compareTo(o2.getEnfant().getDateNaissance());
			}

		};

		Collections.sort(res, comp);

		return res;
	}

	@NotNull
	@Column(name = "NOMATR")
	private Integer nomatr;

	@NotNull
	@Column(name = "NOM_PATRONYMIQUE")
	private String nomPatronymique;

	@Column(name = "NOM_MARITAL")
	private String nomMarital;

	@Column(name = "NOM_USAGE")
	private String nomUsage;

	@Column(name = "PRENOM_USAGE")
	private String prenomUsage;

	@Column(name = "PRENOM")
	private String prenom;

	@NotNull
	@Column(name = "CIVILITE")
	private String titre;

	public String getTitre() {
		if (this.titre.equals("0")) {
			return "Monsieur";
		} else if (this.titre.equals("1")) {
			return "Madame";
		} else if (this.titre.equals("2")) {
			return "Mademoiselle";
		} else {
			return "";
		}
	}

	@NotNull
	@Column(name = "SEXE")
	private String sexe;

	@NotNull
	@Column(name = "DATE_NAISSANCE")
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;

	@NotNull
	@Column(name = "DATE_DERNIERE_EMBAUCHE")
	@Temporal(TemporalType.DATE)
	private Date dateDerniereEmbauche;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SITUATION_FAMILIALE", referencedColumnName = "ID_SITUATION")
	private SituationFamiliale situationFamiliale;

	@Column(name = "NUM_CAFAT")
	private String numCafat;

	@Column(name = "NUM_RUAMM")
	private String numRuamm;

	@Column(name = "NUM_MUTUELLE")
	private String numMutuelle;

	@Column(name = "NUM_CRE")
	private String numCre;

	@Column(name = "NUM_IRCAFEX")
	private String numIrcafex;

	@Column(name = "NUM_CLR")
	private String numClr;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODE_COMMUNE_NAISS_FR", referencedColumnName = "CODCOM")
	private Sicomm codeCommuneNaissFr;

	@Column(name = "CODE_COMMUNE_NAISS_ET", columnDefinition = "numeric")
	private Integer codeCommuneNaissEt;

	@Column(name = "CODE_PAYS_NAISS_ET", columnDefinition = "numeric")
	private Integer codePaysNaissEt;

	@Column(name = "INTITULE_COMPTE")
	private String intituleCompte;

	@Column(name = "RIB", columnDefinition = "numeric")
	private Integer rib;

	@Column(name = "NUM_COMPTE")
	private String numCompte;

	@Column(name = "CD_BANQUE", columnDefinition = "decimal")
	private Integer codeBanque;

	@Column(name = "CD_GUICHET", columnDefinition = "decimal")
	private Integer codeGuichet;

	@Transient
	private String lieuNaissance;

	@Transient
	private String banque;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VOIE", referencedColumnName = "CDVOIE")
	private Sivoie voie;

	@Column(name = "RUE_NON_NOUMEA")
	private String rueNonNoumea;

	@Column(name = "BP")
	private String bP;

	@Column(name = "ADRESSE_COMPLEMENTAIRE")
	private String adresseComplementaire;

	@Column(name = "NUM_RUE")
	private String numRue;

	@Column(name = "NUM_RUE_BIS_TER")
	private String bisTer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CCOM_VILLE_DOM", referencedColumnName = "CODCOM")
	private Sicomm codeCommuneVilleDom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CCOM_VILLE_BP", referencedColumnName = "CODCOM")
	private Sicomm codeCommuneVilleBP;

	@Column(name = "CPOS_VILLE_DOM", columnDefinition = "numeric")
	private Integer codePostalVilleDom;

	@Column(name = "CPOS_VILLE_BP", columnDefinition = "numeric")
	private Integer codePostalVilleBP;

	@Transient
	private String rue;

	@Transient
	private String position;

	public String getDisplayPrenom() {
		return getPrenomUsage();
	}

	public String getDisplayNom() {
		return getNomUsage();
	}

	public static JSONSerializer getSerializerForAgentSuperieurHierarchique() {
		JSONSerializer serializer = new JSONSerializer().transform(new AgentToHierarchiqueTransformer(), Agent.class)
				.transform(new StringTrimTransformer(), String.class);
		return serializer;
	}

	public static JSONSerializer getSerializerForAgentEtatCivil() {
		JSONSerializer serializer = new JSONSerializer().include("nomatr").include("nomPatronymique")
				.include("nomMarital").include("nomUsage").include("prenom").include("sexe")
				.include("situationFamiliale").include("dateNaissance").include("situationFamiliale").include("titre")
				.include("lieuNaissance").transform(new MSDateTransformer(), Date.class)
				.transform(new NullableIntegerTransformer(), Integer.class)
				.transform(new SituationFamilialeTransformer(), SituationFamiliale.class)
				.transform(new StringTrimTransformer(), String.class).exclude("*");
		return serializer;
	}
	
	public static JSONSerializer getSerializerAgentForEae() {
		JSONSerializer serializer = new JSONSerializer().include("idAgent").include("nomatr").include("nomPatronymique")
				.include("nomMarital").include("nomUsage").include("prenom").include("prenomUsage")
				.include("dateNaissance").include("dateDerniereEmbauche")
				.transform(new MSDateTransformer(), Date.class)
				.transform(new NullableIntegerTransformer(), Integer.class)
				.transform(new SituationFamilialeTransformer(), SituationFamiliale.class)
				.transform(new StringTrimTransformer(), String.class).exclude("*");
		return serializer;
	}

	public static JSONSerializer getSerializerForAgentCouvertureSociale() {
		JSONSerializer serializer = new JSONSerializer().include("numCafat").include("numRuamm").include("numMutuelle")
				.include("numCre").include("numIrcafex").include("numClr")
				.transform(new StringTrimTransformer(), String.class).exclude("*");

		return serializer;
	}

	public static JSONSerializer getSerializerForAgentBanque() {
		JSONSerializer serializer = new JSONSerializer().transform(new AgentToBanqueTransformer(), Agent.class)
				.transform(new NullableIntegerTransformer(), Integer.class)
				.transform(new StringTrimTransformer(), String.class).exclude("*");

		return serializer;
	}

	public static JSONSerializer getSerializerForAgentAdresse() {
		JSONSerializer serializer = new JSONSerializer().transform(new AgentToAdresseTransformer(), Agent.class)
				.transform(new StringTrimTransformer(), String.class);
		return serializer;
	}

	public static JSONSerializer getSerializerForEnfantAgent() {
		JSONSerializer serializer = new JSONSerializer().transform(new ParentEnfantTransformer(), ParentEnfant.class)
				.transform(new MSDateTransformer(), Date.class)
				.transform(new NullableIntegerTransformer(), Integer.class)
				.transform(new StringTrimTransformer(), String.class);
		return serializer;
	}

	public static JSONSerializer getSerializerForAgentDelegataire() {
		JSONSerializer serializer = new JSONSerializer().transform(new AgentDelegataireTransformer(), Agent.class)
				.transform(new NullableIntegerTransformer(), Integer.class)
				.transform(new StringTrimTransformer(), String.class);

		return serializer;
	}

	public static JSONSerializer getSerializerForAgentEquipeFichePoste() {
		JSONSerializer serializer = new JSONSerializer().transform(new AgentToEquipeTransformer(), Agent.class)
				.transform(new NullableIntegerTransformer(), Integer.class)
				.transform(new StringTrimTransformer(), String.class);

		return serializer;
	}

	public Set<ParentEnfant> getParentEnfants() {
		return parentEnfants;
	}

	public void setParentEnfants(Set<ParentEnfant> parentEnfants) {
		this.parentEnfants = parentEnfants;
	}

	public Integer getNomatr() {
		return nomatr;
	}

	public void setNomatr(Integer nomatr) {
		this.nomatr = nomatr;
	}

	public String getNomPatronymique() {
		return nomPatronymique;
	}

	public void setNomPatronymique(String nomPatronymique) {
		this.nomPatronymique = nomPatronymique;
	}

	public String getNomMarital() {
		return nomMarital;
	}

	public void setNomMarital(String nomMarital) {
		this.nomMarital = nomMarital;
	}

	public String getNomUsage() {
		return nomUsage;
	}

	public void setNomUsage(String nomUsage) {
		this.nomUsage = nomUsage;
	}

	public String getPrenomUsage() {
		return prenomUsage;
	}

	public void setPrenomUsage(String prenomUsage) {
		this.prenomUsage = prenomUsage;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public SituationFamiliale getSituationFamiliale() {
		return situationFamiliale;
	}

	public void setSituationFamiliale(SituationFamiliale situationFamiliale) {
		this.situationFamiliale = situationFamiliale;
	}

	public String getNumCafat() {
		return numCafat;
	}

	public void setNumCafat(String numCafat) {
		this.numCafat = numCafat;
	}

	public String getNumRuamm() {
		return numRuamm;
	}

	public void setNumRuamm(String numRuamm) {
		this.numRuamm = numRuamm;
	}

	public String getNumMutuelle() {
		return numMutuelle;
	}

	public void setNumMutuelle(String numMutuelle) {
		this.numMutuelle = numMutuelle;
	}

	public String getNumCre() {
		return numCre;
	}

	public void setNumCre(String numCre) {
		this.numCre = numCre;
	}

	public String getNumIrcafex() {
		return numIrcafex;
	}

	public void setNumIrcafex(String numIrcafex) {
		this.numIrcafex = numIrcafex;
	}

	public String getNumClr() {
		return numClr;
	}

	public void setNumClr(String numClr) {
		this.numClr = numClr;
	}

	public Sicomm getCodeCommuneNaissFr() {
		return codeCommuneNaissFr;
	}

	public void setCodeCommuneNaissFr(Sicomm codeCommuneNaissFr) {
		this.codeCommuneNaissFr = codeCommuneNaissFr;
	}

	public Integer getCodeCommuneNaissEt() {
		return codeCommuneNaissEt;
	}

	public void setCodeCommuneNaissEt(Integer codeCommuneNaissEt) {
		this.codeCommuneNaissEt = codeCommuneNaissEt;
	}

	public Integer getCodePaysNaissEt() {
		return codePaysNaissEt;
	}

	public void setCodePaysNaissEt(Integer codePaysNaissEt) {
		this.codePaysNaissEt = codePaysNaissEt;
	}

	public String getIntituleCompte() {
		return intituleCompte;
	}

	public void setIntituleCompte(String intituleCompte) {
		this.intituleCompte = intituleCompte;
	}

	public Integer getRib() {
		return rib;
	}

	public void setRib(Integer rib) {
		this.rib = rib;
	}

	public String getNumCompte() {
		return numCompte;
	}

	public void setNumCompte(String numCompte) {
		this.numCompte = numCompte;
	}

	public Integer getCodeBanque() {
		return codeBanque;
	}

	public void setCodeBanque(Integer codeBanque) {
		this.codeBanque = codeBanque;
	}

	public Integer getCodeGuichet() {
		return codeGuichet;
	}

	public void setCodeGuichet(Integer codeGuichet) {
		this.codeGuichet = codeGuichet;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public String getBanque() {
		return banque;
	}

	public void setBanque(String banque) {
		this.banque = banque;
	}

	public Sivoie getVoie() {
		return voie;
	}

	public void setVoie(Sivoie voie) {
		this.voie = voie;
	}

	public String getRueNonNoumea() {
		return rueNonNoumea;
	}

	public void setRueNonNoumea(String rueNonNoumea) {
		this.rueNonNoumea = rueNonNoumea;
	}

	public String getbP() {
		return bP;
	}

	public void setbP(String bP) {
		this.bP = bP;
	}

	public String getAdresseComplementaire() {
		return adresseComplementaire;
	}

	public void setAdresseComplementaire(String adresseComplementaire) {
		this.adresseComplementaire = adresseComplementaire;
	}

	public String getNumRue() {
		return numRue;
	}

	public void setNumRue(String numRue) {
		this.numRue = numRue;
	}

	public String getBisTer() {
		return bisTer;
	}

	public void setBisTer(String bisTer) {
		this.bisTer = bisTer;
	}

	public Sicomm getCodeCommuneVilleDom() {
		return codeCommuneVilleDom;
	}

	public void setCodeCommuneVilleDom(Sicomm codeCommuneVilleDom) {
		this.codeCommuneVilleDom = codeCommuneVilleDom;
	}

	public Sicomm getCodeCommuneVilleBP() {
		return codeCommuneVilleBP;
	}

	public void setCodeCommuneVilleBP(Sicomm codeCommuneVilleBP) {
		this.codeCommuneVilleBP = codeCommuneVilleBP;
	}

	public Integer getCodePostalVilleDom() {
		return codePostalVilleDom;
	}

	public void setCodePostalVilleDom(Integer codePostalVilleDom) {
		this.codePostalVilleDom = codePostalVilleDom;
	}

	public Integer getCodePostalVilleBP() {
		return codePostalVilleBP;
	}

	public void setCodePostalVilleBP(Integer codePostalVilleBP) {
		this.codePostalVilleBP = codePostalVilleBP;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}

	public Date getDateDerniereEmbauche() {
		return dateDerniereEmbauche;
	}

	public void setDateDerniereEmbauche(Date dateDerniereEmbauche) {
		this.dateDerniereEmbauche = dateDerniereEmbauche;
	}
}
