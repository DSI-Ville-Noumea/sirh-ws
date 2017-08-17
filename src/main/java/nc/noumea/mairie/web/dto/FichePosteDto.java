package nc.noumea.mairie.web.dto;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

import nc.noumea.mairie.model.bean.sirh.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

@XmlRootElement
public class FichePosteDto {

	private Integer idFichePoste;
	private String numero;
	private String titre;
	private String direction;
	private String budget;
	private String budgete;
	private String reglementaire;
	private Double tauxETP;
	private String cadreEmploi;
	private String niveauEtudes;
	private Integer idServiceADS;
	private String service;
	private String sigle;
	private String section;
	private String lieu;
	private String gradePoste;
	private String superieur;

	private String missions;
	private String commentaire;

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
	// #17920
	private Integer idStatutFDP;
	private String natureCredit;

	private List<String> avantages;
	private List<String> delegations;
	private List<String> regimesIndemnitaires;
	private List<String> primes;

	private TitrePosteDto titrePoste;

	private Integer idAgent;

	//Version 2
	private Integer version;
	private String specialisation;
	private String informationsComplementaires;
	private Integer idNiveauManagement;
	private String niveauManagement;

	private List<ActiviteMetierSavoirFaire> activiteMetier = new ArrayList<>();
	private List<String> savoirFaireMetier = new ArrayList<>();
	private List<String> activiteGenerale = new ArrayList<>();
	private List<String> conditionExercice = new ArrayList<>();


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

