package nc.noumea.mairie.web.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Contrat;

@XmlRootElement
public class NoteServiceDto {

	private Integer idAffectation;
	private Date dateDebutAffectation;
	private Date dateFinAffectation;
	private TitrePosteDto titrePoste;
	private String lieu;
	private AgentWithServiceDto agent;
	private Integer nbJoursPeriodeEssai;
	private Date dateFinPeriodeEssai;
	private String typeNoteService;

	public NoteServiceDto() {
	}

	public NoteServiceDto(Affectation aff, AgentWithServiceDto agDto, TitrePosteDto titrePoste,
			Integer nbJoursPeriodeEssai, Contrat contrat, String typeNoteService) {
		this.idAffectation = aff.getIdAffectation();
		this.dateDebutAffectation = aff.getDateDebutAff();
		this.dateFinAffectation = aff.getDateFinAff();
		this.titrePoste = titrePoste;
		this.lieu = aff.getFichePoste().getLieuPoste() == null
				|| aff.getFichePoste().getLieuPoste().getLibelleLieu() == null ? "" : aff.getFichePoste()
				.getLieuPoste().getLibelleLieu().trim();
		this.agent = agDto;
		this.nbJoursPeriodeEssai = nbJoursPeriodeEssai;
		this.dateFinPeriodeEssai = contrat == null ? null : contrat.getDateFinPeriodeEssai();
		this.typeNoteService = typeNoteService;
	}

	public Integer getIdAffectation() {
		return idAffectation;
	}

	public void setIdAffectation(Integer idAffectation) {
		this.idAffectation = idAffectation;
	}

	public Date getDateDebutAffectation() {
		return dateDebutAffectation;
	}

	public void setDateDebutAffectation(Date dateDebutAffectation) {
		this.dateDebutAffectation = dateDebutAffectation;
	}

	public Date getDateFinAffectation() {
		return dateFinAffectation;
	}

	public void setDateFinAffectation(Date dateFinAffectation) {
		this.dateFinAffectation = dateFinAffectation;
	}

	public TitrePosteDto getTitrePoste() {
		return titrePoste;
	}

	public void setTitrePoste(TitrePosteDto titrePoste) {
		this.titrePoste = titrePoste;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public AgentWithServiceDto getAgent() {
		return agent;
	}

	public void setAgent(AgentWithServiceDto agent) {
		this.agent = agent;
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

	public String getTypeNoteService() {
		return typeNoteService;
	}

	public void setTypeNoteService(String typeNoteService) {
		this.typeNoteService = typeNoteService;
	}

}
