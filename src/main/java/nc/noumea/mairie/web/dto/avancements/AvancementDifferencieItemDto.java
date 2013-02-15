package nc.noumea.mairie.web.dto.avancements;

import java.util.Date;

public class AvancementDifferencieItemDto {

	private String nom;
	private String prenom;
	private String grade;
	private Date datePrevisionnelleAvancement;
	private boolean dureeMin;
	private boolean dureeMoy;
	private boolean dureeMax;
	private int classement;
	
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
	public int getClassement() {
		return classement;
	}
	public void setClassement(int classement) {
		this.classement = classement;
	}
}
