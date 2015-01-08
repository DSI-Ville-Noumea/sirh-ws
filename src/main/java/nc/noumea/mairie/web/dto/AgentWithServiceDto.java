package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AgentRecherche;

public class AgentWithServiceDto extends AgentDto {
	private String service;
	private String codeService;
	private String direction;
	private String signature;
	private String position;
	private String sigleService;

	public AgentWithServiceDto() {
		super();
	}

	public AgentWithServiceDto(Agent agent) {
		super(agent);
		this.position = agent.getPosition();
	}

	public AgentWithServiceDto(AgentRecherche ag) {
		super(ag);
	}

	public AgentWithServiceDto(Agent agent, Siserv service) {
		super(agent);
		if (service != null) {
			this.service = service.getLiServ();
			this.codeService = service.getServi();
			this.signature = service.getSignature();
			this.sigleService = service.getSigle();
		}
		this.position = agent.getPosition();
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSigleService() {
		return sigleService;
	}

	public void setSigleService(String sigleService) {
		this.sigleService = sigleService;
	}

}
