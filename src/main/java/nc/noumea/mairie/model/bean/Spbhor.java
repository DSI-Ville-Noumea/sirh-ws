package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPBHOR")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
@NamedQuery(name = "Spbhor.whereCdTauxNotZero", query = "SELECT sp from Spbhor sp WHERE (sp.taux <> 0 and sp.taux <> 1) order by sp.taux DESC")
public class Spbhor {

	@Id
	@Column(name = "CDTHOR", columnDefinition = "decimal")
	private Integer cdThor;

	@NotNull
	@Column(name = "LIBHOR", columnDefinition = "char")
	private String libHor;

	@Column(name = "CDTAUX", columnDefinition = "decimal")
	private Double taux;

	public Integer getCdThor() {
		return cdThor;
	}

	public void setCdThor(Integer cdThor) {
		this.cdThor = cdThor;
	}

	public String getLibHor() {
		return libHor;
	}

	public void setLibHor(String libHor) {
		this.libHor = libHor;
	}

	public Double getTaux() {
		return taux;
	}

	public void setTaux(Double taux) {
		this.taux = taux;
	}
}
