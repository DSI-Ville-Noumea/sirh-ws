package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPTYCO")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Sptyco {

	@Id
	@Column(name = "TYPE2", columnDefinition = "numeric")
	private Integer idType;

	@NotNull
	@Column(name = "LICONG", columnDefinition = "char")
	private String libTypeConge;

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getLibTypeConge() {
		return libTypeConge;
	}

	public void setLibTypeConge(String libTypeConge) {
		this.libTypeConge = libTypeConge;
	}
}
