package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.sirh.AvantageNatureFPPK;

@Entity
@Table(name = "AVANTAGE_NATURE_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class AvantageNatureFP {

	@Id
	private AvantageNatureFPPK avantageNaturePK;

	public AvantageNatureFPPK getAvantageNaturePK() {
		return avantageNaturePK;
	}

	public void setAvantageNaturePK(AvantageNatureFPPK avantageNaturePK) {
		this.avantageNaturePK = avantageNaturePK;
	}
}
