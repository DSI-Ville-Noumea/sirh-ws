package nc.noumea.mairie.web.dto;

import java.util.Date;

import nc.noumea.mairie.model.bean.sirh.ParentEnfant;
import nc.noumea.mairie.service.ISivietService;

public class EnfantDto {

	/*
	 * POUR LE PROJETS KIOSQUE J2EE
	 */

	private Date dateNaissance;
	private String aCharge;
	private String nom;
	private String prenom;
	private String sexe;
	private String lieuNaissance;

	public EnfantDto(ParentEnfant pe, ISivietService sivietService) {
		super();
		if (pe != null) {
			this.dateNaissance = pe.getEnfant().getDateNaissance();
			this.aCharge = pe.getEnfantACharge();
			this.nom = pe.getEnfant().getNom();
			this.prenom = pe.getEnfant().getPrenom();
			this.sexe = pe.getEnfant().getSexe();
			this.lieuNaissance = pe.getEnfant().getLieuNaissance(sivietService);
		}
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getaCharge() {
		return aCharge;
	}

	public void setaCharge(String aCharge) {
		this.aCharge = aCharge;
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

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

}
