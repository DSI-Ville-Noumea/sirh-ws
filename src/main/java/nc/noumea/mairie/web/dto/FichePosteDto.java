package nc.noumea.mairie.web.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import nc.noumea.mairie.model.bean.Activite;
import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.AvantageNature;
import nc.noumea.mairie.model.bean.Competence;
import nc.noumea.mairie.model.bean.Delegation;
import nc.noumea.mairie.model.bean.FicheEmploi;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.PrimePointageFP;
import nc.noumea.mairie.model.bean.RegimeIndemnitaire;

@XmlRootElement
public class FichePosteDto {

	private Integer idFichePoste;
	private String numero;
	private String titre;
	private String direction;
	private String budget;
	private String budgete;
	private String reglementaire;
	private String cadreEmploi;
	private String niveauEtudes;
	private String codeService;
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

	// informations complementaires pour l impression FDP SIRH
	private String dateDebutAffectation;
	private String agent;
	private String categorie;
	private String filiere;
	private String superieurHierarchiqueFP;
	private String superieurHierarchiqueAgent;
	private String remplaceFP;
	private String remplaceAgent;
	private String emploiPrimaire;
	private String emploiSecondaire;
	private String NFA;
	private String OPI;
	private String anneeEmploi;
	private String statutFDP;
	private String natureCredit;

	private List<String> avantages;
	private List<String> delegations;
	private List<String> regimesIndemnitaires;
	private List<String> primes;

	private TitrePosteDto titrePoste;
	
	private Integer idAgent;

	public FichePosteDto() {
		activites = new ArrayList<String>();
		savoirs = new ArrayList<String>();
		savoirsFaire = new ArrayList<String>();
		comportementsProfessionnels = new ArrayList<String>();

		avantages = new ArrayList<String>();
		delegations = new ArrayList<String>();
		regimesIndemnitaires = new ArrayList<String>();
		primes = new ArrayList<String>();
	}

	public FichePosteDto(FichePoste fichePoste) {
		this();
		idFichePoste = fichePoste.getIdFichePoste();
		numero = fichePoste.getNumFP();

		if (null != fichePoste.getAgent()) {
			for (Affectation agt : fichePoste.getAgent()) {
				if(null != agt.getAgent()) {
					idAgent = agt.getAgent().getIdAgent();
					break;
				}
			}
		}
		
		direction = fichePoste.getService().getDirection();
		titre = fichePoste.getTitrePoste().getLibTitrePoste();

		budget = fichePoste.getBudget().getLibelleBudget();
		budgete = fichePoste.getBudgete().getLibHor() == null ? "" : fichePoste.getBudgete().getLibHor().trim();
		reglementaire = fichePoste.getReglementaire().getLibHor() == null ? "" : fichePoste.getReglementaire()
				.getLibHor().trim();
		cadreEmploi = fichePoste.getGradePoste().getGradeGenerique().getCadreEmploiGrade() == null ? "" : fichePoste
				.getGradePoste().getGradeGenerique().getCadreEmploiGrade().getLibelleCadreEmploi();
		niveauEtudes = fichePoste.getNiveauEtude() != null ? fichePoste.getNiveauEtude().getLibelleNiveauEtude() : "";

		codeService  = fichePoste.getService() == null ? "" : fichePoste.getService().getServi();
		service = fichePoste.getService() == null ? "" : fichePoste.getService().getLiServ();

		section = fichePoste.getService().getSection();
		lieu = fichePoste.getLieuPoste().getLibelleLieu() == null ? "" : fichePoste.getLieuPoste().getLibelleLieu()
				.trim();
		gradePoste = fichePoste.getGradePoste().getGradeInitial() == null ? "" : fichePoste.getGradePoste()
				.getGradeInitial().trim();

		// superieur =
		// fichePoste.getResponsable().getTitrePoste().getLibTitrePoste();

		missions = fichePoste.getMissions();

		for (Activite act : fichePoste.getActivites())
			activites.add(act.getNomActivite());

		for (Competence cp : fichePoste.getCompetencesFDP()) {

			// 1 = Savoir
			if (cp.getTypeCompetence().getIdTypeCompetence().equals(1))
				savoirs.add(cp.getNomCompetence());
			// 2 = savoir faire
			if (cp.getTypeCompetence().getIdTypeCompetence().equals(2))
				savoirsFaire.add(cp.getNomCompetence());
			// 3 = comportement professionnel
			if (cp.getTypeCompetence().getIdTypeCompetence().equals(3))
				comportementsProfessionnels.add(cp.getNomCompetence());
		}
		
		for (FicheEmploi emploiPrim : fichePoste.getFicheEmploiPrimaire()) {
			emploiPrimaire = emploiPrim.getNomEmploi();
			break;
		}
		for (FicheEmploi emploiSec : fichePoste.getFicheEmploiSecondaire()) {
			emploiSecondaire = emploiSec.getNomEmploi();
			break;
		}
	}

