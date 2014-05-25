package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "R_BUDGET")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Budget {

	@Id
	@Column(name = "ID_BUDGET")
	private Integer idBudget;

	@NotNull
	@Column(name = "LIB_BUDGET")
	private String libelleBudget;

	public Integer getIdBudget() {
		return idBudget;
	}

	public void setIdBudget(Integer idBudget) {
		this.idBudget = idBudget;
	}

	public String getLibelleBudget() {
		return libelleBudget;
	}

	public void setLibelleBudget(String libelleBudget) {
		this.libelleBudget = libelleBudget;
	}
}
