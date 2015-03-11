package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.TitrePoste;
import nc.noumea.mairie.tools.FichePosteTreeNode;

public interface IFichePosteRepository {

	Hashtable<Integer, FichePosteTreeNode> getAllFichePosteAndAffectedAgents(Date today);

	List<TitrePoste> getListeTitrePosteChefService();

	List<TitrePoste> getListeTitrePosteDirecteur();
}
