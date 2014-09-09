package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.sirh.Agent;

public class AdresseAgentDto {

	/*
	 * POUR LES PROJETS KIOSQUE J2EE
	 */

	private String bp;
	private String adresseComplementaire;
	private String numRue;
	private String bisTer;
	private String rue;
	private String villeDomicile;
	private Integer codePostalDomicile;
	private String villeBp;
	private Integer codePostalBp;

	public AdresseAgentDto(Agent ag) {
		super();
		if (ag != null) {
			this.bp = ag.getbP();
			this.adresseComplementaire = ag.getAdresseComplementaire();
			this.numRue = ag.getNumRue();
			this.bisTer = ag.getBisTer();
			this.rue = ag.getRue();
			this.villeDomicile = ag.getCodeCommuneVilleDom() == null ? null : ag.getCodeCommuneVilleDom().getLibVil()
					.trim();
			this.codePostalDomicile = ag.getCodePostalVilleDom() == null
					|| ag.getCodePostalVilleDom().toString().equals("0") ? null : ag.getCodePostalVilleDom();
			this.villeBp = ag.getCodeCommuneVilleBP() == null ? null : ag.getCodeCommuneVilleBP().getLibVil().trim();
			this.codePostalBp = ag.getCodePostalVilleBP() == null || ag.getCodePostalVilleBP().toString().equals("0") ? null
					: ag.getCodePostalVilleBP();
		}
	}

	public String getBp() {
		return bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
	}

	public String getAdresseComplementaire() {
		return adresseComplementaire;
	}

	public void setAdresseComplementaire(String adresseComplementaire) {
		this.adresseComplementaire = adresseComplementaire;
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

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVilleDomicile() {
		return villeDomicile;
	}

	public void setVilleDomicile(String villeDomicile) {
		this.villeDomicile = villeDomicile;
	}

	public Integer getCodePostalDomicile() {
		return codePostalDomicile;
	}

	public void setCodePostalDomicile(Integer codePostalDomicile) {
		this.codePostalDomicile = codePostalDomicile;
	}

	public String getVilleBp() {
		return villeBp;
	}

	public void setVilleBp(String villeBp) {
		this.villeBp = villeBp;
	}

	public Integer getCodePostalBp() {
		return codePostalBp;
	}

	public void setCodePostalBp(Integer codePostalBp) {
		this.codePostalBp = codePostalBp;
	}

}
