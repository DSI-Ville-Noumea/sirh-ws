package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.web.dto.FichePosteDto;
import nc.noumea.mairie.web.dto.FichePosteTreeNodeDto;
import nc.noumea.mairie.web.dto.InfoEntiteDto;
import nc.noumea.mairie.web.dto.SpbhorDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

public interface IFichePosteService {

	public FichePoste getFichePostePrimaireAgentAffectationEnCours(Integer idAgent, Date dateJour,
			boolean withCompetenceAndActivities);

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

	public FichePoste getFichePosteDetailleSIRHByIdWithRefPrime(Integer idFichePoste);

	List<SpbhorDto> getListSpbhorDto();

	SpbhorDto getSpbhorDtoById(Integer idSpbhor);

	public List<FichePosteDto> getListFichePosteByIdServiceADSAndStatutFDP(Integer idEntite,
			List<Integer> listStatutFDP, boolean withEntiteChildren);

	public InfoEntiteDto getInfoFDP(Integer idEntite, boolean withEntiteChildren);

	// Pour ADS

	public ReturnMessageDto deleteFichePosteByIdEntite(Integer idEntite, Integer idAgent);

	public ReturnMessageDto dupliqueFichePosteByIdEntite(Integer idEntiteNew, Integer idEntiteOld, Integer idAgent);

	public ReturnMessageDto activeFichesPosteByIdEntite(Integer idEntite, Integer idAgent);

	// Pour JOBS

	public ReturnMessageDto deleteFichePosteByIdFichePoste(Integer idFichePoste, Integer idAgent);

	public ReturnMessageDto dupliqueFichePosteByIdFichePoste(Integer idFichePoste, Integer idEntite, Integer idAgent);

	public ReturnMessageDto activeFichePosteByIdFichePoste(Integer idFichePoste, Integer idAgent);

	List<FichePosteTreeNodeDto> getTreeFichesPosteByIdEntite(int idEntite);
}
