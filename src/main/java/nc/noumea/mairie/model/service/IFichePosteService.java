package nc.noumea.mairie.model.service;

import nc.noumea.mairie.model.bean.FichePoste;

public interface IFichePosteService {

	public FichePoste getFichePostePrimaireAgentAffectationEnCours(Integer idAgent);

	public boolean estResponsable(Integer idAgent);

	public FichePoste getFichePosteSecondaireAgentAffectationEnCours(Integer idAgent);
}
