package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AGENT")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class AgentRecherche {

	@Id
	@Column(name = "ID_AGENT")
	private Integer idAgent;

	@NotNull
	@Column(name = "NOMATR")
	private Integer nomatr;

	@Column(name = "NOM_USAGE")
	private String nomUsage;

	@Column(name = "PRENOM_USAGE")
	private String prenomUsage;

	public String getDisplayPrenom() {
		return getPrenomUsage();
	}

	public String getDisplayNom() {
		return getNomUsage();
	}

	@Override
	public String toString() {
		return "Nomatr=" + getNomatr() + ",Prenom=" + getPrenomUsage() + ",Nom=" + getNomUsage() + ",IdAgent="
				+ getIdAgent();
	}

	public Integer getNomatr() {
		return nomatr;
	}

	public void setNomatr(Integer nomatr) {
		this.nomatr = nomatr;
	}

	public String getNomUsage() {
		return nomUsage;
	}

	public void setNomUsage(String nomUsage) {
		this.nomUsage = nomUsage;
	}

	public String getPrenomUsage() {
		return prenomUsage;
	}

	public void setPrenomUsage(String prenomUsage) {
		this.prenomUsage = prenomUsage;
	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}
}
