package nc.noumea.mairie.web.dto.avancements;

import nc.noumea.mairie.model.bean.Spgeng;

public class CommissionAvancementCorpsDto {

	private String corps;
	private AvancementDifferencieDto avancementsDifferencies;
	private ChangementClasseDto changementClasses;

	public CommissionAvancementCorpsDto() {
		
	}
	
	public CommissionAvancementCorpsDto(Spgeng spgeng) {
		this.corps = spgeng.getCdgeng();
	}
	
	public String getCorps() {
		return corps;
	}

	public void setCorps(String corps) {
		this.corps = corps;
	}

	public AvancementDifferencieDto getAvancementsDifferencies() {
		return avancementsDifferencies;
	}

	public void setAvancementsDifferencies(
			AvancementDifferencieDto avancementsDifferencies) {
		this.avancementsDifferencies = avancementsDifferencies;
	}

	public ChangementClasseDto getChangementClasses() {
		return changementClasses;
	}

	public void setChangementClasses(ChangementClasseDto changementClasses) {
		this.changementClasses = changementClasses;
	}
}
