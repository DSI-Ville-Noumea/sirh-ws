package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.DestinataireMailMaladie;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.FormationAgent;
import nc.noumea.mairie.model.bean.sirh.JourFerie;
import nc.noumea.mairie.model.bean.sirh.Utilisateur;

public interface ISirhRepository {

	AutreAdministrationAgent chercherAutreAdministrationAgentAncienne(Integer idAgent, boolean isFonctionnaire);

	List<AutreAdministrationAgent> getListeAutreAdministrationAgent(Integer idAgent);
	
	AvancementFonctionnaire getDernierAvancement(Integer idAgent, Integer anneeAvct);

	List<DiplomeAgent> getListDiplomeByAgent(Integer idAgent);

	List<FormationAgent> getListFormationAgentByAnnee(Integer idAgent, Integer anneeFormation);

	List<JourFerie> getListeJoursFeries(Date dateDebut, Date dateFin);

	List<Utilisateur> getListeUtilisateur();

	List<DestinataireMailMaladie> getListDestinataireMailMaladie();
}
