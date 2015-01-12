package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.FormationAgent;
import nc.noumea.mairie.model.bean.sirh.JourFerie;

public interface ISirhRepository {

	AutreAdministrationAgent chercherAutreAdministrationAgentAncienne(Integer idAgent, boolean isFonctionnaire);

	List<AutreAdministrationAgent> getListeAutreAdministrationAgent(Integer idAgent);

	List<DiplomeAgent> getListDiplomeByAgent(Integer idAgent);

	List<FormationAgent> getListFormationAgentByAnnee(Integer idAgent, Integer anneeFormation);

	List<JourFerie> getListeJoursFeries(Date dateDebut, Date dateFin);
}
