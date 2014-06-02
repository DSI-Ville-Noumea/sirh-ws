package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "P_MEDECIN")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Medecin {

	@Id
	@Column(name = "ID_MEDECIN")
	private Integer idMedecin;

	@NotNull
	@Column(name = "NOM_MEDECIN")
	private String nomMedecin;

	@Column(name = "PRENOM_MEDECIN")
	private String prenomMedecin;

	@Column(name = "TITRE_MEDECIN")
	private String titreMedecin;

	public Integer getIdMedecin() {
		return idMedecin;
	}

	public void setIdMedecin(Integer idMedecin) {
		this.idMedecin = idMedecin;
	}

	public String getNomMedecin() {
		return nomMedecin;
	}

	public void setNomMedecin(String nomMedecin) {
		this.nomMedecin = nomMedecin;
	}

	public String getPrenomMedecin() {
		return prenomMedecin;
	}

	public void setPrenomMedecin(String prenomMedecin) {
		this.prenomMedecin = prenomMedecin;
	}

	public String getTitreMedecin() {
		return titreMedecin;
	}

	public void setTitreMedecin(String titreMedecin) {
		this.titreMedecin = titreMedecin;
	}
}
