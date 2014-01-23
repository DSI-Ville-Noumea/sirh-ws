package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.SpcarrId;

@Entity
@Table(name = "SPCARR")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
@NamedQuery(name = "getCurrentCarriere", query = "select carr from Spcarr carr where carr.id.nomatr = :nomatr and carr.id.datdeb <= :todayFormatMairie and (carr.dateFin = 0 or carr.dateFin >= :todayFormatMairie)")
public class Spcarr {

	@EmbeddedId
	private SpcarrId id;

	public Spcarr() {
	}

	public Spcarr(Integer nomatr, Integer datdeb) {
		this.id = new SpcarrId(nomatr, datdeb);
	}

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
}
