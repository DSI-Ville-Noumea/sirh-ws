package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RegIndemFPPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_REGIME")
	private Integer idRegime;

	@Column(name = "ID_FICHE_POSTE")
	private Integer idFichePoste;

	public Integer getIdRegime() {
		return idRegime;
	}

	public void setIdRegime(Integer idRegime) {
		this.idRegime = idRegime;
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}
}
