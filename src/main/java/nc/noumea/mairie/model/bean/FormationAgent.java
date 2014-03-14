package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "FORMATION_AGENT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class FormationAgent {
	
	@Id
	@Column(name = "ID_FORMATION")
	private Integer idFormation;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TITRE_FORMATION", referencedColumnName = "ID_TITRE_FORMATION")
	private TitreFormation titreFormation;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CENTRE_FORMATION", referencedColumnName = "ID_CENTRE_FORMATION")
	private CentreFormation centreFormation;
	
	@Column(name = "ID_AGENT")
	private Integer idAgent;
	
	@Column(name = "DUREE_FORMATION")
	private Integer dureeFormation;
	
	@Column(name = "UNITE_DUREE")
	private String uniteformation;
	
	@Column(name = "ANNEE_FORMATION")
	private Integer anneeFormation;

	public Integer getIdFormation() {
		return idFormation;
	}

	public void setIdFormation(Integer idFormation) {
		this.idFormation = idFormation;
	}

	public TitreFormation getTitreFormation() {
		return titreFormation;
	}

	public void setTitreFormation(TitreFormation titreFormation) {
		this.titreFormation = titreFormation;
	}

	public CentreFormation getCentreFormation() {
		return centreFormation;
	}

	public void setCentreFormation(CentreFormation centreFormation) {
		this.centreFormation = centreFormation;
	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}

	public Integer getDureeFormation() {
		return dureeFormation;
	}

	public void setDureeFormation(Integer dureeFormation) {
		this.dureeFormation = dureeFormation;
	}

	public String getUniteformation() {
		return uniteformation;
	}

	public void setUniteformation(String uniteformation) {
		this.uniteformation = uniteformation;
	}

	public Integer getAnneeFormation() {
		return anneeFormation;
	}

	public void setAnneeFormation(Integer anneeFormation) {
		this.anneeFormation = anneeFormation;
	}
		
	
}
