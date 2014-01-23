package nc.noumea.mairie.model.bean;

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
}
