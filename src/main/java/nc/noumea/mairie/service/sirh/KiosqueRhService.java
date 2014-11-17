package nc.noumea.mairie.service.sirh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.AccueilRh;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;

import org.springframework.stereotype.Service;

@Service
public class KiosqueRhService implements IKiosqueRhService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public List<ReferentRh> getListeReferentRH() {
		TypedQuery<ReferentRh> query = sirhEntityManager.createQuery("select ref from ReferentRh ref ",
				ReferentRh.class);
		List<ReferentRh> lc = query.getResultList();

		return lc;
	}

	@Override
	public List<AccueilRh> getListeAccueilRh() {
		TypedQuery<AccueilRh> query = sirhEntityManager.createQuery("select ref from AccueilRh ref ", AccueilRh.class);
		List<AccueilRh> lc = query.getResultList();

		return lc;
	}
}
