package nc.noumea.mairie.model.service.eae;

import java.util.List;

import nc.noumea.mairie.model.bean.eae.EaeEvaluateur;

public interface IEaeEvaluateurService {

	List<EaeEvaluateur> listerEaeEvaluateurAgent(Integer idAgent, Integer idCampagneEae);

	Long compterEaeEvaluateurAgent(Integer idAgent, Integer idCampagneEae);
}
