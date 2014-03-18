package nc.noumea.mairie.web.dto.avancements;

import nc.noumea.mairie.model.bean.Spgeng;

public class CommissionAvancementCorpsDto {

	private String corps;
	private AvancementsDto avancementsDifferencies;
	private AvancementsDto changementClasses;

	public CommissionAvancementCorpsDto() {
		super();
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

	public AvancementsDto getAvancementsDifferencies() {
		return avancementsDifferencies;
	}

	public void setAvancementsDifferencies(
			AvancementsDto avancementsDifferencies) {
		this.avancementsDifferencies = avancementsDifferencies;
	}

	public AvancementsDto getChangementClasses() {
		return changementClasses;
	}

	public void setChangementClasses(AvancementsDto changementClasses) {
		this.changementClasses = changementClasses;
	}
}
