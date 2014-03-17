package nc.noumea.mairie.model.bean.eae;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Siserv;

@Entity
@Table(name = "EAE_FICHE_POSTE")
@PersistenceUnit(unitName = "eaePersistenceUnit")
public class EaeFichePoste {

	@Id
	@Column(name = "ID_EAE_FICHE_POSTE")
	private Integer idEaeFichePoste;

	@Column(name = "ID_SHD")
	private Integer idAgentShd;

	@OneToOne(optional = false)
	@JoinColumn(name = "ID_EAE", unique = true, nullable = false)
	private Eae eae;

	@NotNull
	@Column(name = "CODE_SERVICE")
	private String codeService;

	@Transient
	private Siserv service;

	@Transient
	private Agent agentShd;

	public Integer getIdEaeFichePoste() {
		return idEaeFichePoste;
	}

	public void setIdEaeFichePoste(Integer idEaeFichePoste) {
		this.idEaeFichePoste = idEaeFichePoste;
	}

	public Integer getIdAgentShd() {
		return idAgentShd;
	}

	public void setIdAgentShd(Integer idAgentShd) {
		this.idAgentShd = idAgentShd;
	}

	public Eae getEae() {
		return eae;
	}

	public void setEae(Eae eae) {
		this.eae = eae;
	}

	public String getCodeService() {
		return codeService;
	}

	public void setCodeService(String codeService) {
		this.codeService = codeService;
	}

	public Siserv getService() {
		return service;
	}

	public void setService(Siserv service) {
		this.service = service;
	}

	public Agent getAgentShd() {
		return agentShd;
	}

	public void setAgentShd(Agent agentShd) {
		this.agentShd = agentShd;
	}
}
