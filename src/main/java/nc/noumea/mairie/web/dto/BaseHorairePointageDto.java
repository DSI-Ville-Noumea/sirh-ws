package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.sirh.BaseHorairePointage;

public class BaseHorairePointageDto {

	private Integer idBaseHorairePointage;
	private String codeBaseHorairePointage;
	private String libelleBaseHorairePointage;
	private String descriptionBaseHorairePointage;
	private Double heureLundi;
	private Double heureMardi;
	private Double heureMercredi;
	private Double heureJeudi;
	private Double heureVendredi;
	private Double heureSamedi;
	private Double heureDimanche;
	private Double baseLegale;
	private Double baseCalculee;

	public BaseHorairePointageDto() {
		super();
	}

	public BaseHorairePointageDto(BaseHorairePointage base) {
		if (base != null) {
			this.idBaseHorairePointage = base.getIdBaseHorairePointage();
			this.codeBaseHorairePointage = base.getCodeBaseHorairePointage();
			this.libelleBaseHorairePointage = base.getLibelleBaseHorairePointage();
			this.descriptionBaseHorairePointage = base.getDescriptionBaseHorairePointage();
			this.heureLundi = base.getHeureLundi();
			this.heureMardi = base.getHeureMardi();
			this.heureMercredi = base.getHeureMercredi();
			this.heureJeudi = base.getHeureJeudi();
			this.heureVendredi = base.getHeureVendredi();
			this.heureSamedi = base.getHeureSamedi();
			this.heureDimanche = base.getHeureDimanche();
			this.baseLegale = base.getBaseLegale();
			this.baseCalculee = base.getBaseCalculee();
		}
	}

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
