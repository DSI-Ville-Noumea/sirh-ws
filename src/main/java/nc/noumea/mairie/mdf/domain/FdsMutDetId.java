package nc.noumea.mairie.mdf.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FdsMutDetId implements Serializable {
	
	static final long serialVersionUID = 1L;
	
	@Column(name = "TYPMOUV", columnDefinition = "char")
	private String typeMouvement;
	
	@Column(name = "MOISPAIE", columnDefinition = "char")
	private String moisPaye;

	@Column(name = "MATCAFA", columnDefinition = "char")
	private String matriculeCafat;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public String getMatriculeCafat() {
		return matriculeCafat;
	}

	public void setMatriculeCafat(String matriculeCafat) {
		this.matriculeCafat = matriculeCafat;
	}

	public String getTypeMouvement() {
		return typeMouvement;
	}

	public void setTypeMouvement(String typeMouvement) {
		this.typeMouvement = typeMouvement;
	}

	public String getMoisPaye() {
		return moisPaye;
	}

	public void setMoisPaye(String moisPaye) {
		this.moisPaye = moisPaye;
	}
}
