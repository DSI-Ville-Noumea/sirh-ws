package nc.noumea.mairie.service.sirh;

import nc.noumea.mairie.model.bean.sirh.Affectation;

public interface IAffectationService {

	public Affectation getAffectationById(Integer idAffectation);

	public Affectation getAffectationActiveByIdAgent(Integer idAgent);
}
