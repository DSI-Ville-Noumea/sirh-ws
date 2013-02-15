package nc.noumea.mairie.web.dto.avancements;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommissionAvancementDto {

	private List<CommissionAvancementCorpsDto> commissionsParCorps;

	public CommissionAvancementDto() {
		this.commissionsParCorps = new ArrayList<CommissionAvancementCorpsDto>();
	}

	public List<CommissionAvancementCorpsDto> getCommissionsParCorps() {
		return commissionsParCorps;
	}

	public void setCommissionsParCorps(
			List<CommissionAvancementCorpsDto> commissionsParCorps) {
		this.commissionsParCorps = commissionsParCorps;
	}
}
