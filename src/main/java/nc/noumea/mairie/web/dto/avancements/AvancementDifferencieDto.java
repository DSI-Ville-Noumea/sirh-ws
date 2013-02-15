package nc.noumea.mairie.web.dto.avancements;

import java.util.ArrayList;
import java.util.List;

public class AvancementDifferencieDto {

	private int annee;
	private String deliberationLibelle;
	private String cadreEmploiLibelle;
	private String cap;
	private String filiere;
	private String categorie;
	private String employeur;
	private int nbAgents;
	private int quotaAvancementDureeMinimale;

	private List<AvancementDifferencieItemDto> avancementDifferencieItems;

	private String president;

	private List<String> employeurs;
	private List<String> representants;

	public AvancementDifferencieDto() {
		avancementDifferencieItems = new ArrayList<AvancementDifferencieItemDto>();
		employeurs = new ArrayList<String>();
		representants = new ArrayList<String>();
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getDeliberationLibelle() {
		return deliberationLibelle;
	}

	public void setDeliberationLibelle(String deliberationLibelle) {
		this.deliberationLibelle = deliberationLibelle;
	}

	public String getCadreEmploiLibelle() {
		return cadreEmploiLibelle;
	}

	public void setCadreEmploiLibelle(String cadreEmploiLibelle) {
		this.cadreEmploiLibelle = cadreEmploiLibelle;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getEmployeur() {
		return employeur;
	}

	public void setEmployeur(String employeur) {
		this.employeur = employeur;
	}

	public int getNbAgents() {
		return nbAgents;
	}

	public void setNbAgents(int nbAgents) {
		this.nbAgents = nbAgents;
	}

	public int getQuotaAvancementDureeMinimale() {
		return quotaAvancementDureeMinimale;
	}

	public void setQuotaAvancementDureeMinimale(int quotaAvancementDureeMinimale) {
		this.quotaAvancementDureeMinimale = quotaAvancementDureeMinimale;
	}

	public List<AvancementDifferencieItemDto> getAvancementDifferencieItems() {
		return avancementDifferencieItems;
	}

	public void setAvancementDifferencieItems(
			List<AvancementDifferencieItemDto> avancementDifferencieItems) {
		this.avancementDifferencieItems = avancementDifferencieItems;
	}

	public String getPresident() {
		return president;
	}

	public void setPresident(String president) {
		this.president = president;
	}

	public List<String> getEmployeurs() {
		return employeurs;
	}

	public void setEmployeurs(List<String> employeurs) {
		this.employeurs = employeurs;
	}

	public List<String> getRepresentants() {
		return representants;
	}

	public void setRepresentants(List<String> representants) {
		this.representants = representants;
	}
}
