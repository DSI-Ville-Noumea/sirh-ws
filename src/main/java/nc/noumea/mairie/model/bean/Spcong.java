package nc.noumea.mairie.model.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.SpcongId;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.tools.transformer.NullableIntegerTransformer;
import nc.noumea.mairie.tools.transformer.StringTrimTransformer;
import nc.noumea.mairie.tools.transformer.TypeCongeTransformer;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", table = "SPCONG", versionField = "")
public class Spcong implements Serializable {

	@EmbeddedId
	private SpcongId id;

	@OneToOne
	@JoinColumn(name = "TYPE2", referencedColumnName = "TYPE2")
	private Sptyco typeConge;

	@NotNull
	@Column(name = "DATFIN", columnDefinition = "numeric")
	private Integer dateFin;

	@Transient
	Date datFin;

	public Date getDatFin() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Integer dat = this.dateFin;
			String dateRemanie = dat.toString();
			String annee = dateRemanie.substring(0, 4);
			String mois = dateRemanie.substring(4, 6);
			String jour = dateRemanie.substring(6, dateRemanie.length());
			Date dateF = sdf.parse(jour + "/" + mois + "/" + annee);
			this.datFin = dateF;

		} catch (Exception e) {
			this.datFin = null;
		}

		return this.datFin;
	}

	@Transient
	Date datDeb;

	public Date getDatDeb() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Integer dat = this.id.getDateDeb();
			String dateRemanie = dat.toString();
			String annee = dateRemanie.substring(0, 4);
			String mois = dateRemanie.substring(4, 6);
			String jour = dateRemanie.substring(6, dateRemanie.length());
			Date dateF = sdf.parse(jour + "/" + mois + "/" + annee);
			this.datDeb = dateF;

		} catch (Exception e) {
			this.datDeb = null;
		}

		return this.datDeb;
	}

	@NotNull
	@Column(name = "NBJOUR", columnDefinition = "decimal")
	private Double nbJours;

	@NotNull
	@Column(name = "CDVALI", columnDefinition = "char")
	private String statut;

	public String getStatut() {
		if (this.statut != null) {
			if (this.statut.trim().equals("")) {
				return "Saisi";
			} else if (this.statut.trim().equals("E")) {
				return "Edité";
			} else if (this.statut.trim().equals("P")) {
				return "Pré-réception sans édition";
			} else if (this.statut.trim().equals("V")) {
				return "Validé";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	@NotNull
	@Column(name = "CDSAM", columnDefinition = "char")
	private String samediDecompte;

	public String getSamediDecompte() {
		if (this.samediDecompte != null
				&& this.samediDecompte.trim().equals("N")) {
			return "non";
		} else {
			return "";
		}
	}

	public Spcong() {
	}

	public Spcong(Integer nomatr, Integer datDeb) {
		this.id = new SpcongId(nomatr, datDeb);
	}

	public static JSONSerializer getSerializerForAgentHistoConge() {

		JSONSerializer serializer = new JSONSerializer()
				.include("samediDecompte").include("statut").include("datDeb")
				.include("typeConge").include("nbJours").include("datFin")
				.transform(new MSDateTransformer(), Date.class)
				.transform(new TypeCongeTransformer(), Sptyco.class)
				.transform(new NullableIntegerTransformer(), Integer.class)
				.transform(new StringTrimTransformer(), String.class)
				.exclude("*");

		return serializer;
	}
}
