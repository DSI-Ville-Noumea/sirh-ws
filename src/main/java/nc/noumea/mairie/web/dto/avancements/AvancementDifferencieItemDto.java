package nc.noumea.mairie.web.dto.avancements;

import java.util.Date;

import nc.noumea.mairie.model.bean.AvancementFonctionnaire;

public class AvancementDifferencieItemDto {

	private String nom;
	private String prenom;
	private String grade;
	private Date datePrevisionnelleAvancement;
	private boolean dureeMin;
	private boolean dureeMoy;
	private boolean dureeMax;
	private String classement;
	
	public AvancementDifferencieItemDto() {
		
	}
	
	public AvancementDifferencieItemDto (AvancementFonctionnaire avct) {
		
		if (avct.getAgent() != null) {
			this.nom = avct.getAgent().getDisplayNom();
			this.prenom = avct.getAgent().getDisplayPrenom();
		}
		
		if (avct.getGrade() != null)
			this.grade = avct.getGrade().getGradeInitial().trim();
		
		this.datePrevisionnelleAvancement = avct.getDateAvctMoy();
		this.classement = avct.getOrdreMerite();
		
		if (avct.getAvisCap() != null) {
			switch(avct.getAvisCap().getIdAvisCap()) {
				case 1:
					this.dureeMin = true;
					break;
				case 2:
					this.dureeMoy = true;
					break;
				case 3:
					this.dureeMax = true;
					break;
			}
		}
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Date getDatePrevisionnelleAvancement() {
		return datePrevisionnelleAvancement;
	}
	public void setDatePrevisionnelleAvancement(Date datePrevisionnelleAvancement) {
		this.datePrevisionnelleAvancement = datePrevisionnelleAvancement;
	}
	public boolean isDureeMin() {
		return dureeMin;
	}
	public void setDureeMin(boolean dureeMin) {
		this.dureeMin = dureeMin;
	}
	public boolean isDureeMoy() {
		return dureeMoy;
	}
	public void setDureeMoy(boolean dureeMoy) {
		this.dureeMoy = dureeMoy;
	}
	public boolean isDureeMax() {
		return dureeMax;
	}
	public void setDureeMax(boolean dureeMax) {
		this.dureeMax = dureeMax;
	}
	public String getClassement() {
		return classement;
	}
	public void setClassement(String classement) {
		this.classement = classement;
	}
}
