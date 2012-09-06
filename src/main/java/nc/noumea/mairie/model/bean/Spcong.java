package nc.noumea.mairie.model.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.SpcongId;

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
@RooJpaActiveRecord(schema = "MAIRIE", table = "SPCONG")
public class Spcong implements Serializable {

	@EmbeddedId
	private SpcongId id;

	@OneToOne
	@JoinColumn(name = "TYPE2", referencedColumnName = "TYPE2")
	private Sptyco typeConge;

	@NotNull
	@Column(name = "DATFIN", columnDefinition = "numeric")
	private Integer datFin;

	@NotNull
	@Column(name = "NBJOUR", columnDefinition = "decimal")
	private Integer nbJours;

	@NotNull
	@Column(name = "CDVALI", columnDefinition = "char")
	private String statut;

	@NotNull
	@Column(name = "CDSAM", columnDefinition = "char")
	private String samediDecompte;

	public Spcong() {
	}

	public Spcong(Integer nomatr, Integer datDeb) {
		this.id = new SpcongId(nomatr, datDeb);
	}

	private static JSONObject enleveTousChamps(JSONObject json) {
		JSONObject res = json;
		// json.remove("id");
		json.remove("version");
		return res;
	}

	public static String congeToJson(
			List<nc.noumea.mairie.model.bean.Spcong> lcong) {
		String test = new JSONSerializer().exclude("*.class").serialize(lcong);
		JSONArray jsonAr = null;
		try {
			jsonAr = (JSONArray) new JSONParser().parse(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonAr.size(); i++) {
			JSONObject json = (JSONObject) jsonAr.get(i);
			json = enleveTousChamps(json);
			// pour le samedi
			String samedi = (String) json.get("samediDecompte");
			json.remove("samediDecompte");
			if (samedi != null && samedi.trim().equals("N")) {
				json.put("samediDecompte", "non");
			} else {
				json.put("samediDecompte", "");
			}
			// pour le statut
			String statut = (String) json.get("statut");
			json.remove("statut");
			if (statut != null) {
				if (statut.trim().equals("")) {
					json.put("statut", "saisi");
				}
				if (statut.trim().equals("E")) {
					json.put("statut", "édité");
				}
				if (statut.trim().equals("P")) {
					json.put("statut", "pré-reception sans edition");
				}
				if (statut.trim().equals("V")) {
					json.put("statut", "Validé");
				}
			} else {
				json.put("statut", "");
			}

			// pour la date de debut
			JSONObject idCong = (JSONObject) json.get("id");
			idCong.remove("nomatr");
			// TODO
			// bidouille pour le moment
			Object datDeb = idCong.get("datDeb");
			idCong.remove("datDeb");
			json.put("datDeb", datDeb);
			json.remove("id");

			// pour le type de conge
			JSONObject typCon = (JSONObject) json.get("typeConge");
			typCon.remove("idType");
			typCon.remove("version");
			// TODO
			// bidouille pour le moment
			String libtypeConge = (String) typCon.get("libTypeConge");
			typCon.remove("libTypeConge");
			json.put("typeConge", libtypeConge.trim());

			// on formate les dates
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (json.get("datDeb") != null) {
				try {
					Long dat = (Long) json.get("datDeb");
					String dateRemanie = dat.toString();
					String annee = dateRemanie.substring(0, 4);
					String mois = dateRemanie.substring(4, 6);
					String jour = dateRemanie
							.substring(6, dateRemanie.length());
					Date dateDebut = sdf.parse(jour + "/" + mois + "/" + annee);
					String dateDeb = "/Date(" + dateDebut.getTime() + ")/";
					json.put("datDeb", dateDeb);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (json.get("datFin") != null) {
				try {
					Long dat = (Long) json.get("datFin");
					String dateRemanie = dat.toString();
					String annee = dateRemanie.substring(0, 4);
					String mois = dateRemanie.substring(4, 6);
					String jour = dateRemanie
							.substring(6, dateRemanie.length());
					Date dateF = sdf.parse(jour + "/" + mois + "/" + annee);
					String dateFin = "/Date(" + dateF.getTime() + ")/";
					json.put("datFin", dateFin);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// pour finir
			jsonAr.remove(i);
			jsonAr.add(i, json);
		}
		return jsonAr.toJSONString();
	}
}
