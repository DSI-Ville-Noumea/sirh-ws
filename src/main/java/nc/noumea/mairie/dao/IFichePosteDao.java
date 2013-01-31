package nc.noumea.mairie.dao;

import java.util.Date;
import java.util.Hashtable;

import nc.noumea.mairie.tools.FichePosteTreeNode;

public interface IFichePosteDao {

	public Hashtable<Integer, FichePosteTreeNode> GetAllFichePosteAndAffectedAgents(Date today);
}
