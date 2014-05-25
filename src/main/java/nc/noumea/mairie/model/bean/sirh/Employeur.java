package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_EMPLOYEUR")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Employeur {

	@Id
	@Column(name = "ID_EMPLOYEUR")
	private Integer idEmployeur;

	@Column(name = "LIB_EMPLOYEUR")
	private String libelle;

	@Column(name = "TITRE_EMPLOYEUR")
	private String titre;

	public Integer getIdEmployeur() {
		return idEmployeur;
	}

	public void setIdEmployeur(Integer idEmployeur) {
		this.idEmployeur = idEmployeur;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
}
