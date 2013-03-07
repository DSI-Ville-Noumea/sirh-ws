package nc.noumea.mairie.web.dto.avancements;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArreteListDto {

	private List<ArreteDto> arretes;

	public ArreteListDto() {
		arretes = new ArrayList<ArreteDto>();
	}
	
	public List<ArreteDto> getArretes() {
		return arretes;
	}

	public void setArretes(List<ArreteDto> arretes) {
		this.arretes = arretes;
	}
}
