package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "P_CADRE_EMPLOI")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class CadreEmploi {

	@Id
	@Column(name = "ID_CADRE_EMPLOI")
	private Integer idCadreEmploi;

	@NotNull
	@Column(name = "LIB_CADRE_EMPLOI")
	private String libelleCadreEmploi;

	public Integer getIdCadreEmploi() {
		return idCadreEmploi;
	}

	public void setIdCadreEmploi(Integer idCadreEmploi) {
		this.idCadreEmploi = idCadreEmploi;
	}

	public String getLibelleCadreEmploi() {
		return libelleCadreEmploi;
	}

	public void setLibelleCadreEmploi(String libelleCadreEmploi) {
		this.libelleCadreEmploi = libelleCadreEmploi;
	}
}
