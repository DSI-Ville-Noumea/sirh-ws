package nc.noumea.mairie.service.sirh;

import java.text.SimpleDateFormat;
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

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spmtsr;
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

@Service
public class FichePosteService implements IFichePosteService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager								sirhEntityManager;

	@Autowired
	private IFichePosteRepository						fichePosteDao;

	@Autowired
	private IUtilisateurService							utilisateurSrv;

	@Autowired
	private ISirhPtgWSConsumer							sirhPtgWSConsumer;

	@Autowired
	private IAgentService								agentSrv;

	@Autowired
	private IAffectationService							affSrv;

	@Autowired
	private IMairieRepository							mairieRepository;

	@Autowired
	private IADSWSConsumer								adsWSConsumer;

	@Autowired
	private IAdsService									adsService;

	private Logger										logger	= LoggerFactory.getLogger(FichePosteService.class);
	protected Hashtable<Integer, FichePosteTreeNode>	hFpTree;
	protected TreeMap<Integer, FichePosteTreeNode>		hFpTreeWithFPAffecteesEtNonAffectees;

	@Override
	public FichePoste getFichePostePrimaireAgentAffectationEnCours(Integer idAgent, Date dateJour, boolean withCompetenceAndActivities) {

		String requete = "select fp from FichePoste fp ";
		if (withCompetenceAndActivities) {
			requete += "LEFT JOIN FETCH fp.competencesFDP LEFT JOIN FETCH fp.activites";
		}
		requete += ", Affectation aff " + "where aff.fichePoste.idFichePoste = fp.idFichePoste and "
				+ "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and " + "(aff.dateFinAff is null or aff.dateFinAff>=:dateJour)";
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
		TypedQuery<FichePoste> query = sirhEntityManager
				.createQuery("select fp from FichePoste fp JOIN FETCH fp.competencesFDP JOIN FETCH fp.activites, Affectation aff "
						+ "where aff.fichePosteSecondaire.idFichePoste = fp.idFichePoste and "
						+ "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
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
		TypedQuery<String> query = sirhEntityManager.createQuery("select fp.titrePoste.libTitrePoste from FichePoste fp, Affectation aff "
				+ "where aff.fichePoste.idFichePoste = fp.idFichePoste and " + "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
				+ "(aff.dateFinAff is null or aff.dateFinAff>=:dateJour)", String.class);
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
			if (!agents.contains(fichePosteTreeNode.getFichePosteParent().getIdAgent()))
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
				Arrays.asList(idEntite), Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.EN_CREATION.getStatut(),
						EnumStatutFichePoste.GELEE.getStatut(), EnumStatutFichePoste.TRANSITOIRE.getStatut()));

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
			FichePosteTreeNodeDto treeNode = constructFichePosteTreeNodeDto(getFichePosteTreeAffecteesEtNonAffectees().get(idFichePosteParent),
					listEntiteDtoForOptimize, withFichesPosteNonReglementaires, listFichesPosteByFichePosteParent);

			if (null != treeNode)
				result.add(treeNode);
		}

		return result;
	}

	protected List<Integer> rechercheFichesPosteParent(List<FichePoste> listFichesPoste) {

		Hashtable<Integer, FichePosteTreeNode> hTree = new Hashtable<Integer, FichePosteTreeNode>();

		for (FichePoste node : listFichesPoste) {
			hTree.put(node.getIdFichePoste(), new FichePosteTreeNode(node.getIdFichePoste(),
					null == node.getSuperieurHierarchique() ? null : node.getSuperieurHierarchique().getIdFichePoste(), null));
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

	private FichePosteTreeNodeDto constructFichePosteTreeNodeDto(FichePosteTreeNode root, List<EntiteDto> listEntiteDto,
			boolean withFichesPosteNonReglemente, List<FichePoste> listFichesPosteByFichePosteParent) {

		FichePosteTreeNodeDto dto = null;

		if (null != root) {
			FichePoste fichePoste = getFichePosteInListFichesPoste(listFichesPosteByFichePosteParent, root.getIdFichePoste());

			if (null != fichePoste && (withFichesPosteNonReglemente || null == fichePoste.getReglementaire()
					|| !fichePoste.getReglementaire().getCdThor().equals(0))) {
				EntiteDto entite = adsService.getEntiteByIdEntiteOptimise(fichePoste.getIdServiceADS(), listEntiteDto);
				dto = new FichePosteTreeNodeDto(root.getIdFichePoste(), null, root.getIdAgent(), fichePoste, entite == null ? "" : entite.getSigle(),
						entite == null ? "" : entite.getLabel());

				if (null != root.getFichePostesEnfant()) {
					for (FichePosteTreeNode enfant : root.getFichePostesEnfant()) {
						FichePosteTreeNodeDto dtoEnfant = constructFichePosteTreeNodeDto(enfant, listEntiteDto, withFichesPosteNonReglemente,
								listFichesPosteByFichePosteParent);
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
		List<Integer> listeStatut = Arrays.asList(EnumStatutFichePoste.EN_CREATION.getId(), EnumStatutFichePoste.VALIDEE.getId(),
				EnumStatutFichePoste.GELEE.getId(), EnumStatutFichePoste.TRANSITOIRE.getId());
		TreeMap<Integer, FichePosteTreeNode> hTreeTemp = fichePosteDao.getAllFichePoste(new Date(),listeStatut);

		if (null != hFpTreeWithFPAffecteesEtNonAffectees && hTreeTemp.size() == hFpTreeWithFPAffecteesEtNonAffectees.size()
				&& hTreeTemp.lastKey() == hFpTreeWithFPAffecteesEtNonAffectees.lastKey())
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

		logger.debug("Debut construitArbre Fiche Poste Affectees Et Non Affectees");

		List<Integer> listeStatut = Arrays.asList(EnumStatutFichePoste.EN_CREATION.getId(), EnumStatutFichePoste.VALIDEE.getId(),
				EnumStatutFichePoste.GELEE.getId(), EnumStatutFichePoste.TRANSITOIRE.getId());
		TreeMap<Integer, FichePosteTreeNode> hTree = fichePosteDao.getAllFichePoste(new Date(), listeStatut);

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
	 * @return Hashtable l'arbre des fiches de postes
	 */
	private Hashtable<Integer, FichePosteTreeNode> construitArbre() {

		logger.debug("Debut construitArbre Fiche Poste");

		List<Integer> listeStatut = Arrays.asList(EnumStatutFichePoste.VALIDEE.getId(), EnumStatutFichePoste.TRANSITOIRE.getId());
		Hashtable<Integer, FichePosteTreeNode> hTree = fichePosteDao.getAllFichePosteAndAffectedAgents(new Date(), listeStatut);

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
	public List<FichePosteDto> getListFichePosteByIdServiceADSAndStatutFDP(Integer idEntite, List<Integer> listStatutFDP,
			boolean withEntiteChildren) {
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
			FichePosteDto dto = new FichePosteDto(fp, adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entiteRoot, fp.getIdServiceADS()),
					adsService.getLibelleEntityInEntiteDtoTreeByIdEntite(entiteRoot, fp.getIdServiceADS()));
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
	public ReturnMessageDto deleteFichePosteByIdEntite(Integer idEntite, Integer idAgent, String sigleEntite) {
		ReturnMessageDto result = new ReturnMessageDto();

		List<FichePoste> listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntite), null);
		// on regarde que toutes les FDP soient en statut "En creation" et que
		// la FDP n'est jamais été affectée à un agent
		for (FichePoste fp : listeFDP) {
			if (!fp.getStatutFP().getIdStatutFp().toString().equals(EnumStatutFichePoste.EN_CREATION.getId().toString())) {
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

		if (listeFDP.size() == 0) {
			result.getInfos().add("Aucune FDP ne va être supprimée sur l'entité " + sigleEntite + ".");
		} else if (listeFDP.size() == 1) {
			result.getInfos().add(
					"1 FDP va être supprimée sur l'entité " + sigleEntite + ". Merci d'aller regarder le resultat de cette suppression dans SIRH.");
		} else {
			result.getInfos().add(listeFDP.size() + " FDP vont être supprimées sur l'entité " + sigleEntite
					+ ". Merci d'aller regarder le resultat de cette suppression dans SIRH.");
		}
		return result;
	}

	@Override
	public InfoEntiteDto getInfoFDP(Integer idEntite, boolean withEntiteChildren, Date date) {

		InfoEntiteDto result = new InfoEntiteDto();

		if (withEntiteChildren) {
			EntiteDto entiteParent = adsWSConsumer.getEntiteWithChildrenByIdEntite(idEntite);
			List<Integer> listeEnfant = getListIdsEntiteEnfants(entiteParent);

			if (!listeEnfant.contains(entiteParent.getIdEntite()))
				listeEnfant.add(entiteParent.getIdEntite());
			result.setIdEntite(idEntite);
			List<GroupeInfoFichePosteDto> resFDP = fichePosteDao.getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(listeEnfant);
			result.getListeInfoFDP().addAll(resFDP);
			getListeNumFPByIdServiceADSAndTitrePoste(result, listeEnfant, date);

		} else {
			result.setIdEntite(idEntite);
			List<Integer> listeEnfant = new ArrayList<Integer>();
			listeEnfant.add(idEntite);
			List<GroupeInfoFichePosteDto> resFDP = fichePosteDao.getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(listeEnfant);
			result.getListeInfoFDP().addAll(resFDP);
			getListeNumFPByIdServiceADSAndTitrePoste(result, listeEnfant, date);
		}

		return result;

	}

	private void getListeNumFPByIdServiceADSAndTitrePoste(InfoEntiteDto result, List<Integer> listeIdServiceAds, Date date) {

		if (null != result.getListeInfoFDP()) {
			for (GroupeInfoFichePosteDto resFDP : result.getListeInfoFDP()) {

				List<InfoFichePosteDto> listInfoFichePosteDto = fichePosteDao.getListInfoFichePosteDtoByIdServiceADSAndTitrePoste(listeIdServiceAds,
						resFDP.getTitreFDP(), date);

				if (null != listInfoFichePosteDto)
					resFDP.setListInfoFichePosteDto(listInfoFichePosteDto);
			}
		}
	}

	@Override
	public ReturnMessageDto dupliqueFichePosteByIdEntite(Integer idEntiteNew, Integer idEntiteOld, Integer idAgent) {
		ReturnMessageDto result = new ReturnMessageDto();
		// on cherche toutes les FDP validées
		// #18431 : on ajoute de nouveaux statut
		List<Integer> listStatut = new ArrayList<Integer>();
		listStatut.add(EnumStatutFichePoste.VALIDEE.getId());
		listStatut.add(EnumStatutFichePoste.EN_CREATION.getId());
		listStatut.add(EnumStatutFichePoste.TRANSITOIRE.getId());
		listStatut.add(EnumStatutFichePoste.GELEE.getId());
		List<FichePoste> listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntiteOld), listStatut);

		// on crée un job de lancement de duplication des FDP
		for (FichePoste fp : listeFDP) {
			ActionFdpJob job = new ActionFdpJob(fp.getIdFichePoste(), idAgent, "DUPLICATION", idEntiteNew);
			fichePosteDao.persisEntity(job);
		}
		EntiteDto entite = adsWSConsumer.getEntiteByIdEntite(idEntiteNew);
		if (listeFDP.size() == 0) {
			result.getInfos().add("Aucune FDP ne va être dupliquée sur l'entité " + entite.getSigle() + ".");
		} else if (listeFDP.size() == 1) {
			result.getInfos().add("1 FDP va être dupliquée sur l'entité " + entite.getSigle()
					+ ". Merci d'aller regarder le resultat de cette duplication dans SIRH.");
		} else {
			result.getInfos().add(listeFDP.size() + " FDP vont être dupliquées sur l'entité " + entite.getSigle()
					+ ". Merci d'aller regarder le resultat de cette duplication dans SIRH.");
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
		if (!fichePoste.getStatutFP().getIdStatutFp().toString().equals(EnumStatutFichePoste.EN_CREATION.getId().toString())) {
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
			logger.error("Erreur deleteFichePosteByIdFichePoste : " + e.getMessage());
			String erreur = "La FDP " + fichePoste.getNumFP() + " n'a pu être suprimée : " + e.getMessage();
			if (erreur.length() > 255) {
				erreur = erreur.substring(0, 255);
			}
			result.getErrors().add(erreur);
		}

		return result;
	}

	private void supprimerFDP(FichePoste fichePoste, String login) {
		// supprimer la FDP en base (dans HISTO on sauvegarde l'action) et
		// SPPOST
		HistoFichePoste histo = new HistoFichePoste(fichePoste);

		// historisation
		histo.setDateHisto(new Date());
		histo.setUserHisto(login);
		histo.setTypeHisto(EnumTypeHisto.SUPPRESSION.getValue());
		fichePosteDao.persisEntity(histo);
		fichePosteDao.flush();

		// aussi de SPPOST
		Sppost posteAS400 = fichePosteDao.chercherSppost(new Integer(histo.getNumFp().substring(0, 4)),
				new Integer(fichePoste.getNumFP().substring(5, histo.getNumFp().length())));
		if (posteAS400 != null) {
			fichePosteDao.removeEntity(posteAS400);
		}

		fichePoste = fichePosteDao.chercherFichePoste(fichePoste.getIdFichePoste());
		// on supprime enfin la FDP
		fichePosteDao.removeEntity(fichePoste);
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
		if (!(fichePoste.getStatutFP().getIdStatutFp().toString().equals(EnumStatutFichePoste.VALIDEE.getId().toString())
				|| fichePoste.getStatutFP().getIdStatutFp().toString().equals(EnumStatutFichePoste.EN_CREATION.getId().toString())
				|| fichePoste.getStatutFP().getIdStatutFp().toString().equals(EnumStatutFichePoste.TRANSITOIRE.getId().toString())
				|| fichePoste.getStatutFP().getIdStatutFp().toString().equals(EnumStatutFichePoste.GELEE.getId().toString()))) {
			result.getErrors().add("La FDP " + fichePoste.getNumFP() + " n'est pas en statut 'validée','en création','transitoire' ou 'gelée'.");
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
			FichePoste newFDP = dupliquerFDP(result, fichePoste, entite, user.getsAMAccountName());
			result.getInfos().add("La FDP " + fichePoste.getNumFP() + " est dupliquée en " + newFDP.getNumFP() + ".");
			result.setId(newFDP.getIdFichePoste());
		} catch (Exception e) {
			logger.error("Erreur dupliqueFichePosteByIdFichePoste : " + e.getMessage());
			String erreur = "La FDP " + fichePoste.getNumFP() + " n'a pu être dupliquée : " + e.getMessage();
			if (erreur.length() > 255) {
				erreur = erreur.substring(0, 255);
			}
			result.getErrors().add(erreur);
		}

		return result;
	}

	private FichePoste dupliquerFDP(ReturnMessageDto result, FichePoste fichePoste, EntiteDto entite, String login)
			throws CloneNotSupportedException {

		FichePoste fichePDupliquee = cloneFDP(fichePoste);
		fichePDupliquee.setIdFichePoste(null);
		StatutFichePoste statutCreation = fichePosteDao.chercherStatutFPByIdStatut(EnumStatutFichePoste.EN_CREATION.getId());
		fichePDupliquee.setStatutFP(statutCreation);
		fichePDupliquee.setNumFP(null);
		fichePDupliquee.setRemplace(null);
		fichePDupliquee.setAgent(null);
		// #18614 : on recherche le supérieur hiérarchique
		fichePDupliquee.setSuperieurHierarchique(null);
		if (fichePoste.getSuperieurHierarchique() != null && fichePoste.getSuperieurHierarchique().getIdFichePoste() != null) {
			// on cherche dans action fdp si on a une entrée avec l'id de la FDP
			// correspondant
			ActionFdpJob actionParent = fichePosteDao.chercherActionFDPParentDuplication(fichePoste.getSuperieurHierarchique().getIdFichePoste());
			if (actionParent != null && actionParent.getNewIdFichePoste() != null) {
				FichePoste newFDPSuperieur = fichePosteDao.chercherFichePoste(actionParent.getNewIdFichePoste());
				if (newFDPSuperieur != null && newFDPSuperieur.getIdFichePoste() != null) {
					fichePDupliquee.setSuperieurHierarchique(newFDPSuperieur);
				}
			}
		}

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
		fichePDupliquee.setNfa(entite.getNfa() == null ? "0" : entite.getNfa());
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

		ArrayList<CompetenceFP> competencesFPExistant = (ArrayList<CompetenceFP>) fichePosteDao
				.listerCompetenceFPAvecFP(fichePoste.getIdFichePoste());
		for (CompetenceFP lien : competencesFPExistant) {
			CompetenceFPPK compFpPk = new CompetenceFPPK();
			compFpPk.setIdCompetence(lien.getCompetenceFPPK().getIdCompetence());
			compFpPk.setIdFichePoste(fichePDupliquee.getIdFichePoste());

			CompetenceFP compFp = new CompetenceFP();
			compFp.setCompetenceFPPK(compFpPk);
			compFp.setFichePoste(fichePDupliquee);

			fichePDupliquee.getCompetencesFDP().add(compFp);
		}

		ArrayList<AvantageNatureFP> avantagesFPExistant = (ArrayList<AvantageNatureFP>) fichePosteDao
				.listerAvantageNatureFPAvecFP(fichePoste.getIdFichePoste());
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

		ArrayList<PrimePointageFP> primesPointagesFPExistant = (ArrayList<PrimePointageFP>) fichePosteDao
				.listerPrimePointageFP(fichePoste.getIdFichePoste());
		for (PrimePointageFP lien : primesPointagesFPExistant) {
			PrimePointageFPPK fk = new PrimePointageFPPK();
			fk.setNumRubrique(lien.getPrimePointageFPPK().getNumRubrique());
			fk.setIdFichePoste(fichePDupliquee.getIdFichePoste());
			PrimePointageFP prime = new PrimePointageFP();
			prime.setFichePoste(fichePDupliquee);
			prime.setPrimePointageFPPK(fk);
			fichePDupliquee.getPrimePointageFP().add(prime);
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
		if (null != fichePDupliquee.getPrimePointageFP()) {
			for (PrimePointageFP primeFp : fichePDupliquee.getPrimePointageFP()) {
				primeFp.getPrimePointageFPPK().setIdFichePoste(fichePDupliquee.getIdFichePoste());
			}
		}

		try {
			fichePosteDao.flush();
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}

		if (fichePDupliquee.getSuperieurHierarchique() == null || fichePDupliquee.getSuperieurHierarchique().getIdFichePoste() == null) {
			result.getInfos().add("Attention, la FDP " + fichePDupliquee.getNumFP() + " n'a pas de supérieur hiérarchique.");
		}

		return fichePDupliquee;
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
		String dernierNumFP = null;
		try {
			dernierNumFP = fichePosteDao.chercherDernierNumFichePosteByYear(annee);
		} catch (Exception e) {

		}

		if (dernierNumFP != null) {
			return (annee + "/" + String.valueOf(Integer.parseInt(dernierNumFP.substring(5)) + 1));
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

		EntiteDto entite = adsWSConsumer.getEntiteByIdEntite(idEntite);
		if (listeFDP.size() == 0) {
			result.getInfos().add("Aucune FDP ne va être activée sur l'entité " + entite.getSigle() + ".");
		} else if (listeFDP.size() == 1) {
			result.getInfos().add("1 FDP va être activée sur l'entité " + entite.getSigle()
					+ ". Merci d'aller regarder le resultat de cette activation dans SIRH.");
		} else {
			result.getInfos().add(listeFDP.size() + " FDP vont être activées sur l'entité " + entite.getSigle()
					+ ". Merci d'aller regarder le resultat de cette activation dans SIRH.");
		}
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
		if (!fichePoste.getStatutFP().getIdStatutFp().toString().equals(EnumStatutFichePoste.EN_CREATION.getId().toString())) {
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
		// #31427
		// on verifie que l entite a bien un CODE SERVI (AS400)
		if (null == entite.getCodeServi() || "".equals(entite.getCodeServi().trim())) {
			result.getErrors().add("L'entite id " + fichePoste.getIdServiceADS() + " n'a pas de CODE SERVI (AS400).");
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
			logger.error("Erreur activeFichePosteByIdFichePoste : " + e.getMessage());
			String erreur = "La FDP " + fichePoste.getNumFP() + " n'a pu être activée : " + e.getMessage();
			if (erreur.length() > 255) {
				erreur = erreur.substring(0, 255);
			}
			result.getErrors().add(erreur);
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
		if (fichePoste.getNfa() == null && entite.getNfa() == null) {
			result.getErrors().add("Le champ NFA est obligatoire.");
			return result;

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
		if (fichePoste.getSuperieurHierarchique() != null
				&& fichePoste.getSuperieurHierarchique().getIdFichePoste().toString().equals(fichePoste.getIdFichePoste().toString())) {
			result.getErrors().add("Une FDP ne peut être supérieur hiérarchique d'elle-même.");
			return result;
		}
		if (fichePoste.getRemplace() != null
				&& fichePoste.getRemplace().getIdFichePoste().toString().equals(fichePoste.getIdFichePoste().toString())) {
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
		if (natureCredit.getLibNatureCredit().toUpperCase().equals("PERMANENT")
				|| natureCredit.getLibNatureCredit().toUpperCase().equals("REMPLACEMENT")
				|| natureCredit.getLibNatureCredit().toUpperCase().equals("TEMPORAIRE")
				|| natureCredit.getLibNatureCredit().toUpperCase().equals("SURNUMERAIRE")) {
			if (budgete.getLibHor().trim().toLowerCase().equals("non")) {
				// "ERR1112",
				// "Si la nature des crédits est @, alors budgété ne doit pas
				// être @."
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
				// "Fiche de poste remplacee mais budget different de
				// remplacement."
				result.getErrors().add("Fiche de poste remplacee mais budget different de remplacement.");
				return result;
			}
		}

		// si relementaire > 0 alors budget doit etre different de permanent
		// et inversement
		if (natureCredit.getLibNatureCredit().toUpperCase().equals("PERMANENT") && reglementaire.getLibHor().trim().toLowerCase().equals("non")) {
			// "ERR1115",
			// "Le poste n'est pas reglementaire, le budget ne peut pas être
			// permanent."
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
			fichePoste.setNfa(entite.getNfa() == null ? "0" : entite.getNfa());
		}
		// bug #31427
		// on met a jour le code SERVI utile a la paie lors de l affectation d
		// un agent a une fiche de poste
		fichePoste.setIdServi(entite.getCodeServi());
		// on met à jour le statut en "validée"
		StatutFichePoste statutFP = fichePosteDao.chercherStatutFPByIdStatut(EnumStatutFichePoste.VALIDEE.getId());
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

		EntiteDto source = adsWSConsumer.getEntiteByIdEntite(idEntiteSource);
		if (source == null) {
			result.getErrors().add("L'entité " + idEntiteSource + " n'existe pas.");
			return result;
		}

		EntiteDto cible = adsWSConsumer.getEntiteByIdEntite(idEntiteCible);
		if (cible == null) {
			result.getErrors().add("L'entité " + idEntiteCible + " n'existe pas.");
			return result;
		}

		// on cherche le login de l'agent qui fait l'action
		LightUserDto user = utilisateurSrv.getLoginByIdAgent(idAgent);
		if (user == null || user.getsAMAccountName() == null) {
			result.getErrors().add("L'agent qui tente de faire l'action n'a pas de login dans l'AD.");
			return result;
		}

		// on cherche toutes les FDP validées
		List<FichePoste> listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntiteSource),
				Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.GELEE.getStatut(),
						EnumStatutFichePoste.TRANSITOIRE.getStatut(), EnumStatutFichePoste.EN_CREATION.getStatut()));

		if (null == listeFDP || listeFDP.isEmpty()) {
			result.getInfos().add("Aucune FDP ne sont déplacées de l'entité " + source.getSigle() + " vers l'entité " + cible.getSigle() + ".");
			return result;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (FichePoste fp : listeFDP) {
			fp.setIdServiceADS(idEntiteCible);
			// #18976 : on met à jour le vieux service AS400
			EntiteDto servAS400 = adsService.getInfoSiservByIdEntite(fp.getIdServiceADS());
			fp.setIdServi(servAS400 == null || servAS400.getCodeServi() == null ? null : servAS400.getCodeServi());

			// #18977 : on met à jour sppost et spmtsr
			Sppost sppost = fichePosteDao.chercherSppost(new Integer(fp.getNumFP().substring(0, 4)),
					new Integer(fp.getNumFP().substring(5, fp.getNumFP().length())));
			sppost.setPoserv(fp.getIdServi() == null ? "" : fp.getIdServi());

			List<Affectation> listeAffFP = affSrv.getListAffectationActiveByIdFichePoste(fp.getIdFichePoste());

			for (Affectation aff : listeAffFP) {
				Spmtsr spmtsr = mairieRepository.chercherSpmtsrAvecAgentEtDateDebut(aff.getAgent().getNomatr(),
						new Integer(sdf.format(aff.getDateDebutAff())));
				spmtsr.getId().setServi(fp.getIdServi());
				// remettre a jour des donnees erronees eventuellement
				sppost.setPomatr(aff.getAgent().getNomatr());
				// on fait un update manuel car sur l'ID cela ne fonctionne pas
				fichePosteDao.modifierSpmtsrWithId(spmtsr);
			}
			fichePosteDao.persisEntity(sppost);

			HistoFichePoste histo = new HistoFichePoste(fp);
			histo.setDateHisto(new Date());
			histo.setUserHisto(user.getsAMAccountName());
			histo.setTypeHisto(EnumTypeHisto.MODIFICATION.getValue());
			fichePosteDao.persisEntity(histo);
		}

		if (listeFDP.size() == 1) {
			result.getInfos()
					.add(listeFDP.size() + " FDP est déplacée de l'entité " + source.getSigle() + " vers l'entité " + cible.getSigle() + ".");
		} else {
			result.getInfos()
					.add(listeFDP.size() + " FDP sont déplacées de l'entité " + source.getSigle() + " vers l'entité " + cible.getSigle() + ".");
		}

		return result;
	}

	/**
	 * Rendre inactives toutes les fiches de poste a condition qu elles soient
	 * toutes non affectées (dans le futur egalement)
	 */
	@Override
	@Transactional(value = "sirhTransactionManager")
	public ReturnMessageDto inactiveFichePosteFromEntity(Integer idEntite, Integer idAgent) {
		ReturnMessageDto result = new ReturnMessageDto();

		EntiteDto entite = adsWSConsumer.getEntiteWithChildrenByIdEntite(idEntite);
		if (entite == null) {
			result.getErrors().add("L'entité " + idEntite + " n'existe pas.");
			return result;
		}

		// on cherche le login de l'agent qui fait l'action
		LightUserDto user = utilisateurSrv.getLoginByIdAgent(idAgent);
		if (user == null || user.getsAMAccountName() == null) {
			result.getErrors().add("L'agent qui tente de faire l'action n'a pas de login dans l'AD.");
			return result;
		}

		// on cherche toutes les FDP affectées
		result = checkFichesPosteAffecteesOnEntiteAndTheirsChildren(entite, result);

		if (null != result.getErrors() && !result.getErrors().isEmpty()) {
			return result;
		}

		// on check que toutes les fiches de poste sont inactives sur les
		// entités enfant
		checkFichesPosteValideGeleeTransitoireInEntiteChildren(entite, result);

		if (null != result.getErrors() && !result.getErrors().isEmpty()) {
			return result;
		}

		// on cherche toutes les FDP NON affectées
		List<Integer> listeIdFDPNonAffectées = fichePosteDao.getListFichePosteNonAffecteeEtPasInactiveByIdServiceADS(idEntite);

		if (null == listeIdFDPNonAffectées || listeIdFDPNonAffectées.isEmpty()) {
			result.getInfos().add("Aucune FDP non affectées sur l'entité " + entite.getSigle() + ".");
			return result;
		}

		for (Integer idfp : listeIdFDPNonAffectées) {
			FichePoste fp = fichePosteDao.chercherFichePoste(idfp);

			if (!EnumStatutFichePoste.INACTIVE.getId().equals(fp.getStatutFP().getIdStatutFp())) {
				StatutFichePoste statutInactif = fichePosteDao.chercherStatutFPByIdStatut(EnumStatutFichePoste.INACTIVE.getId());
				fp.setStatutFP(statutInactif);

				// #18977 : on met à jour sppost
				Sppost sppost = fichePosteDao.chercherSppost(new Integer(fp.getNumFP().substring(0, 4)),
						new Integer(fp.getNumFP().substring(5, fp.getNumFP().length())));
				// #37406 : des fois les FDP ne sont pas à jour dans SIRH.
				if (sppost == null || sppost.getId() == null || sppost.getId().getPoanne() == null) {
					result.getErrors().add("La FDP " + fp.getNumFP().substring(0, 4) + "/" + fp.getNumFP().substring(5, fp.getNumFP().length())
							+ " n'est pas à jour dans SIRH. Merci de faire la necessaire avant de recommencer l'opération.");
					return result;
				}
				sppost.setCodact("I");
				fichePosteDao.persisEntity(sppost);

				HistoFichePoste histo = new HistoFichePoste(fp);
				histo.setDateHisto(new Date());
				histo.setUserHisto(user.getsAMAccountName());
				histo.setTypeHisto(EnumTypeHisto.MODIFICATION.getValue());
				fichePosteDao.persisEntity(histo);
			}
		}

		if (listeIdFDPNonAffectées.size() == 1) {
			result.getInfos().add(listeIdFDPNonAffectées.size() + " FDP non affectée sur l'entité " + entite.getSigle() + " est passée en inactive.");
		} else {
			result.getInfos()
					.add(listeIdFDPNonAffectées.size() + " FDP non affectées sur l'entité " + entite.getSigle() + " sont passées en inactives.");
		}

		return result;
	}

	private ReturnMessageDto checkFichesPosteAffecteesOnEntiteAndTheirsChildren(EntiteDto entite, ReturnMessageDto result) {

		// on cherche toutes les FDP affectées
		List<Integer> listeIdFDPAffectees = fichePosteDao.getListFichePosteAffecteeInPresentAndFutureByIdServiceADS(entite.getIdEntite());

		if (null != listeIdFDPAffectees && !listeIdFDPAffectees.isEmpty()) {
			result.getErrors().add("Il reste des FDP affectées sur l'entité " + entite.getSigle() + ".");
			return result;
		}

		if (null != entite.getEnfants() && !entite.getEnfants().isEmpty()) {
			for (EntiteDto enfant : entite.getEnfants()) {
				result = checkFichesPosteAffecteesOnEntiteAndTheirsChildren(enfant, result);

				if (!result.getErrors().isEmpty()) {
					return result;
				}
			}
		}

		return result;
	}

	private ReturnMessageDto checkFichesPosteValideGeleeTransitoireInEntiteChildren(EntiteDto entite, ReturnMessageDto result) {

		if (null != entite.getEnfants() && !entite.getEnfants().isEmpty()) {
			for (EntiteDto enfant : entite.getEnfants()) {

				List<FichePoste> listFP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(enfant.getIdEntite()),
						Arrays.asList(EnumStatutFichePoste.EN_CREATION.getId(), EnumStatutFichePoste.GELEE.getId(),
								EnumStatutFichePoste.VALIDEE.getId(), EnumStatutFichePoste.TRANSITOIRE.getId()));

				if (null != listFP && !listFP.isEmpty()) {
					result.getErrors().add("Il y a des fiches de poste en statut En création, Validée, Gelée ou Transitoire sur l'entité enfant "
							+ enfant.getSigle());
					return result;
				}

				result = checkFichesPosteValideGeleeTransitoireInEntiteChildren(enfant, result);

				if (!result.getErrors().isEmpty()) {
					return result;
				}
			}
		}

		return result;
	}

	/**
	 * Rendre transitoire toutes les fiches de poste En Creation, Gelee ET
	 * Valide affectees ou non
	 */
	@Override
	public ReturnMessageDto transiteFichePosteFromEntity(Integer idEntite, Integer idAgent) {
		ReturnMessageDto result = new ReturnMessageDto();

		EntiteDto entite = adsWSConsumer.getEntiteByIdEntite(idEntite);
		if (entite == null) {
			result.getErrors().add("L'entité " + idEntite + " n'existe pas.");
			return result;
		}

		// on cherche le login de l'agent qui fait l'action
		LightUserDto user = utilisateurSrv.getLoginByIdAgent(idAgent);
		if (user == null || user.getsAMAccountName() == null) {
			result.getErrors().add("L'agent qui tente de faire l'action n'a pas de login dans l'AD.");
			return result;
		}

		// on cherche toutes les FDP affectées
		List<FichePoste> listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntite),
				Arrays.asList(EnumStatutFichePoste.EN_CREATION.getId(), EnumStatutFichePoste.GELEE.getId(), EnumStatutFichePoste.VALIDEE.getId()));

		if (null == listeFDP || listeFDP.isEmpty()) {
			result.getInfos().add("Aucune FDP en statut En Création, Validée ou Gelée sur l'entité " + entite.getSigle() + ".");
			return result;
		}

		for (FichePoste fp : listeFDP) {
			StatutFichePoste statutTransitoire = fichePosteDao.chercherStatutFPByIdStatut(EnumStatutFichePoste.TRANSITOIRE.getId());
			fp.setStatutFP(statutTransitoire);

			HistoFichePoste histo = new HistoFichePoste(fp);
			histo.setDateHisto(new Date());
			histo.setUserHisto(user.getsAMAccountName());
			histo.setTypeHisto(EnumTypeHisto.MODIFICATION.getValue());
			fichePosteDao.persisEntity(histo);
		}

		if (listeFDP.size() == 1) {
			result.getInfos().add(listeFDP.size() + " FDP affectée sur l'entité " + entite.getSigle() + " est passée en transitoire.");
		} else {
			result.getInfos().add(listeFDP.size() + " FDP affectées sur l'entité " + entite.getSigle() + " sont passées en transitoire.");
		}

		return result;
	}
}
