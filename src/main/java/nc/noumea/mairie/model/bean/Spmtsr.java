package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.SpmtsrId;

@Entity
@Table(name = "SPMTSR")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spmtsr {

	@EmbeddedId
	private SpmtsrId id;
	
	@NotNull
	@Column(name = "REFARR", columnDefinition = "numeric")
	public Integer refarr;
	
	@Column(name = "DATFIN", columnDefinition = "numeric")
	public Integer datfin;
	
	@NotNull
	@Column(name = "CDECOL", columnDefinition = "numeric")
	public Integer cdecol;

	public SpmtsrId getId() {
		return id;
	}

	public void setId(SpmtsrId id) {
		this.id = id;
	}

	public Integer getRefarr() {
		return refarr;
	}

	public void setRefarr(Integer refarr) {
		this.refarr = refarr;
	}

	public Integer getDatfin() {
		return datfin;
	}

	public void setDatfin(Integer datfin) {
		this.datfin = datfin;
	}

	public Integer getCdecol() {
		return cdecol;
	}

	public void setCdecol(Integer cdecol) {
		this.cdecol = cdecol;
	}
	
	
}
