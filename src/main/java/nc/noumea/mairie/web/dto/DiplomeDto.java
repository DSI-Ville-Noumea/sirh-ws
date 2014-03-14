package nc.noumea.mairie.web.dto;

import java.util.Date;

import nc.noumea.mairie.model.bean.DiplomeAgent;

public class DiplomeDto {
	
	private Integer idDiplome;
	private Date dateObtention;
	private String LibTitreDiplome;
	private String LibSpeDiplome;
	
	public DiplomeDto(DiplomeAgent a) {
		this.dateObtention = a.getDateObtention();
		this.idDiplome = a.getIdDiplome();
		if(null != a.getTitreDiplome()) {
			this.LibTitreDiplome = a.getTitreDiplome().getLibTitreDiplome();
		}
		if(null != a.getSpecialiteDiplome()) {
			this.LibSpeDiplome = a.getSpecialiteDiplome().getLibSpeDiplome();
		}
	}
	
	public Integer getIdDiplome() {
		return idDiplome;
	}
	public void setIdDiplome(Integer idDiplome) {
		this.idDiplome = idDiplome;
	}
	public Date getDateObtention() {
		return dateObtention;
	}
	public void setDateObtention(Date dateObtention) {
		this.dateObtention = dateObtention;
	}
	public String getLibTitreDiplome() {
		return LibTitreDiplome;
	}
	public void setLibTitreDiplome(String libTitreDiplome) {
		LibTitreDiplome = libTitreDiplome;
	}
	public String getLibSpeDiplome() {
		return LibSpeDiplome;
	}
	public void setLibSpeDiplome(String libSpeDiplome) {
		LibSpeDiplome = libSpeDiplome;
	}
	
}
