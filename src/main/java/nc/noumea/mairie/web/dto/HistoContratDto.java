package nc.noumea.mairie.web.dto;

import java.util.Date;

public class HistoContratDto {
	
	private Integer idAgent;
	private String idTiarhe;
	private String typeContrat;
	private Date dateDebut;
	private Date dateFin;
	private String statut;
	private String motif;
	private String justification;
	private String INM;
	private String IBAN;
	private boolean isDateFinMoinsUnJour = false;
	private String codeGrade;
	
	public HistoContratDto() {
		
	}
	public HistoContratDto(HistoContratDto dto) {
		this.idAgent = dto.getIdAgent();
		this.idTiarhe = dto.getIdTiarhe();
		this.typeContrat = dto.getTypeContrat();
		this.dateDebut = dto.getDateDebut();
		this.dateFin = dto.getDateFin();
		this.statut = dto.getStatut();
		this.motif = dto.getMotif();
		this.justification = dto.getJustification();
		this.INM = dto.getINM();
		this.IBAN = dto.getIBAN();
		this.isDateFinMoinsUnJour = dto.isDateFinMoinsUnJour;
		this.codeGrade = dto.getCodeGrade();
	}
	
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
	public String getTypeContrat() {
		return typeContrat;
	}
	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
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
	public String getCodeGrade() {
		return codeGrade;
	}
	public void setCodeGrade(String codeGrade) {
		this.codeGrade = codeGrade;
	}
	@Override
	public boolean equals(Object obj) {
		HistoContratDto compareObj = (HistoContratDto)obj;
		
		if (compareObj == null || compareObj.getIdAgent() == null)
			return false;
		if (compareObj.typeContrat == null && this.typeContrat != null
				|| compareObj.typeContrat == null && this.typeContrat != null)
			return false;
		if (compareObj.motif == null && this.motif != null
				|| compareObj.motif == null && this.motif != null)
			return false;
		if (compareObj.justification == null && this.justification != null
				|| compareObj.justification == null && this.justification != null)
			return false;
		if (compareObj.idTiarhe == null && this.idTiarhe != null
				|| compareObj.idTiarhe == null && this.idTiarhe != null)
			return false;
		
		return compareObj.idAgent.equals(this.idAgent) 
				&& ((compareObj.idTiarhe == null && this.idTiarhe == null) || compareObj.idTiarhe.equals(this.idTiarhe))
				&& ((compareObj.typeContrat == null && this.typeContrat == null) || compareObj.typeContrat.equals(this.typeContrat))
				&& compareObj.statut.equals(this.statut)
				&& ((compareObj.motif == null && this.motif == null) || compareObj.motif.equals(this.motif))
				&& ((compareObj.justification == null && this.justification == null) || compareObj.justification.equals(this.justification))
				&& compareObj.IBAN.equals(this.IBAN)
				&& compareObj.INM.equals(this.INM)
				&& compareObj.codeGrade.equals(this.codeGrade);
	}
	
}
