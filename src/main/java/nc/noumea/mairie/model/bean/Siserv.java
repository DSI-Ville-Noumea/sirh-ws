package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "SISERV")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Siserv {

	@Id
	@Column(name = "SERVI", columnDefinition = "char")
	private String servi;

	@Column(name = "LISERV", columnDefinition = "char")
	private String liserv;

	@Column(name = "SIGLE", columnDefinition = "char")
	private String sigle;

	public String getServi() {
		return servi;
	}

	public void setServi(String servi) {
		this.servi = servi;
	}

	public String getLiserv() {
		return liserv;
	}

	public void setLiserv(String liserv) {
		this.liserv = liserv;
	}

	public String getSigle() {
		return sigle;
	}

	public void setSigle(String sigle) {
		this.sigle = sigle;
	}

}