	public FichePosteDto(FichePoste fichePoste, String direction, String service, String section, String sigle, List<Activite> listeActi, List<Competence> listeComp) {
		this();
		this.idFichePoste = fichePoste.getIdFichePoste();
		this.numero = fichePoste.getNumFP();
		this.commentaire = fichePoste.getObservation();

		this.statutFDP = fichePoste.getStatutFP() == null ? "" : fichePoste.getStatutFP().getLibStatut();
		this.idStatutFDP = fichePoste.getStatutFP() == null ? null : fichePoste.getStatutFP().getIdStatutFp();

		if (null != fichePoste.getAgent()) {
			for (Affectation agt : fichePoste.getAgent()) {
				if (null != agt.getAgent()) {
					this.idAgent = agt.getAgent().getIdAgent();
					break;
				}
			}
		}

		this.titre = fichePoste.getTitrePoste() == null ? "" : fichePoste.getTitrePoste().getLibTitrePoste();

		this.budget = fichePoste.getBudget() == null ? "" : fichePoste.getBudget().getLibelleBudget();
		try {
			budgete = null == fichePoste.getBudgete() || fichePoste.getBudgete().getLibHor() == null ? "" : fichePoste.getBudgete().getLibHor().trim();
		} catch (javax.persistence.EntityNotFoundException e) {
			this.budgete = "";
		}
		try {
			this.reglementaire = null == fichePoste.getReglementaire() || fichePoste.getReglementaire().getLibHor() == null ? "" : fichePoste.getReglementaire().getLibHor().trim();
			this.tauxETP = null == fichePoste.getReglementaire() || fichePoste.getReglementaire().getTaux() == null ? 0.0 : fichePoste.getReglementaire().getTaux();
		} catch (javax.persistence.EntityNotFoundException e) {
			this.reglementaire = "";
			this.tauxETP = 0.0;
		}

		this.cadreEmploi = fichePoste.getGradePoste() == null ? "" : fichePoste.getGradePoste().getGradeGenerique() == null
				|| fichePoste.getGradePoste().getGradeGenerique().getCadreEmploiGrade() == null ? "" : fichePoste.getGradePoste().getGradeGenerique().getCadreEmploiGrade().getLibelleCadreEmploi();
		this.niveauEtudes = fichePoste.getNiveauEtude() != null ? fichePoste.getNiveauEtude().getLibelleNiveauEtude() : "";

		this.idServiceADS = fichePoste.getIdServiceADS();
		this.direction = direction == null ? "" : direction;
		this.service = service == null ? "" : service;
		this.sigle = sigle == null ? "" : sigle;
		this.section = section == null ? "" : section;

		this.lieu = null == fichePoste.getLieuPoste() || fichePoste.getLieuPoste().getLibelleLieu() == null ? "" : fichePoste.getLieuPoste().getLibelleLieu().trim();
		this.gradePoste = fichePoste.getGradePoste() == null || fichePoste.getGradePoste().getGradeInitial() == null ? "" : fichePoste.getGradePoste().getGradeInitial().trim();

		// superieur =
		// fichePoste.getResponsable().getTitrePoste().getLibTitrePoste();

		this.missions = fichePoste.getMissions();

		for (Activite act : listeActi) {
			this.activites.add(act.getNomActivite());
		}

		for (Competence cp : listeComp) {
			if (cp.getTypeCompetence() != null) {
				// 1 = Savoir
				if (cp.getTypeCompetence().getIdTypeCompetence().equals(1))
					this.savoirs.add(cp.getNomCompetence());
				// 2 = savoir faire
				if (cp.getTypeCompetence().getIdTypeCompetence().equals(2))
					this.savoirsFaire.add(cp.getNomCompetence());
				// 3 = comportement professionnel
				if (cp.getTypeCompetence().getIdTypeCompetence().equals(3))
					this.comportementsProfessionnels.add(cp.getNomCompetence());
			}
		}

		for (FicheEmploi emploiPrim : fichePoste.getFicheEmploiPrimaire()) {
			this.emploiPrimaire = emploiPrim.getNomEmploi();
			break;
		}
		for (FicheEmploi emploiSec : fichePoste.getFicheEmploiSecondaire()) {
			emploiSecondaire = emploiSec.getNomEmploi();
			break;
		}

		//Version 2
		//TODOSIRH: check que ca fonctionne avec fiche migrée sans les anciennes références
		this.version = (fichePoste.getFicheMetierPrimaire().isEmpty()) ? 1 : 2;
		this.informationsComplementaires = fichePoste.getInformationsComplementaires();
		this.specialisation = fichePoste.getSpecialisation();
		this.idNiveauManagement = fichePoste.getNiveauManagement().getIdNiveauManagement();
		this.niveauManagement = fichePoste.getNiveauManagement().getLibNiveauManagement();
		for (SavoirFaireFp sf : fichePoste.getSavoirFaire()) {
			savoirFaireMetier.add(sf.getSavoirFaireByIdSavoirFaire().getNomSavoirFaire());
		}
		for (ActiviteGeneraleFp ag : fichePoste.getActivitesGenerales()) {
			activiteGenerale.add(ag.getActiviteGeneraleByIdActiviteGenerale().getNomActiviteGenerale());
		}
		for (ConditionExerciceFp ce : fichePoste.getConditionsExercice()) {
			conditionExercice.add(ce.getConditionExerciceByIdConditionExercice().getNomConditionExercice());
		}
		for (ActiviteMetierSavoirFp amsf : fichePoste.getActiviteMetier()) {
			String nomActiviteMetier = amsf.getActiviteMetierByIdActiviteMetier().getNomActiviteMetier();
			String nomSavoirFaire = amsf.getSavoirFaireByIdSavoirFaire() != null ? amsf.getSavoirFaireByIdSavoirFaire().getNomSavoirFaire() : null;
			activiteMetier.add(new ActiviteMetierSavoirFaire(nomActiviteMetier, nomSavoirFaire));
		}
	}

	public FichePosteDto(Integer idServiceADS, Integer idFichePoste, Set<Affectation> agent) {
		this();
		this.idFichePoste = idFichePoste;
		this.idServiceADS = idServiceADS;
		if (null != agent) {
			for (Affectation agt : agent) {
				if (null != agt && null != agt.getAgent()) {
					this.idAgent = agt.getAgent().getIdAgent();
					break;
				}
			}
		}
	}

