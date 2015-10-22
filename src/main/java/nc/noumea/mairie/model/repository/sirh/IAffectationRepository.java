package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;

public interface IAffectationRepository {

	Affectation getAffectationActiveByAgent(int idAgent);

	List<Affectation> getListeAffectationsAgentAvecService(Integer idAgent, Integer idServiceADS);

	List<Affectation> getListeAffectationsAgentAvecFP(Integer idAgent, Integer idFichePoste);

	List<Affectation> getListeAffectationsAgentByPeriode(Integer idAgent, Date dateDebut, Date dateFin);

	List<Affectation> getListeAffectationsAgentOrderByDateDesc(Integer idAgent);

	List<Affectation> getAffectationByIdFichePoste(Integer idFichePoste);

	Affectation chercherAffectationAgentAvecDateDebut(Integer idAgent, Date date);

	List<Affectation> getListeAffectationsAgentOrderByDateAsc(Integer idAgent);

	Agent getAffectationAgent(Integer idAgent, Date dateDonnee);

	List<Affectation> getListAffectationActiveByIdFichePoste(Integer idFichePoste);

	List<Affectation> getListeAffectationsForListAgentByPeriode(
			List<Integer> listIdsAgent, Date dateDebut, Date dateFin);
}
