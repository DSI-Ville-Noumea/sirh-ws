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
	
	public GradeDto() {
	}
	
	public GradeDto(Spgradn spGradn) {
		this.codeGrade = spGradn.getCdgrad();
		this.libelleGrade = null != spGradn.getLiGrad() ? spGradn.getLiGrad().trim() : "";
		this.gradeInitial = null != spGradn.getGradeInitial() ? spGradn.getGradeInitial().trim() : "";
		if(null != spGradn.getClasse()) {
			this.codeClasse = spGradn.getClasse().getCodcla();
			this.libelleClasse = null != spGradn.getClasse().getLibCla() ? spGradn.getClasse().getLibCla().trim() : "";
		}
		this.dureeMinimum = spGradn.getDureeMinimum();
		this.dureeMoyenne = spGradn.getDureeMoyenne();
		this.dureeMaximum = spGradn.getDureeMaximum();
		if(null != spGradn.getGradeGenerique()
				&& null != spGradn.getGradeGenerique().getCdcadr()) {
			this.codeGradeGenerique = spGradn.getGradeGenerique().getCdcadr().trim();
		}
		if(null != spGradn.getEchelon()) {
			this.codeEchelon = spGradn.getEchelon().getCodEch();
			this.libelleEchelon = null != spGradn.getEchelon().getLibEch() ? spGradn.getEchelon().getLibEch().trim() : "";
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
