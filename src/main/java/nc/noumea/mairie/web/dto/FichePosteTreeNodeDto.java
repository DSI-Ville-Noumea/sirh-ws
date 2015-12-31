package nc.noumea.mairie.web.dto;

import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.FichePoste;

public class FichePosteTreeNodeDto extends FichePosteDto {

	private Integer idFichePoste;
	private Integer idFichePosteParent;
	private Integer idAgent;
	private List<FichePosteTreeNodeDto> fichePostesEnfant;
	private FichePosteTreeNodeDto fichePosteParent;

	public FichePosteTreeNodeDto() {
		fichePostesEnfant = new ArrayList<FichePosteTreeNodeDto>();
	}

	public FichePosteTreeNodeDto(Integer _idFichePoste, Integer _idFichePosteParent, Integer _idAgent) {
		this();
		idFichePoste = _idFichePoste;
		idFichePosteParent = _idFichePosteParent;
		idAgent = _idAgent;
	}

	public FichePosteTreeNodeDto(Integer _idFichePoste, Integer _idFichePosteParent, Integer _idAgent, FichePoste fichePoste, String sigle, String libelleService) {

		super(fichePoste, sigle, libelleService);
		idFichePoste = _idFichePoste;
		idFichePosteParent = _idFichePosteParent;
		idAgent = _idAgent;

		fichePostesEnfant = new ArrayList<FichePosteTreeNodeDto>();
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}

	public Integer getIdFichePosteParent() {
		return idFichePosteParent;
	}

	public void setIdFichePosteParent(Integer idFichePosteParent) {
		this.idFichePosteParent = idFichePosteParent;
	}

	public Integer getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}

	public List<FichePosteTreeNodeDto> getFichePostesEnfant() {
		return fichePostesEnfant;
	}

	public void setFichePostesEnfant(List<FichePosteTreeNodeDto> fichePostesEnfant) {
		this.fichePostesEnfant = fichePostesEnfant;
	}

	public FichePosteTreeNodeDto getFichePosteParent() {
		return fichePosteParent;
	}

	public void setFichePosteParent(FichePosteTreeNodeDto fichePosteParent) {
		this.fichePosteParent = fichePosteParent;
	}
}
