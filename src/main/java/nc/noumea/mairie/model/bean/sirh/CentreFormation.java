package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_CENTRE_FORMATION")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class CentreFormation {

	@Id
	@Column(name = "ID_CENTRE_FORMATION")
	private Integer idCentreFormation;
	
	@Column(name = "LIB_CENTRE_FORMATION")
	private String libCentreFormation;

	public Integer getIdCentreFormation() {
		return idCentreFormation;
	}

	public void setIdCentreFormation(Integer idCentreFormation) {
		this.idCentreFormation = idCentreFormation;
	}

	public String getLibCentreFormation() {
		return libCentreFormation;
	}

	public void setLibCentreFormation(String libCentreFormation) {
		this.libCentreFormation = libCentreFormation;
	}
	
	
}
