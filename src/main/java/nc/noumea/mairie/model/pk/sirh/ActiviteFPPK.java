package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;

import javax.persistence.Column;

public final class ActiviteFPPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_ACTIVITE")
	private Integer idActivite;

	@Column(name = "ID_FICHE_POSTE")
	private Integer idFichePoste;

	public Integer getIdActivite() {
		return idActivite;
	}

	public void setIdActivite(Integer idActivite) {
		this.idActivite = idActivite;
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idActivite == null) ? 0 : idActivite.hashCode());
		result = prime * result + ((idFichePoste == null) ? 0 : idFichePoste.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActiviteFPPK other = (ActiviteFPPK) obj;
		if (idActivite == null) {
			if (other.idActivite != null)
				return false;
		} else if (!idActivite.equals(other.idActivite))
			return false;
		if (idFichePoste == null) {
			if (other.idFichePoste != null)
				return false;
		} else if (!idFichePoste.equals(other.idFichePoste))
			return false;
		return true;
	}
}
