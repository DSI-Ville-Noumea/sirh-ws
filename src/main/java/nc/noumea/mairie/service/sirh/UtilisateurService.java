package nc.noumea.mairie.service.sirh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Siidma;
import nc.noumea.mairie.model.bean.Utilisateur;
import nc.noumea.mairie.service.ISiidmaService;
import nc.noumea.mairie.web.dto.ReturnMessageDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService implements IUtilisateurService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private ISiidmaService siidmaSrv;

	@Autowired
	private IAgentService agentSrv;

	@Override
	public Utilisateur chercherUtilisateurSIRHByLogin(String login) {
		Utilisateur res = null;
		TypedQuery<Utilisateur> query = sirhEntityManager.createQuery(
				"select u from Utilisateur u where  u.login=:login ", Utilisateur.class);
		query.setParameter("login", login.toLowerCase());
		List<Utilisateur> lutil = query.getResultList();

		for (Utilisateur u : lutil)
			res = u;
		return res;
	}

	@Override
	public ReturnMessageDto isUtilisateurSIRH(String newIdAgent) {
		ReturnMessageDto res = new ReturnMessageDto();

		// on cherche si l'agent existe
		Agent ag = agentSrv.getAgent(Integer.valueOf(newIdAgent));
		if (ag == null) {
			String err = "L'agent " + newIdAgent + " n'existe pas.";
			res.getErrors().add(err);
			return res;
		}

		// on cherche son login dans SIIDMA
		// dans SIIDMA les idIndi sont de type 90+nomatr
		String idIndi = "90" + newIdAgent.substring(3, newIdAgent.length());
		Siidma siidma = siidmaSrv.chercherSiidmaByIdIndi(Integer.valueOf(idIndi));
		if (siidma == null) {
			String err = "L'agent " + newIdAgent + " n'existe pas dans SIIDMA.";
			res.getErrors().add(err);
			return res;
		}
		// on verifie que son login est dans la table des utilisateurs SIRH
		Utilisateur utilisateurSIRH = chercherUtilisateurSIRHByLogin(siidma.getId().getCdidut());
		if (utilisateurSIRH == null) {
			String err = "L'agent " + newIdAgent + " n'est pas un utilisateur SIRH.";
			res.getErrors().add(err);
			return res;
		}

		return res;
	}
}
