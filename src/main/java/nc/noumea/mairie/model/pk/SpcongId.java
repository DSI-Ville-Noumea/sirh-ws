package nc.noumea.mairie.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import nc.noumea.mairie.model.bean.Sptyco;

@Embeddable
public class SpcongId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public SpcongId() {
	}

	public SpcongId(Integer nomatr, Integer dateDeb, Integer rang, Sptyco typConge) {
		this.nomatr = nomatr;
		this.dateDeb = dateDeb;
		this.rang = rang;
		this.typConge = typConge;
	}

	@Column(name = "NOMATR", insertable = false, updatable = false, columnDefinition = "numeric")
	private Integer nomatr;

	@Column(name = "DATDEB", insertable = false, updatable = false, columnDefinition = "numeric")
	private Integer dateDeb;

	@Column(name = "RANG", insertable = false, updatable = false, columnDefinition = "numeric")
	private Integer rang;

	@OneToOne
	@JoinColumn(name = "TYPE2", referencedColumnName = "TYPE2", insertable = false, updatable = false)
	private Sptyco typConge;

	public Integer getNomatr() {
		return nomatr;
	}

	public void setNomatr(Integer nomatr) {
		this.nomatr = nomatr;
	}

	public Integer getDateDeb() {
		return dateDeb;
	}

	public void setDateDeb(Integer dateDeb) {
		this.dateDeb = dateDeb;
	}

	public Integer getRang() {
		return rang;
	}

	public void setRang(Integer rang) {
		this.rang = rang;
	}

	public Sptyco getTypConge() {
		return typConge;
	}

	public void setTypConge(Sptyco typConge) {
		this.typConge = typConge;
	}
}
