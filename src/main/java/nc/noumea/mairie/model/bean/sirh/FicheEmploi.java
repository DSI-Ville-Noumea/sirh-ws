package nc.noumea.mairie.model.bean.sirh;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "ficheEmploi", fetch = FetchType.LAZY)
	private Set<FeFp> ficheEmploi = new HashSet<FeFp>();

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

	public Set<FeFp> getFicheEmploi() {
		return ficheEmploi;
	}

	public void setFicheEmploi(Set<FeFp> ficheEmploi) {
		this.ficheEmploi = ficheEmploi;
	}
	
}
