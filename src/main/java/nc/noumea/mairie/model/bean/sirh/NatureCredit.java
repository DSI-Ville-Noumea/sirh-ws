package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "P_NATURE_CREDIT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class NatureCredit {

	@Id
	@Column(name = "ID_NATURE_CREDIT")
	private Integer idNatureCredit;

	@NotNull
	@Column(name = "LIB_NATURE_CREDIT")
	private String libNatureCredit;

	public Integer getIdNatureCredit() {
		return idNatureCredit;
	}

	public void setIdNatureCredit(Integer idNatureCredit) {
		this.idNatureCredit = idNatureCredit;
	}

	public String getLibNatureCredit() {
		return libNatureCredit;
	}

	public void setLibNatureCredit(String libNatureCredit) {
		this.libNatureCredit = libNatureCredit;
	}
}
