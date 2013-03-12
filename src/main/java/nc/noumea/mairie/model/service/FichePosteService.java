package nc.noumea.mairie.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.dao.IFichePosteDao;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.tools.FichePosteTreeNode;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichePosteService implements IFichePosteService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private IFichePosteDao fichePosteDao;

	private Logger logger = LoggerFactory.getLogger(FichePosteService.class);
	private Hashtable<Integer, FichePosteTreeNode> hFpTree;

	@Override
	public FichePoste getFichePostePrimaireAgentAffectationEnCours(Integer idAgent, Date dateJour) {

		FichePoste res = null;
		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(
				"select fp from FichePoste fp JOIN FETCH fp.competencesFDP JOIN FETCH fp.activites, Affectation aff "
						+ "where aff.fichePoste.idFichePoste = fp.idFichePoste and "
						+ "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
						+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)", FichePoste.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", dateJour);

		List<FichePoste> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

	@Override
	public boolean estResponsable(Integer idAgent) {
		String sql = "select count(fp.id_fiche_poste) as nb from sirh.fiche_poste fp inner join sirh.affectation aff on aff.id_fiche_poste = fp.id_fiche_poste where fp.id_responsable = (select fp.id_fiche_poste from sirh.affectation  a inner join sirh.fiche_poste fp on a.id_fiche_poste = fp.id_fiche_poste where a.id_agent=:idAgent and a.date_Debut_Aff<=:dateJour and (a.date_Fin_Aff is null or a.date_Fin_Aff='01/01/0001' or a.date_Fin_Aff>=:dateJour) ) and aff.date_Debut_Aff<=:dateJour and (aff.date_Fin_Aff is null or aff.date_Fin_Aff='01/01/0001' or aff.date_Fin_Aff>=:dateJour)";
		Query query = sirhEntityManager.createNativeQuery(sql);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", new Date());
		Integer nbRes = (Integer) query.getSingleResult();
		return nbRes > 0;
	}

	@Override
	public FichePoste getFichePosteSecondaireAgentAffectationEnCours(Integer idAgent, Date dateJour) {
		FichePoste res = null;
		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(
				"select fp from FichePoste fp JOIN FETCH fp.competencesFDP JOIN FETCH fp.activites, Affectation aff "
						+ "where aff.fichePosteSecondaire.idFichePoste = fp.idFichePoste and "
						+ "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
						+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)", FichePoste.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", dateJour);
		List<FichePoste> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

	@Override
	public String getTitrePosteAgent(Integer idAgent, Date dateJour) {

		String res = null;
		TypedQuery<String> query = sirhEntityManager.createQuery("select fp.titrePoste.libTitrePoste from FichePoste fp, Affectation aff "
				+ "where aff.fichePoste.idFichePoste = fp.idFichePoste and " + "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
				+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)", String.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", dateJour);
		List<String> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

	@Override
	public List<Integer> getListSubFichePoste(int idAgent, int maxDepth) {
		Integer fpId = getIdFichePostePrimaireAgentAffectationEnCours(idAgent, new DateTime().toDate());
		return getSubFichePosteIdsForResponsable(fpId, maxDepth);
	}

	@Override
	public List<Integer> getListSubAgents(int idAgent, int maxDepth) {
		Integer fpId = getIdFichePostePrimaireAgentAffectationEnCours(idAgent, new DateTime().toDate());
		return getSubAgentIdsForFichePoste(fpId, maxDepth);
	}

	@Override
	public List<Integer> getListShdAgents(int idAgent, int maxDepth) {
		Integer fpId = getIdFichePostePrimaireAgentAffectationEnCours(idAgent, new DateTime().toDate());
		return getShdAgentIdsForFichePoste(fpId, maxDepth);
	}
	
	@Override
	public Integer getIdFichePostePrimaireAgentAffectationEnCours(int idAgent, Date date) {

		TypedQuery<Integer> q = sirhEntityManager.createNamedQuery("getCurrentAffectation", Integer.class);
		q.setParameter("idAgent", idAgent);
		q.setParameter("today", date);
		List<Integer> fpIds = q.getResultList();

		if (fpIds.size() != 1) {
			logger.warn(
					"Une erreur s'est produite lors de la recherche d'une affectation pour l'agent {}. Le nombre de résultat est {} affectations au lieu de 1.",
					idAgent, fpIds.size());
			return 0;
		}

		return fpIds.get(0);
	}

	/**
	 * Liste les fiches postes dont la fiche poste en paramètre est la
	 * responsable sur une profondeur de maxDepth niveaux au maximum
	 * 
	 * @param idFichePosteResponsable
	 * @return List<Integer>
	 */
	@Override
	public List<Integer> getSubFichePosteIdsForResponsable(int idFichePosteResponsable, int maxDepth) {

		List<Integer> fichePostes = new ArrayList<Integer>();

		if (!getFichePosteTree().containsKey(idFichePosteResponsable))
			return fichePostes;

		listSubFichesPostes(getFichePosteTree().get(idFichePosteResponsable), fichePostes, maxDepth);

		return fichePostes;
	}

	private List<Integer> listSubFichesPostes(FichePosteTreeNode fichePosteTreeNode, List<Integer> fichePostes, int maxDepth) {

		if (maxDepth == 0)
			return fichePostes;

		for (FichePosteTreeNode node : fichePosteTreeNode.getFichePostesEnfant()) {
			fichePostes.add(node.getIdFichePoste());
			listSubFichesPostes(node, fichePostes, maxDepth - 1);
		}

		return fichePostes;
	}

	/**
	 * Liste les agents dont la fiche poste en paramètre est la responsable sur
	 * une profondeur de maxDepth niveaux au maximum
	 * 
	 * @param idFichePosteResponsable
	 * @return List<Integer>
	 */
	@Override
	public List<Integer> getSubAgentIdsForFichePoste(int idFichePosteResponsable, int maxDepth) {

		List<Integer> agents = new ArrayList<Integer>();

		if (!getFichePosteTree().containsKey(idFichePosteResponsable))
			return agents;

		listSubAgents(getFichePosteTree().get(idFichePosteResponsable), agents, maxDepth);

		return agents;
	}

	private List<Integer> listSubAgents(FichePosteTreeNode fichePosteTreeNode, List<Integer> agents, int maxDepth) {

		if (maxDepth == 0)
			return agents;

		for (FichePosteTreeNode node : fichePosteTreeNode.getFichePostesEnfant()) {
			if (node.getIdAgent() != null)
				agents.add(node.getIdAgent());
			listSubAgents(node, agents, maxDepth - 1);
		}

		return agents;
	}
	
	/**
	 * Liste les agents responsables de la fiche poste en paramètre
	 * sur une profondeur de maxDepth niveaux au maximum
	 * 
	 * @param idFichePoste
	 * @return List<Integer>
	 */
	@Override
	public List<Integer> getShdAgentIdsForFichePoste(int idFichePoste, int maxDepth) {

		List<Integer> agents = new ArrayList<Integer>();

		if (!getFichePosteTree().containsKey(idFichePoste))
			return agents;

		listShdAgents(getFichePosteTree().get(idFichePoste), agents, maxDepth);

		return agents;
	}

	private List<Integer> listShdAgents(FichePosteTreeNode fichePosteTreeNode, List<Integer> agents, int maxDepth) {

		if (maxDepth == 0 || fichePosteTreeNode.getFichePosteParent() == null)
			return agents;

		agents.add(fichePosteTreeNode.getFichePosteParent().getIdAgent());
		
		listShdAgents(fichePosteTreeNode.getFichePosteParent(), agents, maxDepth - 1);

		return agents;
	}

	@Override
	public void construitArbreFichePostes() {
		logger.info("started building fp arbre...");
		Hashtable<Integer, FichePosteTreeNode> tree = getFichePosteTree(true);
		logger.info("finished building fp arbre ...");
		logger.info("fp arbre has {} nodes: ", tree.size());
	}

	/**
	 * Returns the only instance of the tree and builds it thread safely if not
	 * yet existing Retourne l'instance de l'arbre des fiches de poste. Le
	 * construit de manière thread-safe s'il n'existe pas
	 * 
	 * @return Hashtable L'arbre des fiches de postes
	 */
	private Hashtable<Integer, FichePosteTreeNode> getFichePosteTree() {
		return getFichePosteTree(false);
	}

	private Hashtable<Integer, FichePosteTreeNode> getFichePosteTree(boolean forceRebuild) {

		if (!forceRebuild && hFpTree != null)
			return hFpTree;

		synchronized (this) {

			if (!forceRebuild && hFpTree != null)
				return hFpTree;

			hFpTree = construitArbre();
		}

		return hFpTree;
	}

	/**
	 * Construit un arbre hiérarchique des fiches de postes actives
	 * 
	 * @return Hashtablel'arbre des fiches de postes
	 */
	private Hashtable<Integer, FichePosteTreeNode> construitArbre() {

		Hashtable<Integer, FichePosteTreeNode> hTree = fichePosteDao.GetAllFichePosteAndAffectedAgents(new Date());

		int nbNodes = 0, nbNotOrphanNodes = 0;

		for (FichePosteTreeNode node : hTree.values()) {

			logger.debug("node id: {}", node.getIdFichePoste());
			nbNodes++;

			if (node.getIdFichePosteParent() == null) {
				logger.debug("parent node is null", node.getIdFichePoste());
				continue;
			}

			logger.debug("node has a parent: {}", node.getIdFichePosteParent());

			if (!hTree.containsKey(node.getIdFichePosteParent())) {
				logger.debug("parent node is not null but does not exist in tree", node.getIdFichePoste());
				continue;
			}

			nbNotOrphanNodes++;

			FichePosteTreeNode parent = hTree.get(node.getIdFichePosteParent());

			if (parent != null && parent != node) {
				parent.getFichePostesEnfant().add(node);
				node.setFichePosteParent(parent);
			}
		}

		logger.debug("{} nodes total with {} being orphans.", nbNodes, nbNodes - nbNotOrphanNodes);

		return hTree;
	}

	@Override
	public FichePoste getFichePosteById(Integer idFichePoste) {

		FichePoste res = null;
		TypedQuery<FichePoste> query = sirhEntityManager.createQuery("select fp from FichePoste fp where fp.idFichePoste=:idFichePoste",
				FichePoste.class);
		query.setParameter("idFichePoste", idFichePoste);

		List<FichePoste> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}
}
