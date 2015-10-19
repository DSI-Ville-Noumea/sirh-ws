package nc.noumea.mairie.web.dto.avancements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.XmlRootElement;

import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spclas;
import nc.noumea.mairie.model.bean.Speche;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AvancementDetache;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.web.dto.EntiteDto;

@XmlRootElement
public class ArreteDto {

	private boolean changementClasse;
	private int annee;
	private String nomComplet;
	private boolean regularisation;
	private String deliberationLabel;
	private String deliberationCapText;
	private String numeroArrete;
	private Date dateArrete;
	private Date dateCap;
	private String dateAvct;
	private String dureeAvct;
	private String gradeLabel;
	private Integer ina;
	private String ib;
	private String acc;
	private boolean feminin;
	private String directionAgent;
	private String matriculeAgent;
	private String baseReglement;
	private String serviceAgent;
	private boolean agentVDN;
	private String filiere;

	public ArreteDto() {
		super();
	}

	public ArreteDto(AvancementFonctionnaire avct, FichePoste fp, Spcarr carr, Spclas classeGrade, Speche echelonGrade, EntiteDto direction, EntiteDto service) throws ParseException {
		this.agentVDN = avct.isAgentVDN();
		// #19171 : on affiche le statut de l'agent maintenant
		this.baseReglement = carr.getCategorie().getCodeCategorie().toString().equals("4") ? "C" : carr.getCategorie().getCodeCategorie().toString().equals("7") ? "CC" : "F";
		this.matriculeAgent = avct.getAgent().getNomatr().toString();
		this.annee = avct.getAnneeAvancement();
		this.nomComplet = getNomCompletAgent(avct.getAgent());
		this.changementClasse = avct.getIdModifAvancement() == null ? false : avct.getIdModifAvancement() == 7 || avct.getIdModifAvancement() == 6 ? false : true;
		this.regularisation = avct.isRegularisation();
		this.deliberationLabel = avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale() == null ? "" : avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale()
				.getLibDeliberation().toLowerCase().replace("/cp", "/CP");
		this.deliberationCapText = avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale() == null ? "" : avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale()
				.getTexteCap();
		this.dateCap = avct.getDateCap();
		SimpleDateFormat dateMoisAnnee = new SimpleDateFormat("MMMM y", new Locale("fr", "FR"));
		SimpleDateFormat date = new SimpleDateFormat("d MMMM y", new Locale("fr", "FR"));
		SimpleDateFormat jourDate = new SimpleDateFormat("d", new Locale("fr", "FR"));
		if (getDateAvancement(avct) != null) {
			if (jourDate.format(getDateAvancement(avct)).equals("1")) {
				this.dateAvct = "1er " + dateMoisAnnee.format(getDateAvancement(avct));
			} else {
				this.dateAvct = date.format(getDateAvancement(avct));
			}
		}

		this.dureeAvct = avct.getAvisCapEmployeur() == null ? "" : getTexteDureeAvct(avct.getAvisCapEmployeur().getLibLong().toLowerCase());

		String classe = classeGrade == null || classeGrade.getLibCla().trim().equals("") ? "" : ", " + classeGrade.getLibCla().trim();
		if (classe.contains("1°")) {
			classe = classe.replace("1°", "1er");
		}
		String echelon = echelonGrade == null || echelonGrade.getLibEch().trim().equals("") ? "" : ", " + echelonGrade.getLibEch().trim();
		if (echelon.contains("1°")) {
			echelon = echelon.replace("1°", "1er");
		}
		String grade = avct.getGradeNouveau().getGradeInitial().trim();
		if (grade.contains("1°")) {
			grade = grade.replace("1°", "1er");
		}
		String libelleGrade = grade + classe + echelon;

		String filiere = avct.getGradeNouveau().getGradeGenerique().getFiliere() == null ? "" : " de la filière "
				+ avct.getGradeNouveau().getGradeGenerique().getFiliere().getLibelleFili().trim().toLowerCase();

		this.filiere = avct.getGradeNouveau().getGradeGenerique().getFiliere() == null ? "" : avct.getGradeNouveau().getGradeGenerique().getFiliere().getLibelleFili().trim().toUpperCase();

		this.gradeLabel = (libelleGrade.toLowerCase().startsWith("a") || libelleGrade.toLowerCase().startsWith("e") || libelleGrade.toLowerCase().startsWith("i")
				|| libelleGrade.toLowerCase().startsWith("o") || libelleGrade.toLowerCase().startsWith("u") ? "d'" + libelleGrade.replace("°", "ème").toLowerCase().replace("echelon", "échelon")
				: "de " + libelleGrade.replace("°", "ème").toLowerCase().replace("echelon", "échelon")) + filiere;
		this.ina = avct.getGradeNouveau().getBarem().getIna();
		if (avct.getGradeNouveau().getBarem().getIban().startsWith("0")) {
			String res = avct.getGradeNouveau().getBarem().getIban();
			while (res.startsWith("0")) {
				res = res.substring(1, res.length());
			}
			this.ib = res;
		} else {
			this.ib = avct.getGradeNouveau().getBarem().getIban();
		}
		this.feminin = avct.getAgent().getTitre().equals("Monsieur") ? false : true;
		this.numeroArrete = carr.getReferenceArrete().toString().equals("0") ? "" : "20" + carr.getReferenceArrete().toString().substring(0, 2) + "/"
				+ carr.getReferenceArrete().toString().substring(2, carr.getReferenceArrete().toString().length());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		this.dateArrete = carr.getDateArrete() == 0 ? null : sdf.parse(carr.getDateArrete().toString());
		this.acc = avct.getAccRestant();
		this.directionAgent = direction != null ? " (" + direction.getSigle() + ")" : "";
		this.serviceAgent = service != null ? direction.getSigle() + " (" + service.getSigle() + ")" : "";

	}

