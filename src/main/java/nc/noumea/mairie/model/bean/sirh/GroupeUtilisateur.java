package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPE_UTILISATEUR")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class GroupeUtilisateur {

	@EmbeddedId
	private GroupeUtilisateurId id;

	public GroupeUtilisateurId getId() {
		return id;
	}

	public void setId(GroupeUtilisateurId id) {
		this.id = id;
	}
}
