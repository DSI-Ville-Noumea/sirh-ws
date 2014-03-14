package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "R_AUTRE_ADMIN")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class AutreAdministration {

	@Id
	@Column(name = "ID_AUTRE_ADMIN")
	private Integer idAutreAdmin;
	
	@Column(name = "LIB_AUTRE_ADMIN")
	private String libAutreAdmin;

	public Integer getIdAutreAdmin() {
		return idAutreAdmin;
	}

	public void setIdAutreAdmin(Integer idAutreAdmin) {
		this.idAutreAdmin = idAutreAdmin;
	}

	public String getLibAutreAdmin() {
		return libAutreAdmin;
	}

	public void setLibAutreAdmin(String libAutreAdmin) {
		this.libAutreAdmin = libAutreAdmin;
	}
	
	
}
