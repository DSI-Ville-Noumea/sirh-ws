package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_TITRE_DIPLOME")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class TitreDiplome {

	@Id
	@Column(name = "ID_TITRE_DIPLOME")
	private Integer idTitreDiplome;
	
	@Column(name = "LIB_TITRE_DIPLOME")
	private String libTitreDiplome;
	
	@Column(name = "NIVEAU_ETUDE")
	private String niveauEtude;

	public Integer getIdTitreDiplome() {
		return idTitreDiplome;
	}

	public void setIdTitreDiplome(Integer idTitreDiplome) {
		this.idTitreDiplome = idTitreDiplome;
	}

	public String getLibTitreDiplome() {
		return libTitreDiplome;
	}

	public void setLibTitreDiplome(String libTitreDiplome) {
		this.libTitreDiplome = libTitreDiplome;
	}

	public String getNiveauEtude() {
		return niveauEtude;
	}

	public void setNiveauEtude(String niveauEtude) {
		this.niveauEtude = niveauEtude;
	}
	
	
}
