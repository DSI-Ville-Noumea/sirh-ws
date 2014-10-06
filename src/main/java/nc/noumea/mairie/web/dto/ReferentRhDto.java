package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.sirh.ReferentRh;

public class ReferentRhDto {

	private String servi;
	private Integer idAgentReferent;
	private Integer numeroTelephone;

	public ReferentRhDto() {
	}

	public ReferentRhDto(ReferentRh c) {
		this.servi = c.getServi();
		this.idAgentReferent = c.getIdAgentReferent();
		this.numeroTelephone = c.getNumeroTelephone();
	}

	public String getServi() {
		return servi;
	}

	public void setServi(String servi) {
		this.servi = servi;
	}

	public Integer getIdAgentReferent() {
		return idAgentReferent;
	}

	public void setIdAgentReferent(Integer idAgentReferent) {
		this.idAgentReferent = idAgentReferent;
	}

	public Integer getNumeroTelephone() {
		return numeroTelephone;
	}

	public void setNumeroTelephone(Integer numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
	}

}
