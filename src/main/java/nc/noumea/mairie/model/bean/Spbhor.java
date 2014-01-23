package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPBHOR")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spbhor {

	@Id
	@Column(name = "CDTHOR", columnDefinition = "decimal")
	private String cdThor;

	@NotNull
	@Column(name = "LIBHOR", columnDefinition = "char")
	private String libHor;

	public String getCdThor() {
		return cdThor;
	}

	public void setCdThor(String cdThor) {
		this.cdThor = cdThor;
	}

	public String getLibHor() {
		return libHor;
	}

	public void setLibHor(String libHor) {
		this.libHor = libHor;
	}
}
