package nc.noumea.mairie.service.sirh;

import nc.noumea.mairie.model.bean.sirh.Utilisateur;
import nc.noumea.mairie.web.dto.AccessRightOrganigrammeDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

public interface IUtilisateurService {

	public Utilisateur chercherUtilisateurSIRHByLogin(String login);

	public ReturnMessageDto isUtilisateurSIRH(String newIdAgent);

	public AccessRightOrganigrammeDto getOrganigrammeAccessRight(Integer idAgent);

}
