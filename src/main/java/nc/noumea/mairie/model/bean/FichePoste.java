package nc.noumea.mairie.model.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.tools.transformer.ActiviteTransformer;
import nc.noumea.mairie.tools.transformer.FichePosteTransformer;
import nc.noumea.mairie.tools.transformer.NullableIntegerTransformer;
import nc.noumea.mairie.tools.transformer.StringTrimTransformer;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierType = Integer.class, identifierColumn = "ID_FICHE_POSTE", identifierField = "idFichePoste", schema = "SIRH", table = "FICHE_POSTE", versionField = "")
public class FichePoste {

	@OneToOne(optional=true)
	@JoinColumn(name = "ID_TITRE_POSTE", referencedColumnName = "ID_TITRE_POSTE")
	private TitrePoste titrePoste;

	@OneToOne(optional=true)
	@JoinColumn(name = "ID_ENTITE_GEO", referencedColumnName = "CDLIEU")
	private Silieu lieuPoste;

	@OneToOne(optional=true)
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
	@Lob
	@Column(name = "MISSIONS")
	private String missions;

	@OneToOne(optional=true)
	@JoinColumn(name = "ID_STATUT_FP", referencedColumnName = "ID_STATUT_FP")
	private StatutFichePoste statutFP;

	@OneToOne(optional=true)
	@JoinColumn(name = "ID_CDTHOR_BUD", referencedColumnName = "CDTHOR")
	private Spbhor budgete;

	@OneToOne(optional=true)
	@JoinColumn(name = "ID_CDTHOR_REG", referencedColumnName = "CDTHOR")
	private Spbhor reglementaire;

	@OneToOne(optional=true)
	@JoinColumn(name = "ID_SERVI", referencedColumnName = "SERVI")
	private Siserv service;

	@OneToOne(optional=true)
	@JoinColumn(name = "CODE_GRADE_GENERIQUE", referencedColumnName = "CDGENG")
	private Spgeng gradePoste;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(schema = "SIRH", name = "ACTIVITE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_ACTIVITE"))
	private Set<Activite> activites = new HashSet<Activite>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(schema = "SIRH", name = "COMPETENCE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_COMPETENCE"))
	private Set<Competence> competencesFDP = new HashSet<Competence>();

	@OneToOne(optional=true,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(schema = "SIRH", name = "NIVEAU_ETUDE_FP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_FICHE_POSTE") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_NIVEAU_ETUDE"))
	private NiveauEtude niveauEtude;

	@OneToOne(optional = true)
	@JoinColumn(name = "ID_RESPONSABLE", referencedColumnName = "ID_FICHE_POSTE")
	private FichePoste responsable;

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

	public static JSONSerializer getSerializerForFichePoste() {
		JSONSerializer serializer = new JSONSerializer().transform(new FichePosteTransformer(), FichePoste.class)
				.transform(new ActiviteTransformer(), Activite.class).transform(new NullableIntegerTransformer(), Integer.class)
				.transform(new StringTrimTransformer(), String.class);

		return serializer;
	}
}
