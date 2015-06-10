package nc.noumea.mairie.service.sirh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Utilisateur;
import nc.noumea.mairie.web.dto.AccessRightOrganigrammeDto;
import nc.noumea.mairie.ws.IRadiWSConsumer;
import nc.noumea.mairie.ws.dto.LightUserDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

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

	@Override
	public AccessRightOrganigrammeDto getOrganigrammeAccessRight(Integer newIdAgent) {
		
		//il faut recuprere l'element id=86 (c'est le numero du bon ecran) de la table des droits et faire la mapping avec le groupe et l'utilisateur
		// TODO Auto-generated method stub
		return null;
	}
}
