package nc.noumea.mairie.mdf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "STATUS_JOB_MDF")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class StatusJob {

	@Id
	@Column(name = "DATE_TRAITEMENT", columnDefinition = "char")
	private String dateTraitement;
	
	@Column(name = "MOIS_TRAITEMENT", columnDefinition = "char")
	private String moisTraitement;
	
	@Column(name = "SEVERITE", columnDefinition = "char")
	private String severite;
	
	@Column(name = "MESSAGE", columnDefinition = "char")
	private String message;
	
	@Column(name = "ENTITE", columnDefinition = "char")
	private String entite;

	public String getDateTraitement() {
		return dateTraitement;
	}

	public void setDateTraitement(String dateTraitement) {
		this.dateTraitement = dateTraitement;
	}

	public String getMoisTraitement() {
		return moisTraitement;
	}

	public void setMoisTraitement(String moisTraitement) {
		this.moisTraitement = moisTraitement;
	}

	public String getSeverite() {
		return severite;
	}

	public void setSeverite(String severite) {
		this.severite = severite;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEntite() {
		return entite;
	}

	public void setEntite(String entite) {
		this.entite = entite;
	}
}
