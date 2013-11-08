package nc.noumea.mairie.model.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.eae.EaeCampagne;

import org.springframework.stereotype.Service;

@Service
public class EaeCampagneService implements IEaeCampagneService {

	@PersistenceContext(unitName = "eaePersistenceUnit")
	transient EntityManager eaeEntityManager;

	@Override
	public EaeCampagne getEaeCampagneOuverte() {

		EaeCampagne camp = null;

		Query query = eaeEntityManager
				.createQuery(
						"select camp from EaeCampagne camp where camp.dateOuvertureKiosque is not null and camp.dateFermetureKiosque is null and  camp.dateOuvertureKiosque<:dateJour",
						EaeCampagne.class);

		query.setParameter("dateJour", new Date());
		try {
			camp = (EaeCampagne) query.getSingleResult();
		} catch (Exception e) {
			// aucune campagne trouvée
		}

		return camp;

	}

}
