package nc.noumea.mairie.tools;

import java.util.ArrayList;
import java.util.List;

public class FichePosteTreeNode {

	private Integer idFichePoste;
	private Integer idFichePosteParent;
	private Integer idAgent;
	private List<FichePosteTreeNode> fichePostesEnfant;
	private FichePosteTreeNode fichePosteParent;
	
	public FichePosteTreeNode(Integer _idFichePoste, Integer _idFichePosteParent, Integer _idAgent) {
		this();
		idFichePoste = _idFichePoste;
		idFichePosteParent = _idFichePosteParent;
		idAgent = _idAgent;
	}
	
	public FichePosteTreeNode() {
		fichePostesEnfant = new ArrayList<FichePosteTreeNode>();
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

	public List<FichePosteTreeNode> getFichePostesEnfant() {
		return fichePostesEnfant;
	}

	public void setFichePostesEnfant(List<FichePosteTreeNode> fichePostesEnfant) {
		this.fichePostesEnfant = fichePostesEnfant;
	}

	public FichePosteTreeNode getFichePosteParent() {
		return fichePosteParent;
	}

	public void setFichePosteParent(FichePosteTreeNode fichePosteParent) {
		this.fichePosteParent = fichePosteParent;
	}
}
