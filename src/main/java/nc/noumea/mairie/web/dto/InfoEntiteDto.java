package nc.noumea.mairie.web.dto;

import java.util.ArrayList;
import java.util.List;

public class InfoEntiteDto {

	public InfoEntiteDto() {
		super();
		this.listeInfoFDP = new ArrayList<GroupeInfoFichePosteDto>();
	}

	private Integer idEntite;
	private List<GroupeInfoFichePosteDto> listeInfoFDP;

	public Integer getIdEntite() {
		return idEntite;
	}

	public void setIdEntite(Integer idEntite) {
		this.idEntite = idEntite;
	}

	public List<GroupeInfoFichePosteDto> getListeInfoFDP() {
		return listeInfoFDP;
	}

	public void setListeInfoFDP(List<GroupeInfoFichePosteDto> listeInfoFDP) {
		this.listeInfoFDP = listeInfoFDP;
	}
	
}
