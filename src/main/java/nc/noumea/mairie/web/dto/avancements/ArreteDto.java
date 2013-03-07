package nc.noumea.mairie.web.dto.avancements;

import java.util.Date;

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
	private String ina;
	private String ib;
	private String acc;
	private boolean feminin;
	private String directionAgent;

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

	public String getIna() {
		return ina;
	}

	public void setIna(String ina) {
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
