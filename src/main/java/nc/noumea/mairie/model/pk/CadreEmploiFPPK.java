package nc.noumea.mairie.model.pk;

import java.io.Serializable;

import javax.persistence.Column;

public final class CadreEmploiFPPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_CADRE_EMPLOI")
	private Integer idCadreEmploi;

	@Column(name = "ID_FICHE_POSTE")
	private Integer idFichePoste;

	public Integer getIdCadreEmploi() {
		return idCadreEmploi;
	}

	public void setIdCadreEmploi(Integer idCadreEmploi) {
		this.idCadreEmploi = idCadreEmploi;
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}
}
