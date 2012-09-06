package nc.noumea.mairie.model.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.json.simple.JSONArray;
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
@RooJpaActiveRecord(identifierType = Integer.class, identifierColumn = "ID_CONTACT", identifierField = "idContact", schema = "SIRH", table = "CONTACT")
public class Contact {

	@OneToOne
	@JoinColumn(name = "ID_TYPE_CONTACT", referencedColumnName = "ID_TYPE_CONTACT")
	private TypeContact typeContact;

	@Column(name = "ID_AGENT")
	private Integer idAgent;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DIFFUSABLE")
	private String diffusable;

	@Column(name = "PRIORITAIRE",columnDefinition="smallint")
	private Integer prioritaire;

	private static JSONObject enleveTousChamps(JSONObject json) {
		JSONObject res = json;
		json.remove("idContact");
		json.remove("idAgent");
		json.remove("version");
		return res;
	}

	public static String contactToJson(
			List<nc.noumea.mairie.model.bean.Contact> lc) {
		String test = new JSONSerializer().exclude("*.class").serialize(lc);
		JSONArray jsonAr = null;
		try {
			jsonAr = (JSONArray) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonAr.size(); i++) {
			JSONObject json = (JSONObject) jsonAr.get(i);
			json = enleveTousChamps(json);
			json.put("diffusable", json.get("diffusable").toString()
					.equals("1") ? "oui" : "non");
			json.put("prioritaire",
					json.get("prioritaire").toString().equals("1") ? "oui"
							: "non");
			// pour le type de contact on en veut que le libellé
			JSONObject typCon = (JSONObject) json.get("typeContact");
			typCon.remove("idTypeContact");
			typCon.remove("version");
			// TODO
			// bidouille pour le moment
			String libTypeContact = (String) typCon.get("libelle");
			typCon.remove("libelle");
			json.put("typeContact", libTypeContact);
			jsonAr.remove(i);
			jsonAr.add(i, json);
		}
		return jsonAr.toJSONString();
	}
}
