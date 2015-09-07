package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.sirh.ActiviteFPPK;

@Entity
@Table(name = "ACTIVITE_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class ActiviteFP {

	@EmbeddedId
	private ActiviteFPPK activiteFPPK;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_FICHE_POSTE", referencedColumnName = "ID_FICHE_POSTE", insertable = false, updatable = false)
	private FichePoste fichePoste;

	@Column(name = "ACTIVITE_PRINCIPALE", columnDefinition = "smallint")
	private Integer activitePrincipale;

	public ActiviteFPPK getActiviteFPPK() {
		return activiteFPPK;
	}

	public void setActiviteFPPK(ActiviteFPPK activiteFPPK) {
		this.activiteFPPK = activiteFPPK;
	}

	public FichePoste getFichePoste() {
		return fichePoste;
	}

	public void setFichePoste(FichePoste fichePoste) {
		this.fichePoste = fichePoste;
	}

	public Integer getActivitePrincipale() {
		return activitePrincipale;
	}

	public void setActivitePrincipale(Integer activitePrincipale) {
		this.activitePrincipale = activitePrincipale;
	}

}
