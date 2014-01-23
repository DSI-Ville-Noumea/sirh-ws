package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SISERV")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Siserv {

	@Id
	@Column(name = "SERVI", columnDefinition = "char")
	private String servi;

	@NotNull
	@Column(name = "LISERV", columnDefinition = "char")
	private String liServ;

	@NotNull
	@Column(name = "CODACT", columnDefinition = "char")
	private String codeActif;

	@NotNull
	@Column(name = "SIGLE", columnDefinition = "char")
	private String sigle;

	@NotNull
	@Column(name = "DEPEND", columnDefinition = "char")
	private String parentSigle;

	@Transient
	private String division;

	@Transient
	private String direction;

	@Transient
	private String directionSigle;

	@Transient
	private String section;

	public String getDivision() {
		return division == null ? "" : division.trim();
	}

	public String getDirection() {
		return direction == null ? "" : direction.trim();
	}

	public String getDirectionSigle() {
		return directionSigle == null ? "" : directionSigle.trim();
	}

	public String getSection() {
		return section == null ? "" : section.trim();
	}

	public String getServi() {
		return servi == null ? "" : servi.trim();
	}

	public void setServi(String servi) {
		this.servi = servi;
	}

	public String getLiServ() {
		return liServ == null ? "" : liServ.trim();
	}

	public void setLiServ(String liServ) {
		this.liServ = liServ;
	}

	public String getCodeActif() {
		return codeActif;
	}

	public void setCodeActif(String codeActif) {
		this.codeActif = codeActif;
	}

	public String getSigle() {
		return sigle == null ? "" : sigle.trim();
	}

	public void setSigle(String sigle) {
		this.sigle = sigle;
	}

	public String getParentSigle() {
		return parentSigle == null ? "" : parentSigle.trim();
	}

	public void setParentSigle(String parentSigle) {
		this.parentSigle = parentSigle;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setDirectionSigle(String directionSigle) {
		this.directionSigle = directionSigle;
	}

	public void setSection(String section) {
		this.section = section;
	}

}
