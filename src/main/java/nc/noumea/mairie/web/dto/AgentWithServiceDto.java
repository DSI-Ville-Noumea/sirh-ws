package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AgentRecherche;

public class AgentWithServiceDto extends AgentDto {
	private String service;
	private String codeService;

	public AgentWithServiceDto() {
		super();
	}

	public AgentWithServiceDto(Agent agent) {
		super(agent);
	}

	public AgentWithServiceDto(AgentRecherche ag) {
		super(ag);
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

}
