package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "POSIT_DESC")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class PositDesc {

	@Id
	@NotNull
	@Column(name = "POSIT", columnDefinition = "char")
	private String position;

	@NotNull
	@Column(name = "DESCRIPTION")
	private String description;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
