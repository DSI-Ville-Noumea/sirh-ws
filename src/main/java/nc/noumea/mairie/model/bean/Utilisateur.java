package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
