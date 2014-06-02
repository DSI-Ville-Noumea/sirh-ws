package nc.noumea.mairie.web.dto;

import java.util.Date;

import nc.noumea.mairie.model.bean.sirh.SuiviMedical;

public class ConvocationVMDto {

	private Integer idSuiviMedical;
	private Integer nbVisitesRatees;
	private AgentWithServiceDto agentConvoque;
	private MedecinDto medecin;
	private Date dateVisite;
	private String heureVisite;
	private String motif;

	public ConvocationVMDto() {
	}

	public ConvocationVMDto(SuiviMedical sm, AgentWithServiceDto agDto, MedecinDto med) {
		this.idSuiviMedical = sm.getIdSuiviMedical();
		this.nbVisitesRatees = sm.getNbVisitesRatees();
		this.agentConvoque = agDto;
		this.medecin = med;
		this.dateVisite = sm.getDateProchaineVisite();
		this.heureVisite = sm.getHeureProchaineVisite();
		this.motif = sm.getMotif() == null ? null : sm.getMotif().getLibMotifVisiteMedicale();
	}

	public Integer getIdSuiviMedical() {
		return idSuiviMedical;
	}

	public void setIdSuiviMedical(Integer idSuiviMedical) {
		this.idSuiviMedical = idSuiviMedical;
	}

	public Integer getNbVisitesRatees() {
		return nbVisitesRatees;
	}

	public void setNbVisitesRatees(Integer nbVisitesRatees) {
		this.nbVisitesRatees = nbVisitesRatees;
	}

	public MedecinDto getMedecin() {
		return medecin;
	}

	public void setMedecin(MedecinDto medecin) {
		this.medecin = medecin;
	}

	public Date getDateVisite() {
		return dateVisite;
	}

	public void setDateVisite(Date dateVisite) {
		this.dateVisite = dateVisite;
	}

	public String getHeureVisite() {
		return heureVisite;
	}

	public void setHeureVisite(String heureVisite) {
		this.heureVisite = heureVisite;
	}

	public AgentWithServiceDto getAgentConvoque() {
		return agentConvoque;
	}

	public void setAgentConvoque(AgentWithServiceDto agentConvoque) {
		this.agentConvoque = agentConvoque;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

}
