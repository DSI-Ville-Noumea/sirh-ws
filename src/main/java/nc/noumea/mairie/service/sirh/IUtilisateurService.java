package nc.noumea.mairie.service.sirh;

import nc.noumea.mairie.model.bean.sirh.Utilisateur;
import nc.noumea.mairie.web.dto.AccessRightOrganigrammeDto;
import nc.noumea.mairie.ws.dto.LightUserDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

public interface IUtilisateurService {

	Utilisateur chercherUtilisateurSIRHByLogin(String login);

	ReturnMessageDto isUtilisateurSIRH(String newIdAgent);

	AccessRightOrganigrammeDto getOrganigrammeAccessRight(Integer idAgent);

	LightUserDto getLoginByIdAgent(Integer idAgent);

}
