package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.sirh.CompetenceFPPK;

@Entity
@Table(name = "COMPETENCE_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class CompetenceFP {

	@EmbeddedId
	private CompetenceFPPK competenceFPPK;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_FICHE_POSTE", referencedColumnName = "ID_FICHE_POSTE", insertable = false, updatable = false)
	private FichePoste fichePoste;

	public CompetenceFPPK getCompetenceFPPK() {
		return competenceFPPK;
	}

	public void setCompetenceFPPK(CompetenceFPPK competenceFPPK) {
		this.competenceFPPK = competenceFPPK;
	}

	public FichePoste getFichePoste() {
		return fichePoste;
	}

	public void setFichePoste(FichePoste fichePoste) {
		this.fichePoste = fichePoste;
	}
}