	private String getTexteDureeAvct(String dureeAvct) {
		if (dureeAvct.equals("minimum")) {
			return "minimale";
		} else if (dureeAvct.equals("maximum")) {
			return "maximale";
		}
		return dureeAvct;
	}

	public ArreteDto(AvancementDetache avct, FichePoste fp, Spcarr carr, Spclas classeGrade, Speche echelonGrade, EntiteDto direction, EntiteDto service) throws ParseException {
		this.agentVDN = avct.isAgentVDN();
		// #19171 : on affiche le statut de l'agent maintenant
		this.baseReglement = carr.getCategorie().getCodeCategorie().toString().equals("4") ? "C" : carr.getCategorie().getCodeCategorie().toString().equals("7") ? "CC" : "F";
		this.matriculeAgent = avct.getAgent().getNomatr().toString();
		this.annee = avct.getAnneeAvancement();
		this.nomComplet = getNomCompletAgent(avct.getAgent());
		this.changementClasse = avct.getIdModifAvancement() == null ? false : avct.getIdModifAvancement() == 7 || avct.getIdModifAvancement() == 6 ? false : true;
		this.regularisation = avct.isRegularisation();
		this.deliberationLabel = avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale() == null ? "" : avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale()
				.getLibDeliberation().toLowerCase().replace("/cp", "/CP");
		this.deliberationCapText = avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale() == null ? "" : avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale()
				.getTexteCap();
		this.dateCap = null;
		SimpleDateFormat dateMoisAnnee = new SimpleDateFormat("MMMM y", new Locale("fr", "FR"));
		SimpleDateFormat date = new SimpleDateFormat("d MMMM y", new Locale("fr", "FR"));
		SimpleDateFormat jourDate = new SimpleDateFormat("d", new Locale("fr", "FR"));
		if (avct.getDateAvctMoy() != null) {
			if (jourDate.format(avct.getDateAvctMoy().getDay()).equals("1")) {
				this.dateAvct = "1er " + dateMoisAnnee.format(avct.getDateAvctMoy());
			} else {
				this.dateAvct = date.format(avct.getDateAvctMoy());
			}
		}

		this.dureeAvct = "";

		String classe = classeGrade == null || classeGrade.getLibCla().trim().equals("") ? "" : ", " + classeGrade.getLibCla().trim();
		if (classe.contains("1°")) {
			classe = classe.replace("1°", "1er");
		}
		String echelon = echelonGrade == null || echelonGrade.getLibEch().trim().equals("") ? "" : ", " + echelonGrade.getLibEch().trim();
		if (echelon.contains("1°")) {
			echelon = echelon.replace("1°", "1er");
		}
		String grade = avct.getGradeNouveau().getGradeInitial().trim();
		if (grade.contains("1°")) {
			grade = grade.replace("1°", "1er");
		}
		String libelleGrade = grade + classe + echelon;

		String filiere = avct.getGradeNouveau().getGradeGenerique().getFiliere() == null ? "" : " de la filière "
				+ avct.getGradeNouveau().getGradeGenerique().getFiliere().getLibelleFili().trim().toLowerCase();

		this.filiere = avct.getGradeNouveau().getGradeGenerique().getFiliere() == null ? "" : avct.getGradeNouveau().getGradeGenerique().getFiliere().getLibelleFili().trim().toUpperCase();
		
		this.gradeLabel = (libelleGrade.toLowerCase().startsWith("a") || libelleGrade.toLowerCase().startsWith("e") || libelleGrade.toLowerCase().startsWith("i")
				|| libelleGrade.toLowerCase().startsWith("o") || libelleGrade.toLowerCase().startsWith("u") ? "d'" + libelleGrade.replace("°", "ème").toLowerCase().replace("echelon", "échelon")
				: "de " + libelleGrade.replace("°", "ème").toLowerCase().replace("echelon", "échelon")) + filiere;

		this.ina = avct.getGradeNouveau().getBarem().getIna();
		if (avct.getGradeNouveau().getBarem().getIban().startsWith("0")) {
			String res = avct.getGradeNouveau().getBarem().getIban();
			while (res.startsWith("0")) {
				res = res.substring(1, res.length());
			}
			this.ib = res;
		} else {
			this.ib = avct.getGradeNouveau().getBarem().getIban();
		}
		this.feminin = avct.getAgent().getTitre().equals("Monsieur") ? false : true;
		this.numeroArrete = carr.getReferenceArrete().toString().equals("0") ? "" : "20" + carr.getReferenceArrete().toString().substring(0, 2) + "/"
				+ carr.getReferenceArrete().toString().substring(2, carr.getReferenceArrete().toString().length());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		this.dateArrete = carr.getDateArrete() == 0 ? null : sdf.parse(carr.getDateArrete().toString());
		this.acc = avct.getAccRestant();
		this.directionAgent = direction != null ? " (" + direction.getSigle() + ")" : "";
		this.serviceAgent = service != null ? direction.getSigle() + " (" + service.getSigle() + ")" : "";
	}

