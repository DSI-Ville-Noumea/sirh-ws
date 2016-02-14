package nc.noumea.mairie.service.sirh;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Droits;
import nc.noumea.mairie.model.bean.sirh.DroitsElementEnum;
import nc.noumea.mairie.model.bean.sirh.DroitsGroupeEnum;
import nc.noumea.mairie.model.bean.sirh.TypeDroitEnum;
import nc.noumea.mairie.model.bean.sirh.Utilisateur;
import nc.noumea.mairie.model.repository.sirh.IDroitsRepository;
import nc.noumea.mairie.model.repository.sirh.ISirhRepository;
import nc.noumea.mairie.web.dto.AccessRightOrganigrammeDto;
import nc.noumea.mairie.ws.IRadiWSConsumer;
import nc.noumea.mairie.ws.dto.LightUserDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService implements IUtilisateurService {

	private Logger logger = LoggerFactory.getLogger(UtilisateurService.class);

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private IRadiWSConsumer radiWSConsumer;

	@Autowired
	private IDroitsRepository droitsRepository;

	@Autowired
	private ISirhRepository sirhRepository;

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
	public List<LightUserDto> getListeUtilisateurSIRH(Integer idAgent) {

		List<LightUserDto> result = new ArrayList<LightUserDto>();

		// on cherche si l'agent existe
		if(null != idAgent) {
		
			Agent ag = agentSrv.getAgent(idAgent);
			if (ag == null) {
				return result;
			}
	
			// on fait la correspondance entre le login et l'agent via RADI
			LightUserDto user = radiWSConsumer.getAgentCompteAD(Integer.valueOf(radiWSConsumer.getNomatrWithIdAgent(Integer
					.valueOf(idAgent))));
			if (user == null || user.getsAMAccountName() == null) {
				logger.debug("L'agent " + idAgent + " n'a pas de compte AD ou n'a pas son login renseigné.");
				return result;
			}
			// on verifie que son login est dans la table des utilisateurs SIRH
			Utilisateur utilisateurSIRH = chercherUtilisateurSIRHByLogin(user.getsAMAccountName());
			if (utilisateurSIRH == null) {
				logger.debug("L'agent " + idAgent + " n'est pas un utilisateur SIRH.");
				return result;
			}
			
			result.add(user);
		} else {
			
			List<Utilisateur> listUtilisateurs = sirhRepository.getListeUtilisateur();
			
			if(null != listUtilisateurs) {
				for(Utilisateur utilisateur : listUtilisateurs) {
				
					LightUserDto user = radiWSConsumer.getAgentCompteADByLogin(utilisateur.getLogin());
					
					if(null != user
							&& 0 < user.getEmployeeNumber()) {
						result.add(user);
					}
				}
			}
		}

		return result;
	}

	@Override
	public AccessRightOrganigrammeDto getOrganigrammeAccessRight(Integer idAgent) {

		AccessRightOrganigrammeDto result = new AccessRightOrganigrammeDto();

		// il faut recuperer l'element id_element=86 (c'est le numero du bon
		// ecran) de la table des droits
		// et faire la mapping avec le groupe et l'utilisateur
		LightUserDto user = getLoginByIdAgent(idAgent);
		if (null == user) {
			return result;
		}
		List<Droits> droits = droitsRepository.getDroitsByElementAndAgent(
				DroitsElementEnum.ECR_ORG_VISU.getIdElement(), user.getsAMAccountName());

		if (null != droits) {
			for (Droits droit : droits) {
				if (TypeDroitEnum.CONSULTATION.getIdTypeDroit().equals(droit.getIdTypeDroit())) {
					result.setVisualisation(true);
				}
				if (TypeDroitEnum.EDITION.getIdTypeDroit().equals(droit.getIdTypeDroit())) {
					result.setVisualisation(true);
					result.setEdition(true);
				}
			}
		}

		//#16380 : on gere un role administrateur
		List<Droits> droitsAdmin = droitsRepository.getDroitsByGroupeAndAgent(DroitsGroupeEnum.GROUPE_SIRH.getIdGroupe(), user.getsAMAccountName());
		if(null != droitsAdmin && droitsAdmin.size()>0) {
			result.setAdministrateur(true);
		}
		
		return result;
	}

	@Override
	public LightUserDto getLoginByIdAgent(Integer idAgent) {
		// on cherche si l'agent existe
		Agent ag = agentSrv.getAgent(idAgent);
		if (ag == null) {
			logger.debug("L'agent " + idAgent + " n'existe pas.");
			return null;
		}

		// on fait la correspondance entre le login et l'agent via RADI
		LightUserDto user = radiWSConsumer.getAgentCompteAD(Integer.valueOf(radiWSConsumer
				.getNomatrWithIdAgent(idAgent)));
		if (user == null || user.getsAMAccountName() == null) {
			logger.debug("L'agent " + idAgent + " n'a pas de compte AD ou n'a pas son login renseigné.");
			return null;
		}

		return user;
	}
}
