package nc.noumea.mairie.web.dto;

import java.util.Date;

import nc.noumea.mairie.model.bean.sirh.Agent;

public class AgentGeneriqueDto {

	/*
	 * POUR LES PROJETS PTG ET ABS et KIOSQUE J2EE
	 */

	private Integer idAgent;
	private Integer nomatr;
	private String nomMarital;
	private String nomPatronymique;
	private String nomUsage;
	private String prenom;
	private String prenomUsage;
	// #20097 utile au calcul des droits des maladies
	private Date dateDerniereEmbauche;
	// #40074 optimisation our Tickets restaurant
	private Integer	idTicketRestaurant;

	public Integer getIdTicketRestaurant() {
		return idTicketRestaurant;
	}

	public void setIdTicketRestaurant(Integer idTicketRestaurant) {
		this.idTicketRestaurant = idTicketRestaurant;
	}

	public AgentGeneriqueDto(Agent ag) {
		super();
		if (ag != null) {
			this.idAgent = ag.getIdAgent();
			this.nomatr = ag.getNomatr();
			this.nomMarital = ag.getNomMarital();
			this.nomPatronymique = ag.getNomPatronymique();
			this.nomUsage = ag.getNomUsage();
			this.prenom = ag.getPrenom();
			this.prenomUsage = ag.getPrenomUsage();
			this.dateDerniereEmbauche = ag.getDateDerniereEmbauche();
			this.idTicketRestaurant = ag.getIdTitreRepas();
		}
	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}

	public Integer getNomatr() {
		return nomatr;
	}

	public void setNomatr(Integer nomatr) {
		this.nomatr = nomatr;
	}

	public String getNomMarital() {
		return nomMarital;
	}

	public void setNomMarital(String nomMarital) {
		this.nomMarital = nomMarital;
	}

	public String getNomPatronymique() {
		return nomPatronymique;
	}

	public void setNomPatronymique(String nomPatronymique) {
		this.nomPatronymique = nomPatronymique;
	}

	public String getNomUsage() {
		return nomUsage;
	}

	public void setNomUsage(String nomUsage) {
		this.nomUsage = nomUsage;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPrenomUsage() {
		return prenomUsage;
	}

	public void setPrenomUsage(String prenomUsage) {
		this.prenomUsage = prenomUsage;
	}

	public Date getDateDerniereEmbauche() {
		return dateDerniereEmbauche;
	}

	public void setDateDerniereEmbauche(Date dateDerniereEmbauche) {
		this.dateDerniereEmbauche = dateDerniereEmbauche;
	}

}
