package nc.noumea.mairie.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NoeudDto {

	private long idNoeud;
	private int idService;
	private long idRevision;
	private String sigle;
	private String label;
	private Integer idTypeNoeud;
	private String codeServi;
	private String lib22;
	private boolean actif;
	private List<NoeudDto> enfants;

	public NoeudDto() {
		enfants = new ArrayList<>();
	}

	public NoeudDto(NoeudDto noeud) {
		mapNoeud(noeud);

		for (NoeudDto n : noeud.getEnfants()) {
			this.enfants.add(new NoeudDto(n));
		}
	}

	public NoeudDto mapNoeud(NoeudDto noeud) {
		this.idNoeud = noeud.getIdNoeud();
		this.idService = noeud.getIdService();
		this.idRevision = noeud.getIdRevision();
		this.sigle = noeud.getSigle();
		this.label = noeud.getLabel();
		this.idTypeNoeud = noeud.getIdTypeNoeud();
		this.codeServi = noeud.getCodeServi();
		this.lib22 = noeud.getLib22();
		this.actif = noeud.isActif();
		this.enfants = new ArrayList<>();

		return this;
	}

	public long getIdNoeud() {
		return idNoeud;
	}

	public void setIdNoeud(long idNoeud) {
		this.idNoeud = idNoeud;
	}

	public int getIdService() {
		return idService;
	}

	public void setIdService(int idService) {
		this.idService = idService;
	}

	public long getIdRevision() {
		return idRevision;
	}

	public void setIdRevision(long idRevision) {
		this.idRevision = idRevision;
	}

	public String getSigle() {
		return sigle;
	}

	public void setSigle(String sigle) {
		this.sigle = sigle;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getIdTypeNoeud() {
		return idTypeNoeud;
	}

	public void setIdTypeNoeud(Integer idTypeNoeud) {
		this.idTypeNoeud = idTypeNoeud;
	}

	public String getCodeServi() {
		return codeServi;
	}

	public void setCodeServi(String codeServi) {
		this.codeServi = codeServi;
	}

	public List<NoeudDto> getEnfants() {
		return enfants;
	}

	public void setEnfants(List<NoeudDto> enfants) {
		this.enfants = enfants;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public String getLib22() {
		return lib22;
	}

	public void setLib22(String lib22) {
		this.lib22 = lib22;
	}
}
