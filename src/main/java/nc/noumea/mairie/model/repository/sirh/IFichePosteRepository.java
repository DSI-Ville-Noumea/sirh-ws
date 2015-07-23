package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.tools.FichePosteTreeNode;
import nc.noumea.mairie.web.dto.InfoFichePosteDto;

public interface IFichePosteRepository {

	Hashtable<Integer, FichePosteTreeNode> getAllFichePosteAndAffectedAgents(Date today);

	List<FichePoste> getListFichePosteByIdServiceADSAndStatutFDP(Integer idEntite, List<Integer> listStatutFDP);

	void persisEntity(Object obj);

	FichePoste chercherFichePoste(Integer idFichePoste);

	List<InfoFichePosteDto> getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(Integer idEntiteEnfant);
}
