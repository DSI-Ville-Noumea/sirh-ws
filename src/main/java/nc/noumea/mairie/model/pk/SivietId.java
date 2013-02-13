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
public class SivietId implements Serializable {
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
}
