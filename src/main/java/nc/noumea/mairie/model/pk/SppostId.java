package nc.noumea.mairie.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SppostId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "POANNE", insertable = false, updatable = false, columnDefinition = "numeric")
	private Integer poanne;

	@Column(name = "PONUOR", insertable = false, updatable = false, columnDefinition = "numeric")
	private Integer ponuor;

	@Override
	public String toString() {
		return "SppostId [poanne=" + getPoanne() + ", ponuor=" + getPonuor() + "]";
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public SppostId() {
	}

	public SppostId(Integer poanne, Integer ponuor) {
		this.poanne = poanne;
		this.ponuor = ponuor;
	}

	public Integer getPoanne() {
		return poanne;
	}

	public void setPoanne(Integer poanne) {
		this.poanne = poanne;
	}

	public Integer getPonuor() {
		return ponuor;
	}

	public void setPonuor(Integer ponuor) {
		this.ponuor = ponuor;
	}
}
