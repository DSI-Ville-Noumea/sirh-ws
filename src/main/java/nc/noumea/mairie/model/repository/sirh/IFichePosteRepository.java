package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.AvantageNatureFP;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.DelegationFP;
import nc.noumea.mairie.model.bean.sirh.FeFp;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.NiveauEtudeFP;
import nc.noumea.mairie.model.bean.sirh.PrimePointageFP;
import nc.noumea.mairie.model.bean.sirh.RegIndemFP;
import nc.noumea.mairie.tools.FichePosteTreeNode;
import nc.noumea.mairie.web.dto.InfoFichePosteDto;

public interface IFichePosteRepository {

	Hashtable<Integer, FichePosteTreeNode> getAllFichePosteAndAffectedAgents(Date today);

	List<FichePoste> getListFichePosteByIdServiceADSAndStatutFDP(List<Integer> idEntite, List<Integer> listStatutFDP);

	void persisEntity(Object obj);

	void removeEntity(Object obj);

	FichePoste chercherFichePoste(Integer idFichePoste);

	List<InfoFichePosteDto> getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(List<Integer> idEntiteEnfant);

	List<FichePoste> getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(
			List<Integer> listIdsEntite, List<Integer> listStatutFDP);

	List<FeFp> listerFEFPAvecFP(Integer idFichePoste);

	List<NiveauEtudeFP> listerNiveauEtudeFPAvecFP(Integer idFichePoste);

	List<ActiviteFP> listerActiviteFPAvecFP(Integer idFichePoste);

	List<CompetenceFP> listerCompetenceFPAvecFP(Integer idFichePoste);

	List<AvantageNatureFP> listerAvantageNatureFPAvecFP(Integer idFichePoste);

	List<DelegationFP> listerDelegationFPAvecFP(Integer idFichePoste);

	List<PrimePointageFP> listerPrimePointageFP(Integer idFichePoste);

	List<RegIndemFP> listerRegIndemFPFPAvecFP(Integer idFichePoste);
}
