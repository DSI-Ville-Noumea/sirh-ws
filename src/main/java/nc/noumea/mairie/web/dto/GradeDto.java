package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.Spgradn;

public class GradeDto {

	private String codeGrade;
	private String libelleGrade;
	private String gradeInitial;
	
	private String codeClasse;
	private String libelleClasse;
	
	private Integer dureeMinimum;
	private Integer dureeMoyenne;
	private Integer dureeMaximum;
	
	private String codeGradeGenerique;
	private String codeEchelon;
	private String libelleEchelon;
	
	private String codeMotifAvancement;
	
	public GradeDto(Spgradn spGradn) {
		this.codeGrade = spGradn.getCdgrad();
		this.libelleGrade = spGradn.getLiGrad();
		this.gradeInitial = spGradn.getGradeInitial();
		if(null != spGradn.getClasse()) {
			this.codeClasse = spGradn.getClasse().getCodcla();
			this.libelleClasse = spGradn.getClasse().getLibCla();
		}
		this.dureeMinimum = spGradn.getDureeMinimum();
		this.dureeMoyenne = spGradn.getDureeMoyenne();
		this.dureeMaximum = spGradn.getDureeMaximum();
		if(null != spGradn.getGradeGenerique()) {
			this.codeGradeGenerique = spGradn.getGradeGenerique().getCdcadr();
		}
		if(null != spGradn.getEchelon()) {
			this.codeEchelon = spGradn.getEchelon().getCodEch();
			this.libelleEchelon = spGradn.getEchelon().getLibEch();
		}
		if(null != spGradn.getMotifAvct()) {
			this.codeMotifAvancement = spGradn.getMotifAvct().getCodeAvct();
		}
	}
	
	public String getCodeGrade() {
		return codeGrade;
	}
	public void setCodeGrade(String codeGrade) {
		this.codeGrade = codeGrade;
	}
	public String getLibelleGrade() {
		return libelleGrade;
	}
	public void setLibelleGrade(String libelleGrade) {
		this.libelleGrade = libelleGrade;
	}
	public String getCodeClasse() {
		return codeClasse;
	}
	public void setCodeClasse(String codeClasse) {
		this.codeClasse = codeClasse;
	}
	public String getLibelleClasse() {
		return libelleClasse;
	}
	public void setLibelleClasse(String libelleClasse) {
		this.libelleClasse = libelleClasse;
	}
	public Integer getDureeMinimum() {
		return dureeMinimum;
	}
	public void setDureeMinimum(Integer dureeMinimum) {
		this.dureeMinimum = dureeMinimum;
	}
	public Integer getDureeMoyenne() {
		return dureeMoyenne;
	}
	public void setDureeMoyenne(Integer dureeMoyenne) {
		this.dureeMoyenne = dureeMoyenne;
	}
	public Integer getDureeMaximum() {
		return dureeMaximum;
	}
	public void setDureeMaximum(Integer dureeMaximum) {
		this.dureeMaximum = dureeMaximum;
	}
	public String getCodeGradeGenerique() {
		return codeGradeGenerique;
	}
	public void setCodeGradeGenerique(String codeGradeGenerique) {
		this.codeGradeGenerique = codeGradeGenerique;
	}
	public String getCodeEchelon() {
		return codeEchelon;
	}
	public void setCodeEchelon(String codeEchelon) {
		this.codeEchelon = codeEchelon;
	}
	public String getLibelleEchelon() {
		return libelleEchelon;
	}
	public void setLibelleEchelon(String libelleEchelon) {
		this.libelleEchelon = libelleEchelon;
	}

	public String getGradeInitial() {
		return gradeInitial;
	}

	public void setGradeInitial(String gradeInitial) {
		this.gradeInitial = gradeInitial;
	}

	public String getCodeMotifAvancement() {
		return codeMotifAvancement;
	}

	public void setCodeMotifAvancement(String codeMotifAvancement) {
		this.codeMotifAvancement = codeMotifAvancement;
	}
	
	
}
