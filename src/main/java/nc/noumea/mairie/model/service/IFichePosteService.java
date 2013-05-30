package nc.noumea.mairie.model.service;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.FichePoste;

public interface IFichePosteService {

	public FichePoste getFichePostePrimaireAgentAffectationEnCours(Integer idAgent, Date dateJour);

	public boolean estResponsable(Integer idAgent);

	public FichePoste getFichePosteSecondaireAgentAffectationEnCours(Integer idAgent, Date dateJour);

	public String getTitrePosteAgent(Integer idAgent, Date dateJour);

	public void construitArbreFichePostes();

	public Integer getIdFichePostePrimaireAgentAffectationEnCours(int idAgent, Date date);

	public List<Integer> getListSubFichePoste(int idAgent, int maxDepth);

	public List<Integer> getListSubAgents(int idAgent, int maxDepth, String nom);

	public List<Integer> getSubFichePosteIdsForResponsable(int idFichePosteResponsable, int maxDepth);

	public List<Integer> getSubAgentIdsForFichePoste(int idFichePosteResponsable, int maxDepth, String nom);

	public List<Integer> getListShdAgents(int idAgent, int maxDepth);

	public List<Integer> getShdAgentIdsForFichePoste(int idFichePoste, int maxDepth);

	public FichePoste getFichePosteById(Integer idFichePoste);
}
