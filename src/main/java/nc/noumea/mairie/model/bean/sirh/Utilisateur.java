package nc.noumea.mairie.model.bean.sirh;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "UTILISATEUR")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Utilisateur {

	@Id
	@Column(name = "ID_UTILISATEUR")
	private Integer idUtilisateur;

	@Column(name = "LOGIN_UTILISATEUR")
	private String login;

	@ManyToMany
	@JoinTable(name = "GROUPE_UTILISATEUR", joinColumns = @JoinColumn(name = "ID_UTILISATEUR"), inverseJoinColumns = @JoinColumn(name = "ID_GROUPE"))
	private Set<DroitsGroupe> droitsGroupe = new HashSet<DroitsGroupe>();
	
	public Integer getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Integer idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
