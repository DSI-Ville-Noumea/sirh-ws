package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_TYPE_REG_INDEMN")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class TypeRegimeIndemnitaire {

	@Id
	@Column(name = "ID_TYPE_REG_INDEMN")
	private Integer idTypeRegimeIndemnitaire;

	@Column(name = "LIB_TYPE_REG_INDEMN")
	private String libTypeRegimeIndemnitaire;

	public Integer getIdTypeRegimeIndemnitaire() {
		return idTypeRegimeIndemnitaire;
	}

	public void setIdTypeRegimeIndemnitaire(Integer idTypeRegimeIndemnitaire) {
		this.idTypeRegimeIndemnitaire = idTypeRegimeIndemnitaire;
	}

	public String getLibTypeRegimeIndemnitaire() {
		return libTypeRegimeIndemnitaire;
	}

	public void setLibTypeRegimeIndemnitaire(String libTypeRegimeIndemnitaire) {
		this.libTypeRegimeIndemnitaire = libTypeRegimeIndemnitaire;
	}
}
