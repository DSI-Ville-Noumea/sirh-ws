package nc.noumea.mairie.web.dto.avancements;

import java.util.Date;

public class ChangementClasseItemDto {

	private String nom;
	private String prenom;
	private String employeur;
	private String corps;
	private String classeActuelle;
	private String echelonActuel;
	private Date dateEffet;
	private String ACC;
	private String classeProposee;
	private String echelonPropose;
	private Date dateProposee;
	private String avisEmployeur;

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

	public String getEmployeur() {
		return employeur;
	}

	public void setEmployeur(String employeur) {
		this.employeur = employeur;
	}

	public String getCorps() {
		return corps;
	}

	public void setCorps(String corps) {
		this.corps = corps;
	}

	public String getClasseActuelle() {
		return classeActuelle;
	}

	public void setClasseActuelle(String classeActuelle) {
		this.classeActuelle = classeActuelle;
	}

	public String getEchelonActuel() {
		return echelonActuel;
	}

	public void setEchelonActuel(String echelonActuel) {
		this.echelonActuel = echelonActuel;
	}

	public Date getDateEffet() {
		return dateEffet;
	}

	public void setDateEffet(Date dateEffet) {
		this.dateEffet = dateEffet;
	}

	public String getACC() {
		return ACC;
	}

	public void setACC(String aCC) {
		ACC = aCC;
	}

	public String getClasseProposee() {
		return classeProposee;
	}

	public void setClasseProposee(String classeProposee) {
		this.classeProposee = classeProposee;
	}

	public String getEchelonPropose() {
		return echelonPropose;
	}

	public void setEchelonPropose(String echelonPropose) {
		this.echelonPropose = echelonPropose;
	}

	public Date getDateProposee() {
		return dateProposee;
	}

	public void setDateProposee(Date dateProposee) {
		this.dateProposee = dateProposee;
	}

	public String getAvisEmployeur() {
		return avisEmployeur;
	}

	public void setAvisEmployeur(String avisEmployeur) {
		this.avisEmployeur = avisEmployeur;
	}
}
