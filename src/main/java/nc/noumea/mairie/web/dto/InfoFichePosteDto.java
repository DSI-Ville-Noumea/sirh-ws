package nc.noumea.mairie.web.dto;

public class InfoFichePosteDto {

	private Integer idAgent;
	private Integer noMatr;
	private String prenomAgent;
	private String nomAgent;
	private String numFP;
	private String gradePoste;
	private String categorie;
	
	public Integer getIdAgent() {
		return idAgent;
	}
	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}
	public String getPrenomAgent() {
		return prenomAgent;
	}
	public void setPrenomAgent(String prenomAgent) {
		this.prenomAgent = prenomAgent;
	}
	public String getNomAgent() {
		return nomAgent;
	}
	public void setNomAgent(String nomAgent) {
		this.nomAgent = nomAgent;
	}
	public String getNumFP() {
		return numFP;
	}
	public void setNumFP(String numFP) {
		this.numFP = numFP;
	}
	public Integer getNoMatr() {
		return noMatr;
	}
	public void setNoMatr(Integer noMatr) {
		this.noMatr = noMatr;
	}
	public String getGradePoste() {
		return gradePoste;
	}
	public void setGradePoste(String gradePoste) {
		this.gradePoste = gradePoste;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
}
