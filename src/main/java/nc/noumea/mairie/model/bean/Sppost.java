package nc.noumea.mairie.model.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.SppostId;

@Entity
@Table(name = "SPPOST")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Sppost {

	@Override
	public String toString() {
		return "Sppost [id=" + id + "]";
	}

	@Id
	private SppostId id;

	public Sppost() {
	}

	public Sppost(Integer poanne, Integer ponuor) {
		this.id = new SppostId(poanne, ponuor);
	}

}
