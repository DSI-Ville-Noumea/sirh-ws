package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DelegationFPPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_DELEGATION")
	private Integer idDelegation;

	@Column(name = "ID_FICHE_POSTE")
	private Integer idFichePoste;

	public Integer getIdDelegation() {
		return idDelegation;
	}

	public void setIdDelegation(Integer idDelegation) {
		this.idDelegation = idDelegation;
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}
}
