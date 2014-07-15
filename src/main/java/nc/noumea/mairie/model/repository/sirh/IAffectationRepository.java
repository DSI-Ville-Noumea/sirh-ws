package nc.noumea.mairie.model.repository.sirh;

import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Affectation;

public interface IAffectationRepository {

	Affectation getAffectationActiveByAgent(int idAgent);

	List<Affectation> getListeAffectationsAgentAvecService(Integer idAgent, String idService);

	List<Affectation> getListeAffectationsAgentAvecFP(Integer idAgent, Integer idFichePoste);
}
