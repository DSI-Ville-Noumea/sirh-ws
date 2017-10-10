package nc.noumea.mairie.mdf.dto;

public class RemunerationBean {

	private Integer totalBrut;
	
	private Integer montantRemuneration;
	
	private Integer montantRegularisation;

	public Integer getTotalBrut() {
		return totalBrut;
	}

	public void setTotalBrut(Integer totalBrut) {
		this.totalBrut = totalBrut;
	}

	public Integer getMontantRemuneration() {
		return montantRemuneration;
	}

	public void setMontantRemuneration(Integer montantRemuneration) {
		this.montantRemuneration = montantRemuneration;
	}

	public Integer getMontantRegularisation() {
		return montantRegularisation;
	}

	public void setMontantRegularisation(Integer montantRegularisation) {
		this.montantRegularisation = montantRegularisation;
	}
}
