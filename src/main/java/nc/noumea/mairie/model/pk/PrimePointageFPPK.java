package nc.noumea.mairie.model.pk;

import java.io.Serializable;

import javax.persistence.Column;

public final class PrimePointageFPPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_RUBRIQUE")
	private Integer numRubrique;

	public Integer getNumRubrique() {
		return numRubrique;
	}

	public void setNumRubrique(Integer numRubrique) {
		this.numRubrique = numRubrique;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numRubrique == null) ? 0 : numRubrique.hashCode());
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
		PrimePointageFPPK other = (PrimePointageFPPK) obj;
		if (numRubrique == null) {
			if (other.numRubrique != null)
				return false;
		} else if (!numRubrique.equals(other.numRubrique))
			return false;
		return true;
	}
}
