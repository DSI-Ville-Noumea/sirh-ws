package nc.noumea.mairie.model.service;

import nc.noumea.mairie.model.bean.FichePoste;

public interface IFichePosteService {

	public FichePoste getFichePosteAgentAffectationEnCours(Integer id);

	public boolean estResponsable(Integer idAgent);
}
