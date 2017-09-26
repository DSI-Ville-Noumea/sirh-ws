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

	@Column(name = "MONTPP", columnDefinition = "numeric")
	private Integer montantPP;

	@Column(name = "MONTPS", columnDefinition = "numeric")
	private Integer montantPS;

	@Column(name = "MONTBRUT", columnDefinition = "numeric")
	private Integer montantBrut;

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

	public Integer getMontantBrut() {
		return montantBrut;
	}

	public void setMontantBrut(Integer montantBrut) {
		this.montantBrut = montantBrut;
	}

	public Integer getMontantPS() {
		return montantPS;
	}

	public void setMontantPS(Integer montantPS) {
		this.montantPS = montantPS;
	}

	public Integer getMontantPP() {
		return montantPP;
	}

	public void setMontantPP(Integer montantPP) {
		this.montantPP = montantPP;
	}
}
