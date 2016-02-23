package nc.noumea.mairie.web.dto;

import java.util.Date;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AgentRecherche;

public class AgentDto {

	private String nom;
	private String prenom;
	private Integer idAgent;
	private String civilite;
	private String nationalite;
	private Date dateNaissance;
	private String situationFamiliale;
	private String lieuNaissance;
	private Integer nbEnfantCharge;
	private CompteDto compteAgent;
	private String numCafat;
	private String numMutuelle;
	private String numCre;
	private String numRue;
	private String bisTer;
	private String nomRue;
	private Integer codePostal;
	private String ville;

	private FichePosteDto fichePoste;

	
	public AgentDto(AgentWithServiceDto agent) {
		this();
		nom = agent.getNom();
		prenom = agent.getPrenom();
		idAgent = agent.getIdAgent();
	}
	
	public AgentDto() {
	}

	public AgentDto(Agent agent) {
		if (agent != null) {
			this.nom = agent.getDisplayNom();
			this.prenom = agent.getDisplayPrenom();
			this.idAgent = agent.getIdAgent();
			this.civilite = agent.getTitre();
		}
	}

	public AgentDto(Agent agent, CompteDto cptDto) {
		if (agent != null) {
			this.nom = agent.getDisplayNom();
			this.prenom = agent.getDisplayPrenom();
			this.idAgent = agent.getIdAgent();
			this.civilite = agent.getTitre();
			this.nationalite = agent.getNationalite();
			this.dateNaissance = agent.getDateNaissance();
			this.situationFamiliale = agent.getSituationFamiliale().getLibSituationFamiliale();
			this.nbEnfantCharge = agent.getNbEnfantsACharge();
			this.compteAgent = cptDto;
			this.numCafat = agent.getNumCafat() == null ? "Inconnu" : agent.getNumCafat();
			this.numMutuelle = agent.getNumMutuelle() == null ? "Inconnu" : agent.getNumMutuelle();
			this.numCre = agent.getNumCre() == null ? "En cours d'affiliation" : agent.getNumCre();
			this.numRue = agent.getNumRue();
			this.bisTer = agent.getBisTer();
			if (agent.getVoie() == null) {
				this.nomRue = agent.getAdresseComplementaire().trim();
			} else {
				this.nomRue = agent.getVoie().getLiVoie().trim();
			}
			if (agent.getCodeCommuneVilleDom() == null) {
				this.codePostal = agent.getCodePostalVilleBP();
				this.ville = agent.getCodeCommuneVilleBP().getLibVil().trim();
			} else {
				this.codePostal = agent.getCodePostalVilleDom();
				this.ville = agent.getCodeCommuneVilleDom().getLibVil().trim();
			}
		}
	}

	public AgentDto(AgentRecherche agent) {
		if (agent != null) {
			this.nom = agent.getDisplayNom();
			this.prenom = agent.getDisplayPrenom();
			this.idAgent = agent.getIdAgent();
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

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getSituationFamiliale() {
		return situationFamiliale;
	}

	public void setSituationFamiliale(String situationFamiliale) {
		this.situationFamiliale = situationFamiliale;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public Integer getNbEnfantCharge() {
		return nbEnfantCharge;
	}

	public void setNbEnfantCharge(Integer nbEnfantCharge) {
		this.nbEnfantCharge = nbEnfantCharge;
	}

	public CompteDto getCompteAgent() {
		return compteAgent;
	}

	public void setCompteAgent(CompteDto compteAgent) {
		this.compteAgent = compteAgent;
	}

	public String getNumCafat() {
		return numCafat;
	}

	public void setNumCafat(String numCafat) {
		this.numCafat = numCafat;
	}

	public String getNumMutuelle() {
		return numMutuelle;
	}

	public void setNumMutuelle(String numMutuelle) {
		this.numMutuelle = numMutuelle;
	}

	public String getNumCre() {
		return numCre;
	}

	public void setNumCre(String numCre) {
		this.numCre = numCre;
	}

	public String getNumRue() {
		return numRue;
	}

	public void setNumRue(String numRue) {
		this.numRue = numRue;
	}

	public String getBisTer() {
		return bisTer;
	}

	public void setBisTer(String bisTer) {
		this.bisTer = bisTer;
	}

	public String getNomRue() {
		return nomRue;
	}

	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}

	public Integer getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(Integer codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

}
