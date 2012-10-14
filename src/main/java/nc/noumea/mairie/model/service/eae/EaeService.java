package nc.noumea.mairie.model.service.eae;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.eae.Eae;
import nc.noumea.mairie.model.service.IAgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EaeService implements IEaeService {

	@PersistenceContext(unitName = "eaePersistenceUnit")
	private EntityManager eaeEntityManager;

	@Autowired
	private IAgentService agentService;

	@Override
	public List<Eae> listEaesByCampagne(int idCampagne) {

		// TODO: this will change into a WS call to SIRH-WS in order to retrieve
		// the list of EAEs to display
		TypedQuery<Eae> eaeQuery = eaeEntityManager
				.createQuery(
						"select e from Eae e where e.eaeCampagne.idCampagneEae = :idCampagne",
						Eae.class);
		eaeQuery.setParameter("idCampagne", idCampagne);
		List<Eae> result = eaeQuery.getResultList();

		// For each EAE result, retrieve the Agent, SHD and Delegataire
		// informations from the Agent (other persistenceUnit)
		// retrieve also the Evaluateurs of the current EAE
		for (Eae eae : result) {
			agentService.fillEaeWithAgents(eae);
		}

		return result;
	}

	@Override
	public List<Eae> listerEaeDelegataire(Integer idAgentDelegataire,
			Integer idCampagneEae) {
		Query query = eaeEntityManager
				.createQuery(
						"select e from Eae e "
								+ "where  e.eaeCampagne.idCampagneEae =:idCampagneEae and e.idAgentDelegataire=:idAgentDelegataire)",
						Eae.class);
		query.setParameter("idCampagneEae", idCampagneEae);
		query.setParameter("idAgentDelegataire", idAgentDelegataire);
		List<Eae> leae = query.getResultList();

		return leae;
	}

	@Override
	public List<Eae> listerEaeSHD(Integer idAgentSHD, Integer idCampagneEae) {
		Query query = eaeEntityManager
				.createQuery(
						"select e from Eae e "
								+ "where  e.eaeCampagne.idCampagneEae =:idCampagneEae and e.idAgentShd=:idAgentShd)",
						Eae.class);
		query.setParameter("idCampagneEae", idCampagneEae);
		query.setParameter("idAgentShd", idAgentSHD);
		List<Eae> leae = query.getResultList();

		return leae;
	}

}
