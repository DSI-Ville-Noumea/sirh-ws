package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEUR_CAP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class CapEmployeur {

	@EmbeddedId
	private CapEmployeurPK capEmployeurPk;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CAP", insertable = false, updatable = false)
	private Cap cap;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_EMPLOYEUR", insertable = false, updatable = false)
	private Employeur employeur;

	@Column(name = "POSITION")
	private Integer position;

	public CapEmployeurPK getCapEmployeurPk() {
		return capEmployeurPk;
	}

	public void setCapEmployeurPk(CapEmployeurPK capEmployeurPk) {
		this.capEmployeurPk = capEmployeurPk;
	}

	public Cap getCap() {
		return cap;
	}

	public void setCap(Cap cap) {
		this.cap = cap;
	}

	public Employeur getEmployeur() {
		return employeur;
	}

	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
}
