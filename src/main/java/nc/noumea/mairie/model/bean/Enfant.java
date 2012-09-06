package nc.noumea.mairie.model.bean;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.service.ISivietService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierColumn = "ID_ENFANT", schema = "SIRH", identifierField = "idEnfant", identifierType = Integer.class, table = "ENFANT")
@RooSerializable
@RooJson
public class Enfant {

	@NotNull
	@Column(name = "NOM")
	private String nom;

	@NotNull
	@Column(name = "PRENOM")
	private String prenom;

	@NotNull
	@Column(name = "SEXE")
	private String sexe;

	@Column(name = "DATE_NAISSANCE")
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;

	@ManyToOne
	@JoinColumn(name = "CODE_COMMUNE_NAISS_FR", referencedColumnName = "CODCOM")
	private Sicomm codeCommuneNaissFr;

	@Column(name = "CODE_COMMUNE_NAISS_ET", columnDefinition="numeric")
	private Integer codeCommuneNaissEt;

	@Column(name = "CODE_PAYS_NAISS_ET", columnDefinition="numeric")
	private Integer codePaysNaissEt;

	private static JSONObject enleveTousChamps(JSONObject json) {
		JSONObject res = json;
		json.remove("idEnfant");
		json.remove("version");
		return res;
	}

	public static String enfantToJsonArray(Set<Enfant> enfants,
			ISivietService sivietSrv) {
		String test = new JSONSerializer().exclude("*.class")
				.serialize(enfants);
		JSONArray jsonAr = null;
		try {
			jsonAr = (JSONArray) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonAr.size(); i++) {
			JSONObject json = (JSONObject) jsonAr.get(i);
			json = enleveTousChamps(json);
			// pour la date de naissance
			Long dateNaissance = (Long) json.get("dateNaissance");
			if (dateNaissance != null) {
				json.put("dateNaissance", "/Date(" + dateNaissance + ")/");
			}
			// Pour le lieu de naissance on ne veut que le libellé
			JSONObject commNaissFr = (JSONObject) json
					.get("codeCommuneNaissFr");
			if (commNaissFr != null) {
				// TODO
				// bidouille pour le moment
				String libLieuNaissFr = (String) commNaissFr.get("libVil");
				json.put("lieuNaissance", libLieuNaissFr.trim());
			} else {
				Long commNaissEt = (Long) json.get("codeCommuneNaissEt");
				Long paysNaissEt = (Long) json.get("codePaysNaissEt");

				String res = "";
				SIVIET siviet = sivietSrv.getLieuNaissEtr(
						paysNaissEt.intValue(), commNaissEt.intValue());
				if (siviet != null) {
					res = siviet.lieuNaissancetoJson();
					// TODO
					// pour le moment on fait de la bidouille
					res = res.replace("{", "");
					res = res.replace("}", "");
					res = res.substring(res.indexOf(":") + 1, res.length());
					res = res.replace("\"", "");
				}
				json.put("lieuNaissance", res);
			}
			json.remove("codeCommuneNaissFr");
			json.remove("codeCommuneNaissEt");
			json.remove("codePaysNaissEt");
			// TODO
			// pour le moment on ne sait pas recupere enfant à charge
			json.put("aCharge", "");
			jsonAr.remove(i);
			jsonAr.add(i, json);

		}
		return jsonAr.toJSONString();
	}
}
