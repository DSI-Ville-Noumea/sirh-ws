package nc.noumea.mairie.web.dto;

import java.util.List;

public class GroupeInfoFichePosteDto {

	private Integer nbFDP;
	private String titreFDP;
	private Double tauxETP;
	private List<InfoFichePosteDto> listInfoFichePosteDto;
	
	public Integer getNbFDP() {
		return nbFDP;
	}
	public void setNbFDP(Integer nbFDP) {
		this.nbFDP = nbFDP;
	}
	public String getTitreFDP() {
		return titreFDP;
	}
	public void setTitreFDP(String titreFDP) {
		this.titreFDP = titreFDP;
	}
	public Double getTauxETP() {
		return tauxETP;
	}
	public void setTauxETP(Double tauxETP) {
		this.tauxETP = tauxETP;
	}
	public List<InfoFichePosteDto> getListInfoFichePosteDto() {
		return listInfoFichePosteDto;
	}
	public void setListInfoFichePosteDto(
			List<InfoFichePosteDto> listInfoFichePosteDto) {
		this.listInfoFichePosteDto = listInfoFichePosteDto;
	}
	
}
