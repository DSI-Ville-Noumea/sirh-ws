package nc.noumea.mairie.model.bean;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.bean.sirh.CadreEmploi;
import nc.noumea.mairie.model.bean.sirh.Cap;
import nc.noumea.mairie.model.bean.sirh.Deliberation;

@Entity
@Table(name = "SPGENG")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
@NamedQuery(name = "getSpgengFromCadreEmploi", query = "select s from Spgeng s JOIN FETCH s.filiere LEFT JOIN FETCH s.deliberationTerritoriale LEFT JOIN FETCH s.deliberationCommunale JOIN FETCH s.cadreEmploiGrade where s.cadreEmploiGrade.idCadreEmploi = :idCadreEmploi")
public class Spgeng {

	@Override
	public String toString() {
		return "Spgeng [cdgeng=" + cdgeng + ", liGrad=" + liGrad + ", cadreEmploiGrade=" + cadreEmploiGrade
				+ ", texteCapCadreEmploi=" + texteCapCadreEmploi + ", cdcadr=" + cdcadr + ", filiere=" + filiere
				+ ", caps=" + caps + ", deliberationTerritoriale=" + deliberationTerritoriale
				+ ", deliberationCommunale=" + deliberationCommunale + "]";
	}

	@Id
	@Column(name = "CDGENG", columnDefinition = "char")
	private String cdgeng;

	@NotNull
	@Column(name = "LIGRAD", columnDefinition = "char")
	private String liGrad;

	@OneToOne(optional = true)
	@JoinColumn(name = "IDCADREEMPLOI", referencedColumnName = "ID_CADRE_EMPLOI")
	private CadreEmploi cadreEmploiGrade;

	@Column(name = "TEXTECAPCADREEMPLOI", columnDefinition = "char")
	private String texteCapCadreEmploi;

	@Column(name = "CDCADR", columnDefinition = "char")
	private String cdcadr;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "CDFILI", referencedColumnName = "CDFILI")
	private Spfili filiere;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CORPS_CAP", joinColumns = { @javax.persistence.JoinColumn(name = "CDGENG") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "ID_CAP"))
	private Set<Cap> caps;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDDELIBTERR")
	private Deliberation deliberationTerritoriale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDDELIBCOMM")
	private Deliberation deliberationCommunale;

	public String getCdgeng() {
		return cdgeng;
	}

	public void setCdgeng(String cdgeng) {
		this.cdgeng = cdgeng;
	}

	public String getLiGrad() {
		return liGrad;
	}

	public void setLiGrad(String liGrad) {
		this.liGrad = liGrad;
	}

	public CadreEmploi getCadreEmploiGrade() {
		return cadreEmploiGrade;
	}

	public void setCadreEmploiGrade(CadreEmploi cadreEmploiGrade) {
		this.cadreEmploiGrade = cadreEmploiGrade;
	}

	public String getTexteCapCadreEmploi() {
		return texteCapCadreEmploi;
	}

	public void setTexteCapCadreEmploi(String texteCapCadreEmploi) {
		this.texteCapCadreEmploi = texteCapCadreEmploi;
	}

	public String getCdcadr() {
		return cdcadr;
	}

	public void setCdcadr(String cdcadr) {
		this.cdcadr = cdcadr;
	}

	public Spfili getFiliere() {
		return filiere;
	}

	public void setFiliere(Spfili filiere) {
		this.filiere = filiere;
	}

	public Set<Cap> getCaps() {
		return caps;
	}

	public void setCaps(Set<Cap> caps) {
		this.caps = caps;
	}

	public Deliberation getDeliberationTerritoriale() {
		return deliberationTerritoriale;
	}

	public void setDeliberationTerritoriale(Deliberation deliberationTerritoriale) {
		this.deliberationTerritoriale = deliberationTerritoriale;
	}

	public Deliberation getDeliberationCommunale() {
		return deliberationCommunale;
	}

	public void setDeliberationCommunale(Deliberation deliberationCommunale) {
		this.deliberationCommunale = deliberationCommunale;
	}
}
