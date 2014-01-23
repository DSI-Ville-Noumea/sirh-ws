package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_TYPE_DELEGATION")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class TypeDelegation {

	@Id
	@Column(name = "ID_TYPE_DELEGATION")
	private Integer idTypeDelegation;

	@Column(name = "LIB_TYPE_DELEGATION")
	private String libTypeDelegation;

	public Integer getIdTypeDelegation() {
		return idTypeDelegation;
	}

	public void setIdTypeDelegation(Integer idTypeDelegation) {
		this.idTypeDelegation = idTypeDelegation;
	}

	public String getLibTypeDelegation() {
		return libTypeDelegation;
	}

	public void setLibTypeDelegation(String libTypeDelegation) {
		this.libTypeDelegation = libTypeDelegation;
	}
}
