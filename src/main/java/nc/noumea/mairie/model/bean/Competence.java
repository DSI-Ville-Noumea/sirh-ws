package nc.noumea.mairie.model.bean;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierColumn = "ID_COMPETENCE", schema = "SIRH", identifierField = "idCompetence", identifierType = Integer.class, table = "COMPETENCE")
@RooSerializable
public class Competence {

	@NotNull
	@Column(name = "NOM_COMPETENCE")
	private String nomCompetence;

	@OneToOne
	@JoinColumn(name = "ID_TYPE_COMPETENCE", referencedColumnName = "ID_TYPE_COMPETENCE")
	private TypeCompetence typeCompetence;

	private static JSONObject enleveTousChamps(JSONObject json) {
		JSONObject res = json;
		json.remove("idCompetence");
		json.remove("version");
		return res;
	}

	public static String competenceToJsonArray(Set<Competence> competences) {
		String test = new JSONSerializer().exclude("*.class").serialize(
				competences);
		JSONArray jsonAr = null;
		try {
			jsonAr = (JSONArray) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonAr.size(); i++) {
			JSONObject json = (JSONObject) jsonAr.get(i);
			json = enleveTousChamps(json);
			//on ne veut que le libelle du type de competence
			JSONObject typComp = (JSONObject) json.get("typeCompetence");
			json.remove("typeCompetence");
			//TODO
			//bidouille pour le moment
			String libTypeCompetence = (String) typComp.get("libTypeCompetence");
			json.put("typeCompetence", libTypeCompetence);

			jsonAr.remove(i);
			jsonAr.add(i, json);

		}
		return jsonAr.toJSONString();
	}
}
