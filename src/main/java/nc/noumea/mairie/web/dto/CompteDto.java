package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.Sibanq;
import nc.noumea.mairie.model.bean.sirh.Agent;

public class CompteDto {

	private Integer codeBanque;
	private Integer codeGuichet;
	private String numCompte;
	private Integer rib;
	private String libelleBanque;

	public CompteDto(Agent ag, Sibanq banque) {
		super();
		this.codeBanque = ag.getCodeBanque();
		this.codeGuichet = ag.getCodeGuichet();
		this.numCompte = ag.getNumCompte().trim();
		this.rib = ag.getRib();
		this.libelleBanque = banque != null ? banque.getLiBanque().trim() : null;
	}

	public Integer getCodeBanque() {
		return codeBanque;
	}

	public void setCodeBanque(Integer codeBanque) {
		this.codeBanque = codeBanque;
	}

	public Integer getCodeGuichet() {
		return codeGuichet;
	}

	public void setCodeGuichet(Integer codeGuichet) {
		this.codeGuichet = codeGuichet;
	}

	public String getNumCompte() {
		return numCompte;
	}

	public void setNumCompte(String numCompte) {
		this.numCompte = numCompte;
	}

	public Integer getRib() {
		return rib;
	}

	public void setRib(Integer rib) {
		this.rib = rib;
	}

	public String getLibelleBanque() {
		return libelleBanque;
	}

	public void setLibelleBanque(String libelleBanque) {
		this.libelleBanque = libelleBanque;
	}
}
