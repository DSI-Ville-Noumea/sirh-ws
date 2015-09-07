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
	@Column(name = "ID_REFERENT_RH")
	private Integer idReferentRh;

	@Column(name = "ID_AGENT_REFERENT")
	private Integer idAgentReferent;

	@Column(name = "NUMERO_TELEPHONE")
	private Integer numeroTelephone;

	@Column(name = "ID_SERVICE_ADS")
	private Integer idServiceADS;

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

	public Integer getIdReferentRh() {
		return idReferentRh;
	}

	public void setIdReferentRh(Integer idReferentRh) {
		this.idReferentRh = idReferentRh;
	}

	public Integer getIdServiceADS() {
		return idServiceADS;
	}

	public void setIdServiceADS(Integer idServiceADS) {
		this.idServiceADS = idServiceADS;
	}
}
