package nc.noumea.mairie.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SivietId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public SivietId() {
	}

	public SivietId(Integer codePays, Integer sousCodePays) {
		this.codePays = codePays;
		this.sousCodePays = sousCodePays;
	}

	@Column(name = "CODPAY", insertable = false, updatable = false, columnDefinition = "numeric")
	private Integer codePays;

	@Column(name = "SCODPA", insertable = false, updatable = false, columnDefinition = "numeric")
	private Integer sousCodePays;

	public Integer getCodePays() {
		return codePays;
	}

	public void setCodePays(Integer codePays) {
		this.codePays = codePays;
	}

	public Integer getSousCodePays() {
		return sousCodePays;
	}

	public void setSousCodePays(Integer sousCodePays) {
		this.sousCodePays = sousCodePays;
	}
}
