package nc.noumea.mairie.service.sirh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.AccueilRh;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;

import org.springframework.stereotype.Service;

@Service
public class KiosqueRhService implements IKiosqueRhService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public List<AccueilRh> getListeAccueilRh() {
		TypedQuery<AccueilRh> query = sirhEntityManager.createQuery("select ref from AccueilRh ref ", AccueilRh.class);
		List<AccueilRh> lc = query.getResultList();

		return lc;
	}

	@Override
	public ReferentRh getReferentRH(String codeService) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ref from ReferentRh ref ");
		sb.append("where ref.servi = :codeService ");

		TypedQuery<ReferentRh> q = sirhEntityManager.createQuery(sb.toString(), ReferentRh.class);
		q.setParameter("codeService", codeService);

		ReferentRh result = null;
		try {
			result = (ReferentRh) q.getSingleResult();
		} catch (Exception e) {
			// on a pas trouvé alors on cherche le referent global
			StringBuilder sbGlobal = new StringBuilder();
			sbGlobal.append("select ref from ReferentRh ref ");
			sbGlobal.append("where ref.servi is null ");
			Query qGlobal = sirhEntityManager.createQuery(sbGlobal.toString(), ReferentRh.class);
			try {
				result = (ReferentRh) qGlobal.getSingleResult();
			} catch (Exception e2) {
				// on ne fait rien
			}

		}

		return result;
	}
}
