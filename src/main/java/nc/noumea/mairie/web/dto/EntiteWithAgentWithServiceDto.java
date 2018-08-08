package nc.noumea.mairie.web.dto;

import java.util.ArrayList;
import java.util.List;

public class EntiteWithAgentWithServiceDto extends EntiteDto {

	private List<AgentWithServiceDto> listAgentWithServiceDto;

	private List<EntiteWithAgentWithServiceDto> entiteEnfantWithAgents;

	public EntiteWithAgentWithServiceDto() {
		listAgentWithServiceDto = new ArrayList<AgentWithServiceDto>();
		entiteEnfantWithAgents = new ArrayList<EntiteWithAgentWithServiceDto>();
	}
	
	public EntiteWithAgentWithServiceDto(EntiteDto dto) {
		this();
		setIdEntite(dto.getIdEntite());
		setSigle(dto.getSigle());
		setLabel(dto.getLabel());
		setLabelCourt(dto.getLabelCourt());
		setLabelLong(dto.getLabelLong());
		setTypeEntite(dto.getTypeEntite());
		setCodeServi(dto.getCodeServi());
		setEnfants(dto.getEnfants());
		setEntiteParent(dto.getEntiteParent());
		setEntiteRemplacee(dto.getEntiteRemplacee());

		setIdStatut(dto.getIdStatut());
		setIdAgentCreation(dto.getIdAgentCreation());
		setDateCreation(dto.getDateCreation());
		setIdAgentModification(dto.getIdAgentModification());
		setDateModification(dto.getDateModification());
		setRefDeliberationActif(dto.getRefDeliberationActif());
		setDateDeliberationActif(dto.getDateDeliberationActif());
		setRefDeliberationInactif(dto.getRefDeliberationInactif());
		setDateDeliberationInactif(dto.getDateDeliberationInactif());
		setNfa(dto.getNfa());
	}
	
	public List<AgentWithServiceDto> getListAgentWithServiceDto() {
		return listAgentWithServiceDto;
	}

	public void setListAgentWithServiceDto(
			List<AgentWithServiceDto> listAgentWithServiceDto) {
		this.listAgentWithServiceDto = listAgentWithServiceDto;
	}

	public List<EntiteWithAgentWithServiceDto> getEntiteEnfantWithAgents() {
		return entiteEnfantWithAgents;
	}

	public void setEntiteEnfantWithAgents(
			List<EntiteWithAgentWithServiceDto> entiteEnfantWithAgents) {
		this.entiteEnfantWithAgents = entiteEnfantWithAgents;
	}
}
