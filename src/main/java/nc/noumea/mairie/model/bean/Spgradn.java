package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "CODGRG", referencedColumnName = "CDGENG")
	private Spgeng gradeGenerique;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "IBAN", referencedColumnName = "IBAN")
	private Spbarem barem;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "CODCLA", referencedColumnName = "CODCLA")
	private Spclas classe;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "CODECH", referencedColumnName = "CODECH")
	private Speche echelon;

	@Column(name = "DURMIN", columnDefinition = "numeric")
	private Integer dureeMinimum;

	@Column(name = "DURMOY", columnDefinition = "numeric")
	private Integer dureeMoyenne;

	@Column(name = "DURMAX", columnDefinition = "numeric")
	private Integer dureeMaximum;

	@Column(name = "CDTAVA", columnDefinition = "char")
	private String cdTava;

	@Column(name = "ACC", columnDefinition = "char")
	private String acc;

	@Column(name = "BM", columnDefinition = "char")
	private String bm;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "CDSUIV", referencedColumnName = "CDGRAD")
	private Spgradn gradeSuivant;

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

	@Override
	public String toString() {
		return "Spgradn [cdgrad=" + cdgrad + ", liGrad=" + liGrad + ", gradeInitial=" + gradeInitial
				+ ", gradeGenerique=" + gradeGenerique + ", barem=" + barem + ", classe=" + classe + ", echelon="
				+ echelon + ", dureeMinimum=" + dureeMinimum + ", dureeMoyenne=" + dureeMoyenne + ", dureeMaximum="
				+ dureeMaximum + ", cdTava=" + cdTava + "]";
	}

	public Spbarem getBarem() {
		return barem;
	}

	public void setBarem(Spbarem barem) {
		this.barem = barem;
	}

	public Speche getEchelon() {
		return echelon;
	}

	public void setEchelon(Speche echelon) {
		this.echelon = echelon;
	}

	public Spclas getClasse() {
		return classe;
	}

	public void setClasse(Spclas classe) {
		this.classe = classe;
	}

	public Integer getDureeMinimum() {
		return dureeMinimum;
	}

	public void setDureeMinimum(Integer dureeMinimum) {
		this.dureeMinimum = dureeMinimum;
	}

	public Integer getDureeMoyenne() {
		return dureeMoyenne;
	}

	public void setDureeMoyenne(Integer dureeMoyenne) {
		this.dureeMoyenne = dureeMoyenne;
	}

	public Integer getDureeMaximum() {
		return dureeMaximum;
	}

	public void setDureeMaximum(Integer dureeMaximum) {
		this.dureeMaximum = dureeMaximum;
	}

	public String getCdTava() {
		return cdTava;
	}

	public void setCdTava(String cdTava) {
		this.cdTava = cdTava;
	}

	public Spgradn getGradeSuivant() {
		return gradeSuivant;
	}

	public void setGradeSuivant(Spgradn gradeSuivant) {
		this.gradeSuivant = gradeSuivant;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

}
