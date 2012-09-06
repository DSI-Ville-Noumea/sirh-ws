package nc.noumea.mairie.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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

	public SpcongId(Integer nomatr, Integer datDeb) {
		this.nomatr = nomatr;
		this.datDeb = datDeb;
	}

	@Column(name = "NOMATR", insertable = false, updatable = false, columnDefinition = "numeric")
	private Integer nomatr;

	@Column(name = "DATDEB", insertable = false, updatable = false, columnDefinition = "numeric")
	private Integer datDeb;
}
