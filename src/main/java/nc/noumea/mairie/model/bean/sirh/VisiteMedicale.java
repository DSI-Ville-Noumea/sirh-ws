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
@Table(name = "VISITE_MEDICALE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class VisiteMedicale {

	@Id
	@Column(name = "ID_VISITE")
	private Integer			idVisiteMedicale;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AGENT", referencedColumnName = "ID_AGENT")
	private Agent			agent;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MEDECIN", referencedColumnName = "ID_MEDECIN")
	private Medecin			medecin;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RECOMMANDATION", referencedColumnName = "ID_RECOMMANDATION")
	private Recommandation	recommandation;

	@Column(name = "DATE_DERNIERE_VISITE")
	@Temporal(TemporalType.DATE)
	private Date			dateDerniereVisite;

	@Column(name = "DUREE_VALIDITE")
	private Integer			dureeValidite;

	@Column(name = "COMMENTAIRE")
	private String			commentaire;

	public Integer getIdVisiteMedicale() {
		return idVisiteMedicale;
	}

	public void setIdVisiteMedicale(Integer idVisiteMedicale) {
		this.idVisiteMedicale = idVisiteMedicale;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Medecin getMedecin() {
		return medecin;
	}

	public void setMedecin(Medecin medecin) {
		this.medecin = medecin;
	}

	public Recommandation getRecommandation() {
		return recommandation;
	}

	public void setRecommandation(Recommandation recommandation) {
		this.recommandation = recommandation;
	}

	public Date getDateDerniereVisite() {
		return dateDerniereVisite;
	}

	public void setDateDerniereVisite(Date dateDerniereVisite) {
		this.dateDerniereVisite = dateDerniereVisite;
	}

	public Integer getDureeValidite() {
		return dureeValidite;
	}

	public void setDureeValidite(Integer dureeValidite) {
		this.dureeValidite = dureeValidite;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
}
