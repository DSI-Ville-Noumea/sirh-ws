package nc.noumea.mairie.model.service;

import java.util.Date;

import nc.noumea.mairie.model.bean.FichePoste;

public interface IFichePosteService {

	public FichePoste getFichePostePrimaireAgentAffectationEnCours(Integer idAgent,Date dateJour);

	public boolean estResponsable(Integer idAgent);

	public FichePoste getFichePosteSecondaireAgentAffectationEnCours(Integer idAgent,Date dateJour);

	public String getTitrePosteAgent(Integer idAgent, Date dateJour);
}
