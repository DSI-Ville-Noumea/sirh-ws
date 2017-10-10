package nc.noumea.mairie.mdf.dto;

public class CotisationBean {

	private Integer cotisationCalculee;
	
	private Integer surRemuneration;
	
	private Integer surRegularisation;

	public Integer getCotisationCalculee() {
		return cotisationCalculee;
	}

	public void setCotisationCalculee(Integer cotisationCalculee) {
		this.cotisationCalculee = cotisationCalculee;
	}

	public Integer getSurRemuneration() {
		return surRemuneration;
	}

	public void setSurRemuneration(Integer surRemuneration) {
		this.surRemuneration = surRemuneration;
	}

	public Integer getSurRegularisation() {
		return surRegularisation;
	}

	public void setSurRegularisation(Integer surRegularisation) {
		this.surRegularisation = surRegularisation;
	}
}
