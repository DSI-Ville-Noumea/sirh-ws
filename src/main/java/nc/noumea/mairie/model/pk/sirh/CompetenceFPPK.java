package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;

import javax.persistence.Column;

public final class CompetenceFPPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_COMPETENCE")
	private Integer idCompetence;

	@Column(name = "ID_FICHE_POSTE")
	private Integer idFichePoste;

	public Integer getIdCompetence() {
		return idCompetence;
	}

	public void setIdCompetence(Integer idCompetence) {
		this.idCompetence = idCompetence;
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
		result = prime * result + ((idCompetence == null) ? 0 : idCompetence.hashCode());
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
		CompetenceFPPK other = (CompetenceFPPK) obj;
		if (idCompetence == null) {
			if (other.idCompetence != null)
				return false;
		} else if (!idCompetence.equals(other.idCompetence))
			return false;
		if (idFichePoste == null) {
			if (other.idFichePoste != null)
				return false;
		} else if (!idFichePoste.equals(other.idFichePoste))
			return false;
		return true;
	}
}
