package nc.noumea.mairie.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import nc.noumea.mairie.model.pk.SivietId;

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
@RooJpaActiveRecord(schema = "MAIRIE", table = "SIVIET")
public class SIVIET implements Serializable {

	@EmbeddedId
	private SivietId id;

	@Column(name = "LIBCOP", columnDefinition = "char")
	private String libCop;

	public SIVIET() {
	}

	public SIVIET(Integer codePays, Integer sousCodePays) {
		this.id = new SivietId(codePays, sousCodePays);
	}

	public String lieuNaissancetoJson() {
		String test = new JSONSerializer().exclude("*.class").serialize(this);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		json.remove("id");
		json.remove("libCop");
		json.remove("version");
		json.put("lieuNaissance", libCop.trim());
		return json.toJSONString();
	}
}
