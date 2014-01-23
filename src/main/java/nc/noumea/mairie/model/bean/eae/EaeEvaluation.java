package nc.noumea.mairie.model.bean.eae;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EAE_EVALUATION")
@PersistenceUnit(unitName = "eaePersistenceUnit")
public class EaeEvaluation {

	@Id
	@SequenceGenerator(name = "eaeEvaluationGen", sequenceName = "EAE_S_EVALUATION")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "eaeEvaluationGen")
	@Column(name = "ID_EAE_EVALUATION")
	private Integer idEaeEvaluation;

	@Column(name = "AVIS_SHD")
	private String avisShd;

	@OneToOne
	@JoinColumn(name = "ID_EAE")
	private Eae eae;

	public Integer getIdEaeEvaluation() {
		return idEaeEvaluation;
	}

	public void setIdEaeEvaluation(Integer idEaeEvaluation) {
		this.idEaeEvaluation = idEaeEvaluation;
	}

	public String getAvisShd() {
		return avisShd;
	}

	public void setAvisShd(String avisShd) {
		this.avisShd = avisShd;
	}

	public Eae getEae() {
		return eae;
	}

	public void setEae(Eae eae) {
		this.eae = eae;
	}

}
