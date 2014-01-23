package nc.noumea.mairie.model.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.SpcongId;
import nc.noumea.mairie.tools.transformer.MSDateTransformer;
import nc.noumea.mairie.tools.transformer.NullableIntegerTransformer;
import nc.noumea.mairie.tools.transformer.StringTrimTransformer;
import nc.noumea.mairie.tools.transformer.TypeCongeTransformer;
import flexjson.JSONSerializer;

@Entity
@Table(name = "SPCONG")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spcong implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SpcongId id;

	@Transient
	Sptyco typeConge;

	public Sptyco getTypeConge() {
		return this.id.getTypConge();
	}

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
		if (this.samediDecompte != null && this.samediDecompte.trim().equals("N")) {
			return "non";
		} else {
			return "";
		}
	}

	public Spcong() {
	}

	public Spcong(Integer nomatr, Integer dateDeb, Integer rang, Sptyco typConge) {
		this.id = new SpcongId(nomatr, dateDeb, rang, typConge);
	}

	public static JSONSerializer getSerializerForAgentHistoConge() {

		JSONSerializer serializer = new JSONSerializer().include("samediDecompte").include("statut").include("datDeb")
				.include("typeConge").include("nbJours").include("datFin")
				.transform(new MSDateTransformer(), Date.class).transform(new TypeCongeTransformer(), Sptyco.class)
				.transform(new NullableIntegerTransformer(), Integer.class)
				.transform(new StringTrimTransformer(), String.class).exclude("*");

		return serializer;
	}

	public SpcongId getId() {
		return id;
	}

	public void setId(SpcongId id) {
		this.id = id;
	}

	public Integer getDateFin() {
		return dateFin;
	}

	public void setDateFin(Integer dateFin) {
		this.dateFin = dateFin;
	}

	public Double getNbJours() {
		return nbJours;
	}

	public void setNbJours(Double nbJours) {
		this.nbJours = nbJours;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public void setSamediDecompte(String samediDecompte) {
		this.samediDecompte = samediDecompte;
	}
}
