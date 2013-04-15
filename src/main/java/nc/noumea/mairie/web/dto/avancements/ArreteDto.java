package nc.noumea.mairie.web.dto.avancements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.Spcarr;

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
	private Date dateAvct;
	private String dureeAvct;
	private String gradeLabel;
	private Integer ina;
	private String ib;
	private String acc;
	private boolean feminin;
	private String directionAgent;

	public ArreteDto() {

	}

	public ArreteDto(AvancementFonctionnaire avct, FichePoste fp, Spcarr carr) throws ParseException {
		this.annee = avct.getAnneeAvancement();
		this.nomComplet = getNomCompletAgent(avct.getAgent());
		this.changementClasse = avct.getIdModifAvancement() == null ? false : avct.getIdModifAvancement() == 7 || avct.getIdModifAvancement() == 6 ? false : true;
		this.regularisation = avct.isRegularisation();
		this.deliberationLabel = avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale() == null ? "" : avct.getGradeNouveau()
				.getGradeGenerique().getDeliberationCommunale().getLibDeliberation();
		this.deliberationCapText = avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale() == null ? "" : avct.getGradeNouveau()
				.getGradeGenerique().getDeliberationCommunale().getTexteCap();
		this.dateCap = avct.getDateCap();
		this.dateAvct = getDateAvancement(avct);
		this.dureeAvct = avct.getAvisCapEmployeur() == null ? "" : avct.getAvisCapEmployeur().getLibLong().toLowerCase();
		this.gradeLabel = avct.getGradeNouveau().getLiGrad().trim();
		this.ina = avct.getGradeNouveau().getBarem().getIna();
		this.ib = avct.getGradeNouveau().getBarem().getIban();
		this.feminin = avct.getAgent().getTitre().equals("Monsieur") ? false : true;
		this.numeroArrete = carr.getReferenceArrete().toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		this.dateArrete = carr.getDateArrete() == 0 ? null : sdf.parse(carr.getDateArrete().toString());
		this.acc = carr.getAcc();
		this.directionAgent = fp.getService().getDirection();

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
		if (agent.getTitre().equals("Monsieur")) {
			return "Monsieur " + agent.getPrenomUsage() + " " + (agent.getNomUsage() == null ? agent.getNomPatronymique() : agent.getNomUsage());
		} else if (agent.getTitre().equals("Madame")) {
			return "Madame " + agent.getPrenomUsage() + " " + (agent.getNomUsage() == null ? agent.getNomPatronymique() : agent.getNomUsage())
					+ " épouse " + agent.getNomMarital();
		} else {
			return "Mademoiselle " + agent.getPrenomUsage() + " " + (agent.getNomUsage() == null ? agent.getNomPatronymique() : agent.getNomUsage());
		}
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

	public Date getDateAvct() {
		return dateAvct;
	}

	public void setDateAvct(Date dateAvct) {
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
}
