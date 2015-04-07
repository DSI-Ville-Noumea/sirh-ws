package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "R_TYPE_REPRESENTANT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class TypeRepresentant {

	@Id
	@Column(name = "ID_TYPE_REPRESENTANT")
	private Integer idTypeRepresentant;

	@NotNull
	@Column(name = "LIB_TYPE_REPRESENTANT")
	private String libTypeRepresentant;

	public Integer getIdTypeRepresentant() {
		return idTypeRepresentant;
	}

	public void setIdTypeRepresentant(Integer idTypeRepresentant) {
		this.idTypeRepresentant = idTypeRepresentant;
	}

	public String getLibTypeRepresentant() {
		return libTypeRepresentant;
	}

	public void setLibTypeRepresentant(String libTypeRepresentant) {
		this.libTypeRepresentant = libTypeRepresentant;
	}
}
