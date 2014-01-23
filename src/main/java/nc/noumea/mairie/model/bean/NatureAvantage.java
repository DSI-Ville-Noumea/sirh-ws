package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_NATURE_AVANTAGE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class NatureAvantage {

	@Id
	@Column(name = "ID_NATURE_AVANTAGE")
	private Integer idNatureAvantage;

	@Column(name = "LIB_NATURE_AVANTAGE")
	private String libNatureAvantage;

	public Integer getIdNatureAvantage() {
		return idNatureAvantage;
	}

	public void setIdNatureAvantage(Integer idNatureAvantage) {
		this.idNatureAvantage = idNatureAvantage;
	}

	public String getLibNatureAvantage() {
		return libNatureAvantage;
	}

	public void setLibNatureAvantage(String libNatureAvantage) {
		this.libNatureAvantage = libNatureAvantage;
	}
}
