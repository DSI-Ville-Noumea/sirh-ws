package nc.noumea.mairie.model.service;

import nc.noumea.mairie.model.bean.Utilisateur;
import nc.noumea.mairie.web.dto.ReturnMessageDto;

public interface IUtilisateurService {

	public Utilisateur chercherUtilisateurSIRHByLogin(String login);

	public ReturnMessageDto isUtilisateurSIRH(String newIdAgent);

}
