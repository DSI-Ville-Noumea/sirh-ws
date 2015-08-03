package nc.noumea.mairie.service.sirh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.sirh.ActionFdpJob;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.PrimePointageFP;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.model.repository.sirh.IFichePosteRepository;
import nc.noumea.mairie.service.ads.IAdsService;
import nc.noumea.mairie.tools.FichePosteTreeNode;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.FichePosteDto;
import nc.noumea.mairie.web.dto.InfoEntiteDto;
import nc.noumea.mairie.web.dto.InfoFichePosteDto;
import nc.noumea.mairie.web.dto.SpbhorDto;
import nc.noumea.mairie.ws.IADSWSConsumer;
import nc.noumea.mairie.ws.ISirhPtgWSConsumer;
import nc.noumea.mairie.ws.dto.RefPrimeDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

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
	private IFichePosteRepository fichePosteDao;

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
	private Hashtable<Integer, FichePosteTreeNode> hFpTree;

	@Override
	public FichePoste getFichePostePrimaireAgentAffectationEnCours(Integer idAgent, Date dateJour,
			boolean withCompetenceAndActivities) {

		String requete = "select fp from FichePoste fp ";
		if (withCompetenceAndActivities) {
			requete += "LEFT JOIN FETCH fp.competencesFDP LEFT JOIN FETCH fp.activites";
		}
		requete += ", Affectation aff " + "where aff.fichePoste.idFichePoste = fp.idFichePoste and "
				+ "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
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
		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(
				"select fp from FichePoste fp JOIN FETCH fp.competencesFDP JOIN FETCH fp.activites, Affectation aff "
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
		TypedQuery<String> query = sirhEntityManager.createQuery(
				"select fp.titrePoste.libTitrePoste from FichePoste fp, Affectation aff "
						+ "where aff.fichePoste.idFichePoste = fp.idFichePoste and "
						+ "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
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

	private List<Integer> listSubFichesPostes(FichePosteTreeNode fichePosteTreeNode, List<Integer> fichePostes,
			int maxDepth) {

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

	private List<Integer> listSubAgents(FichePosteTreeNode fichePosteTreeNode, List<Integer> agents, int maxDepth,
			String nom) {

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
		TypedQuery<FichePoste> query = sirhEntityManager.createQuery(
				"select fp from FichePoste fp where fp.idFichePoste=:idFichePoste", FichePoste.class);
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
	public List<FichePosteDto> getListFichePosteByIdServiceADSAndStatutFDP(Integer idEntite,
			List<Integer> listStatutFDP, boolean withEntiteChildren) {
		List<FichePosteDto> result = new ArrayList<FichePosteDto>();
		List<FichePoste> listeFDP = new ArrayList<FichePoste>();
		
		EntiteDto entiteRoot = null;
		
		if (withEntiteChildren) {
			entiteRoot = adsWSConsumer.getEntiteWithChildrenByIdEntite(idEntite);
			List<Integer> listeEnfant = getListIdsEntiteEnfants(entiteRoot);
			if (!listeEnfant.contains(entiteRoot.getIdEntite()))
				listeEnfant.add(entiteRoot.getIdEntite());
			
				listeFDP = fichePosteDao
						.getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(listeEnfant, listStatutFDP);
		} else {
			entiteRoot = adsWSConsumer.getEntiteByIdEntite(idEntite);
			listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntite), listStatutFDP);
		}

		for (FichePoste fp : listeFDP) {
			FichePosteDto dto = new FichePosteDto(fp, true, "", "", "", adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entiteRoot, fp.getIdServiceADS()));
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
			if (affSrv.getAffectationByIdFichePoste(fp.getIdFichePoste()).size() > 0) {
				result.getErrors().add("La FDP " + fp.getNumFP() + " a déjà été affectée.");
			}
		}
		if (result.getErrors().size() > 0) {
			return result;
		}

		// on crée un job de lancement de suppression des FDP
		for (FichePoste fp : listeFDP) {
			ActionFdpJob job = new ActionFdpJob(fp.getIdFichePoste(), idAgent, "SUPPRESSION");
			fichePosteDao.persisEntity(job);
		}
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
			List<InfoFichePosteDto> resFDP = fichePosteDao
					.getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(listeEnfant);
			result.getListeInfoFDP().addAll(resFDP);

		} else {
			result.setIdEntite(idEntite);
			List<Integer> listeEnfant = new ArrayList<Integer>();
			listeEnfant.add(idEntite);
			List<InfoFichePosteDto> resFDP = fichePosteDao
					.getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(listeEnfant);
			result.getListeInfoFDP().addAll(resFDP);
		}
		return result;

	}
}
