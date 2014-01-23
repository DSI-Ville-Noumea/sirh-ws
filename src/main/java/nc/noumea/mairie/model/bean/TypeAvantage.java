package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_TYPE_AVANTAGE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class TypeAvantage {

	@Id
	@Column(name = "ID_TYPE_AVANTAGE")
	private Integer idTypeAvantage;

	@Column(name = "LIB_TYPE_AVANTAGE")
	private String libTypeAvantage;

	public Integer getIdTypeAvantage() {
		return idTypeAvantage;
	}

	public void setIdTypeAvantage(Integer idTypeAvantage) {
		this.idTypeAvantage = idTypeAvantage;
	}

	public String getLibTypeAvantage() {
		return libTypeAvantage;
	}

	public void setLibTypeAvantage(String libTypeAvantage) {
		this.libTypeAvantage = libTypeAvantage;
	}
}
