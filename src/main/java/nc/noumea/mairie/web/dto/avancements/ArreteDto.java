package nc.noumea.mairie.web.dto.avancements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spclas;
import nc.noumea.mairie.model.bean.Speche;

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
	private String matriculeAgent;
	private String baseReglement;
	private String serviceAgent;

	public ArreteDto() {

	}

	public ArreteDto(AvancementFonctionnaire avct, FichePoste fp, Spcarr carr) throws ParseException {
		this.baseReglement = carr.getModReg();
		this.matriculeAgent = avct.getAgent().getNomatr().toString();
		this.annee = avct.getAnneeAvancement();
		this.nomComplet = getNomCompletAgent(avct.getAgent());
		this.changementClasse = avct.getIdModifAvancement() == null ? false : avct.getIdModifAvancement() == 7
				|| avct.getIdModifAvancement() == 6 ? false : true;
		this.regularisation = avct.isRegularisation();
		this.deliberationLabel = avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale() == null ? ""
				: avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale().getLibDeliberation()
						.toLowerCase();
		this.deliberationCapText = avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale() == null ? ""
				: avct.getGradeNouveau().getGradeGenerique().getDeliberationCommunale().getTexteCap();
		this.dateCap = avct.getDateCap();
		this.dateAvct = getDateAvancement(avct);
		this.dureeAvct = avct.getAvisCapEmployeur() == null ? "" : avct.getAvisCapEmployeur().getLibLong()
				.toLowerCase();
		String classe = avct.getGradeNouveau().getCodcla() == null
				|| avct.getGradeNouveau().getCodcla().trim().equals("") ? "" : " "
				+ Spclas.findSpclas(avct.getGradeNouveau().getCodcla()).getLibCla().trim();
		String echelon = avct.getGradeNouveau().getCodech() == null
				|| avct.getGradeNouveau().getCodech().trim().equals("") ? "" : " "
				+ Speche.findSpeche(avct.getGradeNouveau().getCodech()).getLibEch().trim();
		String libelleGrade = avct.getGradeNouveau().getGradeInitial().trim() + classe + echelon;

		this.gradeLabel = libelleGrade.startsWith("A") || libelleGrade.startsWith("E") || libelleGrade.startsWith("I")
				|| libelleGrade.startsWith("O") || libelleGrade.startsWith("U") ? "d'" + libelleGrade.toLowerCase()
				: "de " + libelleGrade.toLowerCase();
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
		this.numeroArrete = carr.getReferenceArrete().toString().equals("0") ? "" : "20"
				+ carr.getReferenceArrete().toString().substring(0, 2) + "/"
				+ carr.getReferenceArrete().toString().substring(2, carr.getReferenceArrete().toString().length());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		this.dateArrete = carr.getDateArrete() == 0 ? null : sdf.parse(carr.getDateArrete().toString());
		this.acc = avct.getAccRestant();
		this.directionAgent = fp != null && fp.getService() != null ? " (" + fp.getService().getDirectionSigle() + ")"
				: "";
		this.serviceAgent = fp != null && fp.getService() != null ? fp.getService().getDirectionSigle() + " ("
				+ fp.getService().getSigle().trim() + ")" : "";

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
		return agent.getTitre() + " " + agent.getPrenomUsage().substring(0, 1).toUpperCase()
				+ agent.getPrenomUsage().substring(1, agent.getPrenomUsage().length()).toLowerCase() + " "
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
}
