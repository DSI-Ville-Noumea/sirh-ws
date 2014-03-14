package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.FormationAgent;

public class FormationDto {

	private Integer idFormation;
	private String titreFormation;
	private String centreFormation;
	private Integer anneeFormation;
	private Integer dureeFormation;
	private String uniteDuree;
	
	public FormationDto(FormationAgent fa) {
		this.idFormation = fa.getIdFormation();
		if(null != fa.getTitreFormation())
			this.titreFormation = fa.getTitreFormation().getLibTitreFormation();
		
		if(null != fa.getCentreFormation())
			this.centreFormation = fa.getCentreFormation().getLibCentreFormation();
		this.anneeFormation = fa.getAnneeFormation();
		this.dureeFormation = fa.getDureeFormation();
		this.uniteDuree = fa.getUniteformation();
	}
	
	public Integer getIdFormation() {
		return idFormation;
	}
	public void setIdFormation(Integer idFormation) {
		this.idFormation = idFormation;
	}
	public String getTitreFormation() {
		return titreFormation;
	}
	public void setTitreFormation(String titreFormation) {
		this.titreFormation = titreFormation;
	}
	public String getCentreFormation() {
		return centreFormation;
	}
	public void setCentreFormation(String centreFormation) {
		this.centreFormation = centreFormation;
	}
	public Integer getAnneeFormation() {
		return anneeFormation;
	}
	public void setAnneeFormation(Integer anneeFormation) {
		this.anneeFormation = anneeFormation;
	}
	public Integer getDureeFormation() {
		return dureeFormation;
	}
	public void setDureeFormation(Integer dureeFormation) {
		this.dureeFormation = dureeFormation;
	}
	public String getUniteDuree() {
		return uniteDuree;
	}
	public void setUniteDuree(String uniteDuree) {
		this.uniteDuree = uniteDuree;
	}
	
	
}
