package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Embeddable
public final class AutreAdministrationAgentPK implements Serializable {

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
	
	
}
