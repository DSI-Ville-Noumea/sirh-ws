package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.sirh.NiveauEtudeFPPK;

@Entity
@Table(name = "NIVEAU_ETUDE_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class NiveauEtudeFP {

	@Id
	private NiveauEtudeFPPK niveauEtudeFPPK;

	public NiveauEtudeFPPK getNiveauEtudeFPPK() {
		return niveauEtudeFPPK;
	}

	public void setNiveauEtudeFPPK(NiveauEtudeFPPK niveauEtudeFPPK) {
		this.niveauEtudeFPPK = niveauEtudeFPPK;
	}
}
