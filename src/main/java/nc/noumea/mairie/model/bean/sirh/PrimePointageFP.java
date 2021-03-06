package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Transient;

import nc.noumea.mairie.model.pk.sirh.PrimePointageFPPK;

@Entity
@Table(name = "PRIME_POINTAGE_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class PrimePointageFP {

	@Id
	private PrimePointageFPPK primePointageFPPK;

	@ManyToOne
	@JoinColumn(name = "ID_FICHE_POSTE", referencedColumnName = "ID_FICHE_POSTE", insertable = false, updatable = false)
	private FichePoste fichePoste;

	@Transient
	private String libelle;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public PrimePointageFPPK getPrimePointageFPPK() {
		return primePointageFPPK;
	}

	public void setPrimePointageFPPK(PrimePointageFPPK primePointageFPPK) {
		this.primePointageFPPK = primePointageFPPK;
	}

	public FichePoste getFichePoste() {
		return fichePoste;
	}

	public void setFichePoste(FichePoste fichePoste) {
		this.fichePoste = fichePoste;
	}

}
