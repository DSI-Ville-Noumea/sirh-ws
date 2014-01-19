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
public class SiidmaId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public SiidmaId() {
	}

	public SiidmaId(String cdidut, Integer idIndi) {
		this.cdidut = cdidut;
		this.idIndi = idIndi;
	}

	@Column(name = "CDIDUT", insertable = false, updatable = false, columnDefinition = "char")
	private String cdidut;

	@Column(name = "IDINDI", insertable = false, updatable = false, columnDefinition = "decimal")
	private Integer idIndi;
}
