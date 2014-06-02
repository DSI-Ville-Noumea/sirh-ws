package nc.noumea.mairie.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListConvocationVMDto {

	private List<ConvocationVMDto> convocations;

	private String typePopulation;

	public ListConvocationVMDto() {
		convocations = new ArrayList<ConvocationVMDto>();
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
}
