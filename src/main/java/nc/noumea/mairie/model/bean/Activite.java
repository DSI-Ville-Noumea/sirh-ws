package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ACTIVITE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Activite {

	@Id
	@Column(name = "ID_ACTIVITE")
	private Integer idActivite;

	@NotNull
	@Column(name = "NOM_ACTIVITE")
	private String nomActivite;

	public Integer getIdActivite() {
		return idActivite;
	}

	public void setIdActivite(Integer idActivite) {
		this.idActivite = idActivite;
	}

	public String getNomActivite() {
		return nomActivite;
	}

	public void setNomActivite(String nomActivite) {
		this.nomActivite = nomActivite;
	}
}
