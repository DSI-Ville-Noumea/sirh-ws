package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.sirh.Contact;

public class ContactAgentDto {

	/*
	 * POUR LES PROJETS KIOSQUE J2EE
	 */

	private String diffusable;
	private String prioritaire;
	private String typeContact;
	private String description;

	public ContactAgentDto(Contact c) {
		super();
		if (c != null) {
			this.diffusable = c.getDiffusable();
			this.prioritaire = c.getPrioritaire();
			this.description = c.getDescription();

			String libelleTypeContact = c.getTypeContact().getLibelle().substring(0, 1).toUpperCase();
			libelleTypeContact += c.getTypeContact().getLibelle()
					.substring(1, c.getTypeContact().getLibelle().length()).toLowerCase();
			this.typeContact = libelleTypeContact;
		}
	}

	public String getDiffusable() {
		return diffusable;
	}

	public void setDiffusable(String diffusable) {
		this.diffusable = diffusable;
	}

	public String getPrioritaire() {
		return prioritaire;
	}

	public void setPrioritaire(String prioritaire) {
		this.prioritaire = prioritaire;
	}

	public String getTypeContact() {
		return typeContact;
	}

	public void setTypeContact(String typeContact) {
		this.typeContact = typeContact;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
