package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class DroitsId implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "ID_GROUPE")
	private Integer idGroupe;
	
	@NotNull
	@Column(name = "ID_ELEMENT")
	private Integer idElement;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public DroitsId() {
	}
	

	
	public Integer getIdGroupe() {
		return idGroupe;
	}

	public void setIdGroupe(Integer idGroupe) {
		this.idGroupe = idGroupe;
	}

	public Integer getIdElement() {
		return idElement;
	}

	public void setIdElement(Integer idElement) {
		this.idElement = idElement;
	}

}
