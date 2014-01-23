package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.SpadmnId;

@Entity
@Table(name = "SPADMN")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spadmn {

	@EmbeddedId
	private SpadmnId id;

	@Column(name = "DATFIN", columnDefinition = "numeric")
	private Integer datfin;

	@Column(name = "CDPADM", columnDefinition = "char")
	private String cdpadm;

	public SpadmnId getId() {
		return id;
	}

	public void setId(SpadmnId id) {
		this.id = id;
	}

	public Integer getDatfin() {
		return datfin;
	}

	public void setDatfin(Integer datfin) {
		this.datfin = datfin;
	}

	public String getCdpadm() {
		return cdpadm;
	}

	public void setCdpadm(String cdpadm) {
		this.cdpadm = cdpadm;
	}
}
