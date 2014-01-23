package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.SiidmaId;

@Entity
@Table(name = "SIIDMA")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Siidma {

	@EmbeddedId
	private SiidmaId id;

	@NotNull
	@Column(name = "NOMATR", columnDefinition = "numeric")
	private Integer nomatr;

	public SiidmaId getId() {
		return id;
	}

	public void setId(SiidmaId id) {
		this.id = id;
	}

	public Integer getNomatr() {
		return nomatr;
	}

	public void setNomatr(Integer nomatr) {
		this.nomatr = nomatr;
	}

}
