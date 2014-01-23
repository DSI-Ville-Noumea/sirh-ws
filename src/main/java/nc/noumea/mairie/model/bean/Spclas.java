package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPCLAS")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spclas {

	@Id
	@Column(name = "CODCLA", columnDefinition = "char")
	private String codcla;

	@NotNull
	@Column(name = "LIBCLA", columnDefinition = "char")
	private String libCla;

	public String getCodcla() {
		return codcla;
	}

	public void setCodcla(String codcla) {
		this.codcla = codcla;
	}

	public String getLibCla() {
		return libCla;
	}

	public void setLibCla(String libCla) {
		this.libCla = libCla;
	}
}
