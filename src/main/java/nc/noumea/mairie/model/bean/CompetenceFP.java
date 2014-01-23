package nc.noumea.mairie.model.bean;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.CompetenceFPPK;

@Entity
@Table(name = "COMPETENCE_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class CompetenceFP {

	@EmbeddedId
	private CompetenceFPPK competenceFPPK;

	public CompetenceFPPK getCompetenceFPPK() {
		return competenceFPPK;
	}

	public void setCompetenceFPPK(CompetenceFPPK competenceFPPK) {
		this.competenceFPPK = competenceFPPK;
	}
}
