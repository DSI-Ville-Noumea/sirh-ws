package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPMTSR")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spmtsr {

	@Id
	@Column(name = "NOMATR", columnDefinition = "numeric")
	public Integer nomatr;
	
	@NotNull
	@Column(name = "SERVI", columnDefinition = "char")
	public String servi;
	
	@NotNull
	@Column(name = "REFARR", columnDefinition = "numeric")
	public Integer refarr;
	
	@Column(name = "DATDEB", columnDefinition = "numeric")
	public Integer datdeb;
	
	@Column(name = "DATFIN", columnDefinition = "numeric")
	public Integer datfin;
	
	@NotNull
	@Column(name = "CDECOL", columnDefinition = "numeric")
	public Integer cdecol;

	public Integer getNomatr() {
		return nomatr;
	}

	public void setNomatr(Integer nomatr) {
		this.nomatr = nomatr;
	}

	public String getServi() {
		return servi;
	}

	public void setServi(String servi) {
		this.servi = servi;
	}

	public Integer getRefarr() {
		return refarr;
	}

	public void setRefarr(Integer refarr) {
		this.refarr = refarr;
	}

	public Integer getDatdeb() {
		return datdeb;
	}

	public void setDatdeb(Integer datdeb) {
		this.datdeb = datdeb;
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
