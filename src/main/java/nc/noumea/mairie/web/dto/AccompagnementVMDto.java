package nc.noumea.mairie.web.dto;

import java.util.ArrayList;
import java.util.List;

public class AccompagnementVMDto {

	private List<ConvocationVMDto> agents;
	private AgentWithServiceDto agentResponsable;

	public AccompagnementVMDto() {
		this.agents = new ArrayList<ConvocationVMDto>();
	}

	public AccompagnementVMDto(AgentWithServiceDto agRespDto) {
		this.agents = new ArrayList<ConvocationVMDto>();
		this.agentResponsable = agRespDto;
	}

	public AgentWithServiceDto getAgentResponsable() {
		return agentResponsable;
	}

	public void setAgentResponsable(AgentWithServiceDto agentResponsable) {
		this.agentResponsable = agentResponsable;
	}

	public List<ConvocationVMDto> getAgents() {
		return agents;
	}

	public void setAgents(List<ConvocationVMDto> agents) {
		this.agents = agents;
	}

}