	public FichePosteDto(FichePoste fichePoste, boolean isInfosCompl, String direction, String service, String section, String sigle, List<Activite> listActi, List<Competence> listeComp) {
		this(fichePoste, direction, service, section, sigle, listActi, listeComp);
		this.agent = "";
		this.dateDebutAffectation = "";
		this.categorie = "";
		this.filiere = "";
		this.superieurHierarchiqueFP = "";
		this.superieurHierarchiqueAgent = "";
		this.remplaceFP = "";
		this.remplaceAgent = "";
		this.emploiPrimaire = "";
		this.emploiSecondaire = "";
		this.NFA = "";
		this.OPI = "";
		this.anneeEmploi = "";
		this.natureCredit = "";

		this.statutFDP = fichePoste.getStatutFP().getLibStatut();
		this.idStatutFDP = fichePoste.getStatutFP() == null ? null : fichePoste.getStatutFP().getIdStatutFp();

		if (fichePoste.getNatureCredit() != null) {
			this.natureCredit = fichePoste.getNatureCredit().getLibNatureCredit();
		}

		if (null != fichePoste.getAgent()) {
			for (Affectation agt : fichePoste.getAgent()) {
				this.agent = agt.getAgent().getDisplayNom() + " " + agt.getAgent().getDisplayPrenom().substring(0, 1).toUpperCase()
						+ agt.getAgent().getDisplayPrenom().substring(1, agt.getAgent().getDisplayPrenom().length()).toLowerCase() + " (" + agt.getAgent().getNomatr().toString() + ")";
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				this.dateDebutAffectation = agt.getDateDebutAff() == null ? "" : sdf.format(agt.getDateDebutAff());
			}
		}

		if (null != fichePoste.getGradePoste()) {
			this.categorie = fichePoste.getGradePoste().getGradeGenerique().getCdcadr().trim();

			if (null != fichePoste.getGradePoste().getGradeGenerique() && null != fichePoste.getGradePoste().getGradeGenerique().getFiliere()) {
				try {
					this.filiere = fichePoste.getGradePoste().getGradeGenerique().getFiliere().getLibelleFili().trim();
				} catch (Exception e) {
				}
			}
		}

		if (null != fichePoste.getSuperieurHierarchique()) {
			this.superieurHierarchiqueFP = fichePoste.getSuperieurHierarchique().getNumFP()
					+ " "
					+ fichePoste.getSuperieurHierarchique().getTitrePoste().getLibTitrePoste().substring(0, 1).toUpperCase()
					+ fichePoste.getSuperieurHierarchique().getTitrePoste().getLibTitrePoste().substring(1, fichePoste.getSuperieurHierarchique().getTitrePoste().getLibTitrePoste().length())
							.toLowerCase();

			if (null != fichePoste.getSuperieurHierarchique().getAgent()) {
				for (Affectation supHierar : fichePoste.getSuperieurHierarchique().getAgent()) {
					this.superieurHierarchiqueAgent = supHierar.getAgent().getDisplayNom() + " " + supHierar.getAgent().getDisplayPrenom().substring(0, 1).toUpperCase()
							+ supHierar.getAgent().getDisplayPrenom().substring(1, supHierar.getAgent().getDisplayPrenom().length()).toLowerCase() + " (" + supHierar.getAgent().getNomatr().toString()
							+ ")";
					break;
				}
			}
		}

		if (null != fichePoste.getRemplace()) {
			this.remplaceFP = fichePoste.getRemplace().getNumFP() + " " + fichePoste.getRemplace().getTitrePoste().getLibTitrePoste().substring(0, 1).toUpperCase()
					+ fichePoste.getRemplace().getTitrePoste().getLibTitrePoste().substring(1, fichePoste.getRemplace().getTitrePoste().getLibTitrePoste().length()).toLowerCase();

			if (null != fichePoste.getRemplace().getAgent()) {
				for (Affectation remplace : fichePoste.getRemplace().getAgent()) {
					this.remplaceAgent = remplace.getAgent().getDisplayNom() + " " + remplace.getAgent().getDisplayPrenom().substring(0, 1).toUpperCase()
							+ remplace.getAgent().getDisplayPrenom().substring(1, remplace.getAgent().getDisplayPrenom().length()).toLowerCase() + " (" + remplace.getAgent().getNomatr().toString()
							+ ")";
					break;
				}
			}
		}

		for (FicheEmploi emploiPrim : fichePoste.getFicheEmploiPrimaire()) {
			this.emploiPrimaire = emploiPrim.getNomEmploi();
			break;
		}
		for (FicheEmploi emploiSec : fichePoste.getFicheEmploiSecondaire()) {
			this.emploiSecondaire = emploiSec.getNomEmploi();
			break;
		}

		this.NFA = fichePoste.getNfa() == null ? "" : fichePoste.getNfa();
		this.OPI = fichePoste.getOpi() == null ? "" : fichePoste.getOpi();
		if (null != fichePoste.getAnnee()) {
			this.anneeEmploi = fichePoste.getAnnee().toString();
		}

		for (AvantageNature avg : fichePoste.getAvantagesNature()) {
			String avantage = "Type: " + avg.getTypeAvantage().getLibTypeAvantage() + " - ";
			if (avg.getMontant() != null) {
				avantage += "Montant: " + avg.getMontant() + " - ";
			}
			avantage += "Nature: " + avg.getNatureAvantage().getLibNatureAvantage();
			this.avantages.add(avantage);
		}
		for (Delegation deleg : fichePoste.getDelegations()) {
			this.delegations.add("Type: " + deleg.getTypeDelegation().getLibTypeDelegation() + " - Commentaire: " + deleg.getLibDelegation());
		}
		for (RegimeIndemnitaire reg : fichePoste.getRegimesIndemnitaires()) {
			this.regimesIndemnitaires.add("Type: " + reg.getTypeRegimeIndemnitaire().getLibTypeRegimeIndemnitaire() + " - Forfait: " + reg.getForfait() + " - Nb Points: " + reg.getNombrePoint());
		}
		for (PrimePointageFP prime : fichePoste.getPrimePointageFP()) {
			this.primes.add(prime.getPrimePointageFPPK().getNumRubrique() + " - " + prime.getLibelle());
		}
	}

