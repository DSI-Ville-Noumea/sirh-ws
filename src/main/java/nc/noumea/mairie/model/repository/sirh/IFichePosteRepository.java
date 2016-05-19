package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import nc.noumea.mairie.model.bean.Sppost;
import nc.noumea.mairie.model.bean.sirh.ActionFdpJob;
import nc.noumea.mairie.model.bean.sirh.Activite;
import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.AvantageNature;
import nc.noumea.mairie.model.bean.sirh.AvantageNatureFP;
import nc.noumea.mairie.model.bean.sirh.Competence;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.Delegation;
import nc.noumea.mairie.model.bean.sirh.DelegationFP;
import nc.noumea.mairie.model.bean.sirh.FeFp;
import nc.noumea.mairie.model.bean.sirh.FicheEmploi;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.NiveauEtude;
import nc.noumea.mairie.model.bean.sirh.NiveauEtudeFP;
import nc.noumea.mairie.model.bean.sirh.PrimePointageFP;
import nc.noumea.mairie.model.bean.sirh.RegIndemFP;
import nc.noumea.mairie.model.bean.sirh.RegimeIndemnitaire;
import nc.noumea.mairie.model.bean.sirh.StatutFichePoste;
import nc.noumea.mairie.tools.FichePosteTreeNode;
import nc.noumea.mairie.web.dto.GroupeInfoFichePosteDto;
import nc.noumea.mairie.web.dto.InfoFichePosteDto;

public interface IFichePosteRepository {

	Hashtable<Integer, FichePosteTreeNode> getAllFichePosteAndAffectedAgents(Date today);

	List<FichePoste> getListFichePosteByIdServiceADSAndStatutFDP(List<Integer> idEntite, List<Integer> listStatutFDP);

	void persisEntity(Object obj);

	void removeEntity(Object obj);

	void flush();

	FichePoste chercherFichePoste(Integer idFichePoste);

	FichePoste chercherFichePosteByNumFP(String numFP);

	List<GroupeInfoFichePosteDto> getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(List<Integer> idEntiteEnfant);

	List<FichePoste> getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(List<Integer> listIdsEntite, List<Integer> listStatutFDP);

	List<FeFp> listerFEFPAvecFP(Integer idFichePoste);

	List<NiveauEtudeFP> listerNiveauEtudeFPAvecFP(Integer idFichePoste);

	List<ActiviteFP> listerActiviteFPAvecFP(Integer idFichePoste);

	List<CompetenceFP> listerCompetenceFPAvecFP(Integer idFichePoste);

	List<AvantageNatureFP> listerAvantageNatureFPAvecFP(Integer idFichePoste);

	List<DelegationFP> listerDelegationFPAvecFP(Integer idFichePoste);

	List<PrimePointageFP> listerPrimePointageFP(Integer idFichePoste);

	List<RegIndemFP> listerRegIndemFPFPAvecFP(Integer idFichePoste);

	Sppost chercherSppost(Integer poanne, Integer ponuor);

	FichePoste chercherDerniereFichePosteByYear(Integer annee);

	FeFp chercherFEFPAvecFP(Integer idFichePoste, Integer isPrimaire);

	FicheEmploi chercherFicheEmploi(Integer idFicheEmploi);

	NiveauEtude chercherNiveauEtude(Integer idNiveauEtude);

	Activite chercherActivite(Integer idActivite);

	Competence chercherCompetence(Integer idCompetence);

	AvantageNature chercherAvantageNature(Integer idAvantage);

	Delegation chercherDelegation(Integer idDelegation);

	RegimeIndemnitaire chercherRegimeIndemnitaire(Integer idRegime);

	StatutFichePoste chercherStatutFPByIdStatut(Integer idStatut);

	TreeMap<Integer, FichePosteTreeNode> getAllFichePoste(Date today);

	List<InfoFichePosteDto> getListInfoFichePosteDtoByIdServiceADSAndTitrePoste(List<Integer> idEntiteEnfant, String titrePoste, Date today);

	List<FichePoste> chercherListFichesPosteByListIdsFichePoste(List<Integer> listIdsFichePoste);

	ActionFdpJob chercherActionFDPParentDuplication(Integer idFichePoste);

	List<Integer> getListFichePosteAffecteeByIdServiceADS(Integer idEntite);

	List<Integer> getListFichePosteNonAffecteeByIdServiceADS(Integer idEntite);

}
