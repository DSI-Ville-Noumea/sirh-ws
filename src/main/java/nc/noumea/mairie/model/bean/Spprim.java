package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.SpprimId;

@Entity
@Table(name = "SPPRIM")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spprim {

	@EmbeddedId
	private SpprimId id;

	@Column(name = "DATFIN", columnDefinition = "numeric")
	public Integer datfin;

	@NotNull
	@Column(name = "MTPRI", columnDefinition = "numeric")
	public Integer montantPrime;

	public SpprimId getId() {
		return id;
	}

	public void setId(SpprimId id) {
		this.id = id;
	}

	public Integer getDatfin() {
		return datfin;
	}

	public void setDatfin(Integer datfin) {
		this.datfin = datfin;
	}

	public Integer getMontantPrime() {
		return montantPrime;
	}

	public void setMontantPrime(Integer montantPrime) {
		this.montantPrime = montantPrime;
	}

}
