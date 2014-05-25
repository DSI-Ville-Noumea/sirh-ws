package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.sirh.ActiviteFPPK;

@Entity
@Table(name = "ACTIVITE_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class ActiviteFP {

	@EmbeddedId
	private ActiviteFPPK activiteFPPK;

	public ActiviteFPPK getActiviteFPPK() {
		return activiteFPPK;
	}

	public void setActiviteFPPK(ActiviteFPPK activiteFPPK) {
		this.activiteFPPK = activiteFPPK;
	}

}
