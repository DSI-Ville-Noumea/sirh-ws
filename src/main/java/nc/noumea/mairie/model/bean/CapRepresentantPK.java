package nc.noumea.mairie.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CapRepresentantPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_CAP")
	private Integer idCap;

	@Column(name = "ID_REPRESENTANT")
	private Integer idRepresentant;

	public Integer getIdCap() {
		return idCap;
	}

	public void setIdCap(Integer idCap) {
		this.idCap = idCap;
	}

	public Integer getIdRepresentant() {
		return idRepresentant;
	}

	public void setIdRepresentant(Integer idRepresentant) {
		this.idRepresentant = idRepresentant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCap == null) ? 0 : idCap.hashCode());
		result = prime * result + ((idRepresentant == null) ? 0 : idRepresentant.hashCode());
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
		CapRepresentantPK other = (CapRepresentantPK) obj;
		if (idCap == null) {
			if (other.idCap != null)
				return false;
		} else if (!idCap.equals(other.idCap))
			return false;
		if (idRepresentant == null) {
			if (other.idRepresentant != null)
				return false;
		} else if (!idRepresentant.equals(other.idRepresentant))
			return false;
		return true;
	}
}
