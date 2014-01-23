package nc.noumea.mairie.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.SivietId;

@Entity
@Table(name = "SIVIET")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class SIVIET implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SivietId id;

	@Column(name = "LIBCOP", columnDefinition = "char")
	private String libCop;

	public SIVIET() {
	}

	public SIVIET(Integer codePays, Integer sousCodePays) {
		this.id = new SivietId(codePays, sousCodePays);
	}

	public SivietId getId() {
		return id;
	}

	public void setId(SivietId id) {
		this.id = id;
	}

	public String getLibCop() {
		return libCop;
	}

	public void setLibCop(String libCop) {
		this.libCop = libCop;
	}
}
