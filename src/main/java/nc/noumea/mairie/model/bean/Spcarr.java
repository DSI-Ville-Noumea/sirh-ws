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
@NamedQueries({
	@NamedQuery(name = "getCurrentCarriere", query = "select carr from Spcarr carr where carr.id.nomatr = :nomatr and carr.id.datdeb <= :todayFormatMairie and (carr.dateFin = 0 or carr.dateFin >= :todayFormatMairie)"),
	@NamedQuery(name = "getCarriereFonctionnaireAncienne", query = "select carr from Spcarr carr where carr.id.nomatr = :nomatr and carr.categorie.codeCategorie in (1,2,6,16,17,18,19,20) "
			+ " and carr.id.datdeb = (select min(carr2.id.datdeb) from Spcarr carr2 where carr2.id.nomatr = :nomatr and carr2.categorie.codeCategorie in (1,2,6,16,17,18,19,20) )")
})
public class Spcarr {

	@EmbeddedId
	private SpcarrId id;

	public Spcarr() {
	}

	public Spcarr(Integer nomatr, Integer datdeb) {
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
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "CDGRAD", referencedColumnName = "CDGRAD")
	private Spgradn grade;
	
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

	public Spgradn getGrade() {
		return grade;
	}

	public void setGrade(Spgradn grade) {
		this.grade = grade;
	}

	
}
