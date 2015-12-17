package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.SiBanqGuichet;

public class BanqueGuichetDto {
	private String codeBanque;
	private String libelleBanque;
	private String codeGuichet;
	private String libelleGuichet;

	public BanqueGuichetDto() {
		super();
	}

	public BanqueGuichetDto(SiBanqGuichet bq) {
		// on ajoute des 0 sur 5 caracteres
		this.codeBanque = String.format("%05d", bq.getId().getCodeBanque());
		this.libelleBanque = bq.getLiBanque();
		this.codeGuichet = String.format("%05d", bq.getId().getCodeGuichet());
		this.libelleGuichet = bq.getLiGuichet();
	}

	public String getCodeBanque() {
		return codeBanque;
	}

	public void setCodeBanque(String codeBanque) {
		this.codeBanque = codeBanque;
	}

	public String getLibelleBanque() {
		return libelleBanque;
	}

	public void setLibelleBanque(String libelleBanque) {
		this.libelleBanque = libelleBanque;
	}

	public String getCodeGuichet() {
		return codeGuichet;
	}

	public void setCodeGuichet(String codeGuichet) {
		this.codeGuichet = codeGuichet;
	}

	public String getLibelleGuichet() {
		return libelleGuichet;
	}

	public void setLibelleGuichet(String libelleGuichet) {
		this.libelleGuichet = libelleGuichet;
	}

}
