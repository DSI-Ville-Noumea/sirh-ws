package nc.noumea.mairie.model.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

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
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_AGENT", identifierField = "idAgent", identifierType = Integer.class, table = "AGENT", versionField = "")
public class Agent {

	
	private static final long serialVersionUID = 1L;

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
		JSONSerializer serializer = new JSONSerializer().transform(new AgentToHierarchiqueTransformer(), Agent.class).transform(
				new StringTrimTransformer(), String.class);
		return serializer;
	}

	public static JSONSerializer getSerializerForAgentEtatCivil() {
		JSONSerializer serializer = new JSONSerializer().include("nomatr").include("nomPatronymique").include("nomMarital").include("nomUsage")
				.include("prenom").include("sexe").include("situationFamiliale").include("dateNaissance").include("situationFamiliale")
				.include("titre").include("lieuNaissance").transform(new MSDateTransformer(), Date.class)
				.transform(new NullableIntegerTransformer(), Integer.class).transform(new SituationFamilialeTransformer(), SituationFamiliale.class)
				.transform(new StringTrimTransformer(), String.class).exclude("*");
		return serializer;
	}

	public static JSONSerializer getSerializerForAgentCouvertureSociale() {
		JSONSerializer serializer = new JSONSerializer().include("numCafat").include("numRuamm").include("numMutuelle").include("numCre")
				.include("numIrcafex").include("numClr").transform(new StringTrimTransformer(), String.class).exclude("*");

		return serializer;
	}

	public static JSONSerializer getSerializerForAgentBanque() {
		JSONSerializer serializer = new JSONSerializer().transform(new AgentToBanqueTransformer(), Agent.class)
				.transform(new NullableIntegerTransformer(), Integer.class).transform(new StringTrimTransformer(), String.class).exclude("*");

		return serializer;
	}

	public static JSONSerializer getSerializerForAgentAdresse() {
		JSONSerializer serializer = new JSONSerializer().transform(new AgentToAdresseTransformer(), Agent.class).transform(
				new StringTrimTransformer(), String.class);
		return serializer;
	}

	public static JSONSerializer getSerializerForEnfantAgent() {
		JSONSerializer serializer = new JSONSerializer().transform(new ParentEnfantTransformer(), ParentEnfant.class)
				.transform(new MSDateTransformer(), Date.class).transform(new NullableIntegerTransformer(), Integer.class)
				.transform(new StringTrimTransformer(), String.class);
		return serializer;
	}

	public static JSONSerializer getSerializerForAgentDelegataire() {
		JSONSerializer serializer = new JSONSerializer().transform(new AgentDelegataireTransformer(), Agent.class)
				.transform(new NullableIntegerTransformer(), Integer.class).transform(new StringTrimTransformer(), String.class);

		return serializer;
	}

	public static JSONSerializer getSerializerForAgentEquipeFichePoste() {
		JSONSerializer serializer = new JSONSerializer().transform(new AgentToEquipeTransformer(), Agent.class)
				.transform(new NullableIntegerTransformer(), Integer.class).transform(new StringTrimTransformer(), String.class);

		return serializer;
	}
}
