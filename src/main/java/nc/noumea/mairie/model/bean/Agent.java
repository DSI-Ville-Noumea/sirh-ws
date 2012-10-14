package nc.noumea.mairie.model.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJson
@RooSerializable
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_AGENT", schema = "SIRH", identifierField = "idAgent", identifierType = Integer.class, table = "AGENT", versionField = "")
public class Agent {

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(schema = "SIRH", name = "PARENT_ENFANT", joinColumns = { @javax.persistence.JoinColumn(name = "ID_AGENT") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_ENFANT"))
	private Set<Enfant> enfants = new HashSet<Enfant>();

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

	@Column(name = "CIVILITE")
	private String civilite;

	@NotNull
	@Column(name = "SEXE")
	private String sexe;

	@NotNull
	@Column(name = "DATE_NAISSANCE")
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;

	@OneToOne
	@JoinColumn(name = "ID_SITUATION_FAMILIALE", referencedColumnName = "ID_SITUATION")
	private SituationFamiliale situationFamiliale;

	@Column(name = "NUM_CAFAT")
	private String numCafat;

	public String getNumCafat() {
		if (this.numCafat != null) {
			return this.numCafat.trim();
		}
		return this.numCafat;
	}

	@Column(name = "NUM_RUAMM")
	private String numRuamm;

	public String getNumRuamm() {
		if (this.numRuamm != null) {
			return this.numRuamm.trim();
		}
		return this.numRuamm;
	}

	@Column(name = "NUM_MUTUELLE")
	private String numMutuelle;

	public String getNumMutuelle() {
		if (this.numMutuelle != null) {
			return this.numMutuelle.trim();
		}
		return this.numMutuelle;
	}

	@Column(name = "NUM_CRE")
	private String numCre;

	public String getNumCre() {
		if (this.numCre != null) {
			return this.numCre.trim();
		}
		return this.numCre;
	}

	@Column(name = "NUM_IRCAFEX")
	private String numIrcafex;

	public String getNumIrcafex() {
		if (this.numIrcafex != null) {
			return this.numIrcafex.trim();
		}
		return this.numIrcafex;
	}

	@Column(name = "NUM_CLR")
	private String numClr;

	public String getNumClr() {
		if (this.numClr != null) {
			return this.numClr.trim();
		}
		return this.numClr;
	}

	@ManyToOne
	@JoinColumn(name = "CODE_COMMUNE_NAISS_FR", referencedColumnName = "CODCOM")
	private Sicomm codeCommuneNaissFr;

	@Column(name = "CODE_COMMUNE_NAISS_ET", columnDefinition = "numeric")
	private Integer codeCommuneNaissEt;

	@Column(name = "CODE_PAYS_NAISS_ET", columnDefinition = "numeric")
	private Integer codePaysNaissEt;

	@Column(name = "INTITULE_COMPTE")
	private String intituleCompte;

	public String getIntituleCompte() {
		if (this.intituleCompte != null)
			return this.intituleCompte.trim();
		return this.intituleCompte;
	}

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

	public String getLieuNaissance() {
		if (lieuNaissance != null)
			return lieuNaissance.trim();
		return lieuNaissance;
	}

	@Transient
	private String banque;

	public String getBanque() {
		if (banque != null)
			return banque.trim();
		return banque;
	}

	@OneToOne
	@JoinColumn(name = "ID_VOIE", referencedColumnName = "CDVOIE")
	private Sivoie voie;

	@Column(name = "RUE_NON_NOUMEA")
	private String rueNonNoumea;

	@Column(name = "BP")
	private String bP;

	public String getBP() {
		if (this.bP == null)
			return "";
		return this.bP.trim();
	}

	@Column(name = "ADRESSE_COMPLEMENTAIRE")
	private String adresseComplementaire;

	public String getAdresseComplementaire() {
		if (this.adresseComplementaire == null)
			return "";
		return this.adresseComplementaire.trim();
	}

	@Column(name = "NUM_RUE")
	private String numRue;

	public String getNumRue() {
		if (this.numRue == null)
			return "";
		return this.numRue.trim();
	}

	@Column(name = "NUM_RUE_BIS_TER")
	private String bisTer;

	public String getBisTer() {
		if (this.bisTer == null)
			return "";
		return this.bisTer.trim();
	}

	@ManyToOne
	@JoinColumn(name = "CCOM_VILLE_DOM", referencedColumnName = "CODCOM")
	private Sicomm codeCommuneVilleDom;

	@ManyToOne
	@JoinColumn(name = "CCOM_VILLE_BP", referencedColumnName = "CODCOM")
	private Sicomm codeCommuneVilleBP;

	@Column(name = "CPOS_VILLE_DOM", columnDefinition = "numeric")
	private Integer codePostalVilleDom;

	@Column(name = "CPOS_VILLE_BP", columnDefinition = "numeric")
	private Integer codePostalVilleBP;

	private JSONObject enleveTousChamps(JSONObject json) {
		JSONObject res = json;
		json.remove("idAgent");
		json.remove("nomatr");
		json.remove("nomPatronymique");
		json.remove("nomMarital");
		json.remove("nomUsage");
		json.remove("prenomUsage");
		json.remove("prenom");
		json.remove("civilite");
		json.remove("sexe");
		json.remove("dateNaissance");
		json.remove("situationFamiliale");
		json.remove("numCafat");
		json.remove("numRuamm");
		json.remove("numMutuelle");
		json.remove("numCre");
		json.remove("numIrcafex");
		json.remove("numClr");
		json.remove("codeCommuneNaissFr");
		json.remove("codeCommuneNaissEt");
		json.remove("codePaysNaissEt");
		json.remove("intituleCompte");
		json.remove("rib");
		json.remove("numCompte");
		json.remove("codeBanque");
		json.remove("codeGuichet");
		json.remove("lieuNaissance");
		json.remove("banque");
		json.remove("voie");
		json.remove("rueNonNoumea");
		json.remove("BP");
		json.remove("adresseComplementaire");
		json.remove("numRue");
		json.remove("bisTer");
		json.remove("codeCommuneVilleDom");
		json.remove("codeCommuneVilleBP");
		json.remove("codePostalVilleDom");
		json.remove("codePostalVilleBP");
		json.remove("version");
		return res;
	}

	public String etatCivilToJson() {
		String test = new JSONSerializer().exclude("*.class").serialize(this);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		json = enleveTousChamps(json);
		json.put("nomPatronymique", getNomPatronymique());
		json.put("nomMarital", getNomMarital());
		json.put("nomUsage", getNomUsage());
		json.put("prenom", getPrenomUsage());
		json.put("sexe", getSexe());
		json.put("situationFamiliale",
				situationFamiliale.getLibSituationFamiliale());
		if (dateNaissance != null) {
			json.put("dateNaissance", "/Date(" + dateNaissance.getTime() + ")/");
		}
		if (codeCommuneNaissFr != null) {
			json.put("lieuNaissance", codeCommuneNaissFr.getLibVil().trim());
		} else {
			json.put("lieuNaissance", getLieuNaissance());
		}
		if (civilite.equals("0")) {
			json.put("titre", "Monsieur");
		} else if (civilite.equals("1")) {
			json.put("titre", "Madame");
		} else {
			json.put("titre", "Mademoiselle");
		}
		return json.toJSONString();
	}

	public String couvertureSocialeToJson() {
		String test = new JSONSerializer().exclude("*.class").serialize(this);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		json = enleveTousChamps(json);
		json.put("numCafat", getNumCafat());
		json.put("numRuamm", getNumRuamm());
		json.put("numMutuelle", getNumMutuelle());
		json.put("numCre", getNumCre());
		json.put("numIrcafex", getNumIrcafex());
		json.put("numClr", getNumClr());
		return json.toJSONString();
	}

	public String banqueToJson() {
		String test = new JSONSerializer().exclude("*.class").serialize(this);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		json = enleveTousChamps(json);
		json.put("intituleCompte", getIntituleCompte());
		json.put("rib", getRib());
		json.put("numCompte", getNumCompte());
		json.put("banque", getBanque());
		return json.toJSONString();
	}

	public String adresseToJson() {
		String test = new JSONSerializer().exclude("*.class").serialize(this);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		json = enleveTousChamps(json);
		json.put("rue", voie == null ? rueNonNoumea : voie.getLiVoie().trim());
		json.put("BP", getBP());
		json.put("adresseComplementaire", getAdresseComplementaire());
		json.put("numRue", getNumRue());
		json.put("bisTer", getBisTer());
		json.put("codeCommuneVilleDom", codeCommuneVilleDom == null ? ""
				: codeCommuneVilleDom.getLibVil().trim());
		json.put("codeCommuneVilleBP", codeCommuneVilleBP == null ? ""
				: codeCommuneVilleBP.getLibVil().trim());
		json.put("codePostalVilleDom", codePostalVilleDom == null ? 0
				: codePostalVilleDom);
		json.put("codePostalVilleBP", codePostalVilleBP == null ? 0
				: codePostalVilleBP);

		return json.toJSONString();
	}

	public String responsableHierarchiqueToJson(String titrePoste) {
		String test = new JSONSerializer().exclude("*.class").serialize(this);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		json = enleveTousChamps(json);
		json.put("nom", getNomUsage());
		json.put("prenom", getPrenomUsage());
		json.put("position", titrePoste);
		if (civilite.equals("0")) {
			json.put("titre", "Monsieur");
		} else if (civilite.equals("1")) {
			json.put("titre", "Madame");
		} else {
			json.put("titre", "Mademoiselle");
		}

		return json.toJSONString();
	}
}
