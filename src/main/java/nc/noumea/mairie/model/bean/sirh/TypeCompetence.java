package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "R_TYPE_COMPETENCE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class TypeCompetence {

	@Id
	@Column(name = "ID_TYPE_COMPETENCE")
	private Integer idTypeCompetence;

	@NotNull
	@Column(name = "LIB_TYPE_COMPETENCE")
	private String libTypeCompetence;

	public Integer getIdTypeCompetence() {
		return idTypeCompetence;
	}

	public void setIdTypeCompetence(Integer idTypeCompetence) {
		this.idTypeCompetence = idTypeCompetence;
	}

	public String getLibTypeCompetence() {
		return libTypeCompetence;
	}

	public void setLibTypeCompetence(String libTypeCompetence) {
		this.libTypeCompetence = libTypeCompetence;
	}
}
