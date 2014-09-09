package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.sirh.Agent;

public class CouvertureSocialeDto {

	private String numCafat;
	private String numRuamm;
	private String numMutuelle;
	private String numCre;
	private String numIrcafex;
	private String numClr;

	public CouvertureSocialeDto(Agent ag) {
		super();
		if (ag != null) {
			this.numCafat = ag.getNumCafat();
			this.numRuamm = ag.getNumRuamm();
			this.numMutuelle = ag.getNumMutuelle();
			this.numCre = ag.getNumCre();
			this.numIrcafex = ag.getNumIrcafex();
			this.numClr = ag.getNumClr();
		}
	}

	public String getNumCafat() {
		return numCafat;
	}

	public void setNumCafat(String numCafat) {
		this.numCafat = numCafat;
	}

	public String getNumRuamm() {
		return numRuamm;
	}

	public void setNumRuamm(String numRuamm) {
		this.numRuamm = numRuamm;
	}

	public String getNumMutuelle() {
		return numMutuelle;
	}

	public void setNumMutuelle(String numMutuelle) {
		this.numMutuelle = numMutuelle;
	}

	public String getNumCre() {
		return numCre;
	}

	public void setNumCre(String numCre) {
		this.numCre = numCre;
	}

	public String getNumIrcafex() {
		return numIrcafex;
	}

	public void setNumIrcafex(String numIrcafex) {
		this.numIrcafex = numIrcafex;
	}

	public String getNumClr() {
		return numClr;
	}

	public void setNumClr(String numClr) {
		this.numClr = numClr;
	}
}
