package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.SpadmnId;

@Entity
@Table(name = "SPADMN")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spadmn {

	@Override
	public String toString() {
		return "Spadmn [id=" + id + ", datfin=" + datfin + ", cdpadm=" + positionAdministrative.getCdpAdm() + "]";
	}

	@EmbeddedId
	private SpadmnId id;

	@Column(name = "DATFIN", columnDefinition = "numeric")
	private Integer datfin;
	
	@OneToOne
	@JoinColumn(name = "CDPADM", referencedColumnName = "CDPADM")
	private Spposa positionAdministrative;

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

	public Spposa getPositionAdministrative() {
		return positionAdministrative;
	}

	public void setPositionAdministrative(Spposa positionAdministrative) {
		this.positionAdministrative = positionAdministrative;
	}
	
}
