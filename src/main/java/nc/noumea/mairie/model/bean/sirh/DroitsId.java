package nc.noumea.mairie.model.bean.sirh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Embeddable
public class DroitsId implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GROUPE", referencedColumnName = "ID_GROUPE")
	private GroupeUtilisateur groupeUtilisateur;

	@Column(name = "ID_ELEMENT")
	private Integer idGroupe;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public DroitsId() {
	}

	public GroupeUtilisateur getGroupeUtilisateur() {
		return groupeUtilisateur;
	}

	public void setGroupeUtilisateur(GroupeUtilisateur groupeUtilisateur) {
		this.groupeUtilisateur = groupeUtilisateur;
	}

	public Integer getIdGroupe() {
		return idGroupe;
	}

	public void setIdGroupe(Integer idGroupe) {
		this.idGroupe = idGroupe;
	}
}
