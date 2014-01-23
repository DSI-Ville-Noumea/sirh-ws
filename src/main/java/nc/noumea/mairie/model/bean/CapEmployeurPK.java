package nc.noumea.mairie.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CapEmployeurPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_CAP")
	private Integer idCap;

	@Column(name = "ID_EMPLOYEUR")
	private Integer idEmployeur;

	public Integer getIdCap() {
		return idCap;
	}

	public void setIdCap(Integer idCap) {
		this.idCap = idCap;
	}

	public Integer getIdEmployeur() {
		return idEmployeur;
	}

	public void setIdEmployeur(Integer idEmployeur) {
		this.idEmployeur = idEmployeur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCap == null) ? 0 : idCap.hashCode());
		result = prime * result + ((idEmployeur == null) ? 0 : idEmployeur.hashCode());
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
		CapEmployeurPK other = (CapEmployeurPK) obj;
		if (idCap == null) {
			if (other.idCap != null)
				return false;
		} else if (!idCap.equals(other.idCap))
			return false;
		if (idEmployeur == null) {
			if (other.idEmployeur != null)
				return false;
		} else if (!idEmployeur.equals(other.idEmployeur))
			return false;
		return true;
	}
}
