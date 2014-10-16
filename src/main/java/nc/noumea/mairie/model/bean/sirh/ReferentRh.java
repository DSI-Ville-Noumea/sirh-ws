package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_REFERENT_RH")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class ReferentRh {

	@Id
	@Column(name = "SERVI")
	private String servi;

	@Column(name = "ID_AGENT_REFERENT")
	private Integer idAgentReferent;

	@Column(name = "NUMERO_TELEPHONE")
	private Integer numeroTelephone;

	public String getServi() {
		return servi;
	}

	public void setServi(String servi) {
		this.servi = servi;
	}

	public Integer getIdAgentReferent() {
		return idAgentReferent;
	}

	public void setIdAgentReferent(Integer idAgentReferent) {
		this.idAgentReferent = idAgentReferent;
	}

	public Integer getNumeroTelephone() {
		return numeroTelephone;
	}

	public void setNumeroTelephone(Integer numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
	}
}