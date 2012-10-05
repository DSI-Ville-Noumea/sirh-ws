package nc.noumea.mairie.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import nc.noumea.mairie.model.pk.SiguicId;

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
@RooJpaActiveRecord(schema = "MAIRIE", table = "SIGUIC",versionField="")
public class Siguic implements Serializable {

	@EmbeddedId
	private SiguicId id;

	@Column(name = "LIGUIC",columnDefinition="char")
	private String liGuic;

	public Siguic() {
	}

	public Siguic(Integer codeBanque, Integer codeGuichet) {
		this.id = new SiguicId(codeBanque, codeGuichet);
	}

	public String banqueToJson() {
		String test = new JSONSerializer().exclude("*.class").serialize(this);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		json.remove("id");
		json.remove("liGuic");
		json.remove("version");
		json.put("banque", liGuic.trim());
		return json.toJSONString();
	}
}
