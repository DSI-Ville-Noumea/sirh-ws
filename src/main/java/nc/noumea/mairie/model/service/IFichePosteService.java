package nc.noumea.mairie.model.service;

import java.util.List;

import nc.noumea.mairie.model.bean.FichePoste;

public interface IFichePosteService {

	public FichePoste getFichePosteAgentAffectationEnCours(Integer id);

	public FichePoste getFichePoste(Integer integer);

	public List<FichePoste> getFichePosteAgentService(String servi, Integer idAgent);

	public boolean estResponsable(Integer idAgent);
}