	public FichePosteDto(FichePoste fichePoste, String sigle, String libEntite) {
		this.idFichePoste = fichePoste.getIdFichePoste();
		this.numero = fichePoste.getNumFP();
		this.commentaire = fichePoste.getObservation();

		this.statutFDP = fichePoste.getStatutFP() == null ? "" : fichePoste.getStatutFP().getLibStatut();
		this.idStatutFDP = fichePoste.getStatutFP() == null ? null : fichePoste.getStatutFP().getIdStatutFp();

		if (null != fichePoste.getAgent()) {
			for (Affectation agt : fichePoste.getAgent()) {
				if (null != agt.getAgent()) {
					this.idAgent = agt.getAgent().getIdAgent();
					break;
				}
			}
		}

		this.titre = fichePoste.getTitrePoste() == null ? "" : fichePoste.getTitrePoste().getLibTitrePoste();

		try {
			this.reglementaire = null == fichePoste.getReglementaire() || fichePoste.getReglementaire().getLibHor() == null ? "" : fichePoste.getReglementaire().getLibHor().trim();
			this.tauxETP = null == fichePoste.getReglementaire() || fichePoste.getReglementaire().getTaux() == null ? 0.0 : fichePoste.getReglementaire().getTaux();
		} catch (javax.persistence.EntityNotFoundException e) {
			this.reglementaire = "";
			this.tauxETP = 0.0;
		}

		this.idServiceADS = fichePoste.getIdServiceADS();
		this.sigle = sigle == null ? "" : sigle;
		this.service = libEntite == null ? "" : libEntite;
		this.gradePoste = fichePoste.getGradePoste() == null || fichePoste.getGradePoste().getGradeInitial() == null ? "" : fichePoste.getGradePoste().getGradeInitial().trim();
		this.agent = "";
		this.categorie = "";

		if (null != fichePoste.getAgent()) {
			for (Affectation agt : fichePoste.getAgent()) {
				this.agent = agt.getAgent().getDisplayNom() + " " + agt.getAgent().getDisplayPrenom().substring(0, 1).toUpperCase()
						+ agt.getAgent().getDisplayPrenom().substring(1, agt.getAgent().getDisplayPrenom().length()).toLowerCase() + " (" + agt.getAgent().getNomatr().toString() + ")";
			}
		}

		if (null != fichePoste.getGradePoste() && null != fichePoste.getGradePoste().getGradeGenerique() && null != fichePoste.getGradePoste().getGradeGenerique().getCdcadr()) {
			this.categorie = fichePoste.getGradePoste().getGradeGenerique().getCdcadr().trim();
		}
		// #20579 pour ORGANIGRAMME > YED
		if (null != fichePoste.getGradePoste() && null != fichePoste.getGradePoste().getGradeGenerique() && null != fichePoste.getGradePoste().getGradeGenerique().getFiliere()) {
			try {
				this.filiere = fichePoste.getGradePoste().getGradeGenerique().getFiliere().getLibelleFili().trim();
			} catch (Exception e) {
			}
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

	public Integer getIdServiceADS() {
		return idServiceADS;
	}

	public void setIdServiceADS(Integer idServiceADS) {
		this.idServiceADS = idServiceADS;
	}

	public String getSigle() {
		return sigle;
	}

	public void setSigle(String sigle) {
		this.sigle = sigle;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Integer getIdStatutFDP() {
		return idStatutFDP;
	}

	public void setIdStatutFDP(Integer idStatutFDP) {
		this.idStatutFDP = idStatutFDP;
	}

	public Double getTauxETP() {
		return tauxETP;
	}

	public void setTauxETP(Double tauxETP) {
		this.tauxETP = tauxETP;
	}

	public String getSpecialisation() {
		return specialisation;
	}

	public void setSpecialisation(String specialisation) {
		this.specialisation = specialisation;
	}

	public String getInformationsComplementaires() {
		return informationsComplementaires;
	}

	public void setInformationsComplementaires(String informationsComplementaires) {
		this.informationsComplementaires = informationsComplementaires;
	}

	public Integer getIdNiveauManagement() {
		return idNiveauManagement;
	}

	public void setIdNiveauManagement(Integer idNiveauManagement) {
		this.idNiveauManagement = idNiveauManagement;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<String> getSavoirFaireMetier() {
		return savoirFaireMetier;
	}

	public void setSavoirFaireMetier(List<String> savoirFaireMetier) {
		this.savoirFaireMetier = savoirFaireMetier;
	}

	public List<String> getActiviteGenerale() {
		return activiteGenerale;
	}

	public void setActiviteGenerale(List<String> activiteGenerale) {
		this.activiteGenerale = activiteGenerale;
	}

	public List<String> getConditionExercice() {
		return conditionExercice;
	}

	public void setConditionExercice(List<String> conditionExercice) {
		this.conditionExercice = conditionExercice;
	}

	public List<ActiviteMetierSavoirFaire> getActiviteMetier() {
		return activiteMetier;
	}

	public void setActiviteMetier(List<ActiviteMetierSavoirFaire> activiteMetier) {
		this.activiteMetier = activiteMetier;
	}

	public String getNiveauManagement() {
		return niveauManagement;
	}

	public void setNiveauManagement(String niveauManagement) {
		this.niveauManagement = niveauManagement;
	}

	private static class ActiviteMetierSavoirFaire {
		private String activiteMetier;
		private String savoirFaire;

		public ActiviteMetierSavoirFaire(String activiteMetier, String savoirFaire) {
			this.activiteMetier = activiteMetier;
			this.savoirFaire = savoirFaire;
		}

		public String getActiviteMetier() {
			return activiteMetier;
		}

		public void setActiviteMetier(String activiteMetier) {
			this.activiteMetier = activiteMetier;
		}

		public String getSavoirFaire() {
			return savoirFaire;
		}

		public void setSavoirFaire(String savoirFaire) {
			this.savoirFaire = savoirFaire;
		}
	}
}
