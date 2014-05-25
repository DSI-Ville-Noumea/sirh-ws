package nc.noumea.mairie.model.bean.sirh;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.bean.Spgeng;

@Entity
@Table(name = "P_CAP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
@NamedQuery(name = "getCapWithEmployeursAndRepresentants", query = "SELECT c FROM Cap c JOIN FETCH c.employeurs JOIN FETCH c.representants WHERE c.idCap = :idCap")
public class Cap {

	@Id
	@Column(name = "ID_CAP")
	private Integer idCap;

	@Column(name = "CODE_CAP")
	private String codeCap;

	@Column(name = "REF_CAP")
	private String refCap;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TYPE_CAP")
	private String typeCap;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CORPS_CAP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_CAP") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "CDGENG"))
	private Set<Spgeng> corps;

	@OneToMany(mappedBy = "cap", fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy(value = "position")
	private Set<CapEmployeur> employeurs = new HashSet<CapEmployeur>();

	@OneToMany(mappedBy = "cap", fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy(value = "position")
	private Set<CapRepresentant> representants = new HashSet<CapRepresentant>();

	@Column(name = "CAP_VDN")
	private boolean capVDN;

	public Integer getIdCap() {
		return idCap;
	}

	public void setIdCap(Integer idCap) {
		this.idCap = idCap;
	}

	public String getCodeCap() {
		return codeCap;
	}

	public void setCodeCap(String codeCap) {
		this.codeCap = codeCap;
	}

	public String getRefCap() {
		return refCap;
	}

	public void setRefCap(String refCap) {
		this.refCap = refCap;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeCap() {
		return typeCap;
	}

	public void setTypeCap(String typeCap) {
		this.typeCap = typeCap;
	}

	public Set<Spgeng> getCorps() {
		return corps;
	}

	public void setCorps(Set<Spgeng> corps) {
		this.corps = corps;
	}

	public Set<CapEmployeur> getEmployeurs() {
		return employeurs;
	}

	public void setEmployeurs(Set<CapEmployeur> employeurs) {
		this.employeurs = employeurs;
	}

	public Set<CapRepresentant> getRepresentants() {
		return representants;
	}

	public void setRepresentants(Set<CapRepresentant> representants) {
		this.representants = representants;
	}

	public boolean isCapVDN() {
		return capVDN;
	}

	public void setCapVDN(boolean capVDN) {
		this.capVDN = capVDN;
	}

}
