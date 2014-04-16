package nc.noumea.mairie.model.repository;

import java.util.List;

import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.AvancementDetache;
import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.DiplomeAgent;
import nc.noumea.mairie.model.bean.FormationAgent;
import nc.noumea.mairie.model.bean.MotifAvct;

public interface ISirhRepository {

	AvancementFonctionnaire getAvancement(Integer idAgent, Integer anneeAvancement, boolean isFonctionnaire);

	AvancementDetache getAvancementDetache(Integer idAgent, Integer anneeAvancement);

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
	
	MotifAvct getMotifAvct(Integer idMotifAvct);
}
