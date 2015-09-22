package nc.noumea.mairie.service.sirh;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.JourFerie;
import nc.noumea.mairie.model.repository.sirh.ISirhRepository;
import nc.noumea.mairie.web.dto.JourDto;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidayService implements IHolidayService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;
	
	@Autowired
	private ISirhRepository sirhRepository;

	@Override
	public boolean isHoliday(Date day) {
		TypedQuery<Integer> q = sirhEntityManager.createNamedQuery("isJourHoliday", Integer.class);
		q.setParameter("date", day);

		return (q.getResultList().size() != 0);
	}
	
	@Override
	public boolean isJourFerie(Date day) {
		TypedQuery<Integer> q = sirhEntityManager.createNamedQuery("isJourFerie", Integer.class);
		q.setParameter("date", day);

		return (q.getResultList().size() != 0);
	}
	
	@Override
	public List<JourDto> getListeJoursFeries(Date dateDebut, Date dateFin) {
		
		DateTime dateTimeFin = new DateTime(dateFin).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);

		List<JourFerie> result = sirhRepository.getListeJoursFeries(dateDebut, dateTimeFin.toDate());
		
		List<JourDto> list = new ArrayList<JourDto>();
		if(null != result) {
			for(JourFerie jour : result) {
				JourDto dto = new JourDto();
					dto.setChome(jour.getIdTypeJourFerie() == 2);
					dto.setFerie(jour.getIdTypeJourFerie() == 1);
					dto.setJour(jour.getDateJour());
				list.add(dto);
			}
		}
		
		return list;
	}
}
