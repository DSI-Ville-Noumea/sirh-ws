package nc.noumea.mairie.model.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(identifierType = Integer.class, identifierColumn = "ID_FICHE_POSTE", identifierField = "idFichePoste", schema = "SIRH", table = "FICHE_POSTE")
public class FichePoste {

	@ManyToOne
	@JoinColumn(name = "ID_TITRE_POSTE", referencedColumnName = "ID_TITRE_POSTE")
	private TitrePoste titrePoste;

	@ManyToOne
	@JoinColumn(name = "ID_ENTITE_GEO", referencedColumnName = "CDLIEU")
	private Silieu lieuPoste;

	@ManyToOne
	@JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
	private Budget budget;

	@Column(name = "OPI")
	private String opi;

	@NotNull
	@Column(name = "NFA")
	private String nfa;

	@NotNull
	@Column(name = "ANNEE_CREATION", columnDefinition="numeric")
	private Integer annee;

	@NotNull
	@Column(name = "NUM_FP")
	private String numFP;

	@NotNull
	@Lob
	@Column(name = "MISSIONS")
	private String missions;

	@ManyToOne
	@JoinColumn(name = "ID_STATUT_FP", referencedColumnName = "ID_STATUT_FP")
	private StatutFichePoste statutFP;

	@ManyToOne
	@JoinColumn(name = "ID_CDTHOR_BUD", referencedColumnName = "CDTHOR")
	private Spbhor budgete;

	@ManyToOne
	@JoinColumn(name = "ID_CDTHOR_REG", referencedColumnName = "CDTHOR")
	private Spbhor reglementaire;

	@ManyToOne
	@JoinColumn(name = "ID_SERVI", referencedColumnName = "SERVI")
	private Siserv service;

