package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.SiBanqGuichet;

public class BanqueGuichetDto {
	private Integer codeBanque;
	private String libelleBanque;
	private Integer codeGuichet;
	private String libelleGuichet;

	public BanqueGuichetDto() {
		super();
	}

	public BanqueGuichetDto(SiBanqGuichet bq) {
		// on ajoute des 0 sur 5 caracteres
		this.codeBanque = bq.getId().getCodeBanque();
		this.libelleBanque = bq.getLiBanque();
		this.codeGuichet = bq.getId().getCodeGuichet();
		this.libelleGuichet = bq.getLiGuichet();
	}

	public Integer getCodeBanque() {
		return codeBanque;
	}

	public void setCodeBanque(Integer codeBanque) {
		this.codeBanque = codeBanque;
	}

	public String getLibelleBanque() {
		return libelleBanque;
	}

	public void setLibelleBanque(String libelleBanque) {
		this.libelleBanque = libelleBanque;
	}

	public Integer getCodeGuichet() {
		return codeGuichet;
	}

	public void setCodeGuichet(Integer codeGuichet) {
		this.codeGuichet = codeGuichet;
	}

	public String getLibelleGuichet() {
		return libelleGuichet;
	}

	public void setLibelleGuichet(String libelleGuichet) {
		this.libelleGuichet = libelleGuichet;
	}

}
