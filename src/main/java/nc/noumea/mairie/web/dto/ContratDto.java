package nc.noumea.mairie.web.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import nc.noumea.mairie.model.bean.sirh.Contrat;

@XmlRootElement
public class ContratDto {
	private Integer idContrat;
	private Integer idMotif;
	private AgentDto agent;
	private FichePosteDto fichePoste;
	private Date dateDebutContrat;
	private Date dateFinContrat;
	private Integer nbJoursPeriodeEssai;
	private Date dateFinPeriodeEssai;
	private List<DiplomeDto> listeDiplomes;

	public ContratDto(Contrat contrat, AgentDto agDto, FichePosteDto fpDto, Integer nbJoursPeriodeEssai,
			List<DiplomeDto> listeDip) {
		this.idContrat = contrat.getIdContrat();
		this.idMotif = contrat.getIdMotif();
		this.agent = agDto;
		this.fichePoste = fpDto;
		this.dateDebutContrat = contrat.getDateDebutContrat();
		this.dateFinContrat = contrat.getDateFinContrat();
		this.nbJoursPeriodeEssai = nbJoursPeriodeEssai;
		this.dateFinPeriodeEssai = contrat.getDateFinPeriodeEssai();
		this.listeDiplomes = listeDip;
	}

	public ContratDto() {
		super();
	}

	public Integer getIdContrat() {
		return idContrat;
	}

	public void setIdContrat(Integer idContrat) {
		this.idContrat = idContrat;
	}

	public AgentDto getAgent() {
		return agent;
	}

	public void setAgent(AgentDto agent) {
		this.agent = agent;
	}

	public FichePosteDto getFichePoste() {
		return fichePoste;
	}

	public void setFichePoste(FichePosteDto fichePoste) {
		this.fichePoste = fichePoste;
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

	public Integer getNbJoursPeriodeEssai() {
		return nbJoursPeriodeEssai;
	}

	public void setNbJoursPeriodeEssai(Integer nbJoursPeriodeEssai) {
		this.nbJoursPeriodeEssai = nbJoursPeriodeEssai;
	}

	public Date getDateFinPeriodeEssai() {
		return dateFinPeriodeEssai;
	}

	public void setDateFinPeriodeEssai(Date dateFinPeriodeEssai) {
		this.dateFinPeriodeEssai = dateFinPeriodeEssai;
	}

	public List<DiplomeDto> getListeDiplomes() {
		return listeDiplomes;
	}

	public void setListeDiplomes(List<DiplomeDto> listeDiplomes) {
		this.listeDiplomes = listeDiplomes;
	}

	public Integer getIdMotif() {
		return idMotif;
	}

	public void setIdMotif(Integer idMotif) {
		this.idMotif = idMotif;
	}
}
