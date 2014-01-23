package nc.noumea.mairie.model.bean;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SICOMM")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Sicomm {

	@Id
	@Column(name = "CODCOM")
	private BigDecimal codeCommune;

	@NotNull
	@Column(name = "LIBVIL", columnDefinition = "char")
	private String libVil;

	public BigDecimal getCodeCommune() {
		return codeCommune;
	}

	public void setCodeCommune(BigDecimal codeCommune) {
		this.codeCommune = codeCommune;
	}

	public String getLibVil() {
		return libVil;
	}

	public void setLibVil(String libVil) {
		this.libVil = libVil;
	}
}
