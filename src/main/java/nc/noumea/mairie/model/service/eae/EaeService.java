package nc.noumea.mairie.model.service.eae;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

@Service
public class EaeService implements IEaeService {

	@PersistenceContext(unitName = "eaePersistenceUnit")
	private EntityManager eaeEntityManager;

	@Override
	public List<Integer> listIdEaeByCampagneAndAgent(Integer idCampagneEae, Integer idAgent, List<String> listeCodeService) {
		String reqService = "";
		if (listeCodeService != null) {
			reqService = " union select e.ID_EAE from EAE e inner join EAE_FICHE_POSTE fp on e.ID_EAE = fp.ID_EAE where e.ID_CAMPAGNE_EAE =:idCampagne and fp.CODE_SERVICE in (:listeCodeService)";
		}
		String sql = "select e.ID_EAE from EAE e where ID_CAMPAGNE_EAE =:idCampagne and ID_DELEGATAIRE =:idAgent union select e.ID_EAE from EAE e inner join EAE_EVALUATEUR ev on e.ID_EAE = ev.ID_EAE where e.ID_CAMPAGNE_EAE = :idCampagne and ev.ID_AGENT = :idAgent union select e.ID_EAE from EAE e inner join EAE_FICHE_POSTE fp on e.ID_EAE = fp.ID_EAE where e.ID_CAMPAGNE_EAE = :idCampagne and fp.ID_SHD = :idAgent ";
		Query query = eaeEntityManager.createNativeQuery(sql + reqService);
		query.setParameter("idCampagne", idCampagneEae);
		query.setParameter("idAgent", idAgent);
		query.setParameter("listeCodeService", listeCodeService);
		List<Integer> lIdEae = query.getResultList();
		return lIdEae;
	}
}
