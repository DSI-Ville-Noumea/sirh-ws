package nc.noumea.mairie.model.bean.sirh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.sirh.DroitsId;

@Entity
@Table(name = "DROITS")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Droits implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DroitsId id;

	@Column(name = "ID_TYPE_DROIT")
	private Integer idTypeDroit;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ELEMENT", referencedColumnName = "ID_ELEMENT", insertable = false, updatable = false)
	private DroitsElement element;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_GROUPE", referencedColumnName = "ID_GROUPE", insertable = false, updatable = false)
	private DroitsGroupe droitsGroupe;

	public DroitsId getId() {
		return id;
	}

	public void setId(DroitsId id) {
		this.id = id;
	}

	public Integer getIdTypeDroit() {
		return idTypeDroit;
	}

	public void setIdTypeDroit(Integer idTypeDroit) {
		this.idTypeDroit = idTypeDroit;
	}

	public DroitsElement getElement() {
		return element;
	}

	public void setElement(DroitsElement element) {
		this.element = element;
	}

	public DroitsGroupe getDroitsGroupe() {
		return droitsGroupe;
	}

	public void setDroitsGroupe(DroitsGroupe droitsGroupe) {
		this.droitsGroupe = droitsGroupe;
	}
	
}
