package nc.noumea.mairie.service.sirh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Sppost;
import nc.noumea.mairie.model.bean.ads.StatutEntiteEnum;
import nc.noumea.mairie.model.bean.sirh.ActionFdpJob;
import nc.noumea.mairie.model.bean.sirh.Activite;
import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.AvantageNature;
import nc.noumea.mairie.model.bean.sirh.AvantageNatureFP;
import nc.noumea.mairie.model.bean.sirh.Competence;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.Delegation;
import nc.noumea.mairie.model.bean.sirh.DelegationFP;
import nc.noumea.mairie.model.bean.sirh.EnumTypeHisto;
import nc.noumea.mairie.model.bean.sirh.FeFp;
import nc.noumea.mairie.model.bean.sirh.FicheEmploi;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.HistoFichePoste;
import nc.noumea.mairie.model.bean.sirh.NFA;
import nc.noumea.mairie.model.bean.sirh.NiveauEtude;
import nc.noumea.mairie.model.bean.sirh.NiveauEtudeFP;
import nc.noumea.mairie.model.bean.sirh.PrimePointageFP;
import nc.noumea.mairie.model.bean.sirh.RegIndemFP;
import nc.noumea.mairie.model.bean.sirh.RegimeIndemnitaire;
import nc.noumea.mairie.model.bean.sirh.StatutFichePoste;
import nc.noumea.mairie.model.pk.sirh.PrimePointageFPPK;
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

			listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(
					listeEnfant, listStatutFDP);
		} else {
			entiteRoot = adsWSConsumer.getEntiteByIdEntite(idEntite);
			listeFDP = fichePosteDao
					.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntite), listStatutFDP);
		}

		for (FichePoste fp : listeFDP) {
			FichePosteDto dto = new FichePosteDto(fp, adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entiteRoot,
					fp.getIdServiceADS()));
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

		List<FichePoste> listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntite),
				null);
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
			ActionFdpJob job = new ActionFdpJob(fp.getIdFichePoste(), idAgent, "SUPPRESSION", null);
			fichePosteDao.persisEntity(job);
		}
		result.getInfos()
				.add(listeFDP.size()
						+ " FDP vont être supprimées. Merci d'aller regarder le resultat de cette suppression dans SIRH.");
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

	@Override
	public ReturnMessageDto dupliqueFichePosteByIdEntite(Integer idEntiteNew, Integer idEntiteOld, Integer idAgent) {
		ReturnMessageDto result = new ReturnMessageDto();
		// on cherche toutes les FDP validées
		List<FichePoste> listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(
				Arrays.asList(idEntiteOld), Arrays.asList(2));

		// on crée un job de lancement de duplication des FDP
		for (FichePoste fp : listeFDP) {
			ActionFdpJob job = new ActionFdpJob(fp.getIdFichePoste(), idAgent, "DUPLICATION", idEntiteNew);
			fichePosteDao.persisEntity(job);
		}
		result.getInfos()
				.add(listeFDP.size()
						+ " FDP vont être dupliquées. Merci d'aller regarder le resultat de cette duplication dans SIRH.");
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
		List<Affectation> listAffSurFDP = affSrv.getAffectationByIdFichePoste(idFichePoste);
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
			result.getInfos().add("La FDP id " + fichePoste.getNumFP() + " est supprimée.");
		} catch (Exception e) {
			result.getErrors().add("La FDP id " + fichePoste.getNumFP() + " n'a pu être suprimée.");
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

		ArrayList<NiveauEtudeFP> niveauFPExistant = (ArrayList<NiveauEtudeFP>) fichePosteDao
				.listerNiveauEtudeFPAvecFP(fichePoste.getIdFichePoste());
		for (NiveauEtudeFP lien : niveauFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<ActiviteFP> activiteFPExistant = (ArrayList<ActiviteFP>) fichePosteDao
				.listerActiviteFPAvecFP(fichePoste.getIdFichePoste());
		for (ActiviteFP lien : activiteFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<CompetenceFP> competencesFPExistant = (ArrayList<CompetenceFP>) fichePosteDao
				.listerCompetenceFPAvecFP(fichePoste.getIdFichePoste());
		for (CompetenceFP lien : competencesFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<AvantageNatureFP> avantagesFPExistant = (ArrayList<AvantageNatureFP>) fichePosteDao
				.listerAvantageNatureFPAvecFP(fichePoste.getIdFichePoste());
		for (AvantageNatureFP lien : avantagesFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<DelegationFP> delegationFPExistant = (ArrayList<DelegationFP>) fichePosteDao
				.listerDelegationFPAvecFP(fichePoste.getIdFichePoste());
		for (DelegationFP lien : delegationFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<PrimePointageFP> primesPointagesFPExistant = (ArrayList<PrimePointageFP>) fichePosteDao
				.listerPrimePointageFP(fichePoste.getIdFichePoste());
		for (PrimePointageFP lien : primesPointagesFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		ArrayList<RegIndemFP> regimesFPExistant = (ArrayList<RegIndemFP>) fichePosteDao
				.listerRegIndemFPFPAvecFP(fichePoste.getIdFichePoste());
		for (RegIndemFP lien : regimesFPExistant) {
			fichePosteDao.removeEntity(lien);
		}

		// on actualise la FDP suite à la suppression des liens
		fichePosteDao.flush();

		fichePoste = fichePosteDao.chercherFichePoste(fichePoste.getIdFichePoste());
		// on supprime enfin la FDP
		fichePosteDao.removeEntity(fichePoste);

		// aussi de SPPOST
		Sppost posteAS400 = fichePosteDao.chercherSppost(new Integer(histo.getNumFp().substring(0, 4)), new Integer(
				fichePoste.getNumFP().substring(5, histo.getNumFp().length())));
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
			result.getInfos().add("La FDP id " + fichePoste.getNumFP() + " est dupliquée en " + numNewFDP + ".");
		} catch (Exception e) {
			result.getErrors().add("La FDP id " + fichePoste.getNumFP() + " n'a pu être dupliquée.");
		}

		return result;
	}

	private String dupliquerFDP(FichePoste fichePoste, EntiteDto entite, String login)
			throws CloneNotSupportedException {

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
			if (fePrimaire != null)
				fichePDupliquee.getFicheEmploiPrimaire().add(fePrimaire);
		}
		FeFp lienSecondaire = fichePosteDao.chercherFEFPAvecFP(fichePoste.getIdFichePoste(), 0);
		if (lienSecondaire != null) {
			FicheEmploi feSecondaire = fichePosteDao.chercherFicheEmploi(lienSecondaire.getId().getIdFicheEmploi());
			if (feSecondaire != null)
				fichePDupliquee.getFicheEmploiSecondaire().add(feSecondaire);
		}

		ArrayList<NiveauEtudeFP> niveauFPExistant = (ArrayList<NiveauEtudeFP>) fichePosteDao
				.listerNiveauEtudeFPAvecFP(fichePoste.getIdFichePoste());
		if (niveauFPExistant.size() > 0) {
			NiveauEtude niveau = fichePosteDao.chercherNiveauEtude(niveauFPExistant.get(0).getNiveauEtudeFPPK()
					.getIdNiveauEtude());
			if (niveau != null)
				fichePDupliquee.setNiveauEtude(niveau);
		}

		ArrayList<ActiviteFP> activiteFPExistant = (ArrayList<ActiviteFP>) fichePosteDao
				.listerActiviteFPAvecFP(fichePoste.getIdFichePoste());
		for (ActiviteFP lien : activiteFPExistant) {
			Activite acti = fichePosteDao.chercherActivite(lien.getActiviteFPPK().getIdActivite());
			if (acti != null)
				fichePDupliquee.getActivites().add(acti);
		}

		ArrayList<CompetenceFP> competencesFPExistant = (ArrayList<CompetenceFP>) fichePosteDao
				.listerCompetenceFPAvecFP(fichePoste.getIdFichePoste());
		for (CompetenceFP lien : competencesFPExistant) {
			Competence comp = fichePosteDao.chercherCompetence(lien.getCompetenceFPPK().getIdCompetence());
			if (comp != null)
				fichePDupliquee.getCompetencesFDP().add(comp);
		}

		ArrayList<AvantageNatureFP> avantagesFPExistant = (ArrayList<AvantageNatureFP>) fichePosteDao
				.listerAvantageNatureFPAvecFP(fichePoste.getIdFichePoste());
		for (AvantageNatureFP lien : avantagesFPExistant) {
			AvantageNature avNat = fichePosteDao.chercherAvantageNature(lien.getAvantageNaturePK().getIdAvantage());
			if (avNat != null)
				fichePDupliquee.getAvantagesNature().add(avNat);
		}

		ArrayList<DelegationFP> delegationFPExistant = (ArrayList<DelegationFP>) fichePosteDao
				.listerDelegationFPAvecFP(fichePoste.getIdFichePoste());
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
			PrimePointageFP prime = new PrimePointageFP();
			prime.setFichePoste(fichePDupliquee);
			prime.setPrimePointageFPPK(fk);
			fichePosteDao.persisEntity(prime);
		}

		ArrayList<RegIndemFP> regimesFPExistant = (ArrayList<RegIndemFP>) fichePosteDao
				.listerRegIndemFPFPAvecFP(fichePoste.getIdFichePoste());
		for (RegIndemFP lien : regimesFPExistant) {
			RegimeIndemnitaire reg = fichePosteDao.chercherRegimeIndemnitaire(lien.getRegIndemFPPK().getIdRegime());
			if (reg != null)
				fichePDupliquee.getRegimesIndemnitaires().add(reg);
		}

		// on crée la FDP en base
		try {
			fichePosteDao.persisEntity(fichePDupliquee);
		} catch (AbstractMethodError e) {
			System.out.println("ici");
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

		try {
			fichePosteDao.flush();
		} catch (Exception e) {
			System.out.println("ici");
		}

		return fichePDupliquee.getNumFP();
	}

	private FichePoste cloneFDP(FichePoste fichePoste) {
		FichePoste ficheClone = new FichePoste();

		// on remet à vide les listes
		ficheClone.setActivites(new HashSet<Activite>());
		ficheClone.setAgent(new HashSet<Affectation>());
		ficheClone.setNiveauEtude(null);
		ficheClone.setPrimePointageFP(new HashSet<PrimePointageFP>());
		ficheClone.setRegimesIndemnitaires(new HashSet<RegimeIndemnitaire>());
		ficheClone.setCompetencesFDP(new HashSet<Competence>());
		ficheClone.setAvantagesNature(new HashSet<AvantageNature>());
		ficheClone.setDelegations(new HashSet<Delegation>());
		ficheClone.setFicheEmploiPrimaire(new HashSet<FicheEmploi>());
		ficheClone.setFicheEmploiSecondaire(new HashSet<FicheEmploi>());
		ficheClone.setSuperieurHierarchique(null);
		ficheClone.setRemplace(null);

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
		List<FichePoste> listeFDP = fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(idEntite),
				Arrays.asList(1));

		// on crée un job de lancement de suppression des FDP
		for (FichePoste fp : listeFDP) {
			ActionFdpJob job = new ActionFdpJob(fp.getIdFichePoste(), idAgent, "ACTIVATION", null);
			fichePosteDao.persisEntity(job);
		}
		result.getInfos().add(
				listeFDP.size()
						+ " FDP vont être activées. Merci d'aller regarder le resultat de cette activation dans SIRH.");
		return result;
	}
}
