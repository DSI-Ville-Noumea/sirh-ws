package nc.noumea.mairie.service.sirh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Affectation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AffectationService implements IAffectationService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	private Logger logger = LoggerFactory.getLogger(AffectationService.class);

	@Override
	public Affectation getAffectationById(Integer idAffectation) {

		Affectation res = null;
		TypedQuery<Affectation> query = sirhEntityManager.createQuery(
				"select aff from Affectation aff where aff.idAffectation=:idAffectation", Affectation.class);
		query.setParameter("idAffectation", idAffectation);

		List<Affectation> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

}
