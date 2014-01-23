package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "R_SITUATION_FAMILIALE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class SituationFamiliale {

	@Id
	@Column(name = "ID_SITUATION")
	private Integer idSituationFamiliale;

	@NotNull
	@Column(name = "LIB_SITUATION")
	private String libSituationFamiliale;

	public Integer getIdSituationFamiliale() {
		return idSituationFamiliale;
	}

	public void setIdSituationFamiliale(Integer idSituationFamiliale) {
		this.idSituationFamiliale = idSituationFamiliale;
	}

	public String getLibSituationFamiliale() {
		return libSituationFamiliale;
	}

	public void setLibSituationFamiliale(String libSituationFamiliale) {
		this.libSituationFamiliale = libSituationFamiliale;
	}
}
