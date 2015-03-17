package nc.noumea.mairie.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class SpprimId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public SpprimId() {
	}

	@Column(name = "NOMATR", columnDefinition = "numeric")
	public Integer nomatr;

	@NotNull
	@Column(name = "NORUBR", columnDefinition = "char")
	public Integer norubr;

	@Column(name = "DATDEB", columnDefinition = "numeric")
	public Integer datdeb;

	public Integer getNomatr() {
		return nomatr;
	}

	public void setNomatr(Integer nomatr) {
		this.nomatr = nomatr;
	}

	public Integer getNorubr() {
		return norubr;
	}

	public void setNorubr(Integer norubr) {
		this.norubr = norubr;
	}

	public Integer getDatdeb() {
		return datdeb;
	}

	public void setDatdeb(Integer datdeb) {
		this.datdeb = datdeb;
	}
}
