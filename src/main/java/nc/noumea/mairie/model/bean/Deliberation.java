package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_DELIBERATION")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Deliberation {

	@Id
	@Column(name = "ID_DELIBERATION")
	private Integer idDeliberation;

	@Column(name = "LIB_DELIBERATION")
	private String libDeliberation;

	@Column(name = "TEXTE_CAP")
	private String texteCap;

	public Integer getIdDeliberation() {
		return idDeliberation;
	}

	public void setIdDeliberation(Integer idDeliberation) {
		this.idDeliberation = idDeliberation;
	}

	public String getLibDeliberation() {
		return libDeliberation;
	}

	public void setLibDeliberation(String libDeliberation) {
		this.libDeliberation = libDeliberation;
	}

	public String getTexteCap() {
		return texteCap;
	}

	public void setTexteCap(String texteCap) {
		this.texteCap = texteCap;
	}
}
