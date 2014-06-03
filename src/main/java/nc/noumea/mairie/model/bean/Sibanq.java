package nc.noumea.mairie.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "SIBANQ")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Sibanq implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CDBANQ", columnDefinition = "decimal")
	private Integer idBanque;

	@Column(name = "LIBANQ", columnDefinition = "char")
	private String liBanque;

	public Sibanq() {
	}

	public Integer getIdBanque() {
		return idBanque;
	}

	public void setIdBanque(Integer idBanque) {
		this.idBanque = idBanque;
	}

	public String getLiBanque() {
		return liBanque;
	}

	public void setLiBanque(String liBanque) {
		this.liBanque = liBanque;
	}
}
