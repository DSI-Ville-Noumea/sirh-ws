package nc.noumea.mairie.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.service.ISivietService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_ENFANT", identifierField = "idEnfant", identifierType = Integer.class, table = "ENFANT", versionField = "")
@RooSerializable
@RooJson
public class Enfant {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
				setLieuNaissance(sivietSrv.getLieuNaissEtr(this.codePaysNaissEt.intValue(), this.codeCommuneNaissEt.intValue()).getLibCop());
			} else {
				setLieuNaissance(null);
			}
		}
		return this.lieuNaissance;
	}
}
