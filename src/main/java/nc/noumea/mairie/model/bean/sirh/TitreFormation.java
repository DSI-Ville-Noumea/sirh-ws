package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_TITRE_FORMATION")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class TitreFormation {

	@Id
	@Column(name = "ID_TITRE_FORMATION")
	public Integer idTitreFormation;
	
	@Column(name = "LIB_TITRE_FORMATION")
	public String libTitreFormation;

	public Integer getIdTitreFormation() {
		return idTitreFormation;
	}

	public void setIdTitreFormation(Integer idTitreFormation) {
		this.idTitreFormation = idTitreFormation;
	}

	public String getLibTitreFormation() {
		return libTitreFormation;
	}

	public void setLibTitreFormation(String libTitreFormation) {
		this.libTitreFormation = libTitreFormation;
	}
	
}
