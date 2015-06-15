package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_SPECIALITE_DIPLOME")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class SpecialiteDiplome {

	@Id
	@Column(name = "ID_SPECIALITE_DIPLOME")
	private Integer idSpecialiteDiplome;
	
	@Column(name = "LIB_SPECIALITE_DIPLOME")
	private String libSpeDiplome;

	public Integer getIdSpecialiteDiplome() {
		return idSpecialiteDiplome;
	}

	public void setIdSpecialiteDiplome(Integer idSpecialiteDiplome) {
		this.idSpecialiteDiplome = idSpecialiteDiplome;
	}

	public String getLibSpeDiplome() {
		return libSpeDiplome;
	}

	public void setLibSpeDiplome(String libSpeDiplome) {
		this.libSpeDiplome = libSpeDiplome;
	}
	
	
}
