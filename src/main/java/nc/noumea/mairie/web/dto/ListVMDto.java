package nc.noumea.mairie.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListVMDto {

	private List<ConvocationVMDto> convocations;

	private List<AccompagnementVMDto> accompagnements;

	private String typePopulation;

	public ListVMDto() {
		convocations = new ArrayList<ConvocationVMDto>();
		accompagnements = new ArrayList<AccompagnementVMDto>();
	}

	public List<ConvocationVMDto> getConvocations() {
		return convocations;
	}

	public void setConvocations(List<ConvocationVMDto> convocations) {
		this.convocations = convocations;
	}

	public String getTypePopulation() {
		return typePopulation;
	}

	public void setTypePopulation(String typePopulation) {
		this.typePopulation = typePopulation;
	}

	public List<AccompagnementVMDto> getAccompagnements() {
		return accompagnements;
	}

	public void setAccompagnements(List<AccompagnementVMDto> accompagnements) {
		this.accompagnements = accompagnements;
	}
}
