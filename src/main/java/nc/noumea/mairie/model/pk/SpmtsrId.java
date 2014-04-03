package nc.noumea.mairie.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class SpmtsrId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public SpmtsrId() {
	}
	
	@Column(name = "NOMATR", columnDefinition = "numeric")
	public Integer nomatr;
	
	@NotNull
	@Column(name = "SERVI", columnDefinition = "char")
	public String servi;
	
	@Column(name = "DATDEB", columnDefinition = "numeric")
	public Integer datdeb;
	
	public Integer getNomatr() {
		return nomatr;
	}

	public void setNomatr(Integer nomatr) {
		this.nomatr = nomatr;
	}

	public String getServi() {
		return servi;
	}

	public void setServi(String servi) {
		this.servi = servi;
	}

	public Integer getDatdeb() {
		return datdeb;
	}

	public void setDatdeb(Integer datdeb) {
		this.datdeb = datdeb;
	}
}
