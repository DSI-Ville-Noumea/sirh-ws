package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "FICHE_EMPLOI")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class FicheEmploi {

	@Id
	@Column(name = "ID_FICHE_EMPLOI")
	private Integer idFicheEmploi;

	@Column(name = "REF_MAIRIE")
	private String refMairie;

	@Column(name = "NOM_METIER_EMPLOI")
	private String nomEmploi;

	public Integer getIdFicheEmploi() {
		return idFicheEmploi;
	}

	public void setIdFicheEmploi(Integer idFicheEmploi) {
		this.idFicheEmploi = idFicheEmploi;
	}

	public String getRefMairie() {
		return refMairie;
	}

	public void setRefMairie(String refMairie) {
		this.refMairie = refMairie;
	}

	public String getNomEmploi() {
		return nomEmploi;
	}

	public void setNomEmploi(String nomEmploi) {
		this.nomEmploi = nomEmploi;
	}
}
