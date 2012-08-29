package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
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
@RooJpaActiveRecord(identifierColumn = "NOMATR", schema = "MAIRIE", identifierField = "nomatr", identifierType = Integer.class, table = "SPSOLD")
public class SpSold {

	@NotNull
	@Column(name = "SOLDE1")
	private Double soldeAnneeEnCours;

	@NotNull
	@Column(name = "SOLDE2")
	private Double soldeAnneePrec;

	private JSONObject enleveTousChamps(JSONObject json) {
		JSONObject res = json;
		json.remove("soldeAnneeEnCours");
		json.remove("soldeAnneePrec");
		json.remove("nomatr");
		json.remove("version");
		return res;
	}

	public String soldeToJson() {
		String test = new JSONSerializer().exclude("*.class").serialize(this);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		json = enleveTousChamps(json);
		json.put("soldeAnneeEnCours", soldeAnneeEnCours);
		json.put("soldeAnneePrec", soldeAnneePrec);

		return json.toJSONString();
	}
}