	private Date getDateAvancement(AvancementFonctionnaire avct) {
		if (avct != null && avct.getAvisCapEmployeur() != null) {
			switch (avct.getAvisCapEmployeur().getIdAvisCap()) {
				case 1:
					return avct.getDateAvctMini();
				case 3:
					return avct.getDateAvctMaxi();
				default:
					return avct.getDateAvctMoy();
			}
		} else {
			return null;
		}
	}

	private String getNomCompletAgent(Agent agent) {
		return agent.getTitre() + " " + agent.getPrenomUsage().substring(0, 1).toUpperCase() + agent.getPrenomUsage().substring(1, agent.getPrenomUsage().length()).toLowerCase() + " "
				+ agent.getNomUsage().toUpperCase();
	}

	public boolean isChangementClasse() {
		return changementClasse;
	}

	public void setChangementClasse(boolean changementClasse) {
		this.changementClasse = changementClasse;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getNomComplet() {
		return nomComplet;
	}

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

	public boolean isRegularisation() {
		return regularisation;
	}

	public void setRegularisation(boolean regularisation) {
		this.regularisation = regularisation;
	}

	public String getDeliberationLabel() {
		return deliberationLabel;
	}

	public void setDeliberationLabel(String deliberationLabel) {
		this.deliberationLabel = deliberationLabel;
	}

	public String getDeliberationCapText() {
		return deliberationCapText;
	}

	public void setDeliberationCapText(String deliberationCapText) {
		this.deliberationCapText = deliberationCapText;
	}

	public String getNumeroArrete() {
		return numeroArrete;
	}

	public void setNumeroArrete(String numeroArrete) {
		this.numeroArrete = numeroArrete;
	}

	public Date getDateArrete() {
		return dateArrete;
	}

	public void setDateArrete(Date dateArrete) {
		this.dateArrete = dateArrete;
	}

	public Date getDateCap() {
		return dateCap;
	}

	public void setDateCap(Date dateCap) {
		this.dateCap = dateCap;
	}

	public String getDateAvct() {
		return dateAvct;
	}

	public void setDateAvct(String dateAvct) {
		this.dateAvct = dateAvct;
	}

	public String getDureeAvct() {
		return dureeAvct;
	}

	public void setDureeAvct(String dureeAvct) {
		this.dureeAvct = dureeAvct;
	}

	public String getGradeLabel() {
		return gradeLabel;
	}

	public void setGradeLabel(String gradeLabel) {
		this.gradeLabel = gradeLabel;
	}

	public Integer getIna() {
		return ina;
	}

	public void setIna(Integer ina) {
		this.ina = ina;
	}

	public String getIb() {
		return ib;
	}

	public void setIb(String ib) {
		this.ib = ib;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public boolean isFeminin() {
		return feminin;
	}

	public void setFeminin(boolean feminin) {
		this.feminin = feminin;
	}

	public String getDirectionAgent() {
		return directionAgent;
	}

	public void setDirectionAgent(String directionAgent) {
		this.directionAgent = directionAgent;
	}

	public String getMatriculeAgent() {
		return matriculeAgent;
	}

	public void setMatriculeAgent(String matriculeAgent) {
		this.matriculeAgent = matriculeAgent;
	}

	public String getBaseReglement() {
		return baseReglement;
	}

	public void setBaseReglement(String baseReglement) {
		this.baseReglement = baseReglement;
	}

	public String getServiceAgent() {
		return serviceAgent;
	}

	public void setServiceAgent(String serviceAgent) {
		this.serviceAgent = serviceAgent;
	}

	public boolean isAgentVDN() {
		return agentVDN;
	}

	public void setAgentVDN(boolean agentVDN) {
		this.agentVDN = agentVDN;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
}
