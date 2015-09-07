package nc.noumea.mairie.service.sirh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Sppost;
import nc.noumea.mairie.model.bean.ads.StatutEntiteEnum;
import nc.noumea.mairie.model.bean.sirh.ActionFdpJob;
import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.AvantageNature;
import nc.noumea.mairie.model.bean.sirh.AvantageNatureFP;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.Delegation;
import nc.noumea.mairie.model.bean.sirh.DelegationFP;
import nc.noumea.mairie.model.bean.sirh.EnumStatutFichePoste;
import nc.noumea.mairie.model.bean.sirh.EnumTypeHisto;
import nc.noumea.mairie.model.bean.sirh.FeFp;
import nc.noumea.mairie.model.bean.sirh.FicheEmploi;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.HistoFichePoste;
import nc.noumea.mairie.model.bean.sirh.NFA;
import nc.noumea.mairie.model.bean.sirh.NatureCredit;
import nc.noumea.mairie.model.bean.sirh.NiveauEtude;
import nc.noumea.mairie.model.bean.sirh.NiveauEtudeFP;
import nc.noumea.mairie.model.bean.sirh.PrimePointageFP;
import nc.noumea.mairie.model.bean.sirh.RegIndemFP;
import nc.noumea.mairie.model.bean.sirh.RegimeIndemnitaire;
import nc.noumea.mairie.model.bean.sirh.StatutFichePoste;
import nc.noumea.mairie.model.pk.sirh.ActiviteFPPK;
import nc.noumea.mairie.model.pk.sirh.CompetenceFPPK;
import nc.noumea.mairie.model.pk.sirh.FeFpPK;
import nc.noumea.mairie.model.pk.sirh.PrimePointageFPPK;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.model.repository.sirh.IFichePosteRepository;
import nc.noumea.mairie.service.ads.IAdsService;
import nc.noumea.mairie.tools.FichePosteTreeNode;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.FichePosteDto;
import nc.noumea.mairie.web.dto.FichePosteTreeNodeDto;
import nc.noumea.mairie.web.dto.GroupeInfoFichePosteDto;
import nc.noumea.mairie.web.dto.InfoEntiteDto;
import nc.noumea.mairie.web.dto.InfoFichePosteDto;
import nc.noumea.mairie.web.dto.SpbhorDto;
import nc.noumea.mairie.ws.IADSWSConsumer;
import nc.noumea.mairie.ws.ISirhPtgWSConsumer;
import nc.noumea.mairie.ws.dto.LightUserDto;
import nc.noumea.mairie.ws.dto.RefPrimeDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FichePosteService implements IFichePosteService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private IFichePosteRepository fichePosteDao;

	@Autowired
	private IUtilisateurService utilisateurSrv;

	@Autowired
	private ISirhPtgWSConsumer sirhPtgWSConsumer;

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private IAffectationService affSrv;

	@Autowired
	private IMairieRepository mairieRepository;

	@Autowired
	private IADSWSConsumer adsWSConsumer;

	@Autowired
	private IAdsService adsService;

	private Logger logger = LoggerFactory.getLogger(FichePosteService.class);
	protected Hashtable<Integer, FichePosteTreeNode> hFpTree;
	protected TreeMap<Integer, FichePosteTreeNode> hFpTreeWithFPAffecteesEtNonAffectees;

	@Override
	public FichePoste getFichePostePrimaireAgentAffectationEnCours(Integer idAgent, Date dateJour, boolean withCompetenceAndActivities) {

		String requete = "select fp from FichePoste fp ";
		if (withCompetenceAndActivities) {
			requete += "LEFT JOIN FETCH fp.competencesFDP LEFT JOIN FETCH fp.activites";
		}
		requete += ", Affectation aff " + "where aff.fichePoste.idFichePoste = fp.idFichePoste and " + "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
				+ "(aff.dateFinAff is null or aff.dateFinAff>=:dateJour)";
		FichePoste res = null;
		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(requete, FichePoste.class);
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
		String sql = "select count(fp.id_fiche_poste) as nb from fiche_poste fp inner join affectation aff on aff.id_fiche_poste = fp.id_fiche_poste where fp.id_responsable = (select fp.id_fiche_poste from affectation  a inner join fiche_poste fp on a.id_fiche_poste = fp.id_fiche_poste where a.id_agent=:idAgent and a.date_Debut_Aff<=:dateJour and (a.date_Fin_Aff is null or a.date_Fin_Aff>=:dateJour) ) and aff.date_Debut_Aff<=:dateJour and (aff.date_Fin_Aff is null or aff.date_Fin_Aff>=:dateJour)";
		Query query = sirhEntityManager.createNativeQuery(sql);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", new Date());
		Integer nbRes = (Integer) query.getSingleResult();
		return nbRes > 0;
	}

	@Override
	public FichePoste getFichePosteSecondaireAgentAffectationEnCours(Integer idAgent, Date dateJour) {
		FichePoste res = null;
		TypedQuery<FichePoste> query = sirhEntityManager.createQuery("select fp from FichePoste fp JOIN FETCH fp.competencesFDP JOIN FETCH fp.activites, Affectation aff "
				+ "where aff.fichePosteSecondaire.idFichePoste = fp.idFichePoste and " + "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
				+ "(aff.dateFinAff is null or aff.dateFinAff>=:dateJour)", FichePoste.class);
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
		TypedQuery<String> query = sirhEntityManager.createQuery("select fp.titrePoste.libTitrePoste from FichePoste fp, Affectation aff " + "where aff.fichePoste.idFichePoste = fp.idFichePoste and "
				+ "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and " + "(aff.dateFinAff is null or aff.dateFinAff>=:dateJour)", String.class);
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
	public List<Integer> getListSubAgents(int idAgent, int maxDepth, String nom) {
		Integer fpId = getIdFichePostePrimaireAgentAffectationEnCours(idAgent, new DateTime().toDate());
		return getSubAgentIdsForFichePoste(fpId, maxDepth, nom);
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
			logger.warn("Une erreur s'est produite lors de la recherche d'une affectation pour l'agent {}. Le nombre de résultat est {} affectations au lieu de 1.", idAgent, fpIds.size());
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
	public List<Integer> getSubAgentIdsForFichePoste(int idFichePosteResponsable, int maxDepth, String nom) {

		List<Integer> agents = new ArrayList<Integer>();

		if (!getFichePosteTree().containsKey(idFichePosteResponsable))
			return agents;

		listSubAgents(getFichePosteTree().get(idFichePosteResponsable), agents, maxDepth, nom);

		return agents;
	}

	private List<Integer> listSubAgents(FichePosteTreeNode fichePosteTreeNode, List<Integer> agents, int maxDepth, String nom) {

		if (maxDepth == 0)
			return agents;

		for (FichePosteTreeNode node : fichePosteTreeNode.getFichePostesEnfant()) {
			if (node.getIdAgent() != null) {
				if (nom != null) {
					if (agentSrv.findAgentWithName(node.getIdAgent(), nom) != null) {
						agents.add(node.getIdAgent());
					}
				} else {
					agents.add(node.getIdAgent());
				}
			}
			listSubAgents(node, agents, maxDepth - 1, nom);
		}

		return agents;
	}

	/**
	 * Liste les agents responsables de la fiche poste en paramètre sur une
	 * profondeur de maxDepth niveaux au maximum
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

		if (fichePosteTreeNode.getFichePosteParent().getIdAgent() != null) {
			agents.add(fichePosteTreeNode.getFichePosteParent().getIdAgent());
		}

		listShdAgents(fichePosteTreeNode.getFichePosteParent(), agents, maxDepth - 1);

		return agents;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FichePosteTreeNodeDto> getTreeFichesPosteByIdEntite(int idEntite, boolean withFichesPosteNonReglementaires) {

		// on recherche la liste des fiches de poste appartement a un service
		List<FichePoste> listFichesPoste = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(
				Arrays.asList(idEntite),
				Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.EN_CREATION.getStatut(), EnumStatutFichePoste.GELEE.getStatut(),
						EnumStatutFichePoste.TRANSITOIRE.getStatut()));

		if (null == listFichesPoste || listFichesPoste.isEmpty())
			return null;

		// on recherche le ou les fiches de poste ayant la plus haute hierarchie
		// (niveau 0)
		List<Integer> listTreeParent = rechercheFichesPosteParent(listFichesPoste);

		// #17987 dans le but d ameliorer les performances et reduire le nombre
		// de requetes
		// on recupere tous les idFichePoste pour récupérer toutes les fiches de
		// poste en une seule requete
		List<FichePoste> listFichesPosteByFichePosteParent = getAllFichesPoste(listTreeParent);

		List<EntiteDto> listEntiteDtoForOptimize = new ArrayList<EntiteDto>();
		List<FichePosteTreeNodeDto> result = new ArrayList<FichePosteTreeNodeDto>();

		for (Integer idFichePosteParent : listTreeParent) {
			FichePosteTreeNodeDto treeNode = constructFichePosteTreeNodeDto(getFichePosteTreeAffecteesEtNonAffectees().get(idFichePosteParent), listEntiteDtoForOptimize,
					withFichesPosteNonReglementaires, listFichesPosteByFichePosteParent);

			if (null != treeNode)
				result.add(treeNode);
		}

		return result;
	}

	protected List<Integer> rechercheFichesPosteParent(List<FichePoste> listFichesPoste) {

		Hashtable<Integer, FichePosteTreeNode> hTree = new Hashtable<Integer, FichePosteTreeNode>();

		for (FichePoste node : listFichesPoste) {
			hTree.put(node.getIdFichePoste(), new FichePosteTreeNode(node.getIdFichePoste(), null == node.getSuperieurHierarchique() ? null : node.getSuperieurHierarchique().getIdFichePoste(), null));
		}

		for (FichePoste node : listFichesPoste) {

			logger.debug("node id: {}", node.getIdFichePoste());

			if (node.getSuperieurHierarchique() == null) {
				logger.debug("parent node is null", node.getIdFichePoste());
				continue;
			}

			logger.debug("node has a parent: {}", node.getSuperieurHierarchique().getIdFichePoste());

			if (!hTree.containsKey(node.getSuperieurHierarchique().getIdFichePoste())) {
				logger.debug("parent node is not null but does not exist in tree", node.getIdFichePoste());
				continue;
			}

			FichePosteTreeNode fichePosteNode = hTree.get(node.getIdFichePoste());
			FichePosteTreeNode parent = hTree.get(node.getSuperieurHierarchique().getIdFichePoste());

			if (parent != null && !parent.getIdFichePoste().equals(node.getIdFichePoste())) {
				parent.getFichePostesEnfant().add(fichePosteNode);
				fichePosteNode.setFichePosteParent(parent);
			}
		}

		List<Integer> listIdsFichesPosteParent = new ArrayList<Integer>();
		for (FichePosteTreeNode node : hTree.values()) {
			if (null == node.getFichePosteParent()) {
				listIdsFichesPosteParent.add(node.getIdFichePoste());
			}
		}

		return listIdsFichesPosteParent;
	}

	private List<FichePoste> getAllFichesPoste(List<Integer> listTreeParent) {

		List<Integer> listIdsFichePoste = new ArrayList<Integer>();
		for (Integer idFichePosteParent : listTreeParent) {
			listIdsFichePoste.addAll(getAllIdsFichePosteRecursive(getFichePosteTreeAffecteesEtNonAffectees().get(idFichePosteParent)));
		}

		List<FichePoste> listResult = null;
		if (null != listIdsFichePoste && !listIdsFichePoste.isEmpty()) {
			listResult = fichePosteDao.chercherListFichesPosteByListIdsFichePoste(listIdsFichePoste);
		}
		return listResult;
	}

	private List<Integer> getAllIdsFichePosteRecursive(FichePosteTreeNode node) {

		List<Integer> result = new ArrayList<Integer>();
		result.add(node.getIdFichePoste());

		for (FichePosteTreeNode enfant : node.getFichePostesEnfant()) {
			result.addAll(getAllIdsFichePosteRecursive(enfant));
		}

		return result;
	}

	private FichePosteTreeNodeDto constructFichePosteTreeNodeDto(FichePosteTreeNode root, List<EntiteDto> listEntiteDto, boolean withFichesPosteNonReglemente,
			List<FichePoste> listFichesPosteByFichePosteParent) {

		FichePosteTreeNodeDto dto = null;

		if (null != root) {
			FichePoste fichePoste = getFichePosteInListFichesPoste(listFichesPosteByFichePosteParent, root.getIdFichePoste());

			if (null != fichePoste && (withFichesPosteNonReglemente || null == fichePoste.getReglementaire() || !fichePoste.getReglementaire().getCdThor().equals(0))) {
				EntiteDto entite = adsService.getEntiteByIdEntiteOptimise(fichePoste.getIdServiceADS(), listEntiteDto);
				dto = new FichePosteTreeNodeDto(root.getIdFichePoste(), null, root.getIdAgent(), fichePoste, entite == null ? "" : entite.getSigle());

				if (null != root.getFichePostesEnfant()) {
					for (FichePosteTreeNode enfant : root.getFichePostesEnfant()) {
						FichePosteTreeNodeDto dtoEnfant = constructFichePosteTreeNodeDto(enfant, listEntiteDto, withFichesPosteNonReglemente, listFichesPosteByFichePosteParent);
						if (null != dtoEnfant)
							dto.getFichePostesEnfant().add(dtoEnfant);
					}
				}
			}
		}

		return dto;
	}

	private FichePoste getFichePosteInListFichesPoste(List<FichePoste> listFichesPosteByFichePosteParent, Integer idFichePoste) {

		if (null != listFichesPosteByFichePosteParent) {
			for (FichePoste fp : listFichesPosteByFichePosteParent) {
				if (fp.getIdFichePoste().equals(idFichePoste)) {
					return fp;
				}
			}
		}
		return null;
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
	 * Retourne l'instance de l'arbre des fiches de poste affectees et non
	 * affectees en statut EnCreation, Validee, Gelee et transitoire. Le
	 * construit de manière thread-safe s'il n'existe pas.
	 * 
	 * @return Hashtable L'arbre des fiches de postes
	 */
	protected TreeMap<Integer, FichePosteTreeNode> getFichePosteTreeAffecteesEtNonAffectees() {

		TreeMap<Integer, FichePosteTreeNode> hTreeTemp = fichePosteDao.getAllFichePoste(new Date());

		if (null != hFpTreeWithFPAffecteesEtNonAffectees && hTreeTemp.size() == hFpTreeWithFPAffecteesEtNonAffectees.size() && hTreeTemp.lastKey() == hFpTreeWithFPAffecteesEtNonAffectees.lastKey())
			return hFpTreeWithFPAffecteesEtNonAffectees;

		synchronized (this) {

			if (null != hFpTreeWithFPAffecteesEtNonAffectees && hTreeTemp.size() == hFpTreeWithFPAffecteesEtNonAffectees.size()
					&& hTreeTemp.lastKey() == hFpTreeWithFPAffecteesEtNonAffectees.lastKey())
				return hFpTreeWithFPAffecteesEtNonAffectees;

			hFpTreeWithFPAffecteesEtNonAffectees = construitArbreWithFPAffecteesEtNonAffectees();
		}

		return hFpTreeWithFPAffecteesEtNonAffectees;
	}

	/**
	 * Construit un arbre hiérarchique des fiches de postes actives
	 * 
	 * @return Hashtablel'arbre des fiches de postes
	 */
	private TreeMap<Integer, FichePosteTreeNode> construitArbreWithFPAffecteesEtNonAffectees() {

		TreeMap<Integer, FichePosteTreeNode> hTree = fichePosteDao.getAllFichePoste(new Date());

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

	/**
	 * Construit un arbre hiérarchique des fiches de postes actives
	 * 
	 * @return Hashtablel'arbre des fiches de postes
	 */
	private Hashtable<Integer, FichePosteTreeNode> construitArbre() {

		Hashtable<Integer, FichePosteTreeNode> hTree = fichePosteDao.getAllFichePosteAndAffectedAgents(new Date());

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
		TypedQuery<FichePoste> query = sirhEntityManager.createQuery("select fp from FichePoste fp where fp.idFichePoste=:idFichePoste", FichePoste.class);
		query.setParameter("idFichePoste", idFichePoste);

		List<FichePoste> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

	@Override
	public FichePoste getFichePosteDetailleSIRHByIdWithRefPrime(Integer idFichePoste) {

		FichePoste fp = getFichePosteById(idFichePoste);
		if (fp != null) {
			for (PrimePointageFP prime : fp.getPrimePointageFP()) {
				RefPrimeDto primeDto = sirhPtgWSConsumer.getPrime(prime.getPrimePointageFPPK().getNumRubrique());
				prime.setLibelle(primeDto.getLibelle());
			}
		}

		return fp;
	}

	@Override
	public List<SpbhorDto> getListSpbhorDto() {

		List<SpbhorDto> listResult = new ArrayList<SpbhorDto>();

		List<Spbhor> listOfPartialTimes = mairieRepository.getListSpbhor();

		if (null != listOfPartialTimes) {
			for (Spbhor hor : listOfPartialTimes) {
				SpbhorDto dto = new SpbhorDto(hor);
				listResult.add(dto);
			}
		}
		return listResult;
	}

	@Override
	public SpbhorDto getSpbhorDtoById(Integer idSpbhor) {

		Spbhor result = mairieRepository.getSpbhorById(idSpbhor);
		return new SpbhorDto(result);
	}

	@Override
	public List<FichePosteDto> getListFichePosteByIdServiceADSAndStatutFDP(Integer idEntite, List<Integer> listStatutFDP, boolean withEntiteChildren) {
		List<FichePosteDto> result = new ArrayList<FichePosteDto>();
		List<FichePoste> listeFDP = new ArrayList<FichePoste>();

		EntiteDto entiteRoot = null;

		if (withEntiteChildren) {
			entiteRoot = adsWSConsumer.getEntiteWithChildrenByIdEntite(idEntite);
			List<Integer> listeEnfant = getListIdsEntiteEnfants(entiteRoot);
			if (!listeEnfant.contains(entiteRoot.getIdEntite()))
				listeEnfant.add(entiteRoot.getIdEntite());

			listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(listeEnfant, listStatutFDP);
		} else {
			entiteRoot = adsWSConsumer.getEntiteByIdEntite(idEntite);
			listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntite), listStatutFDP);
		}

		for (FichePoste fp : listeFDP) {
			FichePosteDto dto = new FichePosteDto(fp, adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entiteRoot, fp.getIdServiceADS()));
			result.add(dto);
		}
		return result;
	}

	private List<Integer> getListIdsEntiteEnfants(EntiteDto entiteDto) {

		List<Integer> result = new ArrayList<Integer>();

		if (null != entiteDto && null != entiteDto.getEnfants()) {
			for (EntiteDto enfant : entiteDto.getEnfants()) {
				result.add(enfant.getIdEntite().intValue());
				result.addAll(getListIdsEntiteEnfants(enfant));
			}
		}
		return result;
	}

	@Override
	public ReturnMessageDto deleteFichePosteByIdEntite(Integer idEntite, Integer idAgent) {
		ReturnMessageDto result = new ReturnMessageDto();

		List<FichePoste> listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntite), null);
		// on regarde que toutes les FDP soient en statut "En creation" et que
		// la FDP n'est jamais été affectée à un agent
		for (FichePoste fp : listeFDP) {
			if (fp.getStatutFP().getIdStatutFp() != 1) {
				result.getErrors().add("La FDP " + fp.getNumFP() + " n'est pas en statut 'En création'.");
			}
			if (affSrv.getListAffectationByIdFichePoste(fp.getIdFichePoste()).size() > 0) {
				result.getErrors().add("La FDP " + fp.getNumFP() + " a déjà été affectée.");
			}
		}
		if (result.getErrors().size() > 0) {
			return result;
		}

		// on crée un job de lancement de suppression des FDP
		for (FichePoste fp : listeFDP) {
			ActionFdpJob job = new ActionFdpJob(fp.getIdFichePoste(), idAgent, "SUPPRESSION", null);
			fichePosteDao.persisEntity(job);
		}
		result.getInfos().add(listeFDP.size() + " FDP vont être supprimées. Merci d'aller regarder le resultat de cette suppression dans SIRH.");
		return result;
	}

	@Override
	public InfoEntiteDto getInfoFDP(Integer idEntite, boolean withEntiteChildren) {

		InfoEntiteDto result = new InfoEntiteDto();

		if (withEntiteChildren) {
			EntiteDto entiteParent = adsWSConsumer.getEntiteWithChildrenByIdEntite(idEntite);
			List<Integer> listeEnfant = getListIdsEntiteEnfants(entiteParent);

			if (!listeEnfant.contains(entiteParent.getIdEntite()))
				listeEnfant.add(entiteParent.getIdEntite());
			result.setIdEntite(idEntite);
			List<GroupeInfoFichePosteDto> resFDP = fichePosteDao.getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(listeEnfant);
			result.getListeInfoFDP().addAll(resFDP);
			getListeNumFPByIdServiceADSAndTitrePoste(result, listeEnfant);

		} else {
			result.setIdEntite(idEntite);
			List<Integer> listeEnfant = new ArrayList<Integer>();
			listeEnfant.add(idEntite);
			List<GroupeInfoFichePosteDto> resFDP = fichePosteDao.getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(listeEnfant);
			result.getListeInfoFDP().addAll(resFDP);
			getListeNumFPByIdServiceADSAndTitrePoste(result, listeEnfant);
		}

		return result;

	}

	private void getListeNumFPByIdServiceADSAndTitrePoste(InfoEntiteDto result, List<Integer> listeIdServiceAds) {

		if (null != result.getListeInfoFDP()) {
			for (GroupeInfoFichePosteDto resFDP : result.getListeInfoFDP()) {

				List<InfoFichePosteDto> listInfoFichePosteDto = fichePosteDao.getListInfoFichePosteDtoByIdServiceADSAndTitrePoste(listeIdServiceAds, resFDP.getTitreFDP(), new Date());

				if (null != listInfoFichePosteDto)
					resFDP.setListInfoFichePosteDto(listInfoFichePosteDto);
			}
		}
	}

	@Override
	public ReturnMessageDto dupliqueFichePosteByIdEntite(Integer idEntiteNew, Integer idEntiteOld, Integer idAgent) {
		ReturnMessageDto result = new ReturnMessageDto();
		// on cherche toutes les FDP validées
		List<FichePoste> listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntiteOld), Arrays.asList(2));

		// on crée un job de lancement de duplication des FDP
		for (FichePoste fp : listeFDP) {
			ActionFdpJob job = new ActionFdpJob(fp.getIdFichePoste(), idAgent, "DUPLICATION", idEntiteNew);
			fichePosteDao.persisEntity(job);
		}
		EntiteDto entite = adsWSConsumer.getEntiteByIdEntite(idEntiteNew);
		if (listeFDP.size() == 0) {
			result.getInfos().add("Aucune FDP ne va être dupliquée sur l'entité " + entite.getSigle() + ".");
		} else if (listeFDP.size() == 1) {
			result.getInfos().add("1 FDP va être dupliquée sur l'entité " + entite.getSigle() + ". Merci d'aller regarder le resultat de cette duplication dans SIRH.");
		} else {
			result.getInfos().add(listeFDP.size() + " FDP vont être dupliquées sur l'entité " + entite.getSigle() + ". Merci d'aller regarder le resultat de cette duplication dans SIRH.");
		}
		return result;
	}

	@Override
	@Transactional(value = "sirhTransactionManager")
	public ReturnMessageDto deleteFichePosteByIdFichePoste(Integer idFichePoste, Integer idAgent) {
		ReturnMessageDto result = new ReturnMessageDto();

		FichePoste fichePoste = fichePosteDao.chercherFichePoste(idFichePoste);
		// on verifie que la FDP existe
		if (fichePoste == null) {
			result.getErrors().add("La FDP id " + idFichePoste + " n'existe pas.");
			return result;
		}
		// on verifie que la FDP est bien "en création"
		if (!fichePoste.getStatutFP().getIdStatutFp().toString().equals("1")) {
			result.getErrors().add("La FDP id " + idFichePoste + " n'est pas en statut 'en création'.");
			return result;
		}
		// on vérifie que la FDP n'est pas dejà affectée
		List<Affectation> listAffSurFDP = affSrv.getListAffectationByIdFichePoste(idFichePoste);
		if (listAffSurFDP.size() > 0) {
			result.getErrors().add("La FDP id " + idFichePoste + " est affectée.");
			return result;
		}

		// on cherche le login de l'agent qui fait l'action
		LightUserDto user = utilisateurSrv.getLoginByIdAgent(idAgent);
		if (user == null || user.getsAMAccountName() == null) {
			result.getErrors().add("L'agent qui tente de faire l'action n'a pas de login dans l'AD.");
			return result;
		}

		// on supprime la FDP
		try {
			supprimerFDP(fichePoste, user.getsAMAccountName());
			result.getInfos().add("La FDP " + fichePoste.getNumFP() + " est supprimée.");
		} catch (Exception e) {
			result.getErrors().add("La FDP " + fichePoste.getNumFP() + " n'a pu être suprimée.");
		}

		return result;
	}

	private void supprimerFDP(FichePoste fichePoste, String login) {
		HistoFichePoste histo = new HistoFichePoste(fichePoste);
		// supprimer la FDP en base (dans HISTO on sauvegarde l'action) et
		// SPPOST
		// supprimer les actvites, comptences...
		ArrayList<FeFp> liensA = (ArrayList<FeFp>) fichePosteDao.listerFEFPAvecFP(fichePoste.getIdFichePoste());
		for (FeFp lien : liensA) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<NiveauEtudeFP> niveauFPExistant = (ArrayList<NiveauEtudeFP>) fichePosteDao.listerNiveauEtudeFPAvecFP(fichePoste.getIdFichePoste());
		for (NiveauEtudeFP lien : niveauFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<ActiviteFP> activiteFPExistant = (ArrayList<ActiviteFP>) fichePosteDao.listerActiviteFPAvecFP(fichePoste.getIdFichePoste());
		for (ActiviteFP lien : activiteFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<CompetenceFP> competencesFPExistant = (ArrayList<CompetenceFP>) fichePosteDao.listerCompetenceFPAvecFP(fichePoste.getIdFichePoste());
		for (CompetenceFP lien : competencesFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<AvantageNatureFP> avantagesFPExistant = (ArrayList<AvantageNatureFP>) fichePosteDao.listerAvantageNatureFPAvecFP(fichePoste.getIdFichePoste());
		for (AvantageNatureFP lien : avantagesFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<DelegationFP> delegationFPExistant = (ArrayList<DelegationFP>) fichePosteDao.listerDelegationFPAvecFP(fichePoste.getIdFichePoste());
		for (DelegationFP lien : delegationFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<PrimePointageFP> primesPointagesFPExistant = (ArrayList<PrimePointageFP>) fichePosteDao.listerPrimePointageFP(fichePoste.getIdFichePoste());
		for (PrimePointageFP lien : primesPointagesFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<RegIndemFP> regimesFPExistant = (ArrayList<RegIndemFP>) fichePosteDao.listerRegIndemFPFPAvecFP(fichePoste.getIdFichePoste());
		for (RegIndemFP lien : regimesFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		// on actualise la FDP suite à la suppression des liens
		fichePosteDao.flush();

		fichePoste = fichePosteDao.chercherFichePoste(fichePoste.getIdFichePoste());
		// on supprime enfin la FDP
		fichePosteDao.removeEntity(fichePoste);

		// aussi de SPPOST
		Sppost posteAS400 = fichePosteDao.chercherSppost(new Integer(histo.getNumFp().substring(0, 4)), new Integer(fichePoste.getNumFP().substring(5, histo.getNumFp().length())));
		if (posteAS400 != null) {
			fichePosteDao.removeEntity(posteAS400);
		}

		// historisation
		histo.setDateHisto(new Date());
		histo.setUserHisto(login);
		histo.setTypeHisto(EnumTypeHisto.SUPPRESSION.getValue());
		fichePosteDao.persisEntity(histo);
		fichePosteDao.flush();

	}

	@Override
	@Transactional(value = "sirhTransactionManager")
	public ReturnMessageDto dupliqueFichePosteByIdFichePoste(Integer idFichePoste, Integer idEntite, Integer idAgent) {
		// #16242 : RG dupliaction et #17455

		ReturnMessageDto result = new ReturnMessageDto();

		FichePoste fichePoste = fichePosteDao.chercherFichePoste(idFichePoste);
		// on verifie que la FDP existe
		if (fichePoste == null) {
			result.getErrors().add("La FDP id " + idFichePoste + " n'existe pas.");
			return result;
		}
		// on verifie que la FDP est bien "validée"
		if (!fichePoste.getStatutFP().getIdStatutFp().toString().equals("2")) {
			result.getErrors().add("La FDP id " + idFichePoste + " n'est pas en statut 'validée'.");
			return result;
		}
		// on verifie que l'entite est bien "prévision"
		EntiteDto entite = adsService.getEntiteByIdEntiteOptimise(idEntite, new ArrayList<EntiteDto>());

		if (null == entite) {
			result.getErrors().add("L'entite id " + idEntite + " n'existe pas ou plus.");
			return result;
		}

		if (!entite.getIdStatut().toString().equals(String.valueOf(StatutEntiteEnum.PREVISION.getIdRefStatutEntite()))) {
			result.getErrors().add("L'entite id " + idEntite + " n'est pas en statut 'prévision'.");
			return result;
		}
		// on cherche le login de l'agent qui fait l'action
		LightUserDto user = utilisateurSrv.getLoginByIdAgent(idAgent);
		if (user == null || user.getsAMAccountName() == null) {
			result.getErrors().add("L'agent qui tente de faire l'action n'a pas de login dans l'AD.");
			return result;
		}

		// on duplique la FDP
		try {
			String numNewFDP = dupliquerFDP(fichePoste, entite, user.getsAMAccountName());
			result.getInfos().add("La FDP " + fichePoste.getNumFP() + " est dupliquée en " + numNewFDP + ".");
		} catch (Exception e) {
			result.getErrors().add("La FDP " + fichePoste.getNumFP() + " n'a pu être dupliquée.");
		}

		return result;
	}

	private String dupliquerFDP(FichePoste fichePoste, EntiteDto entite, String login) throws CloneNotSupportedException {

		FichePoste fichePDupliquee = cloneFDP(fichePoste);
		fichePDupliquee.setIdFichePoste(null);
		StatutFichePoste statutCreation = fichePosteDao.chercherStatutFPByIdStatut(1);
		fichePDupliquee.setStatutFP(statutCreation);
		fichePDupliquee.setNumFP(null);
		fichePDupliquee.setSuperieurHierarchique(null);
		fichePDupliquee.setRemplace(null);
		fichePDupliquee.setAgent(null);
		// on positionne l'année sur l'année cours
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer annee = cal.get(Calendar.YEAR);
		fichePDupliquee.setAnnee(annee);
		// on genere le numero de la FDP
		fichePDupliquee.setNumFP(createFichePosteNumber(fichePDupliquee.getAnnee()));
		// on gere les infos liées à l'entité
		fichePDupliquee.setDateDebAppliServ(entite.getDateDeliberationActif());
		fichePDupliquee.setNumDeliberation(entite.getRefDeliberationActif());
		EntiteDto serv = adsService.getInfoSiservByIdEntite(entite.getIdEntite());
		fichePDupliquee.setIdServi(serv == null || serv.getCodeServi() == null ? null : serv.getCodeServi());
		fichePDupliquee.setIdServiceADS(entite.getIdEntite());
		// on cherche la NFA de l'entite
		NFA nfaEntite = fichePosteDao.chercherNFA(entite.getIdEntite());
		fichePDupliquee.setNfa(nfaEntite == null ? "0" : nfaEntite.getNfa());
		fichePDupliquee.setNumDeliberation(entite.getRefDeliberationActif());

		// on crée les liens
		FeFp lienPrimaire = fichePosteDao.chercherFEFPAvecFP(fichePoste.getIdFichePoste(), 1);
		if (lienPrimaire != null) {
			FicheEmploi fePrimaire = fichePosteDao.chercherFicheEmploi(lienPrimaire.getId().getIdFicheEmploi());
			if (fePrimaire != null) {
				FeFpPK feFpPk = new FeFpPK();
				feFpPk.setIdFicheEmploi(fePrimaire.getIdFicheEmploi());
				feFpPk.setIdFichePoste(fichePDupliquee.getIdFichePoste());

				FeFp feFp = new FeFp();
				feFp.setId(feFpPk);
				feFp.setFePrimaire(1);
				feFp.setFichePoste(fichePDupliquee);
				feFp.setFicheEmploi(fePrimaire);

				fichePDupliquee.getFicheEmploi().add(feFp);
			}
		}

		FeFp lienSecondaire = fichePosteDao.chercherFEFPAvecFP(fichePoste.getIdFichePoste(), 0);
		if (lienSecondaire != null) {
			FicheEmploi feSecondaire = fichePosteDao.chercherFicheEmploi(lienSecondaire.getId().getIdFicheEmploi());
			if (feSecondaire != null) {
				FeFpPK feFpPk = new FeFpPK();
				feFpPk.setIdFicheEmploi(feSecondaire.getIdFicheEmploi());
				feFpPk.setIdFichePoste(fichePDupliquee.getIdFichePoste());

				FeFp feFp = new FeFp();
				feFp.setId(feFpPk);
				feFp.setFePrimaire(0);
				feFp.setFichePoste(fichePDupliquee);
				feFp.setFicheEmploi(feSecondaire);

				fichePDupliquee.getFicheEmploi().add(feFp);
			}
		}

		ArrayList<NiveauEtudeFP> niveauFPExistant = (ArrayList<NiveauEtudeFP>) fichePosteDao.listerNiveauEtudeFPAvecFP(fichePoste.getIdFichePoste());
		if (niveauFPExistant.size() > 0) {
			NiveauEtude niveau = fichePosteDao.chercherNiveauEtude(niveauFPExistant.get(0).getNiveauEtudeFPPK().getIdNiveauEtude());
			if (niveau != null)
				fichePDupliquee.setNiveauEtude(niveau);
		}

		ArrayList<ActiviteFP> activiteFPExistant = (ArrayList<ActiviteFP>) fichePosteDao.listerActiviteFPAvecFP(fichePoste.getIdFichePoste());
		for (ActiviteFP lien : activiteFPExistant) {
			ActiviteFPPK actiFpPk = new ActiviteFPPK();
			actiFpPk.setIdActivite(lien.getActiviteFPPK().getIdActivite());
			actiFpPk.setIdFichePoste(fichePDupliquee.getIdFichePoste());

			ActiviteFP actiFp = new ActiviteFP();
			actiFp.setActiviteFPPK(actiFpPk);
			actiFp.setActivitePrincipale(1);
			actiFp.setFichePoste(fichePDupliquee);

			fichePDupliquee.getActivites().add(actiFp);

		}

		ArrayList<CompetenceFP> competencesFPExistant = (ArrayList<CompetenceFP>) fichePosteDao.listerCompetenceFPAvecFP(fichePoste.getIdFichePoste());
		for (CompetenceFP lien : competencesFPExistant) {
			CompetenceFPPK compFpPk = new CompetenceFPPK();
			compFpPk.setIdCompetence(lien.getCompetenceFPPK().getIdCompetence());
			compFpPk.setIdFichePoste(fichePDupliquee.getIdFichePoste());

			CompetenceFP compFp = new CompetenceFP();
			compFp.setCompetenceFPPK(compFpPk);
			compFp.setFichePoste(fichePDupliquee);

			fichePDupliquee.getCompetencesFDP().add(compFp);
		}

		ArrayList<AvantageNatureFP> avantagesFPExistant = (ArrayList<AvantageNatureFP>) fichePosteDao.listerAvantageNatureFPAvecFP(fichePoste.getIdFichePoste());
		for (AvantageNatureFP lien : avantagesFPExistant) {
			AvantageNature avNat = fichePosteDao.chercherAvantageNature(lien.getAvantageNaturePK().getIdAvantage());
			if (avNat != null)
				fichePDupliquee.getAvantagesNature().add(avNat);
		}

		ArrayList<DelegationFP> delegationFPExistant = (ArrayList<DelegationFP>) fichePosteDao.listerDelegationFPAvecFP(fichePoste.getIdFichePoste());
		for (DelegationFP lien : delegationFPExistant) {
			Delegation del = fichePosteDao.chercherDelegation(lien.getDelegationFPPK().getIdDelegation());
			if (del != null)
				fichePDupliquee.getDelegations().add(del);
		}

		ArrayList<PrimePointageFP> primesPointagesFPExistant = (ArrayList<PrimePointageFP>) fichePosteDao.listerPrimePointageFP(fichePoste.getIdFichePoste());
		for (PrimePointageFP lien : primesPointagesFPExistant) {
			PrimePointageFPPK fk = new PrimePointageFPPK();
			fk.setNumRubrique(lien.getPrimePointageFPPK().getNumRubrique());
			PrimePointageFP prime = new PrimePointageFP();
			prime.setFichePoste(fichePDupliquee);
			prime.setPrimePointageFPPK(fk);
			fichePosteDao.persisEntity(prime);
		}

		ArrayList<RegIndemFP> regimesFPExistant = (ArrayList<RegIndemFP>) fichePosteDao.listerRegIndemFPFPAvecFP(fichePoste.getIdFichePoste());
		for (RegIndemFP lien : regimesFPExistant) {
			RegimeIndemnitaire reg = fichePosteDao.chercherRegimeIndemnitaire(lien.getRegIndemFPPK().getIdRegime());
			if (reg != null)
				fichePDupliquee.getRegimesIndemnitaires().add(reg);
		}

		// on crée la FDP en base
		try {
			fichePosteDao.persisEntity(fichePDupliquee);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}

		// aussi dans SPPOST
		Sppost sppostDuplique = new Sppost(fichePDupliquee);
		fichePosteDao.persisEntity(sppostDuplique);
		// on historise
		HistoFichePoste histo = new HistoFichePoste(fichePDupliquee);
		histo.setDateHisto(new Date());
		histo.setUserHisto(login);
		histo.setTypeHisto(EnumTypeHisto.CREATION.getValue());
		fichePosteDao.persisEntity(histo);

		// probleme avec FE_FP.ID_FICHE_POSTE = NULL lors de l insert en BDD
		// pas trouve mieux que cette solution
		if (null != fichePDupliquee.getFicheEmploi()) {
			for (FeFp feFp : fichePDupliquee.getFicheEmploi()) {
				feFp.getId().setIdFichePoste(fichePDupliquee.getIdFichePoste());
			}
		}
		if (null != fichePDupliquee.getActivites()) {
			for (ActiviteFP actiFp : fichePDupliquee.getActivites()) {
				actiFp.getActiviteFPPK().setIdFichePoste(fichePDupliquee.getIdFichePoste());
			}
		}
		if (null != fichePDupliquee.getCompetencesFDP()) {
			for (CompetenceFP compFp : fichePDupliquee.getCompetencesFDP()) {
				compFp.getCompetenceFPPK().setIdFichePoste(fichePDupliquee.getIdFichePoste());
			}
		}

		try {
			fichePosteDao.flush();
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}

		return fichePDupliquee.getNumFP();
	}

	private FichePoste cloneFDP(FichePoste fichePoste) {
		FichePoste ficheClone = new FichePoste();

		// on remet à vide les listes
		ficheClone.setActivites(new HashSet<ActiviteFP>());
		ficheClone.setAgent(new HashSet<Affectation>());
		ficheClone.setNiveauEtude(null);
		ficheClone.setPrimePointageFP(new HashSet<PrimePointageFP>());
		ficheClone.setRegimesIndemnitaires(new HashSet<RegimeIndemnitaire>());
		ficheClone.setCompetencesFDP(new HashSet<CompetenceFP>());
		ficheClone.setAvantagesNature(new HashSet<AvantageNature>());
		ficheClone.setDelegations(new HashSet<Delegation>());
		ficheClone.setFicheEmploiPrimaire(new HashSet<FicheEmploi>());
		ficheClone.setFicheEmploiSecondaire(new HashSet<FicheEmploi>());
		ficheClone.setSuperieurHierarchique(null);
		ficheClone.setRemplace(null);
		ficheClone.setObservation("");

		// on ne remplit pas les infos liées au service
		ficheClone.setDateDebAppliServ(null);
		ficheClone.setIdServi(null);
		ficheClone.setIdServiceADS(null);
		ficheClone.setNfa(null);
		ficheClone.setNumDeliberation(null);

		// on renseigne les autres infos
		ficheClone.setDateDebutValiditeFp(null);
		ficheClone.setDateFinValiditeFp(null);
		ficheClone.setIdFichePoste(null);
		ficheClone.setAnnee(null);
		ficheClone.setNumFP(null);
		ficheClone.setStatutFP(null);
		ficheClone.setBudget(fichePoste.getBudget());
		ficheClone.setBudgete(fichePoste.getBudgete());
		ficheClone.setGradePoste(fichePoste.getGradePoste());
		ficheClone.setIdBaseHoraireAbsence(fichePoste.getIdBaseHoraireAbsence());
		ficheClone.setIdBaseHorairePointage(fichePoste.getIdBaseHorairePointage());
		ficheClone.setLieuPoste(fichePoste.getLieuPoste());
		ficheClone.setMissions(fichePoste.getMissions());
		ficheClone.setNatureCredit(fichePoste.getNatureCredit());
		ficheClone.setOpi(fichePoste.getOpi());
		ficheClone.setReglementaire(fichePoste.getReglementaire());
		ficheClone.setTitrePoste(fichePoste.getTitrePoste());

		return ficheClone;
	}

	private String createFichePosteNumber(Integer annee) {
		// RG_PE_FP_C01
		FichePoste derniereFP = null;
		try {
			derniereFP = fichePosteDao.chercherDerniereFichePosteByYear(annee);
		} catch (Exception e) {

		}

		if (derniereFP != null && derniereFP.getIdFichePoste() != null) {
			return (annee + "/" + String.valueOf(Integer.parseInt(derniereFP.getNumFP().substring(5)) + 1));
		} else {
			return (annee + "/" + String.valueOf(1));
		}
	}

	@Override
	public ReturnMessageDto activeFichesPosteByIdEntite(Integer idEntite, Integer idAgent) {
		ReturnMessageDto result = new ReturnMessageDto();

		// on chacher toutes les FDP "en creation"
		List<FichePoste> listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntite), Arrays.asList(1));

		// on crée un job de lancement de suppression des FDP
		for (FichePoste fp : listeFDP) {
			ActionFdpJob job = new ActionFdpJob(fp.getIdFichePoste(), idAgent, "ACTIVATION", null);
			fichePosteDao.persisEntity(job);
		}
		result.getInfos().add(listeFDP.size() + " FDP vont être activées. Merci d'aller regarder le resultat de cette activation dans SIRH.");
		return result;
	}

	@Override
	@Transactional(value = "sirhTransactionManager")
	public ReturnMessageDto activeFichePosteByIdFichePoste(Integer idFichePoste, Integer idAgent) {
		ReturnMessageDto result = new ReturnMessageDto();

		FichePoste fichePoste = fichePosteDao.chercherFichePoste(idFichePoste);
		// on verifie que la FDP existe
		if (fichePoste == null) {
			result.getErrors().add("La FDP id " + idFichePoste + " n'existe pas.");
			return result;
		}
		// on verifie que la FDP est bien "en création"
		if (!fichePoste.getStatutFP().getIdStatutFp().toString().equals("1")) {
			result.getErrors().add("La FDP id " + idFichePoste + " n'est pas en statut 'en création'.");
			return result;
		}
		// le service ne doit pas etre vide
		if (fichePoste.getIdServiceADS() == null) {
			result.getErrors().add("Le champ service est obligatoire.");
			return result;
		}
		// on verifie que l'entite est bien "active"
		EntiteDto entite = adsService.getEntiteByIdEntiteOptimise(fichePoste.getIdServiceADS(), new ArrayList<EntiteDto>());
		if (!entite.getIdStatut().toString().equals(String.valueOf(StatutEntiteEnum.ACTIF.getIdRefStatutEntite()))) {
			result.getErrors().add("L'entite id " + fichePoste.getIdServiceADS() + " n'est pas en statut 'actif'.");
			return result;
		}
		// on cherche le login de l'agent qui fait l'action
		LightUserDto user = utilisateurSrv.getLoginByIdAgent(idAgent);
		if (user == null || user.getsAMAccountName() == null) {
			result.getErrors().add("L'agent qui tente de faire l'action n'a pas de login dans l'AD.");
			return result;
		}

		// on verifie les RG pour activer la FDP
		result = checkRGActivationFDP(fichePoste, result, entite);
		if (result.getErrors().size() > 0) {
			return result;
		}

		// on active la FDP
		try {
			activerFDP(fichePoste, entite, user.getsAMAccountName());
			result.getInfos().add("La FDP " + fichePoste.getNumFP() + " est activée.");
		} catch (Exception e) {
			result.getErrors().add("La FDP " + fichePoste.getNumFP() + " n'a pu être activée.");
		}

		return result;
	}

	private ReturnMessageDto checkRGActivationFDP(FichePoste fichePoste, ReturnMessageDto result, EntiteDto entite) {
		// l'année ne doit pas etre vide
		if (fichePoste.getAnnee() == null) {
			result.getErrors().add("Le champ année est obligatoire.");
			return result;
		}
		// le garde ne doit pas etre vide
		if (fichePoste.getGradePoste() == null) {
			result.getErrors().add("Le champ grade est obligatoire.");
			return result;
		}
		// le titre ne doit pas etre vide
		if (fichePoste.getTitrePoste() == null) {
			result.getErrors().add("Le champ titre du poste est obligatoire.");
			return result;
		}
		// le budget ne doit pas etre vide
		if (fichePoste.getBudget() == null) {
			result.getErrors().add("Le champ budget est obligatoire.");
			return result;
		}
		// le lieu ne doit pas etre vide
		if (fichePoste.getLieuPoste() == null) {
			result.getErrors().add("Le champ lieu est obligatoire.");
			return result;
		}
		// le niveau d'etude ne doit pas etre vide
		if (fichePoste.getNiveauEtude() == null) {
			result.getErrors().add("Le champ niveau d'étude est obligatoire.");
			return result;
		}

		// le service doit avoir un numero et une date de delib
		if (entite.getRefDeliberationActif() == null || entite.getDateDeliberationActif() == null) {
			result.getErrors().add("Le service associé n'a pas de delibération.");
			return result;
		}

		// le NFA ne doit pas etre vide
		if (fichePoste.getNfa() == null) {
			// si vide alors on regarde si on trouve la NFA dans la table de
			// paramétrage
			NFA nfaEntite = fichePosteDao.chercherNFA(fichePoste.getIdServiceADS());
			if (nfaEntite == null || nfaEntite.getNfa() == null) {
				result.getErrors().add("Le champ NFA est obligatoire.");
				return result;
			}
		}
		// le responsable hierarchique ne doit pas etre vide
		if (fichePoste.getSuperieurHierarchique() == null) {
			result.getErrors().add("Le champ supérieur hiérarchique est obligatoire.");
			return result;
		}
		// la mission ne doit pas etre vide
		if (fichePoste.getMissions() == null) {
			result.getErrors().add("Le champ mission est obligatoire.");
			return result;
		}

		// il doit y avoir au moins 1 activité
		if (fichePoste.getActivites().size() == 0) {
			result.getErrors().add("Il doit au moins y avoir 1 activité.");
			return result;
		}

		// la base pointage ne doit pas etre vide
		if (fichePoste.getIdBaseHorairePointage() == null) {
			result.getErrors().add("Le champ base horaire pointage est obligatoire.");
			return result;
		}

		// la base absence ne doit pas etre vide
		if (fichePoste.getIdBaseHoraireAbsence() == null) {
			result.getErrors().add("Le champ base horaire absence est obligatoire.");
			return result;
		}
		// il faut verifier le hierachique et le remplacé ne sont pas la FDP
		// elle
		// meme
		if (fichePoste.getSuperieurHierarchique() != null && fichePoste.getSuperieurHierarchique().getIdFichePoste().toString().equals(fichePoste.getIdFichePoste().toString())) {
			result.getErrors().add("Une FDP ne peut être supérieur hiérarchique d'elle-même.");
			return result;
		}
		if (fichePoste.getRemplace() != null && fichePoste.getRemplace().getIdFichePoste().toString().equals(fichePoste.getIdFichePoste().toString())) {
			result.getErrors().add("Une FDP ne peut être en remplacement d'elle-même.");
			return result;
		}

		// reglementaire ne doit pas etre vide
		if (fichePoste.getReglementaire() == null) {
			result.getErrors().add("Le champ reglementaire est obligatoire.");
			return result;
		}

		// budgete ne doit pas etre vide
		if (fichePoste.getBudgete() == null) {
			result.getErrors().add("Le champ budgeté est obligatoire.");
			return result;
		}

		// nature des credits ne doit pas etre vide
		if (fichePoste.getNatureCredit() == null) {
			result.getErrors().add("Le champ nature des crédits est obligatoire.");
			return result;
		}

		// on check les RG sur reglementaire etc...
		NatureCredit natureCredit = fichePoste.getNatureCredit();
		Spbhor budgete = fichePoste.getBudgete();
		Spbhor reglementaire = fichePoste.getReglementaire();
		// si nature credit = NON alors budgete doit etre egal a 0
		if (natureCredit.getLibNatureCredit().trim().toUpperCase().equals("NON") && !budgete.getLibHor().trim().toLowerCase().equals("non")) {
			// "ERR1111",
			// "Si la nature des crédits est @, alors budgété doit être @."
			result.getErrors().add("Si la nature des crédits est 'NON', alors budgété doit être 'Non'.");
			return result;
		}
		// si nature credit = PERMANENT ou REMPLACEMENT ou TEMPORAIRE ou
		// SURNUMERAIRE alors budgete >0 et <=100
		if (natureCredit.getLibNatureCredit().toUpperCase().equals("PERMANENT") || natureCredit.getLibNatureCredit().toUpperCase().equals("REMPLACEMENT")
				|| natureCredit.getLibNatureCredit().toUpperCase().equals("TEMPORAIRE") || natureCredit.getLibNatureCredit().toUpperCase().equals("SURNUMERAIRE")) {
			if (budgete.getLibHor().trim().toLowerCase().equals("non")) {
				// "ERR1112",
				// "Si la nature des crédits est @, alors budgété ne doit pas être @."
				result.getErrors().add("Si la nature des crédits est 'PERMANENT', alors budgété doit être 'Non'.");
				return result;
			}
		}

		// si nature credit = REMPLACEMENT, alors fiche poste remplacee doit
		// etre renseigné et insersement
		if (natureCredit.getLibNatureCredit().toUpperCase().equals("REMPLACEMENT") && fichePoste.getRemplace() == null) {
			// "ERR1113",
			// "Budget de remplacement : fiche de poste remplacee necessaire.");
			result.getErrors().add("Budget de remplacement : fiche de poste remplacee necessaire.");
			return result;

		}
		if (fichePoste.getRemplace() != null) {
			if (!natureCredit.getLibNatureCredit().toUpperCase().equals("REMPLACEMENT")) {
				// "ERR1114",
				// "Fiche de poste remplacee mais budget different de remplacement."
				result.getErrors().add("Fiche de poste remplacee mais budget different de remplacement.");
				return result;
			}
		}

		// si relementaire > 0 alors budget doit etre different de permanent
		// et inversement
		if (natureCredit.getLibNatureCredit().toUpperCase().equals("PERMANENT") && reglementaire.getLibHor().trim().toLowerCase().equals("non")) {
			// "ERR1115",
			// "Le poste n'est pas reglementaire, le budget ne peut pas être permanent."
			result.getErrors().add("Le poste n'est pas reglementaire, le budget ne peut pas être permanent.");
			return result;

		}
		if (!reglementaire.getLibHor().trim().toLowerCase().equals("non")) {
			if (!natureCredit.getLibNatureCredit().equals("PERMANENT")) {
				// "ERR1116",
				// "Le poste est reglementaire, le budget doit être permanent."
				result.getErrors().add("Le poste est reglementaire, le budget doit être permanent.");
				return result;
			}
		}

		return result;
	}

	private void activerFDP(FichePoste fichePoste, EntiteDto entite, String login) {
		// on met à jour les champs de la deliberation
		fichePoste.setNumDeliberation(entite.getRefDeliberationActif());
		fichePoste.setDateDebAppliServ(entite.getDateDeliberationActif());
		// on met à jour la NFA
		if (fichePoste.getNfa() == null) {
			NFA nfaEntite = fichePosteDao.chercherNFA(fichePoste.getIdServiceADS());
			fichePoste.setNfa(nfaEntite.getNfa());
		}
		// on met à jour le statut en "validée"
		StatutFichePoste statutFP = fichePosteDao.chercherStatutFPByIdStatut(2);
		fichePoste.setStatutFP(statutFP);

		// on historise
		HistoFichePoste histo = new HistoFichePoste(fichePoste);
		// on sauvegarde
		fichePosteDao.persisEntity(fichePoste);
		// historisation
		histo.setDateHisto(new Date());
		histo.setUserHisto(login);
		histo.setTypeHisto(EnumTypeHisto.MODIFICATION.getValue());
		fichePosteDao.persisEntity(histo);

	}

	@Override
	@Transactional(value = "sirhTransactionManager")
	public ReturnMessageDto deplaceFichePosteFromEntityToOtherEntity(Integer idEntiteSource, Integer idEntiteCible, Integer idAgent) {
		ReturnMessageDto result = new ReturnMessageDto();

		// on cherche le login de l'agent qui fait l'action
		LightUserDto user = utilisateurSrv.getLoginByIdAgent(idAgent);
		if (user == null || user.getsAMAccountName() == null) {
			result.getErrors().add("L'agent qui tente de faire l'action n'a pas de login dans l'AD.");
			return result;
		}

		// on cherche toutes les FDP validées
		List<FichePoste> listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntiteSource),
				Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.GELEE.getStatut(), EnumStatutFichePoste.TRANSITOIRE.getStatut()));

		if (null == listeFDP || listeFDP.isEmpty()) {
			result.getInfos().add("Aucune FDP sont déplacées de l'entité " + idEntiteSource + " vers l'entité " + idEntiteCible + ".");
			return result;
		}

		// on crée un job de lancement de duplication des FDP
		for (FichePoste fp : listeFDP) {
			fp.setIdServiceADS(idEntiteCible);

			HistoFichePoste histo = new HistoFichePoste(fp);
			histo.setDateHisto(new Date());
			histo.setUserHisto(user.getsAMAccountName());
			histo.setTypeHisto(EnumTypeHisto.MODIFICATION.getValue());
			fichePosteDao.persisEntity(histo);
		}
		result.getInfos().add(listeFDP.size() + " FDP sont déplacées de l'entité " + idEntiteSource + " vers l'entité " + idEntiteCible + ".");
		return result;
	}
}
