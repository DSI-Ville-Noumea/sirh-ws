package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.sirh.AccueilRh;

public class AccueilRhDto {

	private Integer idAccueilKiosque;
	private String texteAccueilKiosque;

	public AccueilRhDto() {
	}

	public AccueilRhDto(AccueilRh c) {
		this.idAccueilKiosque = c.getIdAccueilKiosque();
		this.texteAccueilKiosque = c.getTexteAccueilKiosque();

	}

	public Integer getIdAccueilKiosque() {
		return idAccueilKiosque;
	}

	public void setIdAccueilKiosque(Integer idAccueilKiosque) {
		this.idAccueilKiosque = idAccueilKiosque;
	}

	public String getTexteAccueilKiosque() {
		return texteAccueilKiosque;
	}

	public void setTexteAccueilKiosque(String texteAccueilKiosque) {
		this.texteAccueilKiosque = texteAccueilKiosque;
	}

}
