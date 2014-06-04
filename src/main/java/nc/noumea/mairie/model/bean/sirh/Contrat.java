package nc.noumea.mairie.model.bean.sirh;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CONTRAT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Contrat {

	@Id
	@Column(name = "ID_CONTRAT")
	private Integer idContrat;

	@NotNull
	@Column(name = "DATDEB")
	@Temporal(TemporalType.DATE)
	private Date dateDebutContrat;

	@Column(name = "DATE_FIN")
	@Temporal(TemporalType.DATE)
	private Date dateFinContrat;

	@Column(name = "DATE_FIN_PERIODE_ESS")
	@Temporal(TemporalType.DATE)
	private Date dateFinPeriodeEssai;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AGENT", referencedColumnName = "ID_AGENT")
	private Agent agent;

	@NotNull
	@Column(name = "ID_MOTIF")
	private Integer idMotif;

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Integer getIdContrat() {
		return idContrat;
	}

	public void setIdContrat(Integer idContrat) {
		this.idContrat = idContrat;
	}

	public Date getDateDebutContrat() {
		return dateDebutContrat;
	}

	public void setDateDebutContrat(Date dateDebutContrat) {
		this.dateDebutContrat = dateDebutContrat;
	}

	public Date getDateFinContrat() {
		return dateFinContrat;
	}

	public void setDateFinContrat(Date dateFinContrat) {
		this.dateFinContrat = dateFinContrat;
	}

	public Date getDateFinPeriodeEssai() {
		return dateFinPeriodeEssai;
	}

	public void setDateFinPeriodeEssai(Date dateFinPeriodeEssai) {
		this.dateFinPeriodeEssai = dateFinPeriodeEssai;
	}

	public Integer getIdMotif() {
		return idMotif;
	}

	public void setIdMotif(Integer idMotif) {
		this.idMotif = idMotif;
	}
}
