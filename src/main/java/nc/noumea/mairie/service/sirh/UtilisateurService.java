package nc.noumea.mairie.service.sirh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Utilisateur;
import nc.noumea.mairie.web.dto.LightUserDto;
import nc.noumea.mairie.web.dto.ReturnMessageDto;
import nc.noumea.mairie.ws.IRadiWSConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService implements IUtilisateurService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private IRadiWSConsumer radiWSConsumer;

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

		// on fait la correspondance entre le login et l'agent via RADI
		LightUserDto user = radiWSConsumer.getAgentCompteAD(Integer.valueOf(radiWSConsumer.getNomatrWithIdAgent(Integer
				.valueOf(newIdAgent))));
		if (user == null || user.getsAMAccountName() == null) {
			String err = "L'agent " + newIdAgent + " n'a pas de compte AD ou n'a pas son login renseigné.";
			res.getErrors().add(err);
			return res;
		}
		// on verifie que son login est dans la table des utilisateurs SIRH
		Utilisateur utilisateurSIRH = chercherUtilisateurSIRHByLogin(user.getsAMAccountName());
		if (utilisateurSIRH == null) {
			String err = "L'agent " + newIdAgent + " n'est pas un utilisateur SIRH.";
			res.getErrors().add(err);
			return res;
		}

		return res;
	}
}
