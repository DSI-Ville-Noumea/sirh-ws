package nc.noumea.mairie.model.bean.eae;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.bean.Agent;

@Entity
@Table(name = "EAE")
@PersistenceUnit(unitName = "eaePersistenceUnit")
public class Eae {

	@Id
	@Column(name = "ID_EAE")
	private Integer idEae;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_CAMPAGNE_EAE", referencedColumnName = "ID_CAMPAGNE_EAE")
	private EaeCampagne eaeCampagne;

	@Column(name = "ID_DELEGATAIRE")
	private Integer idAgentDelegataire;

	@OneToMany(mappedBy = "eae", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<EaeEvaluateur> eaeEvaluateurs = new HashSet<EaeEvaluateur>();

	@OneToMany(mappedBy = "eae", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<EaeFichePoste> eaeFichePoste = new HashSet<EaeFichePoste>();

	@OneToOne(mappedBy = "eae", fetch = FetchType.LAZY)
	private EaeEvalue eaeEvalue;

	@OneToOne(mappedBy = "eae", fetch = FetchType.LAZY)
	private EaeEvaluation eaeEvaluation;

	/*
	 * Transient properties (will be populated by AS400 entity manager)
	 */
	@Transient
	private Agent agentEvalue;

	@Transient
	private Agent agentDelegataire;

	public Integer getIdEae() {
		return idEae;
	}

	public void setIdEae(Integer idEae) {
		this.idEae = idEae;
	}

	public EaeCampagne getEaeCampagne() {
		return eaeCampagne;
	}

	public void setEaeCampagne(EaeCampagne eaeCampagne) {
		this.eaeCampagne = eaeCampagne;
	}

	public Integer getIdAgentDelegataire() {
		return idAgentDelegataire;
	}

	public void setIdAgentDelegataire(Integer idAgentDelegataire) {
		this.idAgentDelegataire = idAgentDelegataire;
	}

	public Set<EaeEvaluateur> getEaeEvaluateurs() {
		return eaeEvaluateurs;
	}

	public void setEaeEvaluateurs(Set<EaeEvaluateur> eaeEvaluateurs) {
		this.eaeEvaluateurs = eaeEvaluateurs;
	}

	public EaeEvalue getEaeEvalue() {
		return eaeEvalue;
	}

	public void setEaeEvalue(EaeEvalue eaeEvalue) {
		this.eaeEvalue = eaeEvalue;
	}

	public EaeEvaluation getEaeEvaluation() {
		return eaeEvaluation;
	}

	public void setEaeEvaluation(EaeEvaluation eaeEvaluation) {
		this.eaeEvaluation = eaeEvaluation;
	}

	public Agent getAgentEvalue() {
		return agentEvalue;
	}

	public void setAgentEvalue(Agent agentEvalue) {
		this.agentEvalue = agentEvalue;
	}

	public Agent getAgentDelegataire() {
		return agentDelegataire;
	}

	public void setAgentDelegataire(Agent agentDelegataire) {
		this.agentDelegataire = agentDelegataire;
	}

	public Set<EaeFichePoste> getEaeFichePoste() {
		return eaeFichePoste;
	}

	public void setEaeFichePoste(Set<EaeFichePoste> eaeFichePoste) {
		this.eaeFichePoste = eaeFichePoste;
	}
}
