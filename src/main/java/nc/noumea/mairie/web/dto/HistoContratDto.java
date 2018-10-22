package nc.noumea.mairie.web.dto;

import java.util.Date;

public class HistoContratDto {
	
	private Integer idAgent;
	private String idTiarhe;
	private String typeContratCarriere;
	private String typeContratContrat;
	private Date dateDebut;
	private Date dateFin;
	private String statut;
	private String motif;
	private String justification;
	private String INM;
	private String IBAN;
	private boolean isDateFinMoinsUnJour = false;
	
	public Integer getIdAgent() {
		return idAgent;
	}
	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}
	public String getIdTiarhe() {
		return idTiarhe;
	}
	public void setIdTiarhe(String idTiarhe) {
		this.idTiarhe = idTiarhe;
	}
	public String getTypeContratCarriere() {
		return typeContratCarriere;
	}
	public void setTypeContratCarriere(String typeContratCarriere) {
		this.typeContratCarriere = typeContratCarriere;
	}
	public String getTypeContratContrat() {
		return typeContratContrat;
	}
	public void setTypeContratContrat(String typeContratContrat) {
		this.typeContratContrat = typeContratContrat;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public String getINM() {
		return INM;
	}
	public void setINM(String iNM) {
		INM = iNM;
	}
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	public boolean isDateFinMoinsUnJour() {
		return isDateFinMoinsUnJour;
	}
	public void setDateFinMoinsUnJour(boolean isDateFinMoinsUnJour) {
		this.isDateFinMoinsUnJour = isDateFinMoinsUnJour;
	}
	
	@Override
	public boolean equals(Object obj) {
		HistoContratDto compareObj = (HistoContratDto)obj;
		
		if (compareObj == null || compareObj.getIdAgent() == null)
			return false;
		
		return compareObj.idAgent.equals(this.idAgent) 
				&& compareObj.idTiarhe.equals(this.idTiarhe)
				&& compareObj.typeContratCarriere.equals(this.typeContratCarriere)
				&& compareObj.typeContratContrat.equals(this.typeContratContrat)
				&& compareObj.statut.equals(this.statut)
				&& compareObj.motif.equals(this.motif)
				&& compareObj.justification.equals(this.justification)
				&& compareObj.IBAN.equals(this.IBAN)
				&& compareObj.INM.equals(this.INM);
	}
	
}
