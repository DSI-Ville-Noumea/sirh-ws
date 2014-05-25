package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public final class ParentEnfantPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_AGENT")
	private Integer idAgent;

	@Column(name = "ID_ENFANT")
	private Integer idEnfant;

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}

	public Integer getIdEnfant() {
		return idEnfant;
	}

	public void setIdEnfant(Integer idEnfant) {
		this.idEnfant = idEnfant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAgent == null) ? 0 : idAgent.hashCode());
		result = prime * result + ((idEnfant == null) ? 0 : idEnfant.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParentEnfantPK other = (ParentEnfantPK) obj;
		if (idAgent == null) {
			if (other.idAgent != null)
				return false;
		} else if (!idAgent.equals(other.idAgent))
			return false;
		if (idEnfant == null) {
			if (other.idEnfant != null)
				return false;
		} else if (!idEnfant.equals(other.idEnfant))
			return false;
		return true;
	}
}
