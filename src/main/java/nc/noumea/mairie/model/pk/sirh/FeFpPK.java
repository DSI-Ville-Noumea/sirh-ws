package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class FeFpPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "ID_FICHE_EMPLOI")
	private Integer idFicheEmploi;

	@NotNull
	@Column(name = "ID_FICHE_POSTE")
	private Integer idFichePoste;

	public Integer getIdFicheEmploi() {
		return idFicheEmploi;
	}

	public void setIdFicheEmploi(Integer idFicheEmploi) {
		this.idFicheEmploi = idFicheEmploi;
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}
}
