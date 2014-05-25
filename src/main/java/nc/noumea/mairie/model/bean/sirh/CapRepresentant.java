package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "REPRESENTANT_CAP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class CapRepresentant {

	@EmbeddedId
	private CapRepresentantPK capRepresentantPk;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CAP", insertable = false, updatable = false)
	private Cap cap;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_REPRESENTANT", insertable = false, updatable = false)
	private Representant representant;

	@Column(name = "POSITION")
	private Integer position;

	public CapRepresentantPK getCapRepresentantPk() {
		return capRepresentantPk;
	}

	public void setCapRepresentantPk(CapRepresentantPK capRepresentantPk) {
		this.capRepresentantPk = capRepresentantPk;
	}

	public Cap getCap() {
		return cap;
	}

	public void setCap(Cap cap) {
		this.cap = cap;
	}

	public Representant getRepresentant() {
		return representant;
	}

	public void setRepresentant(Representant representant) {
		this.representant = representant;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
}
