package nc.noumea.mairie.model.repository.sirh;

import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.FormationAgent;

public interface ISirhRepository {

	Affectation getAffectationActiveByAgent(int idAgent);

	List<Affectation> getListeAffectationsAgentAvecService(Integer idAgent, String idService);

	List<Affectation> getListeAffectationsAgentAvecFP(Integer idAgent, Integer idFichePoste);

	Agent getAgentWithListNomatr(Integer noMatr);

	Agent getAgentEligibleEAESansAffectes(Integer noMatr);

	AutreAdministrationAgent chercherAutreAdministrationAgentAncienne(Integer idAgent, boolean isFonctionnaire);

	List<AutreAdministrationAgent> getListeAutreAdministrationAgent(Integer idAgent);

	List<DiplomeAgent> getListDiplomeByAgent(Integer idAgent);

	List<FormationAgent> getListFormationAgentByAnnee(Integer idAgent, Integer anneeFormation);

	Agent getAgent(Integer idAgent);
}
