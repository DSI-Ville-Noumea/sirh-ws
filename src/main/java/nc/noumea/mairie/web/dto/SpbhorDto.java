package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.Spbhor;

public class SpbhorDto {

	private Integer cdThor;
	private String label;
	private Double taux;
	
	public SpbhorDto(Spbhor p) {
		this.cdThor = p.getCdThor();
		this.label = p.getLibHor();
		this.taux = p.getTaux();
	}

	public Integer getCdThor() {
		return cdThor;
	}

	public void setCdThor(Integer cdThor) {
		this.cdThor = cdThor;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getTaux() {
		return taux;
	}

	public void setTaux(Double taux) {
		this.taux = taux;
	}
}
