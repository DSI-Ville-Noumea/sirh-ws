package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "R_MOTIF_VM")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class MotifVM {

	@Id
	@Column(name = "ID_MOTIF_VM", columnDefinition = "char")
	private Integer idMotifVisiteMedicale;

	@NotNull
	@Column(name = "LIB_MOTIF_VM")
	private String libMotifVisiteMedicale;

	public Integer getIdMotifVisiteMedicale() {
		return idMotifVisiteMedicale;
	}

	public void setIdMotifVisiteMedicale(Integer idMotifVisiteMedicale) {
		this.idMotifVisiteMedicale = idMotifVisiteMedicale;
	}

	public String getLibMotifVisiteMedicale() {
		return libMotifVisiteMedicale;
	}

	public void setLibMotifVisiteMedicale(String libMotifVisiteMedicale) {
		this.libMotifVisiteMedicale = libMotifVisiteMedicale;
	}

}
