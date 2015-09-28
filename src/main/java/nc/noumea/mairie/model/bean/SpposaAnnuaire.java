package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPPOSA_ANNUAIRE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class SpposaAnnuaire {

	@Id
	@NotNull
	@Column(name = "CDPADM", columnDefinition = "char")
	private String cdpAdm;

	public String getCdpAdm() {
		return cdpAdm;
	}

	public void setCdpAdm(String cdpAdm) {
		this.cdpAdm = cdpAdm;
	}

}
