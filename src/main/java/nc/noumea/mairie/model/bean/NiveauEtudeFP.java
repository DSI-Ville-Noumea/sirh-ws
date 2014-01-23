package nc.noumea.mairie.model.bean;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.NiveauEtudeFPPK;

@Entity
@Table(name = "NIVEAU_ETUDE_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class NiveauEtudeFP {

	@EmbeddedId
	private NiveauEtudeFPPK niveauEtudeFPPK;

	public NiveauEtudeFPPK getNiveauEtudeFPPK() {
		return niveauEtudeFPPK;
	}

	public void setNiveauEtudeFPPK(NiveauEtudeFPPK niveauEtudeFPPK) {
		this.niveauEtudeFPPK = niveauEtudeFPPK;
	}
}
