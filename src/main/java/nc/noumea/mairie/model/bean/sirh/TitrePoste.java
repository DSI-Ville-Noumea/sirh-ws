package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_TITRE_POSTE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class TitrePoste {

	@Id
	@Column(name = "ID_TITRE_POSTE")
	private Integer idTitrePoste;

	@Column(name = "LIB_TITRE_POSTE")
	private String libTitrePoste;

	public Integer getIdTitrePoste() {
		return idTitrePoste;
	}

	public void setIdTitrePoste(Integer idTitrePoste) {
		this.idTitrePoste = idTitrePoste;
	}

	public String getLibTitrePoste() {
		return libTitrePoste;
	}

	public void setLibTitrePoste(String libTitrePoste) {
		this.libTitrePoste = libTitrePoste;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TitrePoste other = (TitrePoste) obj;
		if (idTitrePoste == null) {
			if (other.idTitrePoste != null)
				return false;
		} else if (!idTitrePoste.equals(other.idTitrePoste))
			return false;
		if (libTitrePoste == null) {
			if (other.libTitrePoste != null)
				return false;
		} else if (!libTitrePoste.equals(other.libTitrePoste))
			return false;
		return true;
	}
}
