package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "R_NIVEAU_ETUDE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class NiveauEtude {

	@Id
	@Column(name = "ID_NIVEAU_ETUDE")
	private Integer idNiveauEtude;

	@NotNull
	@Column(name = "CODE_NIVEAU_ETUDE")
	private String libelleNiveauEtude;

	public Integer getIdNiveauEtude() {
		return idNiveauEtude;
	}

	public void setIdNiveauEtude(Integer idNiveauEtude) {
		this.idNiveauEtude = idNiveauEtude;
	}

	public String getLibelleNiveauEtude() {
		return libelleNiveauEtude;
	}

	public void setLibelleNiveauEtude(String libelleNiveauEtude) {
		this.libelleNiveauEtude = libelleNiveauEtude;
	}
}
