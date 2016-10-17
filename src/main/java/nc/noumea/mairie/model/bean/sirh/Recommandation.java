package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "P_RECOMMANDATION")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Recommandation {

	@Id
	@Column(name = "ID_RECOMMANDATION")
	private Integer	idRecommandation;

	@NotNull
	@Column(name = "DESC_RECOMMANDATION")
	private String	description;

	public Integer getIdRecommandation() {
		return idRecommandation;
	}

	public void setIdRecommandation(Integer idRecommandation) {
		this.idRecommandation = idRecommandation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