	@ManyToOne
	@JoinColumn(name = "CODE_GRADE_GENERIQUE", referencedColumnName = "CDGENG")
	private Spgeng gradePoste;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(schema = "SIRH", name = "ACTIVITE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_ACTIVITE"))
	private Set<Activite> activites = new HashSet<Activite>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(schema = "SIRH", name = "COMPETENCE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_COMPETENCE"))
	private Set<Competence> competences = new HashSet<Competence>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(schema = "SIRH", name = "CADRE_EMPLOI_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_CADRE_EMPLOI"))
	private Set<CadreEmploi> cardresEmploi = new HashSet<CadreEmploi>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(schema = "SIRH", name = "NIVEAU_ETUDE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_NIVEAU_ETUDE"))
	private Set<NiveauEtude> niveauEtude = new HashSet<NiveauEtude>();

	@ManyToOne
	@JoinColumn(name = "ID_RESPONSABLE", referencedColumnName = "ID_FICHE_POSTE")
	private FichePoste responsable;

	private JSONObject enleveTousChamps(JSONObject json) {
		JSONObject res = json;
		json.remove("idFichePoste");
		json.remove("version");
		return res;
	}

	public String fpToJson(String activites, String competences,
			String cadreEmploi, String niveauEtude, String service,
			String direction, String section) {
		String test = new JSONSerializer().exclude("*.class").serialize(this);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// on ajoute les activites
		json.put("activites", activites);
		// on ajoute les compétences
		json.put("competences", competences);
		// on ajoute le cadre emploi
		// on ne veut que le libelle du cadre emploi
		// TODO
		// bidouille pour le moment
		String resCadreEmp = (String) cadreEmploi;
		resCadreEmp = resCadreEmp.replace("{", "");
		resCadreEmp = resCadreEmp.replace("}", "");
		resCadreEmp = resCadreEmp.substring(resCadreEmp.indexOf(":") + 1,
				resCadreEmp.length());
		resCadreEmp = resCadreEmp.replace("\"", "");
		resCadreEmp = resCadreEmp.replace("]", "");
		json.put("cadreEmploi", resCadreEmp);
		// on ajoute le niveau etude
		// on ne veut que le libelle du niveau etude
		// TODO
		// bidouille pour le moment
		String resNiveauEtude = (String) niveauEtude;
		resNiveauEtude = resNiveauEtude.replace("{", "");
		resNiveauEtude = resNiveauEtude.replace("}", "");
		resNiveauEtude = resNiveauEtude.substring(
				resNiveauEtude.indexOf(":") + 1, resNiveauEtude.length());
		resNiveauEtude = resNiveauEtude.replace("\"", "");
		resNiveauEtude = resNiveauEtude.replace("]", "");
		json.put("niveauEtude", resNiveauEtude);

		json = enleveTousChamps(json);
		// pour la fiche de poste de l'agent on supprime le responsable
		json.remove("responsable");

		// gestion des services
		json.remove("service");
		json.put("service", service.trim());
		json.put("direction", direction.trim());
		json.put("section", section.trim());
		// pour le grade de la FP on ne veut que le libellé
		JSONObject grade = (JSONObject) json.get("gradePoste");
		json.remove("gradePoste");
		// TODO
		// Bidouille pourle moment
		String gradePoste = (String) grade.get("liGrad");
		gradePoste = gradePoste.replace("{", "");
		gradePoste = gradePoste.replace("}", "");
		gradePoste = gradePoste.substring(gradePoste.indexOf(":") + 1,
				gradePoste.length());
		gradePoste = gradePoste.replace("\"", "");
		json.put("gradePoste", gradePoste.trim());
		// pour le reglementaire de la FP on ne veut que le libellé
		JSONObject reglementaire = (JSONObject) json.get("reglementaire");
		json.remove("reglementaire");
		// TODO
		// Bidouille pourle moment
		String regelementairePoste = (String) reglementaire.get("libHor");
		regelementairePoste = regelementairePoste.replace("{", "");
		regelementairePoste = regelementairePoste.replace("}", "");
		regelementairePoste = regelementairePoste.substring(
				regelementairePoste.indexOf(":") + 1,
				regelementairePoste.length());
		regelementairePoste = regelementairePoste.replace("\"", "");
		json.put("reglementaire", regelementairePoste.trim());
		// pour le budgete de la FP on ne veut que le libellé
		JSONObject budgete = (JSONObject) json.get("budgete");
		json.remove("budgete");
		// TODO
		// Bidouille pourle moment
		String budgetePoste = (String) budgete.get("libHor");
		budgetePoste = budgetePoste.replace("{", "");
		budgetePoste = budgetePoste.replace("}", "");
		budgetePoste = budgetePoste.substring(budgetePoste.indexOf(":") + 1,
				budgetePoste.length());
		budgetePoste = budgetePoste.replace("\"", "");
		json.put("budgete", budgetePoste.trim());
		// pour le budget de la FP on ne veut que le libellé
		JSONObject budget = (JSONObject) json.get("budget");
		json.remove("budget");
		// TODO
		// Bidouille pourle moment
		String bud = (String) budget.get("libelleBudget");
		bud = bud.replace("{", "");
		bud = bud.replace("}", "");
		bud = bud.substring(bud.indexOf(":") + 1, bud.length());
		bud = bud.replace("\"", "");
		json.put("budget", bud.trim());
		// pour le lieu de la FP on ne veut que le libellé
		JSONObject lieu = (JSONObject) json.get("lieuPoste");
		json.remove("lieuPoste");
		// TODO
		// Bidouille pourle moment
		String lieuPoste = (String) lieu.get("libelleLieu");
		lieuPoste = lieuPoste.replace("{", "");
		lieuPoste = lieuPoste.replace("}", "");
		lieuPoste = lieuPoste.substring(lieuPoste.indexOf(":") + 1,
				lieuPoste.length());
		lieuPoste = lieuPoste.replace("\"", "");
		json.put("lieuPoste", lieuPoste.trim());
		// pour le titre du poste on ne veut que le libellé
		JSONObject titreP = (JSONObject) json.get("titrePoste");
		json.remove("titrePoste");
		// TODO
		// Bidouille pourle moment
		String titrePoste = (String) titreP.get("libTitrePoste");
		titrePoste = titrePoste.replace("{", "");
		titrePoste = titrePoste.replace("}", "");
		titrePoste = titrePoste.substring(titrePoste.indexOf(":") + 1,
				titrePoste.length());
		titrePoste = titrePoste.replace("\"", "");
		json.put("titrePoste", titrePoste.trim());
		// pour le statut de la FP on ne veut que le libellé
		JSONObject statut = (JSONObject) json.get("statutFP");
		json.remove("statutFP");
		// TODO
		// Bidouille pourle moment
		String statutPoste = (String) statut.get("libStatut");
		statutPoste = statutPoste.replace("{", "");
		statutPoste = statutPoste.replace("}", "");
		statutPoste = statutPoste.substring(statutPoste.indexOf(":") + 1,
				statutPoste.length());
		statutPoste = statutPoste.replace("\"", "");
		json.put("statutFP", statutPoste.trim());

		return json.toJSONString();
	}

	public String superieurHierarchiqueAgentToJson() {
		// TODO Auto-generated method stub
		return null;
	}
}
