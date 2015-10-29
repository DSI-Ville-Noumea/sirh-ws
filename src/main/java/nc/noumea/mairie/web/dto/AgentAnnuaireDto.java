package nc.noumea.mairie.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.PositDesc;
import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Contact;
import nc.noumea.mairie.model.bean.sirh.FichePoste;

public class AgentAnnuaireDto {

	private Integer idAgent;
	private Integer nomatr;
	private String nomUsage;
	private String prenom;
	private String prenomUsage;
	private String nomPatronymique;
	private Date dateNaissance;
	private String sexe;
	private String villeDomicile;
	private Integer codePostalDomicile;
	private String villeBp;
	private Integer codePostalBp;
	/*
	 * Champs concernant les contacts
	 */
	private List<ContactAgentDto> contacts;

	/*
	 * Champs concernant la PA
	 */
	private String cdpadm;
	private String lipadm;
	private String positDesc;
	/*
	 * Champs concernant la FDP
	 */
	private TitrePosteDto poste;
	private Integer idAgentSuperieurHierarchique;
	private String lieuPoste;
	/*
	 * Champs concernant l'entité de l'agent
	 */
	private String servi4;
	private String servi16;
	private String sigleEntite;
	private String libelleEntite;
	private String sigleDirection;
	private String libelleDirection;

	public AgentAnnuaireDto() {
	}

	public AgentAnnuaireDto(Agent ag, Spadmn position, List<Contact> lc, TitrePosteDto posteDto, Integer idAgentSuperieur, String servi4, String servi16, EntiteDto entite, EntiteDto direction,
			FichePoste fichePoste, PositDesc positDesc) {
		this.idAgent = ag.getIdAgent();
		this.nomatr = ag.getNomatr();
		this.nomUsage = ag.getNomUsage() == null ? null : ag.getNomUsage().trim();
		this.prenom = ag.getPrenom() == null ? null : ag.getPrenom().trim();
		this.prenomUsage = ag.getPrenomUsage() == null ? null : ag.getPrenomUsage().trim();
		this.nomPatronymique = ag.getNomPatronymique() == null ? null : ag.getNomPatronymique().trim();
		this.dateNaissance = ag.getDateNaissance();
		this.sexe = ag.getSexe() == null ? null : ag.getSexe().trim();
		//#19389
		this.villeDomicile = ag.getCodeCommuneVilleDom() == null ? null : ag.getCodeCommuneVilleDom().getLibVil().trim();
		this.codePostalDomicile = ag.getCodePostalVilleDom() == null || ag.getCodePostalVilleDom().toString().equals("0") ? null : ag.getCodePostalVilleDom();
		this.villeBp = ag.getCodeCommuneVilleBP() == null ? null : ag.getCodeCommuneVilleBP().getLibVil().trim();
		this.codePostalBp = ag.getCodePostalVilleBP() == null || ag.getCodePostalVilleBP().toString().equals("0") ? null
				: ag.getCodePostalVilleBP();
		// Contacts
		List<ContactAgentDto> listeContact = new ArrayList<ContactAgentDto>();
		for (Contact c : lc) {
			ContactAgentDto dtoContact = new ContactAgentDto(c);
			listeContact.add(dtoContact);
		}
		this.contacts = listeContact;
		// PA
		this.cdpadm = position.getPositionAdministrative() == null ? null : position.getPositionAdministrative().getCdpAdm().trim();
		this.lipadm = position.getPositionAdministrative() == null ? null : position.getPositionAdministrative().getLibelle().trim();
		this.positDesc = positDesc == null ? null : positDesc.getDescription();
		// FDP
		this.poste = posteDto;
		this.idAgentSuperieurHierarchique = idAgentSuperieur;
		this.lieuPoste = null == fichePoste.getLieuPoste() || fichePoste.getLieuPoste().getLibelleLieu() == null ? "" : fichePoste.getLieuPoste().getLibelleLieu().trim();
		// Entite
		this.servi4 = servi4;
		this.servi16 = servi16;
		this.sigleEntite = entite == null ? null : entite.getSigle().trim();
		this.libelleEntite = entite == null ? null : entite.getLabel().trim();
		this.sigleDirection = direction == null ? null : direction.getSigle().trim();
		this.libelleDirection = direction == null ? null : direction.getLabel().trim();

	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
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

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNomPatronymique() {
		return nomPatronymique;
	}

	public void setNomPatronymique(String nomPatronymique) {
		this.nomPatronymique = nomPatronymique;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public List<ContactAgentDto> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactAgentDto> contacts) {
		this.contacts = contacts;
	}

	public String getCdpadm() {
		return cdpadm;
	}

	public void setCdpadm(String cdpadm) {
		this.cdpadm = cdpadm;
	}

	public String getLipadm() {
		return lipadm;
	}

	public void setLipadm(String lipadm) {
		this.lipadm = lipadm;
	}

	public String getPositDesc() {
		return positDesc;
	}

	public void setPositDesc(String positDesc) {
		this.positDesc = positDesc;
	}

	public TitrePosteDto getPoste() {
		return poste;
	}

	public void setPoste(TitrePosteDto poste) {
		this.poste = poste;
	}

	public Integer getIdAgentSuperieurHierarchique() {
		return idAgentSuperieurHierarchique;
	}

	public void setIdAgentSuperieurHierarchique(Integer idAgentSuperieurHierarchique) {
		this.idAgentSuperieurHierarchique = idAgentSuperieurHierarchique;
	}

	public String getServi4() {
		return servi4;
	}

	public void setServi4(String servi4) {
		this.servi4 = servi4;
	}

	public String getServi16() {
		return servi16;
	}

	public void setServi16(String servi16) {
		this.servi16 = servi16;
	}

	public String getSigleEntite() {
		return sigleEntite;
	}

	public void setSigleEntite(String sigleEntite) {
		this.sigleEntite = sigleEntite;
	}

	public String getLibelleEntite() {
		return libelleEntite;
	}

	public void setLibelleEntite(String libelleEntite) {
		this.libelleEntite = libelleEntite;
	}

	public String getSigleDirection() {
		return sigleDirection;
	}

	public void setSigleDirection(String sigleDirection) {
		this.sigleDirection = sigleDirection;
	}

	public String getLibelleDirection() {
		return libelleDirection;
	}

	public void setLibelleDirection(String libelleDirection) {
		this.libelleDirection = libelleDirection;
	}

	public String getLieuPoste() {
		return lieuPoste;
	}

	public void setLieuPoste(String lieuPoste) {
		this.lieuPoste = lieuPoste;
	}

	public String getPrenomUsage() {
		return prenomUsage;
	}

	public void setPrenomUsage(String prenomUsage) {
		this.prenomUsage = prenomUsage;
	}

	public String getVilleDomicile() {
		return villeDomicile;
	}

	public void setVilleDomicile(String villeDomicile) {
		this.villeDomicile = villeDomicile;
	}

	public Integer getCodePostalDomicile() {
		return codePostalDomicile;
	}

	public void setCodePostalDomicile(Integer codePostalDomicile) {
		this.codePostalDomicile = codePostalDomicile;
	}

	public String getVilleBp() {
		return villeBp;
	}

	public void setVilleBp(String villeBp) {
		this.villeBp = villeBp;
	}

	public Integer getCodePostalBp() {
		return codePostalBp;
	}

	public void setCodePostalBp(Integer codePostalBp) {
		this.codePostalBp = codePostalBp;
	}

}
