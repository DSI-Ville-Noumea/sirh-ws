package nc.noumea.mairie.model.bean.sirh;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.bean.Sicomm;
import nc.noumea.mairie.service.ISivietService;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "ENFANT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Enfant {

	@Id
	@Column(name = "ID_ENFANT")
	private Integer idEnfant;

	@Autowired
	@Transient
	ISivietService sivietSrv;

	@NotNull
	@Column(name = "NOM")
	private String nom;

	@NotNull
	@Column(name = "PRENOM")
	private String prenom;

	@NotNull
	@Column(name = "SEXE")
	private String sexe;

	@Column(name = "DATE_NAISSANCE")
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;

	@ManyToOne
	@JoinColumn(name = "CODE_COMMUNE_NAISS_FR", referencedColumnName = "CODCOM")
	private Sicomm codeCommuneNaissFr;

	@Column(name = "CODE_COMMUNE_NAISS_ET", columnDefinition = "numeric")
	private Integer codeCommuneNaissEt;

	@Column(name = "CODE_PAYS_NAISS_ET", columnDefinition = "numeric")
	private Integer codePaysNaissEt;

	@Transient
	private String lieuNaissance;

	public String getLieuNaissance() {
		if (this.codeCommuneNaissFr != null) {
			setLieuNaissance(this.codeCommuneNaissFr.getLibVil());
		} else {
			if (this.codePaysNaissEt != null && this.codeCommuneNaissEt != null) {
				setLieuNaissance(sivietSrv.getLieuNaissEtr(this.codePaysNaissEt.intValue(),
						this.codeCommuneNaissEt.intValue()).getLibCop());
			} else {
				setLieuNaissance(null);
			}
		}
		return this.lieuNaissance;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Sicomm getCodeCommuneNaissFr() {
		return codeCommuneNaissFr;
	}

	public void setCodeCommuneNaissFr(Sicomm codeCommuneNaissFr) {
		this.codeCommuneNaissFr = codeCommuneNaissFr;
	}

	public Integer getCodeCommuneNaissEt() {
		return codeCommuneNaissEt;
	}

	public void setCodeCommuneNaissEt(Integer codeCommuneNaissEt) {
		this.codeCommuneNaissEt = codeCommuneNaissEt;
	}

	public Integer getCodePaysNaissEt() {
		return codePaysNaissEt;
	}

	public void setCodePaysNaissEt(Integer codePaysNaissEt) {
		this.codePaysNaissEt = codePaysNaissEt;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public Integer getIdEnfant() {
		return idEnfant;
	}

	public void setIdEnfant(Integer idEnfant) {
		this.idEnfant = idEnfant;
	}
}
