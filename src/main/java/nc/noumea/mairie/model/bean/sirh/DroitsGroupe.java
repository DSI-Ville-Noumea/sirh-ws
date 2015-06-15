package nc.noumea.mairie.model.bean.sirh;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "DROITS_GROUPE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class DroitsGroupe {
	
	@Id
	@Column(name = "ID_GROUPE")
	private Integer idGroupe;

	@Column(name = "LIB_GROUPE")
	private String libGroupe;
	
	@OneToMany(mappedBy = "droitsGroupe", fetch = FetchType.LAZY)
	private Set<Droits> droits = new HashSet<Droits>();
	
	@ManyToMany()
	@JoinTable(
			name = "GROUPE_UTILISATEUR", 
			inverseJoinColumns = @JoinColumn(name = "ID_UTILISATEUR"), 
			joinColumns = @JoinColumn(name = "ID_GROUPE"))
	private Set<Utilisateur> utilisateurs = new HashSet<Utilisateur>();

	public Integer getIdGroupe() {
		return idGroupe;
	}

	public void setIdGroupe(Integer idGroupe) {
		this.idGroupe = idGroupe;
	}

	public String getLibGroupe() {
		return libGroupe;
	}

	public void setLibGroupe(String libGroupe) {
		this.libGroupe = libGroupe;
	}

	public Set<Droits> getDroits() {
		return droits;
	}

	public void setDroits(Set<Droits> droits) {
		this.droits = droits;
	}

	public Set<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}
	
}
