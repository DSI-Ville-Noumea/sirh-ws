package nc.noumea.mairie.model.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@RooJpaActiveRecord(identifierColumn = "ID_AGENT", schema = "SIRH", identifierField = "idAgent", identifierType = Integer.class, table = "AGENT")
public class Agent {

	@ManyToMany(cascade = CascadeType.ALL)
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

	@NotNull
	@Column(name = "DATE_NAISSANCE")
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;

	@OneToOne
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

	@ManyToOne
	@JoinColumn(name = "CODE_COMMUNE_NAISS_FR", referencedColumnName = "CODCOM")
	private Sicomm codeCommuneNaissFr;

	@Column(name = "CODE_COMMUNE_NAISS_ET")
	private Integer codeCommuneNaissEt;

	@Column(name = "CODE_PAYS_NAISS_ET")
	private Integer codePaysNaissEt;

	@Column(name = "INTITULE_COMPTE")
	private String intituleCompte;

	@Column(name = "RIB")
	private Integer rib;

	@Column(name = "NUM_COMPTE")
	private String numCompte;

	@Column(name = "CD_BANQUE")
	private Integer codeBanque;

	@Column(name = "CD_GUICHET")
	private Integer codeGuichet;

	private String lieuNaissance;

	private String banque;

	@OneToOne
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

	@ManyToOne
	@JoinColumn(name = "CCOM_VILLE_DOM", referencedColumnName = "CODCOM")
	private Sicomm codeCommuneVilleDom;

	@ManyToOne
	@JoinColumn(name = "CCOM_VILLE_BP", referencedColumnName = "CODCOM")
	private Sicomm codeCommuneVilleBP;

	@Column(name = "CPOS_VILLE_DOM")
	private Integer codePostalVilleDom;

	@Column(name = "CPOS_VILLE_BP")
	private Integer codePostalVilleBP;

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

	private JSONObject enleveTousChamps(JSONObject json) {
		JSONObject res = json;
		json.remove("idAgent");
		json.remove("nomatr");
		json.remove("nomPatronymique");
		json.remove("nomMarital");
		json.remove("nomUsage");
		json.remove("prenomUsage");
		json.remove("prenom");
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
		json.put("nomPatronymique", nomPatronymique);
		json.put("nomMarital", nomMarital);
		json.put("nomUsage", nomUsage);
		json.put("prenomUsage", prenomUsage);
		json.put("prenom", prenom);
		json.put("situationFamiliale",
				situationFamiliale.getLibSituationFamiliale());
		if (dateNaissance != null) {
			json.put("dateNaissance", "/Date(" + dateNaissance.getTime() + ")/");
		}
		if (codeCommuneNaissFr != null) {
			json.put("lieuNaissance", codeCommuneNaissFr.getLibVil().trim());
		} else {
			json.put("lieuNaissance", lieuNaissance);
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
		json.put("numCafat", numCafat);
		json.put("numRuamm", numRuamm);
		json.put("numMutuelle", numMutuelle);
		json.put("numCre", numCre);
		json.put("numIrcafex", numIrcafex);
		json.put("numClr", numClr);
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
		json.put("intituleCompte", intituleCompte == null ? null
				: intituleCompte.trim());
		json.put("rib", rib);
		json.put("numCompte", numCompte == null ? null : numCompte.trim());
		json.put("banque", banque == null ? null : banque.trim());
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
		json.put("BP", bP == null ? null : bP.trim());
		json.put("adresseComplementaire", adresseComplementaire == null ? null
				: adresseComplementaire.trim());
		json.put("numRue", numRue == null ? null : numRue.trim());
		json.put("bisTer", bisTer == null ? null : bisTer.trim());
		json.put("codeCommuneVilleDom", codeCommuneVilleDom == null ? null
				: codeCommuneVilleDom.getLibVil().trim());
		json.put("codeCommuneVilleBP", codeCommuneVilleBP == null ? null
				: codeCommuneVilleBP.getLibVil().trim());
		json.put("codePostalVilleDom", codePostalVilleDom == null ? null
				: codePostalVilleDom);
		json.put("codePostalVilleBP", codePostalVilleBP == null ? null
				: codePostalVilleBP);

		return json.toJSONString();
	}

	public String titulaireFPToJson() {
		String test = new JSONSerializer().exclude("*.class").serialize(this);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		json = enleveTousChamps(json);
		json.put("nom", nomUsage);
		json.put("prenom", prenomUsage);

		return json.toJSONString();
	}
}
