package nc.noumea.mairie.model.bean;

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

@Entity
@Table(name = "DIPLOME_AGENT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class DiplomeAgent {

	@Id
	@Column(name = "ID_DIPLOME")
	public Integer idDiplome;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TITRE_DIPLOME", referencedColumnName = "ID_TITRE_DIPLOME")
	public TitreDiplome titreDiplome;
	
	@Column(name = "ID_AGENT")
	public Integer idAgent;
	
	@Column(name = "ID_DOCUMENT")
	public Integer idDocument;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SPECIALITE_DIPLOME", referencedColumnName = "ID_SPECIALITE_DIPLOME")
	public SpecialiteDiplome specialiteDiplome;
	
	@Column(name = "DATE_OBTENTION")
	@Temporal(TemporalType.DATE)
	public Date dateObtention;
	
	@Column(name = "NOM_ECOLE")
	public String nomEcole;
	
	@Column(name = "DATE_VALIDITE")
	@Temporal(TemporalType.DATE)
	public Date dateValidite;
	
	
	public Integer getIdDiplome() {
		return idDiplome;
	}
	public void setIdDiplome(Integer idDiplome) {
		this.idDiplome = idDiplome;
	}
	
	public TitreDiplome getTitreDiplome() {
		return titreDiplome;
	}
	public void setTitreDiplome(TitreDiplome titreDiplome) {
		this.titreDiplome = titreDiplome;
	}
	public Integer getIdAgent() {
		return idAgent;
	}
	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}
	public Integer getIdDocument() {
		return idDocument;
	}
	public void setIdDocument(Integer idDocument) {
		this.idDocument = idDocument;
	}
	public SpecialiteDiplome getSpecialiteDiplome() {
		return specialiteDiplome;
	}
	public void setSpecialiteDiplome(SpecialiteDiplome specialiteDiplome) {
		this.specialiteDiplome = specialiteDiplome;
	}
	public Date getDateObtention() {
		return dateObtention;
	}
	public void setDateObtention(Date dateObtention) {
		this.dateObtention = dateObtention;
	}
	public String getNomEcole() {
		return nomEcole;
	}
	public void setNomEcole(String nomEcole) {
		this.nomEcole = nomEcole;
	}
	public Date getDateValidite() {
		return dateValidite;
	}
	public void setDateValidite(Date dateValidite) {
		this.dateValidite = dateValidite;
	}
	
	
}
