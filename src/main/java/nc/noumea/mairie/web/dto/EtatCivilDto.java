package nc.noumea.mairie.web.dto;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Agent;

public class EtatCivilDto {

	/*
	 * POUR LE PROJETS KIOSQUE J2EE
	 */

	private AgentGeneriqueDto agent;
	private String sexe;
	private String situationFamiliale;
	private Date dateNaissance;
	private String titre;
	private String lieuNaissance;
	private AdresseAgentDto adresse;
	private List<ContactAgentDto> listeContacts;

	public EtatCivilDto(Agent ag, List<ContactAgentDto> listContact) throws ParseException {
		super();
		if (ag != null) {
			this.agent = new AgentGeneriqueDto(ag);
			this.sexe = ag.getSexe();
			this.situationFamiliale = ag.getSituationFamiliale().getLibSituationFamiliale();
			this.dateNaissance = ag.getDateNaissance();
			this.titre = ag.getTitre();
			this.lieuNaissance = ag.getLieuNaissance();
			this.adresse = new AdresseAgentDto(ag);
			this.listeContacts = listContact;
		}
	}

	public AgentGeneriqueDto getAgent() {
		return agent;
	}

	public void setAgent(AgentGeneriqueDto agent) {
		this.agent = agent;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getSituationFamiliale() {
		return situationFamiliale;
	}

	public void setSituationFamiliale(String situationFamiliale) {
		this.situationFamiliale = situationFamiliale;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public AdresseAgentDto getAdresse() {
		return adresse;
	}

	public void setAdresse(AdresseAgentDto adresse) {
		this.adresse = adresse;
	}

	public List<ContactAgentDto> getListeContacts() {
		return listeContacts;
	}

	public void setListeContacts(List<ContactAgentDto> listeContacts) {
		this.listeContacts = listeContacts;
	}

}
