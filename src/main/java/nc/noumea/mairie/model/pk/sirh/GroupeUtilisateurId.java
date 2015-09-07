package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GroupeUtilisateurId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_GROUPE")
	private Integer idGroupe;

	@Column(name = "ID_UTILISATEUR")
	private Integer idUtilisateur;

	public GroupeUtilisateurId() {
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idGroupe == null) ? 0 : idGroupe.hashCode());
		result = prime * result
				+ ((idUtilisateur == null) ? 0 : idUtilisateur.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupeUtilisateurId other = (GroupeUtilisateurId) obj;
		if (idGroupe == null) {
			if (other.idGroupe != null)
				return false;
		} else if (!idGroupe.equals(other.idGroupe))
			return false;
		if (idUtilisateur == null) {
			if (other.idUtilisateur != null)
				return false;
		} else if (!idUtilisateur.equals(other.idUtilisateur))
			return false;
		return true;
	}





	public Integer getIdUtilisateur() {
		return idUtilisateur;
	}
	
	public void setIdUtilisateur(Integer idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}





	public Integer getIdGroupe() {
		return idGroupe;
	}





	public void setIdGroupe(Integer idGroupe) {
		this.idGroupe = idGroupe;
	}

	
	
	
}
