package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "P_MOTIF_AVCT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class MotifAvct {

	@Id
	@Column(name = "ID_MOTIF_AVCT", columnDefinition = "char")
	private Integer idMotifAvct;
	
	@NotNull
	@Column(name = "LIB_MOTIF_AVCT")
	private String libMotifAvct;
	
	@NotNull
	@Column(name = "CODE")
	private String codeAvct;

	public Integer getIdMotifAvct() {
		return idMotifAvct;
	}

	public void setIdMotifAvct(Integer idMotifAvct) {
		this.idMotifAvct = idMotifAvct;
	}

	public String getLibMotifAvct() {
		return libMotifAvct;
	}

	public void setLibMotifAvct(String libMotifAvct) {
		this.libMotifAvct = libMotifAvct;
	}

	public String getCodeAvct() {
		return codeAvct;
	}

	public void setCodeAvct(String codeAvct) {
		this.codeAvct = codeAvct;
	}
	
	
}
