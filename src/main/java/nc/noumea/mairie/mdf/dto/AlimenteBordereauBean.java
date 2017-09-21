package nc.noumea.mairie.mdf.dto;

public class AlimenteBordereauBean {

	private String codeCollectivité;

	private String moisPaye;

	private String denominationEmployeur;

	private String dateEmission;

	private EffectifBean effectif;

	private RemunerationBean remuneration;

	private CotisationBean cotisationPatronale;

	private CotisationBean cotisationSalariale;

	public String getCodeCollectivité() {
		return codeCollectivité;
	}

	public void setCodeCollectivité(String codeCollectivité) {
		this.codeCollectivité = codeCollectivité;
	}

	public String getMoisPaye() {
		return moisPaye;
	}

	public void setMoisPaye(String moisPaye) {
		this.moisPaye = moisPaye;
	}

	public EffectifBean getEffectif() {
		return effectif;
	}

	public void setEffectif(EffectifBean effectif) {
		this.effectif = effectif;
	}

	public RemunerationBean getRemuneration() {
		return remuneration;
	}

	public void setRemuneration(RemunerationBean remuneration) {
		this.remuneration = remuneration;
	}

	public CotisationBean getCotisationPatronale() {
		return cotisationPatronale;
	}

	public void setCotisationPatronale(CotisationBean cotisationPatronale) {
		this.cotisationPatronale = cotisationPatronale;
	}

	public CotisationBean getCotisationSalariale() {
		return cotisationSalariale;
	}

	public void setCotisationSalariale(CotisationBean cotisationSalariale) {
		this.cotisationSalariale = cotisationSalariale;
	}

	public String getDenominationEmployeur() {
		return denominationEmployeur;
	}

	public void setDenominationEmployeur(String denominationEmployeur) {
		this.denominationEmployeur = denominationEmployeur;
	}

	public String getDateEmission() {
		return dateEmission;
	}

	public void setDateEmission(String dateEmission) {
		this.dateEmission = dateEmission;
	}
}
