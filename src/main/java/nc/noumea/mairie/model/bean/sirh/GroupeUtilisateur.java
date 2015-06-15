package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.sirh.GroupeUtilisateurId;

@Entity
@Table(name = "GROUPE_UTILISATEUR")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class GroupeUtilisateur {

	@EmbeddedId
	private GroupeUtilisateurId id;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "ID_GROUPE", referencedColumnName = "ID_GROUPE")
	private Droits droits;
	
	public GroupeUtilisateurId getId() {
		return id;
	}

	public void setId(GroupeUtilisateurId id) {
		this.id = id;
	}

	public Droits getDroits() {
		return droits;
	}

	public void setDroits(Droits droits) {
		this.droits = droits;
	}
	
	
}
