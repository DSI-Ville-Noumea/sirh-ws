package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "SPFILI")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spfili {

	@Id
	@Column(name = "CDFILI", columnDefinition = "char")
	private String cdfili;

	@Column(name = "LIFILI", columnDefinition = "char")
	private String libelleFili;

	public String getCdfili() {
		return cdfili;
	}

	public void setCdfili(String cdfili) {
		this.cdfili = cdfili;
	}

	public String getLibelleFili() {
		return libelleFili;
	}

	public void setLibelleFili(String libelleFili) {
		this.libelleFili = libelleFili;
	}
}
