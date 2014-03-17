package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AgentRecherche;

public class AgentDto {

	private String nom;
	private String prenom;
	private Integer idAgent;

	private FichePosteDto fichePoste;

	public AgentDto() {
	}

	public AgentDto(Agent agent) {
		if (agent != null) {
			nom = agent.getDisplayNom();
			prenom = agent.getDisplayPrenom();
			idAgent = agent.getIdAgent();
		}
	}

	public AgentDto(AgentRecherche agent) {
		if (agent != null) {
			nom = agent.getDisplayNom();
			prenom = agent.getDisplayPrenom();
			idAgent = agent.getIdAgent();
		}
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}

	public FichePosteDto getFichePoste() {
		return fichePoste;
	}

	public void setFichePoste(FichePosteDto fichePoste) {
		this.fichePoste = fichePoste;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.idAgent == null) {
			return false;
		}
		return this.idAgent.equals(((AgentDto) obj).getIdAgent());
	}

}
