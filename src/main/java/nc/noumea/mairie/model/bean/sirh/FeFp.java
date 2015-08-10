package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.sirh.FeFpPK;

@Entity
@Table(name = "FE_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class FeFp {

	@Id
	private FeFpPK id;

	@Column(name = "FE_PRIMAIRE", columnDefinition = "smallint")
	private Integer fePrimaire;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_FICHE_POSTE", referencedColumnName = "ID_FICHE_POSTE", insertable = false, updatable = false)
	private FichePoste fichePoste;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_FICHE_EMPLOI", referencedColumnName = "ID_FICHE_EMPLOI", insertable = false, updatable = false)
	private FicheEmploi ficheEmploi;

	public FeFpPK getId() {
		return id;
	}

	public void setId(FeFpPK id) {
		this.id = id;
	}

	public Integer getFePrimaire() {
		return fePrimaire;
	}

	public void setFePrimaire(Integer fePrimaire) {
		this.fePrimaire = fePrimaire;
	}

	public FichePoste getFichePoste() {
		return fichePoste;
	}

	public void setFichePoste(FichePoste fichePoste) {
		this.fichePoste = fichePoste;
	}

	public FicheEmploi getFicheEmploi() {
		return ficheEmploi;
	}

	public void setFicheEmploi(FicheEmploi ficheEmploi) {
		this.ficheEmploi = ficheEmploi;
	}
	
}
