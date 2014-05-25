package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "COMPETENCE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Competence {

	@Id
	@Column(name = "ID_COMPETENCE")
	private Integer idCompetence;

	@NotNull
	@Column(name = "NOM_COMPETENCE")
	private String nomCompetence;

	@OneToOne
	@JoinColumn(name = "ID_TYPE_COMPETENCE", referencedColumnName = "ID_TYPE_COMPETENCE")
	private TypeCompetence typeCompetence;

	public Integer getIdCompetence() {
		return idCompetence;
	}

	public void setIdCompetence(Integer idCompetence) {
		this.idCompetence = idCompetence;
	}

	public String getNomCompetence() {
		return nomCompetence;
	}

	public void setNomCompetence(String nomCompetence) {
		this.nomCompetence = nomCompetence;
	}

	public TypeCompetence getTypeCompetence() {
		return typeCompetence;
	}

	public void setTypeCompetence(TypeCompetence typeCompetence) {
		this.typeCompetence = typeCompetence;
	}

}
