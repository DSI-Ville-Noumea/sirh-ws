package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.SpcarrId;

@Entity
@Table(name = "SPCARR")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
@NamedQueries({ @NamedQuery(name = "getCurrentCarriereWithoutSpgradn", query = "select carr from SpcarrWithoutSpgradn carr where carr.id.nomatr = :nomatr and carr.id.datdeb <= :todayFormatMairie and (carr.dateFin = 0 or carr.dateFin >= :todayFormatMairie)") })
public class SpcarrWithoutSpgradn {

	@Override
	public String toString() {
		return "SpcarrWithoutSpgradn [id=" + id + ", categorie=" + categorie + ", dateFin=" + dateFin + ", dateArrete=" + dateArrete
				+ ", referenceArrete=" + referenceArrete + ", modReg=" + modReg + "]";
	}

	@EmbeddedId
	private SpcarrId id;

	public SpcarrWithoutSpgradn() {
	}

	public SpcarrWithoutSpgradn(Integer nomatr, Integer datdeb) {
		this.id = new SpcarrId(nomatr, datdeb);
	}

	@NotNull
	@OneToOne
	@JoinColumn(name = "CDCATE", referencedColumnName = "CDCATE")
	private Spcatg categorie;

	@NotNull
	@Column(name = "DATFIN", columnDefinition = "numeric")
	private Integer dateFin;

	@NotNull
	@Column(name = "DATARR", columnDefinition = "numeric")
	private Integer dateArrete;

	@NotNull
	@Column(name = "REFARR", columnDefinition = "numeric")
	private Integer referenceArrete;

	@Column(name = "MODREG", columnDefinition = "char")
	private String modReg;

	@Column(name = "ACCJOUR", columnDefinition = "decimal")
	private Integer accJour;

	@Column(name = "ACCMOIS", columnDefinition = "decimal")
	private Integer accMois;

	@Column(name = "ACCANNEE", columnDefinition = "decimal")
	private Integer accAnnee;

	@Column(name = "BMJOUR", columnDefinition = "decimal")
	private Integer bmJour;

	@Column(name = "BMMOIS", columnDefinition = "decimal")
	private Integer bmMois;

	@Column(name = "BMANNEE", columnDefinition = "decimal")
	private Integer bmAnnee;

	public SpcarrId getId() {
		return id;
	}

	public void setId(SpcarrId id) {
		this.id = id;
	}

	public Integer getDateFin() {
		return dateFin;
	}

	public void setDateFin(Integer dateFin) {
		this.dateFin = dateFin;
	}

	public Integer getDateArrete() {
		return dateArrete;
	}

	public void setDateArrete(Integer dateArrete) {
		this.dateArrete = dateArrete;
	}

	public Integer getReferenceArrete() {
		return referenceArrete;
	}

	public void setReferenceArrete(Integer referenceArrete) {
		this.referenceArrete = referenceArrete;
	}

	public String getModReg() {
		return modReg;
	}

	public void setModReg(String modReg) {
		this.modReg = modReg;
	}

	public Spcatg getCategorie() {
		return categorie;
	}

	public void setCategorie(Spcatg categorie) {
		this.categorie = categorie;
	}

	public Integer getAccJour() {
		return accJour;
	}

	public void setAccJour(Integer accJour) {
		this.accJour = accJour;
	}

	public Integer getAccMois() {
		return accMois;
	}

	public void setAccMois(Integer accMois) {
		this.accMois = accMois;
	}

	public Integer getAccAnnee() {
		return accAnnee;
	}

	public void setAccAnnee(Integer accAnnee) {
		this.accAnnee = accAnnee;
	}

	public Integer getBmJour() {
		return bmJour;
	}

	public void setBmJour(Integer bmJour) {
		this.bmJour = bmJour;
	}

	public Integer getBmMois() {
		return bmMois;
	}

	public void setBmMois(Integer bmMois) {
		this.bmMois = bmMois;
	}

	public Integer getBmAnnee() {
		return bmAnnee;
	}

	public void setBmAnnee(Integer bmAnnee) {
		this.bmAnnee = bmAnnee;
	}

}
