package nc.noumea.mairie.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@RooJpaActiveRecord(identifierType=Integer.class,identifierColumn = "ID_AFFECTATION", identifierField = "idAffectation", schema = "SIRH", table = "AFFECTATION")
public class Affectation {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_AGENT", referencedColumnName = "ID_AGENT")
	private Agent agent;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_FICHE_POSTE", referencedColumnName = "ID_FICHE_POSTE")
	private FichePoste fichePoste;

	@NotNull
	@Column(name = "DATE_DEBUT_AFF")
	@Temporal(TemporalType.DATE)
	private Date dateDebutAff;

	@Column(name = "DATE_FIN_AFF")
	@Temporal(TemporalType.DATE)
	private Date dateFinAff;

	@Column(name = "TEMPS_TRAVAIL")
	private String tempsTravail;

	private JSONObject enleveTousChamps(JSONObject json) {
		JSONObject res = json;
		json.remove("agent");
		json.remove("fichePoste");
		json.remove("idAffectation");
		json.remove("dateDebutAff");
		json.remove("dateFinAff");
		json.remove("tempsTravail");
		json.remove("version");
		return res;
	}

	public String infoTitulaire() {
		String test = new JSONSerializer().exclude("*.class").serialize(this);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		json = enleveTousChamps(json);
		json.put("tempsTravail", tempsTravail == null ? "0 %" : tempsTravail
				+ "%");
		return json.toJSONString();
	}
}
