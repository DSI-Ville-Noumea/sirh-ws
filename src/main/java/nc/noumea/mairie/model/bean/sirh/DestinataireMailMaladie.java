package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_DESTINATAIRE_MAIL_MALADIE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class DestinataireMailMaladie {

	@Id
	@Column(name = "ID_DESTINATAIRE_MAIL_MALADIE")
	private Integer		idDestinataireMailMaladie;

	@OneToOne()
	@JoinColumn(name = "ID_GROUPE")
	private DroitsGroupe	groupe;

	public Integer getIdDestinataireMailMaladie() {
		return idDestinataireMailMaladie;
	}

	public void setIdDestinataireMailMaladie(Integer idDestinataireMailMaladie) {
		this.idDestinataireMailMaladie = idDestinataireMailMaladie;
	}

	public DroitsGroupe getGroupe() {
		return groupe;
	}

	public void setGroupe(DroitsGroupe groupe) {
		this.groupe = groupe;
	}

}
