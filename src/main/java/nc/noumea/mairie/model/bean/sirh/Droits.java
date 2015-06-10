package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "DROITS")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Droits {

	@EmbeddedId
	private DroitsId id;

	@Column(name = "ID_TYPE_DROIT")
	private Integer idTypeDroit;

	public DroitsId getId() {
		return id;
	}

	public void setId(DroitsId id) {
		this.id = id;
	}

	public Integer getIdTypeDroit() {
		return idTypeDroit;
	}

	public void setIdTypeDroit(Integer idTypeDroit) {
		this.idTypeDroit = idTypeDroit;
	}
	
}
