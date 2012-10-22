package nc.noumea.mairie.model.service.eae;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.eae.EaeEvaluateur;

import org.springframework.stereotype.Service;

@Service
public class EaeEvaluateurService implements IEaeEvaluateurService {

	@PersistenceContext(unitName = "eaePersistenceUnit")
	private EntityManager eaeEntityManager;

	@Override
	public List<EaeEvaluateur> listerEaeEvaluateurAgent(Integer idAgent, Integer idCampagneEae) {
		Query query = eaeEntityManager.createQuery("select eval from EaeEvaluateur eval "
				+ "where  eval.eae.eaeCampagne.idCampagneEae =:idCampagneEae and eval.idAgent=:idAgent)", EaeEvaluateur.class);
		query.setParameter("idCampagneEae", idCampagneEae);
		query.setParameter("idAgent", idAgent);
		List<EaeEvaluateur> leval = query.getResultList();

		return leval;
	}

	@Override
	public Long compterEaeEvaluateurAgent(Integer idAgent, Integer idCampagneEae) {
		Query query = eaeEntityManager.createQuery("select count(eval) from EaeEvaluateur eval "
				+ "where  eval.eae.eaeCampagne.idCampagneEae =:idCampagneEae and eval.idAgent=:idAgent)");
		query.setParameter("idCampagneEae", idCampagneEae);
		query.setParameter("idAgent", idAgent);
		Long nbEval = (Long) query.getSingleResult();

		return nbEval;
	}

}
