package nc.noumea.mairie.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import nc.noumea.mairie.model.bean.Activite;
import nc.noumea.mairie.model.bean.Competence;
import nc.noumea.mairie.model.bean.FichePoste;

import org.joda.time.DateTime;

@XmlRootElement
public class FichePosteDto {

	private String directionService;
	private String section;
	private String filiereCadre;
	private String corpsGrade;
	private String categorie;
	private String fEPrimaire;
	private String fESecondaire;
	private Date derniereModification;
	private String intitulePoste;
	private String superieur;
	private String missions;
	
	private List<String> activites;
	private List<String> savoirs;
	private List<String> savoirsFaire;
	private List<String> aptitudes;
	
	public FichePosteDto() {
		activites = new ArrayList<String>();
		savoirs = new ArrayList<String>();
		savoirsFaire = new ArrayList<String>();
		aptitudes = new ArrayList<String>();
	}
	
	public FichePosteDto(FichePoste fichePoste) {
		this();
		
		directionService = String.format("%s - %s", fichePoste.getService().getDirection(), fichePoste.getService().getSigle());

		section = "emploi formation";
		filiereCadre = "pompiers / SIS";
		corpsGrade = "Corps / Grade";
		categorie = "A";
		fEPrimaire = "ABC100";
		fESecondaire = "DEF200";
		derniereModification = new DateTime(2047, 11, 1, 0, 0, 0).toDate();
		
		intitulePoste = fichePoste.getTitrePoste().getLibTitrePoste();
		superieur = fichePoste.getResponsable().getTitrePoste().getLibTitrePoste();
		
		missions = fichePoste.getMissions();
		
		for(Activite act : fichePoste.getActivites())
			activites.add(act.getNomActivite());
		
		for(Competence cp : fichePoste.getCompetencesFDP()) {
			// 1 = Savoir
			if(cp.getTypeCompetence().getIdTypeCompetence().equals(1))
				savoirs.add(cp.getNomCompetence());
			// 2 = savoir faire
			if(cp.getTypeCompetence().getIdTypeCompetence().equals(2))
				savoirsFaire.add(cp.getNomCompetence());
			// 3 = comportement professionnel
			if(cp.getTypeCompetence().getIdTypeCompetence().equals(3))
				aptitudes.add(cp.getNomCompetence());
		}
	}
	
	public String getDirectionService() {
		return directionService;
	}
	public void setDirectionService(String directionService) {
		this.directionService = directionService;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getFiliereCadre() {
		return filiereCadre;
	}
	public void setFiliereCadre(String filiereCadre) {
		this.filiereCadre = filiereCadre;
	}
	public String getCorpsGrade() {
		return corpsGrade;
	}
	public void setCorpsGrade(String corpsGrade) {
		this.corpsGrade = corpsGrade;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public String getfEPrimaire() {
		return fEPrimaire;
	}
	public void setfEPrimaire(String fEPrimaire) {
		this.fEPrimaire = fEPrimaire;
	}
	public String getfESecondaire() {
		return fESecondaire;
	}
	public void setfESecondaire(String fESecondaire) {
		this.fESecondaire = fESecondaire;
	}
	public Date getDerniereModification() {
		return derniereModification;
	}
	public void setDerniereModification(Date derniereModification) {
		this.derniereModification = derniereModification;
	}
	public String getIntitulePoste() {
		return intitulePoste;
	}
	public void setIntitulePoste(String intitulePoste) {
		this.intitulePoste = intitulePoste;
	}
	public String getSuperieur() {
		return superieur;
	}
	public void setSuperieur(String superieur) {
		this.superieur = superieur;
	}
	public String getMissions() {
		return missions;
	}
	public void setMissions(String missions) {
		this.missions = missions;
	}

	public List<String> getActivites() {
		return activites;
	}
	public void setActivites(List<String> activites) {
		this.activites = activites;
	}
	public List<String> getSavoirs() {
		return savoirs;
	}
	public void setSavoirs(List<String> savoirs) {
		this.savoirs = savoirs;
	}
	public List<String> getSavoirsFaire() {
		return savoirsFaire;
	}
	public void setSavoirsFaire(List<String> savoirsFaire) {
		this.savoirsFaire = savoirsFaire;
	}
	public List<String> getAptitudes() {
		return aptitudes;
	}
	public void setAptitudes(List<String> aptitudes) {
		this.aptitudes = aptitudes;
	}
}
