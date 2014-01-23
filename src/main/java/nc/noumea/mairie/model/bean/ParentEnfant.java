package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.ParentEnfantPK;

@Entity
@Table(name = "PARENT_ENFANT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class ParentEnfant {

	@EmbeddedId
	private ParentEnfantPK parentEnfantPK;

	@OneToOne
	@JoinColumn(name = "ID_AGENT", referencedColumnName = "ID_AGENT", insertable = false, updatable = false)
	private Agent parent;

	@OneToOne
	@JoinColumn(name = "ID_ENFANT", referencedColumnName = "ID_ENFANT", insertable = false, updatable = false)
	private Enfant enfant;

	@Column(name = "ENFANT_A_CHARGE")
	private Boolean enfantACharge;

	public String getEnfantACharge() {
		if (this.enfantACharge == true) {
			return "oui";
		}
		return "non";
	}

	public ParentEnfantPK getParentEnfantPK() {
		return parentEnfantPK;
	}

	public void setParentEnfantPK(ParentEnfantPK parentEnfantPK) {
		this.parentEnfantPK = parentEnfantPK;
	}

	public Agent getParent() {
		return parent;
	}

	public void setParent(Agent parent) {
		this.parent = parent;
	}

	public Enfant getEnfant() {
		return enfant;
	}

	public void setEnfant(Enfant enfant) {
		this.enfant = enfant;
	}

	public void setEnfantACharge(Boolean enfantACharge) {
		this.enfantACharge = enfantACharge;
	}
}
