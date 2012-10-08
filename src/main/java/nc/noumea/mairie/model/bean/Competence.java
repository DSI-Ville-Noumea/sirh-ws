package nc.noumea.mairie.model.bean;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.enums.EnumTypeCompetence;

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
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_COMPETENCE", schema = "SIRH", identifierField = "idCompetence", identifierType = Integer.class, table = "COMPETENCE", versionField = "")
@RooSerializable
public class Competence {

	@NotNull
	@Column(name = "NOM_COMPETENCE")
	private String nomCompetence;

	@OneToOne
	@JoinColumn(name = "ID_TYPE_COMPETENCE", referencedColumnName = "ID_TYPE_COMPETENCE")
	private TypeCompetence typeCompetence;

	public static String competenceToJsonArray(Set<Competence> competences) {
		String test = new JSONSerializer().exclude("*.class").serialize(
				competences);
		JSONArray jsonAr = null;
		try {
			jsonAr = (JSONArray) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ArrayList<String> comportement = new ArrayList<String>();
		ArrayList<String> savoir = new ArrayList<String>();
		ArrayList<String> savoirFaire = new ArrayList<String>();
		for (int i = 0; i < jsonAr.size(); i++) {
			JSONObject json = (JSONObject) jsonAr.get(i);
			// on ne veut que le libelle du type de competence
			JSONObject typComp = (JSONObject) json.get("typeCompetence");
			String libTypeCompetence = (String) typComp
					.get("libTypeCompetence");
			String nomCompetence = (String) json.get("nomCompetence");
			// SIRHWS-10
			if (libTypeCompetence.equals(EnumTypeCompetence.COMPORTEMENT
					.getValue())) {
				comportement.add(nomCompetence);
			} else if (libTypeCompetence.equals(EnumTypeCompetence.SAVOIR
					.getValue())) {
				savoir.add(nomCompetence);
			} else if (libTypeCompetence.equals(EnumTypeCompetence.SAVOIR_FAIRE
					.getValue())) {
				savoirFaire.add(nomCompetence);
			}

		}
		jsonAr.clear();
		if (comportement.size() > 0) {
			JSONObject json = new JSONObject();
			json.put("typeCompetence",
					EnumTypeCompetence.COMPORTEMENT.getValue());
			json.put("nomCompetence", comportement);
			jsonAr.add(json);
		}
		if (savoir.size() > 0) {
			JSONObject json = new JSONObject();
			json.put("typeCompetence", EnumTypeCompetence.SAVOIR.getValue());
			json.put("nomCompetence", savoir);
			jsonAr.add(json);
		}
		if (savoirFaire.size() > 0) {
			JSONObject json = new JSONObject();
			json.put("typeCompetence",
					EnumTypeCompetence.SAVOIR_FAIRE.getValue());
			json.put("nomCompetence", savoirFaire);
			jsonAr.add(json);
		}
		return jsonAr.toJSONString();
	}
}
