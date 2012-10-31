package nc.noumea.mairie.model.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.Spcong;

import org.springframework.stereotype.Service;

@Service
public class SpcongService implements ISpcongService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public List<Spcong> getHistoriqueCongeAnnee(Long nomatr) {
		// on veut l'historique sur 1 an
		Date dateJour = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateJourString = sdf.format(dateJour);
		Integer annee = Integer.valueOf(dateJourString.substring(0, 4)) - 1;
		String datFinMax = annee.toString()
				+ dateJourString.substring(4, dateJourString.length());

		Query query = sirhEntityManager
				.createQuery(
						"select spcong from Spcong spcong "
								+ "where spcong.id.nomatr=:nomatr and spcong.dateFin>=:datFin order by spcong.id.dateDeb desc",
						Spcong.class);
		query.setParameter("nomatr", nomatr.intValue());
		query.setParameter("datFin", Integer.valueOf(datFinMax));
		List<Spcong> lcong = query.getResultList();

		return lcong;
	}

	@Override
	public List<Spcong> getToutHistoriqueConge(Long nomatr) {
		// on veut l'historique au delà d'1 an
		Date dateJour = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateJourString = sdf.format(dateJour);
		Integer annee = Integer.valueOf(dateJourString.substring(0, 4)) - 1;
		String datFinMax = annee.toString()
				+ dateJourString.substring(4, dateJourString.length());
		Query query = sirhEntityManager
				.createQuery(
						"select spcong from Spcong spcong "
								+ "where spcong.id.nomatr=:nomatr and spcong.dateFin<:dateFin order by spcong.id.dateDeb desc",
						Spcong.class);
		query.setParameter("nomatr", nomatr.intValue());
		query.setParameter("dateFin", Integer.valueOf(datFinMax));
		List<Spcong> lcong = query.getResultList();

		return lcong;
	}

}
