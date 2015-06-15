package nc.noumea.mairie.model.bean.sirh;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "DROITS_ELEMENT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class DroitsElement {
	
	@Id
	@Column(name = "ID_ELEMENT")
	private Integer idElement;
	
	@Column(name = "LIB_ELEMENT")
	private String libElement;
	
	@OneToMany(mappedBy = "element", fetch = FetchType.LAZY)
	private Set<Droits> droits = new HashSet<Droits>();

	public Integer getIdElement() {
		return idElement;
	}

	public void setIdElement(Integer idElement) {
		this.idElement = idElement;
	}

	public String getLibElement() {
		return libElement;
	}

	public void setLibElement(String libElement) {
		this.libElement = libElement;
	}

	public Set<Droits> getDroits() {
		return droits;
	}

	public void setDroits(Set<Droits> droits) {
		this.droits = droits;
	}
}
