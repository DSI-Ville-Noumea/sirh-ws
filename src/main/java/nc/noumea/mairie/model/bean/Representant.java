package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_REPRESENTANT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Representant {

	@Id
	@Column(name = "ID_REPRESENTANT")
	private Integer idRepresentant;

	@Column(name = "NOM_REPRESENTANT")
	private String nom;

	@Column(name = "PRENOM_REPRESENTANT")
	private String prenom;

	public Integer getIdRepresentant() {
		return idRepresentant;
	}

	public void setIdRepresentant(Integer idRepresentant) {
		this.idRepresentant = idRepresentant;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}
