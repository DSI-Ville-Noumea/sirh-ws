package nc.noumea.mairie.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.tools.ServiceTreeNode;

import org.springframework.stereotype.Service;

@Service
public class SiservService implements ISiservService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	private Hashtable<String, ServiceTreeNode> hTree;

	@Override
	public Siserv getDirection(String servi) {
		Siserv res = null;
		if (servi.length() == 4 && estAlphabetique(servi) && !servi.substring(1, 2).equals("A")) {
			String codeDirection = servi.substring(0, 2) + "AA";
			TypedQuery<Siserv> query = sirhEntityManager.createQuery("select serv from Siserv serv where  servi=:codeDirection", Siserv.class);
			query.setParameter("codeDirection", codeDirection);
			List<Siserv> lserv = query.getResultList();

			for (Siserv serv : lserv)
				res = serv;
		}

		return res;
	}

	public static boolean estAlphabetique(String param) {
		if (param == null || param.length() == 0)
			return false;

		for (int i = 0; i < param.length(); i++) {

			char aChar = param.charAt(i);
			// Si le caract�re en cours n'est pas une lettre
			if (!Character.isLetter(aChar))
				return false;
		}
		return true;
	}

	@Override
	public Siserv getSection(String servi) {
		Siserv res = null;
		if (servi.length() == 4 && estAlphabetique(servi) && !servi.substring(3, 4).equals("A")) {
			TypedQuery<Siserv> query = sirhEntityManager.createQuery("select serv from Siserv serv where  servi=:codeSection", Siserv.class);
			query.setParameter("codeSection", servi);
			List<Siserv> lserv = query.getResultList();

			for (Siserv serv : lserv)
				res = serv;
		}

		return res;
	}

	@Override
	public Siserv getDivision(String servi) {
		Siserv res = null;
		if (servi.length() == 4 && estAlphabetique(servi) && !servi.substring(2, 3).equals("A")) {
			String codeDivision = servi.substring(0, 3) + "A";
			TypedQuery<Siserv> query = sirhEntityManager.createQuery("select serv from Siserv serv where  servi=:codeDivision", Siserv.class);
			query.setParameter("codeDivision", codeDivision);
			List<Siserv> lserv = query.getResultList();

			for (Siserv serv : lserv)
				res = serv;
		}
		return res;
	}

	@Override
	public Siserv getService(String servi) {
		Siserv res = null;
		TypedQuery<Siserv> query = sirhEntityManager.createQuery("select serv from Siserv serv where  servi=:service", Siserv.class);
		query.setParameter("service", servi);
		List<Siserv> lserv = query.getResultList();

		for (Siserv serv : lserv)
			res = serv;
		return res;
	}

	@Override
	public List<String> getListServiceAgent(Integer idAgent) {
		return getListServiceAgent(idAgent, null);
	}

	@Override
	public List<String> getListServiceAgent(Integer idAgent, String sigleServiceParent) {

		List<String> services = new ArrayList<String>();
		String agentServiceSigle = getServiceAgent(idAgent).getSigle().trim();

		// On récupère les sous services de l'agent
		listSousServices(getServiceTree().get(agentServiceSigle), services);

		// Si aucun sigle ne nous a été donné en paramètre, on renvoie la liste
		// entière
		if (sigleServiceParent == null || sigleServiceParent.equals(""))
			return services;

		// Sinon, un service a été précisé comme filtre
		// on vérifie qu'il appartient bien aux services de l'agent
		// sinon, on retourne une liste vide
		if (getServiceTree().get(sigleServiceParent) == null || !services.contains((getServiceTree().get(sigleServiceParent).getService())))
			return new ArrayList<String>();

		// et ensuite on récupère les services et sous services du sigleParent
		services = new ArrayList<String>();
		listSousServices(getServiceTree().get(sigleServiceParent), services);

		return services;
	}

	@Override
	public Siserv getServiceAgent(Integer idAgent) {
		String hql = "select serv from FichePoste fp ,Affectation aff , Siserv serv "
				+ "where fp.service.servi = serv.servi and aff.fichePoste.idFichePoste = fp.idFichePoste and  aff.agent.idAgent =:idAgent and aff.dateDebutAff<=:dateJour "
				+ "and (aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)";
		Query query = sirhEntityManager.createQuery(hql, Siserv.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", new Date());
		Siserv serv = (Siserv) query.getSingleResult();
		return serv;
	}

	private void listSousServices(ServiceTreeNode serviceTreeNode, List<String> services) {
		if (services.contains(serviceTreeNode.getService())) {
			return;
		}

		services.add(serviceTreeNode.getService());

		if (serviceTreeNode.getServicesEnfant().size() == 0)
			return;

		for (ServiceTreeNode enfant : serviceTreeNode.getServicesEnfant()) {
			listSousServices(enfant, services);
		}
	}

	@Override
	public List<Siserv> getListServiceActif() {
		TypedQuery<Siserv> query = sirhEntityManager.createQuery("select serv from Siserv serv where  codeActif<>'I')", Siserv.class);
		List<Siserv> lserv = query.getResultList();

		return lserv;
	}

	@Override
	public ServiceTreeNode getAgentServiceTree(Integer idAgent) {

		String agentServiceSigle = getServiceAgent(idAgent).getSigle().trim();
		ServiceTreeNode result = getServiceTree().get(agentServiceSigle);

		return result;
	}

	@Override
	public List<String> getListSubServicesCodes(String servi) {

		List<String> result = new ArrayList<String>();
		Siserv siserv = getService(servi);
		
		ServiceTreeNode rootNode = getServiceTree().get(siserv.getSigle().trim());
		
		result.add(siserv.getServi());
		listSubServices(rootNode, result);
		
		return result;
	}
	
	private void listSubServices(ServiceTreeNode servi, List<String> services) {
		
		if (servi == null)
			return;
		
		services.add(servi.getService());

		for (ServiceTreeNode child : servi.getServicesEnfant())
			listSubServices(child, services);
	}

	/**
	 * Returns the only instance of the tree and builds it thread safely if not
	 * yet existing Retourne l'instance de l'arbre des services Le construit de
	 * manière thread-safe s'il n'existe pas
	 * 
	 * @return L'arbre des services
	 */
	private Hashtable<String, ServiceTreeNode> getServiceTree() {

		if (hTree != null)
			return hTree;

		synchronized (this) {

			if (hTree != null)
				return hTree;

			hTree = construitArbre();
		}

		return hTree;
	}

	/**
	 * Construit un arbre hiérarchique des services de la mairie
	 * 
	 * @return l'arbre des services de SIRH
	 */
	private Hashtable<String, ServiceTreeNode> construitArbre() {

		Hashtable<String, ServiceTreeNode> hTree = new Hashtable<String, ServiceTreeNode>();

		for (Siserv serv : getListServiceActif()) {

			ServiceTreeNode node = new ServiceTreeNode();
			node.setService(serv.getServi());
			node.setServiceLibelle(serv.getLiServ().trim());
			node.setSigle(serv.getSigle().trim());
			node.setSigleParent(serv.getParentSigle().trim());

			hTree.put(node.getSigle(), node);
		}

		for (ServiceTreeNode node : hTree.values()) {

			ServiceTreeNode parent = hTree.get(node.getSigleParent());
			if (parent != node) {
				parent.getServicesEnfant().add(node);
				node.setServiceParent(parent);
			}
		}

		return hTree;
	}

	@Override
	public Siserv getServiceBySigle(String sigleService) {
		Siserv res = null;
		TypedQuery<Siserv> query = sirhEntityManager
				.createQuery("select serv from Siserv serv where  sigle=:sigle and codeActif<>'I' ", Siserv.class);
		query.setParameter("sigle", sigleService);
		List<Siserv> lserv = query.getResultList();

		for (Siserv serv : lserv)
			res = serv;
		return res;
	}

	@Override
	public ServiceTreeNode getAgentDirection(Integer idAgent) {

		ServiceTreeNode agentService = getAgentServiceTree(idAgent);
		
		ServiceTreeNode directionAgent = agentService;
		boolean directionFound = false;
		
		while (!directionFound && directionAgent != null) {
			if (directionAgent.getService().endsWith("AA"))
				directionFound = true;
			else
				directionAgent = directionAgent.getServiceParent();
		}
		
		return directionAgent;
	}
}
