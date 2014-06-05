package nc.noumea.mairie.service.sirh;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

@Service
public class HolidayService implements IHolidayService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public boolean isHoliday(Date day) {
		TypedQuery<Integer> q = sirhEntityManager.createNamedQuery("isJourHoliday", Integer.class);
		q.setParameter("date", day);

		return (q.getResultList().size() != 0);
	}
}
