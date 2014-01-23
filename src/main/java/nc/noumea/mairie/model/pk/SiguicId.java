package nc.noumea.mairie.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SiguicId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public SiguicId() {
	}

	public SiguicId(Integer codeBanque, Integer codeGuichet) {
		this.codeBanque = codeBanque;
		this.codeGuichet = codeGuichet;
	}

	@Column(name = "CDBANQ", insertable = false, updatable = false, columnDefinition = "decimal")
	private Integer codeBanque;

	@Column(name = "CDGUIC", insertable = false, updatable = false, columnDefinition = "decimal")
	private Integer codeGuichet;

	public Integer getCodeBanque() {
		return codeBanque;
	}

	public void setCodeBanque(Integer codeBanque) {
		this.codeBanque = codeBanque;
	}

	public Integer getCodeGuichet() {
		return codeGuichet;
	}

	public void setCodeGuichet(Integer codeGuichet) {
		this.codeGuichet = codeGuichet;
	}
}
