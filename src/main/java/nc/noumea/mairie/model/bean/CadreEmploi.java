package nc.noumea.mairie.model.bean;

import java.util.Set;

import javax.persistence.Column;
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
@RooJpaActiveRecord(identifierColumn = "ID_CADRE_EMPLOI", schema = "SIRH", identifierField = "idCadreEmploi", identifierType = Integer.class, table = "P_CADRE_EMPLOI")
@RooSerializable
public class CadreEmploi {

	@NotNull
	@Column(name = "LIB_CADRE_EMPLOI")
	private String libelleCadreEmploi;

	private static JSONObject enleveTousChamps(JSONObject json) {
		JSONObject res = json;
		json.remove("idCadreEmploi");
		json.remove("version");
		return res;
	}

	public static String cadreEmploiToJsonArray(Set<CadreEmploi> cadreEmplois) {
		String test = new JSONSerializer().exclude("*.class").serialize(
				cadreEmplois);
		JSONArray jsonAr = null;
		try {
			jsonAr = (JSONArray) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonAr.size(); i++) {
			JSONObject json = (JSONObject) jsonAr.get(i);
			json = enleveTousChamps(json);

			jsonAr.remove(i);
			jsonAr.add(i, json);

		}
		return jsonAr.toJSONString();
	}
}
