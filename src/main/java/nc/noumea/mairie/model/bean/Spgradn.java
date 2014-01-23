package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPGRADN")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spgradn {

	@Id
	@Column(name = "CDGRAD", columnDefinition = "char")
	private String cdgrad;

	@NotNull
	@Column(name = "LIGRAD", columnDefinition = "char")
	private String liGrad;

	@NotNull
	@Column(name = "GRADE", columnDefinition = "char")
	private String gradeInitial;

	@OneToOne(optional = true)
	@JoinColumn(name = "CODGRG", referencedColumnName = "CDGENG")
	private Spgeng gradeGenerique;

	@OneToOne(optional = true)
	@JoinColumn(name = "IBAN", referencedColumnName = "IBAN")
	private Spbarem barem;

	@Column(name = "CODCLA", columnDefinition = "char")
	private String codcla;

	@Column(name = "CODECH", columnDefinition = "char")
	private String codech;

	public String getCdgrad() {
		return cdgrad;
	}

	public void setCdgrad(String cdgrad) {
		this.cdgrad = cdgrad;
	}

	public String getLiGrad() {
		return liGrad;
	}

	public void setLiGrad(String liGrad) {
		this.liGrad = liGrad;
	}

	public String getGradeInitial() {
		return gradeInitial;
	}

	public void setGradeInitial(String gradeInitial) {
		this.gradeInitial = gradeInitial;
	}

	public Spgeng getGradeGenerique() {
		return gradeGenerique;
	}

	public void setGradeGenerique(Spgeng gradeGenerique) {
		this.gradeGenerique = gradeGenerique;
	}

	public Spbarem getBarem() {
		return barem;
	}

	public void setBarem(Spbarem barem) {
		this.barem = barem;
	}

	public String getCodcla() {
		return codcla;
	}

	public void setCodcla(String codcla) {
		this.codcla = codcla;
	}

	public String getCodech() {
		return codech;
	}

	public void setCodech(String codech) {
		this.codech = codech;
	}
}
