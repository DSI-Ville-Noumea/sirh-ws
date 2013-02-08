package nc.noumea.mairie.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import nc.noumea.mairie.model.bean.Activite;
import nc.noumea.mairie.model.bean.Competence;
import nc.noumea.mairie.model.bean.FichePoste;

@XmlRootElement
public class FichePosteDto {

	private String numero;
	private String titre;
	private String direction;
	private String budget;
	private String budgete;
	private String reglementaire;
	private String cadreEmploi;
	private String niveauEtudes;
	private String service;
	private String section;
	private String lieu;
	private String gradePoste;
	private String superieur;

	private String missions;
	
	private List<String> activites;
	private List<String> savoirs;
	private List<String> savoirsFaire;
	private List<String> comportementsProfessionnels;
	
	public FichePosteDto() {
		activites = new ArrayList<String>();
		savoirs = new ArrayList<String>();
		savoirsFaire = new ArrayList<String>();
		comportementsProfessionnels = new ArrayList<String>();
	}
	
	public FichePosteDto(FichePoste fichePoste) {
		this();
		
		numero = fichePoste.getNumFP();
		direction = fichePoste.getService().getDirection();
		titre = fichePoste.getTitrePoste().getLibTitrePoste();
		
		budget = fichePoste.getBudget().getLibelleBudget();
		budgete = fichePoste.getBudgete().getLibHor() == null ? "" : fichePoste.getBudgete().getLibHor().trim();
		reglementaire = fichePoste.getReglementaire().getLibHor() == null ? "" : fichePoste.getReglementaire().getLibHor().trim();
		cadreEmploi = fichePoste.getGradePoste().getGradeGenerique().getCadreEmploiGrade().getLibelleCadreEmploi();
		niveauEtudes = fichePoste.getNiveauEtude() != null ? fichePoste.getNiveauEtude().getLibelleNiveauEtude() : "";
		service = fichePoste.getService().getDivision() == null ? "" : fichePoste.getService().getDivision().trim();
		section = fichePoste.getService().getSection() == null ? "" : fichePoste.getService().getSection().trim();
		lieu = fichePoste.getLieuPoste().getLibelleLieu() == null ? "" : fichePoste.getLieuPoste().getLibelleLieu().trim();
		gradePoste = fichePoste.getGradePoste().getGradeInitial() == null ? "" : fichePoste.getGradePoste().getGradeInitial().trim();
		
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
				comportementsProfessionnels.add(cp.getNomCompetence());
		}
	}
	
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getBudgete() {
		return budgete;
	}

	public void setBudgete(String budgete) {
		this.budgete = budgete;
	}

	public String getReglementaire() {
		return reglementaire;
	}

	public void setReglementaire(String reglementaire) {
		this.reglementaire = reglementaire;
	}

	public String getCadreEmploi() {
		return cadreEmploi;
	}

	public void setCadreEmploi(String cadreEmploi) {
		this.cadreEmploi = cadreEmploi;
	}

	public String getNiveauEtudes() {
		return niveauEtudes;
	}

	public void setNiveauEtudes(String niveauEtudes) {
		this.niveauEtudes = niveauEtudes;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public String getGradePoste() {
		return gradePoste;
	}

	public void setGradePoste(String gradePoste) {
		this.gradePoste = gradePoste;
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

	public List<String> getComportementsProfessionnels() {
		return comportementsProfessionnels;
	}

	public void setComportementsProfessionnels(
			List<String> comportementsProfessionnels) {
		this.comportementsProfessionnels = comportementsProfessionnels;
	}
}
