package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "R_AVIS_CAP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class AvisCap {

	@Id
	@Column(name = "ID_AVIS_CAP")
	private Integer idAvisCap;

	@NotNull
	@Column(name = "LIB_LONG_AVIS_CAP")
	private String libLong;

	public Integer getIdAvisCap() {
		return idAvisCap;
	}

	public void setIdAvisCap(Integer idAvisCap) {
		this.idAvisCap = idAvisCap;
	}

	public String getLibLong() {
		return libLong;
	}

	public void setLibLong(String libLong) {
		this.libLong = libLong;
	}
}