	public FichePosteDto(FichePoste fichePoste, boolean isInfosCompl) {
		this(fichePoste);
		agent = "";
		dateDebutAffectation = "";
		categorie = "";
		filiere = "";
		superieurHierarchiqueFP = "";
		superieurHierarchiqueAgent = "";
		remplaceFP = "";
		remplaceAgent = "";
		emploiPrimaire = "";
		emploiSecondaire = "";
		NFA = "";
		OPI = "";
		anneeEmploi = "";
		natureCredit = "";

		statutFDP = fichePoste.getStatutFP().getLibStatut();

		if (fichePoste.getNatureCredit() != null) {
			natureCredit = fichePoste.getNatureCredit().getLibNatureCredit();
		}
		
		if (null != fichePoste.getAgent()) {
			for (Affectation agt : fichePoste.getAgent()) {
				agent = agt.getAgent().getDisplayNom()
						+ " "
						+ agt.getAgent().getDisplayPrenom().substring(0, 1).toUpperCase()
						+ agt.getAgent().getDisplayPrenom().substring(1, agt.getAgent().getDisplayPrenom().length())
								.toLowerCase() + " (" + agt.getAgent().getNomatr().toString() + ")";
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				dateDebutAffectation = agt.getDateDebutAff() == null ? "" : sdf.format(agt.getDateDebutAff());
			}
		}

		if (null != fichePoste.getGradePoste()) {
			categorie = fichePoste.getGradePoste().getGradeGenerique().getCdcadr().trim();

			if (null != fichePoste.getGradePoste().getGradeGenerique()
					&& null != fichePoste.getGradePoste().getGradeGenerique().getFiliere()) {
				filiere = fichePoste.getGradePoste().getGradeGenerique().getFiliere().getLibelleFili().trim();
			}
		}

		if (null != fichePoste.getSuperieurHierarchique()) {
			superieurHierarchiqueFP = fichePoste.getSuperieurHierarchique().getNumFP()
					+ " "
					+ fichePoste.getSuperieurHierarchique().getTitrePoste().getLibTitrePoste().substring(0, 1)
							.toUpperCase()
					+ fichePoste
							.getSuperieurHierarchique()
							.getTitrePoste()
							.getLibTitrePoste()
							.substring(1,
									fichePoste.getSuperieurHierarchique().getTitrePoste().getLibTitrePoste().length())
							.toLowerCase();

			if (null != fichePoste.getSuperieurHierarchique().getAgent()) {
				for (Affectation supHierar : fichePoste.getSuperieurHierarchique().getAgent()) {
					superieurHierarchiqueAgent = supHierar.getAgent().getDisplayNom()
							+ " "
							+ supHierar.getAgent().getDisplayPrenom().substring(0, 1).toUpperCase()
							+ supHierar.getAgent().getDisplayPrenom()
									.substring(1, supHierar.getAgent().getDisplayPrenom().length()).toLowerCase()
							+ " (" + supHierar.getAgent().getNomatr().toString() + ")";
					break;
				}
			}
		}

		if (null != fichePoste.getRemplace()) {
			remplaceFP = fichePoste.getRemplace().getNumFP()
					+ " "
					+ fichePoste.getRemplace().getTitrePoste().getLibTitrePoste().substring(0, 1).toUpperCase()
					+ fichePoste.getRemplace().getTitrePoste().getLibTitrePoste()
							.substring(1, fichePoste.getRemplace().getTitrePoste().getLibTitrePoste().length())
							.toLowerCase();

			if (null != fichePoste.getRemplace().getAgent()) {
				for (Affectation remplace : fichePoste.getRemplace().getAgent()) {
					remplaceAgent = remplace.getAgent().getDisplayNom()
							+ " "
							+ remplace.getAgent().getDisplayPrenom().substring(0, 1).toUpperCase()
							+ remplace.getAgent().getDisplayPrenom()
									.substring(1, remplace.getAgent().getDisplayPrenom().length()).toLowerCase() + " ("
							+ remplace.getAgent().getNomatr().toString() + ")";
					break;
				}
			}
		}

		for (FicheEmploi emploiPrim : fichePoste.getFicheEmploiPrimaire()) {
			emploiPrimaire = emploiPrim.getNomEmploi();
			break;
		}
		for (FicheEmploi emploiSec : fichePoste.getFicheEmploiSecondaire()) {
			emploiSecondaire = emploiSec.getNomEmploi();
			break;
		}

		NFA = fichePoste.getNfa() == null ? "" : fichePoste.getNfa();
		OPI = fichePoste.getOpi() == null ? "" : fichePoste.getOpi();
		if (null != fichePoste.getAnnee()) {
			anneeEmploi = fichePoste.getAnnee().toString();
		}

		for (AvantageNature avg : fichePoste.getAvantagesNature()) {
			String avantage = "Type: " + avg.getTypeAvantage().getLibTypeAvantage() + " - ";
			if (avg.getMontant() != null) {
				avantage += "Montant: " + avg.getMontant() + " - ";
			}
			avantage += "Nature: " + avg.getNatureAvantage().getLibNatureAvantage();
			avantages.add(avantage);
		}
		for (Delegation deleg : fichePoste.getDelegations()) {
			delegations.add("Type: " + deleg.getTypeDelegation().getLibTypeDelegation() + " - Commentaire: "
					+ deleg.getLibDelegation());
		}
		for (RegimeIndemnitaire reg : fichePoste.getRegimesIndemnitaires()) {
			regimesIndemnitaires.add("Type: " + reg.getTypeRegimeIndemnitaire().getLibTypeRegimeIndemnitaire()
					+ " - Forfait: " + reg.getForfait() + " - Nb Points: " + reg.getNombrePoint());
		}
		for (PrimePointageFP prime : fichePoste.getPrimePointageFP()) {
			primes.add(prime.getPrimePointageFPPK().getNumRubrique() + " - " + prime.getLibelle());
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

	public void setComportementsProfessionnels(List<String> comportementsProfessionnels) {
		this.comportementsProfessionnels = comportementsProfessionnels;
	}

	public String getDateDebutAffectation() {
		return dateDebutAffectation;
	}

	public void setDateDebutAffectation(String dateDebutAffectation) {
		this.dateDebutAffectation = dateDebutAffectation;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public String getSuperieurHierarchiqueFP() {
		return superieurHierarchiqueFP;
	}

	public void setSuperieurHierarchiqueFP(String superieurHierarchiqueFP) {
		this.superieurHierarchiqueFP = superieurHierarchiqueFP;
	}

	public String getSuperieurHierarchiqueAgent() {
		return superieurHierarchiqueAgent;
	}

	public void setSuperieurHierarchiqueAgent(String superieurHierarchiqueAgent) {
		this.superieurHierarchiqueAgent = superieurHierarchiqueAgent;
	}

	public String getRemplaceFP() {
		return remplaceFP;
	}

	public void setRemplaceFP(String remplaceFP) {
		this.remplaceFP = remplaceFP;
	}

	public String getRemplaceAgent() {
		return remplaceAgent;
	}

	public void setRemplaceAgent(String remplaceAgent) {
		this.remplaceAgent = remplaceAgent;
	}

	public String getEmploiPrimaire() {
		return emploiPrimaire;
	}

	public void setEmploiPrimaire(String emploiPrimaire) {
		this.emploiPrimaire = emploiPrimaire;
	}

	public String getEmploiSecondaire() {
		return emploiSecondaire;
	}

	public void setEmploiSecondaire(String emploiSecondaire) {
		this.emploiSecondaire = emploiSecondaire;
	}

	public String getNFA() {
		return NFA;
	}

	public void setNFA(String nFA) {
		NFA = nFA;
	}

	public String getOPI() {
		return OPI;
	}

	public void setOPI(String oPI) {
		OPI = oPI;
	}

	public String getAnneeEmploi() {
		return anneeEmploi;
	}

	public void setAnneeEmploi(String anneeEmploi) {
		this.anneeEmploi = anneeEmploi;
	}

	public List<String> getAvantages() {
		return avantages;
	}

	public void setAvantages(List<String> avantages) {
		this.avantages = avantages;
	}

	public List<String> getDelegations() {
		return delegations;
	}

	public void setDelegations(List<String> delegations) {
		this.delegations = delegations;
	}

	public List<String> getRegimesIndemnitaires() {
		return regimesIndemnitaires;
	}

	public void setRegimesIndemnitaires(List<String> regimesIndemnitaires) {
		this.regimesIndemnitaires = regimesIndemnitaires;
	}

	public List<String> getPrimes() {
		return primes;
	}

	public void setPrimes(List<String> primes) {
		this.primes = primes;
	}

	public String getStatutFDP() {
		return statutFDP;
	}

	public void setStatutFDP(String statutFDP) {
		this.statutFDP = statutFDP;
	}

	public String getNatureCredit() {
		return natureCredit;
	}

	public void setNatureCredit(String natureCredit) {
		this.natureCredit = natureCredit;
	}
	
	public TitrePosteDto getTitrePoste() {
		return titrePoste;
	}

	public void setTitrePoste(TitrePosteDto titrePoste) {
		this.titrePoste = titrePoste;
	}

	public String getCodeService() {
		return codeService;
	}

	public void setCodeService(String codeService) {
		this.codeService = codeService;
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}

}
