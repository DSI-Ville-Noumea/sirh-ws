package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Id;
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
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", table = "SPSOLD", versionField = "")
public class SpSold {

	@Id
	@Column(name = "NOMATR", columnDefinition = "numeric")
	private Integer nomatr;

	@NotNull
	@Column(name = "SOLDE1", columnDefinition = "decimal")
	private Double soldeAnneeEnCours;

	@NotNull
	@Column(name = "SOLDE2", columnDefinition = "decimal")
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
