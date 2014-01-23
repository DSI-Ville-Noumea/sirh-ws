package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SILIEU")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Silieu {
	@Id
	@Column(name = "CDLIEU", columnDefinition = "decimal")
	private Long codeLieu;

	@NotNull
	@Column(name = "LILIEU", columnDefinition = "char")
	private String libelleLieu;

	public Long getCodeLieu() {
		return codeLieu;
	}

	public void setCodeLieu(Long codeLieu) {
		this.codeLieu = codeLieu;
	}

	public String getLibelleLieu() {
		return libelleLieu;
	}

	public void setLibelleLieu(String libelleLieu) {
		this.libelleLieu = libelleLieu;
	}
}
