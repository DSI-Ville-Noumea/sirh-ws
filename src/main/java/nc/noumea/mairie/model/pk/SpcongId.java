package nc.noumea.mairie.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import nc.noumea.mairie.model.bean.Sptyco;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@Embeddable
public class SpcongId implements Serializable {
	public SpcongId() {
	}

	public SpcongId(Integer nomatr, Integer dateDeb,Integer rang,Sptyco typConge) {
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
	@JoinColumn(name = "TYPE2", referencedColumnName = "TYPE2",insertable=false,updatable=false)
	private Sptyco typConge;
}
