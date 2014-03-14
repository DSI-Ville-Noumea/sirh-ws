package nc.noumea.mairie.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.AutreAdministrationAgentPK;

@Entity
@Table(name = "AUTRE_ADMIN_AGENT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class AutreAdministrationAgent {

	@EmbeddedId
	private AutreAdministrationAgentPK autreAdministrationAgentPK;
	
	@Column(name = "DATE_SORTIE")
	@Temporal(TemporalType.DATE)
	private Date dateSortie;

	@NotNull
	@Column(name = "FONCTIONNAIRE")
	private Integer fonctionnaire;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "ID_AUTRE_ADMIN", referencedColumnName = "ID_AUTRE_ADMIN", insertable = false, updatable = false)
	private AutreAdministration autreAdministration;

	public AutreAdministrationAgentPK getAutreAdministrationAgentPK() {
		return autreAdministrationAgentPK;
	}

	public void setAutreAdministrationAgentPK(
			AutreAdministrationAgentPK autreAdministrationAgentPK) {
		this.autreAdministrationAgentPK = autreAdministrationAgentPK;
	}

	public Date getDateSortie() {
		return dateSortie;
	}

	public void setDateSortie(Date dateSortie) {
		this.dateSortie = dateSortie;
	}

	public Integer getFonctionnaire() {
		return fonctionnaire;
	}

	public void setFonctionnaire(Integer fonctionnaire) {
		this.fonctionnaire = fonctionnaire;
	}

	public AutreAdministration getAutreAdministration() {
		return autreAdministration;
	}

	public void setAutreAdministration(AutreAdministration autreAdministration) {
		this.autreAdministration = autreAdministration;
	}
	
}
