package nc.noumea.mairie.model.service.eae;

import java.math.BigDecimal;
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
		if (listeCodeService != null && listeCodeService.size() != 0) {
			reqService = " union select e.ID_EAE from EAE e inner join EAE_FICHE_POSTE fp on e.ID_EAE = fp.ID_EAE inner join EAE_EVALUE evalue on e.ID_EAE = evalue.ID_EAE where e.ID_CAMPAGNE_EAE =:idCampagne and fp.CODE_SERVICE in (:listeCodeService) and evalue.ID_AGENT!=:idAgent ";
		}
		String sql = "select e.ID_EAE from EAE e where ID_CAMPAGNE_EAE =:idCampagne and ID_DELEGATAIRE =:idAgent union select e.ID_EAE from EAE e inner join EAE_EVALUATEUR ev on e.ID_EAE = ev.ID_EAE where e.ID_CAMPAGNE_EAE = :idCampagne and ev.ID_AGENT = :idAgent union select e.ID_EAE from EAE e inner join EAE_FICHE_POSTE fp on e.ID_EAE = fp.ID_EAE where e.ID_CAMPAGNE_EAE = :idCampagne and fp.ID_SHD = :idAgent ";
		Query query = eaeEntityManager.createNativeQuery(sql + reqService);
		query.setParameter("idCampagne", idCampagneEae);
		query.setParameter("idAgent", idAgent);
		if (listeCodeService != null && listeCodeService.size() != 0)
			query.setParameter("listeCodeService", listeCodeService);
		List<Integer> lIdEae = query.getResultList();
		return lIdEae;
	}

	@Override
	public Integer compterlistIdEaeByCampagneAndAgent(Integer idCampagneEae, Integer idAgent, List<String> listService) {
		String reqService = "";
		if (listService != null && listService.size() != 0) {
			reqService = " union select e.ID_EAE from EAE e inner join EAE_FICHE_POSTE fp on e.ID_EAE = fp.ID_EAE inner join EAE_EVALUE evalue on e.ID_EAE = evalue.ID_EAE where e.ID_CAMPAGNE_EAE =:idCampagne and fp.CODE_SERVICE in (:listeCodeService)  and evalue.ID_AGENT!=:idAgent ";
		}
		String sql = "select count(e.id_eae) from EAE e where e.id_EAE in ( select e.ID_EAE from EAE e where ID_CAMPAGNE_EAE =:idCampagne and ID_DELEGATAIRE =:idAgent union select e.ID_EAE from EAE e inner join EAE_EVALUATEUR ev on e.ID_EAE = ev.ID_EAE where e.ID_CAMPAGNE_EAE = :idCampagne and ev.ID_AGENT = :idAgent union select e.ID_EAE from EAE e inner join EAE_FICHE_POSTE fp on e.ID_EAE = fp.ID_EAE where e.ID_CAMPAGNE_EAE = :idCampagne and fp.ID_SHD = :idAgent "
				+ reqService + " )";
		Query query = eaeEntityManager.createNativeQuery(sql);
		query.setParameter("idCampagne", idCampagneEae);
		query.setParameter("idAgent", idAgent);
		if (listService != null && listService.size() != 0)
			query.setParameter("listeCodeService", listService);

		BigDecimal nbRes = (BigDecimal) query.getSingleResult();
		return nbRes.intValue();
	}
}
