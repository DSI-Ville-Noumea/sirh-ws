package nc.noumea.mairie.web.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.FichePoste;

@XmlRootElement
public class FichePosteLightDto {

	private Integer idFichePoste;
	private String numero;
	private String titre;
	private Integer idServiceADS;
	private String sigle;
	private String statutFDP;
	private String gradePoste;
	private String agent;
	private String categorie;
	private String reglementaire;
	private Integer idAgent;

	private TitrePosteDto titrePoste;
	

	public FichePosteLightDto() {
	}

	public FichePosteLightDto(FichePoste fichePoste, String direction, String service, String section, String sigle) {
		this();
		this.idFichePoste = fichePoste.getIdFichePoste();
		this.numero = fichePoste.getNumFP();

		this.statutFDP = fichePoste.getStatutFP() == null ? "" : fichePoste.getStatutFP().getLibStatut();

		if (null != fichePoste.getAgent()) {
			for (Affectation agt : fichePoste.getAgent()) {
				if (null != agt.getAgent()) {
					this.idAgent = agt.getAgent().getIdAgent();
					break;
				}
			}
		}

		this.titre = fichePoste.getTitrePoste() == null ? "" : fichePoste.getTitrePoste().getLibTitrePoste();

		
		try {
			this.reglementaire = null == fichePoste.getReglementaire()
					|| fichePoste.getReglementaire().getLibHor() == null ? "" : fichePoste.getReglementaire()
					.getLibHor().trim();
		} catch (javax.persistence.EntityNotFoundException e) {
			this.reglementaire = "";
		}

		this.idServiceADS = fichePoste.getIdServiceADS();
		this.sigle = sigle == null ? "" : sigle;
		this.gradePoste = fichePoste.getGradePoste() == null || fichePoste.getGradePoste().getGradeInitial() == null ? ""
				: fichePoste.getGradePoste().getGradeInitial().trim();
	}

	public FichePosteLightDto(Integer idServiceADS, Integer idFichePoste, Set<Affectation> agent) {
		this();
		this.idFichePoste = idFichePoste;
		this.idServiceADS = idServiceADS;
		if (null != agent) {
			for (Affectation agt : agent) {
				if (null != agt && null != agt.getAgent()) {
					this.idAgent = agt.getAgent().getIdAgent();
					break;
				}
			}
		}
	}

	public FichePosteLightDto(FichePoste fichePoste, boolean isInfosCompl, String direction, String service, String section,
			String sigle) {
		this(fichePoste, direction, service, section, sigle);
		this.agent = "";
		this.categorie = "";

		this.statutFDP = fichePoste.getStatutFP().getLibStatut();

		if (null != fichePoste.getAgent()) {
			for (Affectation agt : fichePoste.getAgent()) {
				this.agent = agt.getAgent().getDisplayNom()
						+ " "
						+ agt.getAgent().getDisplayPrenom().substring(0, 1).toUpperCase()
						+ agt.getAgent().getDisplayPrenom().substring(1, agt.getAgent().getDisplayPrenom().length())
								.toLowerCase() + " (" + agt.getAgent().getNomatr().toString() + ")";
			}
		}

		if (null != fichePoste.getGradePoste()) {
			this.categorie = fichePoste.getGradePoste().getGradeGenerique().getCdcadr().trim();
		}
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getReglementaire() {
		return reglementaire;
	}

	public void setReglementaire(String reglementaire) {
		this.reglementaire = reglementaire;
	}
	
	public String getGradePoste() {
		return gradePoste;
	}

	public void setGradePoste(String gradePoste) {
		this.gradePoste = gradePoste;
	}
	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getStatutFDP() {
		return statutFDP;
	}

	public void setStatutFDP(String statutFDP) {
		this.statutFDP = statutFDP;
	}

	public TitrePosteDto getTitrePoste() {
		return titrePoste;
	}

	public void setTitrePoste(TitrePosteDto titrePoste) {
		this.titrePoste = titrePoste;
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}

	public Integer getIdServiceADS() {
		return idServiceADS;
	}

	public void setIdServiceADS(Integer idServiceADS) {
		this.idServiceADS = idServiceADS;
	}

	public String getSigle() {
		return sigle;
	}

	public void setSigle(String sigle) {
		this.sigle = sigle;
	}
}
