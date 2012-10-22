package nc.noumea.mairie.model.service.eae;

import java.util.List;

import nc.noumea.mairie.model.bean.eae.Eae;

public interface IEaeService {

	List<Eae> listerEaeDelegataire(Integer idAgentDelegataire,
			Integer idCampagneEae);

	Long compterEaeDelegataire(Integer idAgentDelegataire,
			Integer idCampagneEae);

	List<Eae> listerEaeSHD(Integer idAgentSHD, Integer idCampagneEae);

	Long compterEaeSHD(Integer idAgentSHD, Integer idCampagneEae);

	List<Eae> listEaesByCampagne(int idCampagne);
}
