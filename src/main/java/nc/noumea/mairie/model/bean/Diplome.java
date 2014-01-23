package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_DIPLOME_GENERIQUE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Diplome {

	@Id
	@Column(name = "ID_DIPLOME_GENERIQUE")
	private Integer idDiplomeGenerique;

	@Column(name = "LIB_DIPLOME_GENERIQUE")
	private String libDiplomen;

	public Integer getIdDiplomeGenerique() {
		return idDiplomeGenerique;
	}

	public void setIdDiplomeGenerique(Integer idDiplomeGenerique) {
		this.idDiplomeGenerique = idDiplomeGenerique;
	}

	public String getLibDiplomen() {
		return libDiplomen;
	}

	public void setLibDiplomen(String libDiplomen) {
		this.libDiplomen = libDiplomen;
	}
}
