package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

@Service
public class PointageService implements IPointageService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public List<Integer> getPrimePointagesByAgent(Integer idAgent, Date date) {
		StringBuilder sb = new StringBuilder();
		sb.append("select paff.num_rubrique from affectation aff ");
		sb.append("inner join prime_pointage_aff paff on aff.id_affectation=paff.id_affectation ");
		sb.append("where aff.id_agent = :idAgent and aff.date_Debut_Aff <= :date ");
		sb.append("and (aff.date_Fin_Aff is null or aff.date_Fin_Aff >= :date) ");
		sb.append("order by paff.num_rubrique ");

		Query q = sirhEntityManager.createNativeQuery(sb.toString());
		q.setParameter("idAgent", idAgent);
		q.setParameter("date", date);

		@SuppressWarnings("unchecked")
		List<Integer> result = q.getResultList();

		return result;
	}
}
