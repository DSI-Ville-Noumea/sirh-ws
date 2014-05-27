package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.sirh.FichePoste;

public class TitrePosteDto {

	private String libTitrePoste;

	public TitrePosteDto() {
		super();
	}

	public TitrePosteDto(FichePoste fichePoste) {
		this.libTitrePoste = fichePoste.getTitrePoste().getLibTitrePoste();
	}

	public String getLibTitrePoste() {
		return libTitrePoste;
	}

	public void setLibTitrePoste(String libTitrePoste) {
		this.libTitrePoste = libTitrePoste;
	}

}
