package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SIVOIE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Sivoie {

	@Id
	@Column(name = "CDVOIE", columnDefinition = "numeric")
	private Integer idVoie;

	@NotNull
	@Column(name = "LIVOIE", columnDefinition = "char")
	private String liVoie;

	public Integer getIdVoie() {
		return idVoie;
	}

	public void setIdVoie(Integer idVoie) {
		this.idVoie = idVoie;
	}

	public String getLiVoie() {
		return liVoie;
	}

	public void setLiVoie(String liVoie) {
		this.liVoie = liVoie;
	}
}
