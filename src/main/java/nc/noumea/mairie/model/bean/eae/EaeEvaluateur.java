package nc.noumea.mairie.model.bean.eae;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import nc.noumea.mairie.model.bean.Agent;

@Entity
@Table(name = "EAE_EVALUATEUR")
@PersistenceUnit(unitName = "eaePersistenceUnit")
public class EaeEvaluateur {

	@Id
	@SequenceGenerator(name = "eaeEvaluateurGen", sequenceName = "EAE_S_EVALUATEUR")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "eaeEvaluateurGen")
	@Column(name = "ID_EAE_EVALUATEUR")
	private Integer idEaeEvaluateur;

	@Column(name = "ID_AGENT")
	private int idAgent;

	@ManyToOne
	@JoinColumn(name = "ID_EAE", referencedColumnName = "ID_EAE")
	private Eae eae;

	/*
	 * Transient properties (will be populated by AS400 entity manager)
	 */
	@Transient
	private Agent agent;

	public Integer getIdEaeEvaluateur() {
		return idEaeEvaluateur;
	}

	public void setIdEaeEvaluateur(Integer idEaeEvaluateur) {
		this.idEaeEvaluateur = idEaeEvaluateur;
	}

	public int getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(int idAgent) {
		this.idAgent = idAgent;
	}

	public Eae getEae() {
		return eae;
	}

	public void setEae(Eae eae) {
		this.eae = eae;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}
}
