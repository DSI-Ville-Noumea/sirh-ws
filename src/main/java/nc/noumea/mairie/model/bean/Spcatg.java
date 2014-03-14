package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPCATG")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spcatg {

	@Id
	@Column(name = "CDCATE", columnDefinition = "numeric")
	private Integer codeCategorie;
	
	@NotNull
	@Column(name = "LICATE", columnDefinition = "char")
	private String libelleCategorie;

	public Integer getCodeCategorie() {
		return codeCategorie;
	}

	public void setCodeCategorie(Integer codeCategorie) {
		this.codeCategorie = codeCategorie;
	}

	public String getLibelleCategorie() {
		return libelleCategorie;
	}

	public void setLibelleCategorie(String libelleCategorie) {
		this.libelleCategorie = libelleCategorie;
	}
}
