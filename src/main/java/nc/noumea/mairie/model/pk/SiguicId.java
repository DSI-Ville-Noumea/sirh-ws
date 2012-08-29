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
public class SiguicId implements Serializable {
	public SiguicId() {
	}

	public SiguicId(Integer codeBanque, Integer codeGuichet) {
		this.codeBanque = codeBanque;
		this.codeGuichet = codeGuichet;
	}

	@Column(name = "CDBANQ", insertable = false, updatable = false)
	private Integer codeBanque;

	@Column(name = "CDGUIC", insertable = false, updatable = false)
	private Integer codeGuichet;
}
