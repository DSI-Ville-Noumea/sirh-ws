package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Embeddable
public class AutreAdministrationAgentPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name = "ID_AUTRE_ADMIN")
	private Integer idAutreAdmin;

	@NotNull
	@Column(name = "ID_AGENT")
	private Integer idAgent;
	
	@NotNull
	@Column(name = "DATE_ENTREE")
	@Temporal(TemporalType.DATE)
	private Date dateEntree;

	public Integer getIdAutreAdmin() {
		return idAutreAdmin;
	}

	public void setIdAutreAdmin(Integer idAutreAdmin) {
		this.idAutreAdmin = idAutreAdmin;
	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}

	public Date getDateEntree() {
		return dateEntree;
	}

	public void setDateEntree(Date dateEntree) {
		this.dateEntree = dateEntree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateEntree == null) ? 0 : dateEntree.hashCode());
		result = prime * result + ((idAgent == null) ? 0 : idAgent.hashCode());
		result = prime * result
				+ ((idAutreAdmin == null) ? 0 : idAutreAdmin.hashCode());
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
		AutreAdministrationAgentPK other = (AutreAdministrationAgentPK) obj;
		if (dateEntree == null) {
			if (other.dateEntree != null)
				return false;
		} else if (!dateEntree.equals(other.dateEntree))
			return false;
		if (idAgent == null) {
			if (other.idAgent != null)
				return false;
		} else if (!idAgent.equals(other.idAgent))
			return false;
		if (idAutreAdmin == null) {
			if (other.idAutreAdmin != null)
				return false;
		} else if (!idAutreAdmin.equals(other.idAutreAdmin))
			return false;
		return true;
	}
	
	
}
