package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CONTACT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Contact {

	@Id
	@Column(name = "ID_CONTACT")
	private Integer idContact;

	@OneToOne
	@JoinColumn(name = "ID_TYPE_CONTACT", referencedColumnName = "ID_TYPE_CONTACT")
	private TypeContact typeContact;

	@Column(name = "ID_AGENT")
	private Integer idAgent;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DIFFUSABLE")
	private String diffusable;

	public String getDiffusable() {
		if (this.diffusable.equals("1")) {
			return "oui";
		} else {
			return "non";
		}
	}

	@Column(name = "PRIORITAIRE", columnDefinition = "smallint")
	private Integer contactPrioritaire;

	@Transient
	private String prioritaire;

	public String getPrioritaire() {
		if (this.contactPrioritaire.toString().equals("1")) {
			return "oui";
		} else {
			return "non";
		}
	}

	public Integer getIdContact() {
		return idContact;
	}

	public void setIdContact(Integer idContact) {
		this.idContact = idContact;
	}

	public TypeContact getTypeContact() {
		return typeContact;
	}

	public void setTypeContact(TypeContact typeContact) {
		this.typeContact = typeContact;
	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getContactPrioritaire() {
		return contactPrioritaire;
	}

	public void setContactPrioritaire(Integer contactPrioritaire) {
		this.contactPrioritaire = contactPrioritaire;
	}

	public void setDiffusable(String diffusable) {
		this.diffusable = diffusable;
	}

	public void setPrioritaire(String prioritaire) {
		this.prioritaire = prioritaire;
	}
}
