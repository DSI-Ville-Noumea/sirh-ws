package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.sirh.FeFpPK;

@Entity
@Table(name = "FE_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class FeFp {

	@Id
	private FeFpPK id;

	@Column(name = "FE_PRIMAIRE", columnDefinition = "smallint")
	private Integer fePrimaire;

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
}
