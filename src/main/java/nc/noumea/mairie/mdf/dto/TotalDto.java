package nc.noumea.mairie.mdf.dto;

import javax.persistence.Column;
import javax.persistence.Id;

public class TotalDto {

	private String dateFichier;
	private Integer typeEnregistrement;
	private Integer nombreLignesDetail;
	private String codeCollectivité;
	
	public String getDateFichier() {
		return dateFichier;
	}
	public void setDateFichier(String dateFichier) {
		this.dateFichier = dateFichier;
	}
	public Integer getTypeEnregistrement() {
		return typeEnregistrement;
	}
	public void setTypeEnregistrement(Integer typeEnregistrement) {
		this.typeEnregistrement = typeEnregistrement;
	}
	public Integer getNombreLignesDetail() {
		return nombreLignesDetail;
	}
	public void setNombreLignesDetail(Integer nombreLignesDetail) {
		this.nombreLignesDetail = nombreLignesDetail;
	}
	public String getCodeCollectivité() {
		return codeCollectivité;
	}
	public void setCodeCollectivité(String codeCollectivité) {
		this.codeCollectivité = codeCollectivité;
	}
	
	
}
