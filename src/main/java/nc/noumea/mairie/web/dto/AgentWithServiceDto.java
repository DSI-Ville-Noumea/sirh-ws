package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AgentRecherche;

public class AgentWithServiceDto extends AgentDto {
	private String service;
	private String codeService;
	private String direction;

	public AgentWithServiceDto() {
		super();
	}

	public AgentWithServiceDto(Agent agent) {
		super(agent);
	}

	public AgentWithServiceDto(AgentRecherche ag) {
		super(ag);
	}

	public AgentWithServiceDto(Agent agent, Siserv service) {
		super(agent);
		this.service = service.getLiServ().trim();
		this.codeService = service.getServi();
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCodeService() {
		return codeService;
	}

	public void setCodeService(String codeService) {
		this.codeService = codeService;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
