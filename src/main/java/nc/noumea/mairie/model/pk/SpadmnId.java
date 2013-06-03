package nc.noumea.mairie.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
@Embeddable
public class SpadmnId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6569165183644744768L;
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public SpadmnId() {
	}

	public SpadmnId(Integer nomatr, Integer datdeb, String type3) {
		this.nomatr = nomatr;
		this.datdeb = datdeb;
	}
	@Column(name = "NOMATR", insertable = false, updatable = false, columnDefinition = "numeric")
	private Integer nomatr;

	@Column(name = "DATDEB", insertable = false, updatable = false, columnDefinition = "numeric")
	private Integer datdeb;
	
	
}
