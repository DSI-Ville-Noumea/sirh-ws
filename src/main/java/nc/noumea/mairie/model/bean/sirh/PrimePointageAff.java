package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Transient;

import nc.noumea.mairie.model.pk.sirh.PrimePointageAffPK;

@Entity
@Table(name = "PRIME_POINTAGE_AFF")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class PrimePointageAff {

	@Id
	private PrimePointageAffPK primePointageAffPK;

	@ManyToOne
	@JoinColumn(name = "ID_AFFECTATION", referencedColumnName = "ID_AFFECTATION", insertable = false, updatable = false)
	private Affectation affectation;

	@Transient
	private String libelle;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public PrimePointageAffPK getPrimePointageAffPK() {
		return primePointageAffPK;
	}

	public void setPrimePointageAffPK(PrimePointageAffPK primePointageAffPK) {
		this.primePointageAffPK = primePointageAffPK;
	}

	public Affectation getAffectation() {
		return affectation;
	}

	public void setAffectation(Affectation affectation) {
		this.affectation = affectation;
	}

}
