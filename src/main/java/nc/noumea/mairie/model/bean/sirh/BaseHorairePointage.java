package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "P_BASE_HORAIRE_POINTAGE")
public class BaseHorairePointage {

	@Id
	@Column(name = "ID_BASE_HORAIRE_POINTAGE")
	@NotNull
	private Integer idBaseHorairePointage;

	@Column(name = "CODE_BASE_HORAIRE_POINTAGE")
	private String codeBaseHorairePointage;

	@Column(name = "LIBELLE_BASE_HORAIRE_POINTAGE")
	private String libelleBaseHorairePointage;

	@Column(name = "DESCRIPTION_BASE_HORAIRE_POINTAGE")
	private String descriptionBaseHorairePointage;

	@Column(name = "HEURE_LUNDI", columnDefinition = "decimal")
	private Double heureLundi;

	@Column(name = "HEURE_MARDI", columnDefinition = "decimal")
	private Double heureMardi;

	@Column(name = "HEURE_MERCREDI", columnDefinition = "decimal")
	private Double heureMercredi;

	@Column(name = "HEURE_JEUDI", columnDefinition = "decimal")
	private Double heureJeudi;

	@Column(name = "HEURE_VENDREDI", columnDefinition = "decimal")
	private Double heureVendredi;

	@Column(name = "HEURE_SAMEDI", columnDefinition = "decimal")
	private Double heureSamedi;

	@Column(name = "HEURE_DIMANCHE", columnDefinition = "decimal")
	private Double heureDimanche;

	@Column(name = "BASE_LEGALE", columnDefinition = "decimal")
	private Double baseLegale;

	@Column(name = "BASE_CALCULEE", columnDefinition = "decimal")
	private Double baseCalculee;

	public Integer getIdBaseHorairePointage() {
		return idBaseHorairePointage;
	}

	public void setIdBaseHorairePointage(Integer idBaseHorairePointage) {
		this.idBaseHorairePointage = idBaseHorairePointage;
	}

	public String getCodeBaseHorairePointage() {
		return codeBaseHorairePointage;
	}

	public void setCodeBaseHorairePointage(String codeBaseHorairePointage) {
		this.codeBaseHorairePointage = codeBaseHorairePointage;
	}

	public String getLibelleBaseHorairePointage() {
		return libelleBaseHorairePointage;
	}

	public void setLibelleBaseHorairePointage(String libelleBaseHorairePointage) {
		this.libelleBaseHorairePointage = libelleBaseHorairePointage;
	}

	public String getDescriptionBaseHorairePointage() {
		return descriptionBaseHorairePointage;
	}

	public void setDescriptionBaseHorairePointage(String descriptionBaseHorairePointage) {
		this.descriptionBaseHorairePointage = descriptionBaseHorairePointage;
	}

	public Double getHeureLundi() {
		return heureLundi;
	}

	public void setHeureLundi(Double heureLundi) {
		this.heureLundi = heureLundi;
	}

	public Double getHeureMardi() {
		return heureMardi;
	}

	public void setHeureMardi(Double heureMardi) {
		this.heureMardi = heureMardi;
	}

	public Double getHeureMercredi() {
		return heureMercredi;
	}

	public void setHeureMercredi(Double heureMercredi) {
		this.heureMercredi = heureMercredi;
	}

	public Double getHeureJeudi() {
		return heureJeudi;
	}

	public void setHeureJeudi(Double heureJeudi) {
		this.heureJeudi = heureJeudi;
	}

	public Double getHeureVendredi() {
		return heureVendredi;
	}

	public void setHeureVendredi(Double heureVendredi) {
		this.heureVendredi = heureVendredi;
	}

	public Double getHeureSamedi() {
		return heureSamedi;
	}

	public void setHeureSamedi(Double heureSamedi) {
		this.heureSamedi = heureSamedi;
	}

	public Double getHeureDimanche() {
		return heureDimanche;
	}

	public void setHeureDimanche(Double heureDimanche) {
		this.heureDimanche = heureDimanche;
	}

	public Double getBaseLegale() {
		return baseLegale;
	}

	public void setBaseLegale(Double baseLegale) {
		this.baseLegale = baseLegale;
	}

	public Double getBaseCalculee() {
		return baseCalculee;
	}

	public void setBaseCalculee(Double baseCalculee) {
		this.baseCalculee = baseCalculee;
	}

}
