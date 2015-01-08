package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPPOSA")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spposa {
	
	@Id
	@NotNull
	@Column(name = "CDPADM", columnDefinition = "char")
	private String cdpAdm;
	
	@Column(name = "LIPADM", columnDefinition = "char")
	private String libelle;

	@Column(name = "DROITC", columnDefinition = "char")
	private String droitConges;

	@Column(name = "DUREE", columnDefinition = "numeric")
	private Integer duree;
	
	@Column(name = "POSIT", columnDefinition = "char")
	private String position;

	public String getCdpAdm() {
		return cdpAdm;
	}

	public void setCdpAdm(String cdpAdm) {
		this.cdpAdm = cdpAdm;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDroitConges() {
		return droitConges;
	}

	public void setDroitConges(String droitConges) {
		this.droitConges = droitConges;
	}

	public Integer getDuree() {
		return duree;
	}

	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
}
