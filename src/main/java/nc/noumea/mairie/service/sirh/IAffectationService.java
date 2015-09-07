package nc.noumea.mairie.service.sirh;

import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Affectation;

public interface IAffectationService {

	public Affectation getAffectationById(Integer idAffectation);

	public Affectation getAffectationActiveByIdAgent(Integer idAgent);

	public List<Affectation> getListAffectationByIdFichePoste(Integer idFichePoste);

	public Affectation getAffectationByIdFichePoste(Integer idFichePoste);
}
