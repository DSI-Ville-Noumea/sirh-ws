package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "R_TYPE_CONTACT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class TypeContact {

	@Id
	@Column(name = "ID_TYPE_CONTACT")
	private Integer idTypeContact;

	@NotNull
	@Column(name = "LIBELLE")
	private String libelle;

	public Integer getIdTypeContact() {
		return idTypeContact;
	}

	public void setIdTypeContact(Integer idTypeContact) {
		this.idTypeContact = idTypeContact;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
