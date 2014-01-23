package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPECHE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Speche {

	@Id
	@Column(name = "CODECH", columnDefinition = "char")
	private String codEch;

	@NotNull
	@Column(name = "LIBECH", columnDefinition = "char")
	private String libEch;

	public String getCodEch() {
		return codEch;
	}

	public void setCodEch(String codEch) {
		this.codEch = codEch;
	}

	public String getLibEch() {
		return libEch;
	}

	public void setLibEch(String libEch) {
		this.libEch = libEch;
	}
}
