package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AvantageNatureFPPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_AVANTAGE")
	private Integer idAvantage;

	@Column(name = "ID_FICHE_POSTE")
	private Integer idFichePoste;

	public Integer getIdAvantage() {
		return idAvantage;
	}

	public void setIdAvantage(Integer idAvantage) {
		this.idAvantage = idAvantage;
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}
}
