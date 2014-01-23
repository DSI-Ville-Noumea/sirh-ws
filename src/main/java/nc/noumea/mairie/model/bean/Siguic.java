package nc.noumea.mairie.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.SiguicId;

@Entity
@Table(name = "SIGUIC")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Siguic implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SiguicId id;

	@Column(name = "LIGUIC", columnDefinition = "char")
	private String liGuic;

	public Siguic() {
	}

	public Siguic(Integer codeBanque, Integer codeGuichet) {
		this.id = new SiguicId(codeBanque, codeGuichet);
	}

	public SiguicId getId() {
		return id;
	}

	public void setId(SiguicId id) {
		this.id = id;
	}

	public String getLiGuic() {
		return liGuic;
	}

	public void setLiGuic(String liGuic) {
		this.liGuic = liGuic;
	}
}
