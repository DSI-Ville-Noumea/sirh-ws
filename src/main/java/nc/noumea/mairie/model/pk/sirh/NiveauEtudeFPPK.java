package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NiveauEtudeFPPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_NIVEAU_ETUDE")
	private Integer idNiveauEtude;

	@Column(name = "ID_FICHE_POSTE")
	private Integer idFichePoste;

	public Integer getIdNiveauEtude() {
		return idNiveauEtude;
	}

	public void setIdNiveauEtude(Integer idNiveauEtude) {
		this.idNiveauEtude = idNiveauEtude;
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
		result = prime * result + ((idFichePoste == null) ? 0 : idFichePoste.hashCode());
		result = prime * result + ((idNiveauEtude == null) ? 0 : idNiveauEtude.hashCode());
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
		NiveauEtudeFPPK other = (NiveauEtudeFPPK) obj;
		if (idFichePoste == null) {
			if (other.idFichePoste != null)
				return false;
		} else if (!idFichePoste.equals(other.idFichePoste))
			return false;
		if (idNiveauEtude == null) {
			if (other.idNiveauEtude != null)
				return false;
		} else if (!idNiveauEtude.equals(other.idNiveauEtude))
			return false;
		return true;
	}
}
