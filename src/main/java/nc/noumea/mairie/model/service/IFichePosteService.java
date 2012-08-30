package nc.noumea.mairie.model.service;

import nc.noumea.mairie.model.bean.FichePoste;

public interface IFichePosteService {

	public FichePoste getFichePosteAgent(Integer id);

	public FichePoste getFichePoste(Long idFichePoste);
}
