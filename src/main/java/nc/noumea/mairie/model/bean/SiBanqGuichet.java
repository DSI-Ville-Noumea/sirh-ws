package nc.noumea.mairie.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.SiguicId;

@Entity
@Table(name = "SIGUICX1")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class SiBanqGuichet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private SiguicId id;

	@Column(name = "LIBANQ", columnDefinition = "char")
	private String liBanque;

	@Column(name = "LIGUIC", columnDefinition = "char")
	private String liGuichet;

	public SiBanqGuichet() {
	}

	public String getLiBanque() {
		if (liBanque == null)
			return "";
		return liBanque.trim();
	}

	public void setLiBanque(String liBanque) {
		this.liBanque = liBanque;
	}

	public String getLiGuichet() {
		if (liGuichet == null)
			return "";
		return liGuichet.trim();
	}

	public void setLiGuichet(String liGuichet) {
		this.liGuichet = liGuichet;
	}

	public SiguicId getId() {
		return id;
	}

	public void setId(SiguicId id) {
		this.id = id;
	}
}
