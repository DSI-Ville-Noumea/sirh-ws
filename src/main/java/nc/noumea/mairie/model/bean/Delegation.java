package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "DELEGATION")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Delegation {

	@Id
	@Column(name = "ID_DELEGATION")
	private Integer idDelegation;

	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TYPE_DELEGATION", referencedColumnName = "ID_TYPE_DELEGATION")
	private TypeDelegation typeDelegation;

	@Column(name = "LIB_DELEGATION")
	private String libDelegation;

	public Integer getIdDelegation() {
		return idDelegation;
	}

	public void setIdDelegation(Integer idDelegation) {
		this.idDelegation = idDelegation;
	}

	public TypeDelegation getTypeDelegation() {
		return typeDelegation;
	}

	public void setTypeDelegation(TypeDelegation typeDelegation) {
		this.typeDelegation = typeDelegation;
	}

	public String getLibDelegation() {
		return libDelegation;
	}

	public void setLibDelegation(String libDelegation) {
		this.libDelegation = libDelegation;
	}
}
