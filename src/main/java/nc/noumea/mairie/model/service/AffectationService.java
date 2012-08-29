package nc.noumea.mairie.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.Affectation;

import org.springframework.stereotype.Service;

@Service
public class AffectationService implements IAffectationService {

	@PersistenceContext
	transient EntityManager entityManager;

	@Override
	public Affectation getAffectationCouranteAgent(Integer id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = null;
		try {
			dateJour = sdf.parse(dateTemp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Affectation res = null;
		Query query = entityManager
				.createQuery(
						"select aff from Affectation aff , Agent ag "
								+ "where  ag.idAgent = aff.agent.idAgent  and "
								+ "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
								+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)",
						Affectation.class);
		query.setParameter("idAgent", id);
		query.setParameter("dateJour", dateJour);
		List<Affectation> laff = query.getResultList();

		for (Affectation aff : laff)
			res = aff;

		return res;
	}
}
