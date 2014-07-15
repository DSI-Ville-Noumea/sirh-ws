package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.Hashtable;

import nc.noumea.mairie.tools.FichePosteTreeNode;

public interface IFichePosteRepository {

	public Hashtable<Integer, FichePosteTreeNode> getAllFichePosteAndAffectedAgents(Date today);
}
