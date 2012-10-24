package nc.noumea.mairie.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.tools.ServiceTreeNode;

import org.springframework.stereotype.Service;

@Service
public class SiservService implements ISiservService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	private static Hashtable<String, ServiceTreeNode> hTree;

	@Override
	public Siserv getDirection(String servi) {
		Siserv res = null;
		if (servi.length() == 4 && estAlphabetique(servi) && !servi.substring(1, 2).equals("A")) {
			String codeDirection = servi.substring(0, 2) + "AA";
			Query query = sirhEntityManager.createQuery("select serv from Siserv serv " + "where  servi=:codeDirection)", Siserv.class);
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
			Query query = sirhEntityManager.createQuery("select serv from Siserv serv " + "where  servi=:codeSection)", Siserv.class);
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
			Query query = sirhEntityManager.createQuery("select serv from Siserv serv " + "where  servi=:codeDivision)", Siserv.class);
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
		Query query = sirhEntityManager.createQuery("select serv from Siserv serv " + "where  servi=:service)", Siserv.class);
		query.setParameter("service", servi);
		List<Siserv> lserv = query.getResultList();

		for (Siserv serv : lserv)
			res = serv;
		return res;
	}

	@Override
	public List<String> getListServiceAgent(Integer idAgent) {
		// on construit l'arbre si il existe pas
		if (hTree == null)
			construitArbre();

		// on recupere le service de l'agent
		Siserv agentService = getServiceAgent(idAgent);

		// on recupere les sous-services du service de l'agent
		List<String> services = new ArrayList<String>();

		listSousServices(hTree.get(agentService.getSigle()), services);

		return services;
	}

	private Siserv getServiceAgent(Integer idAgent) {
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

		services.add(serviceTreeNode.getService());

		if (serviceTreeNode.getServicesEnfant().size() == 0)
			return;

		for (ServiceTreeNode enfant : serviceTreeNode.getServicesEnfant()) {
			listSousServices(enfant, services);
		}
	}

	private void construitArbre() {

		hTree = new Hashtable<String, ServiceTreeNode>();

		for (Siserv serv : getListServiceActif()) {

			ServiceTreeNode node = new ServiceTreeNode();
			node.setService(serv.getServi());
			node.setSigle(serv.getSigle());
			node.setSigleParent(serv.getParentSigle());

			hTree.put(node.getSigle(), node);
		}

		for (ServiceTreeNode node : hTree.values()) {

			ServiceTreeNode parent = hTree.get(node.getSigleParent());
			parent.getServicesEnfant().add(node);
			node.setServiceParent(parent);
		}
	}

	@Override
	public List<Siserv> getListServiceActif() {
		Query query = sirhEntityManager.createQuery("select serv from Siserv serv where  codeActif<>'I')", Siserv.class);
		List<Siserv> lserv = query.getResultList();

		return lserv;
	}
}
