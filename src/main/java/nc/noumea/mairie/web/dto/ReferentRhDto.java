package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;

public class ReferentRhDto {

	private Integer idReferentRh;
	private Integer idServiceADS;
	private String sigleService;
	private Integer idAgentReferent;
	private String prenomAgentReferent;
	private Integer numeroTelephone;

	public ReferentRhDto() {
	}

	public ReferentRhDto(ReferentRh c, Agent ag, EntiteDto serv) {
		if (c != null) {
			this.idReferentRh = c.getIdReferentRh();
			this.idServiceADS = c.getIdServiceADS();
			this.idAgentReferent = c.getIdAgentReferent();
			this.numeroTelephone = c.getNumeroTelephone();
		}
		if (ag != null) {
			this.prenomAgentReferent = ag.getPrenomUsage();
		}
		if (serv != null) {
			this.sigleService = serv.getSigle();
		}
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

	public String getSigleService() {
		return sigleService;
	}

	public void setSigleService(String sigleService) {
		this.sigleService = sigleService;
	}

	public String getPrenomAgentReferent() {
		return prenomAgentReferent;
	}

	public void setPrenomAgentReferent(String prenomAgentReferent) {
		this.prenomAgentReferent = prenomAgentReferent;
	}

	public Integer getIdReferentRh() {
		return idReferentRh;
	}

	public void setIdReferentRh(Integer idReferentRh) {
		this.idReferentRh = idReferentRh;
	}

	public Integer getIdServiceADS() {
		return idServiceADS;
	}

	public void setIdServiceADS(Integer idServiceADS) {
		this.idServiceADS = idServiceADS;
	}

}
