package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "R_STATUT_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class StatutFichePoste {

	@Id
	@Column(name = "ID_STATUT_FP")
	private Integer idStatutFp;

	@NotNull
	@Column(name = "LIB_STATUT_FP")
	private String libStatut;

	public Integer getIdStatutFp() {
		return idStatutFp;
	}

	public void setIdStatutFp(Integer idStatutFp) {
		this.idStatutFp = idStatutFp;
	}

	public String getLibStatut() {
		return libStatut;
	}

	public void setLibStatut(String libStatut) {
		this.libStatut = libStatut;
	}
}
