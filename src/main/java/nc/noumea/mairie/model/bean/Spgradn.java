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

	@OneToOne(optional = true)
	@JoinColumn(name = "CODCLA", referencedColumnName = "CODCLA")
	private Spclas classe;

	@OneToOne(optional = true)
	@JoinColumn(name = "CODECH", referencedColumnName = "CODECH")
	private Speche echelon;

	@Column(name = "DURMIN", columnDefinition = "numeric")
	private Integer dureeMinimum;
	
	@Column(name = "DURMOY", columnDefinition = "numeric")
	private Integer dureeMoyenne;
	
	@Column(name = "DURMAX", columnDefinition = "numeric")
	private Integer dureeMaximum;
	
	@OneToOne(optional = true)
	@JoinColumn(name = "CDTAVA", referencedColumnName = "ID_MOTIF_AVCT")
	private PMotifAvct motifAvct;
	
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

	public PMotifAvct getMotifAvct() {
		return motifAvct;
	}

	public void setMotifAvct(PMotifAvct motifAvct) {
		this.motifAvct = motifAvct;
	}
	
}
