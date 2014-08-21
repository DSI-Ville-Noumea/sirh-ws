package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Affectation;

import org.springframework.stereotype.Service;

@Service
public class AffectationService implements IAffectationService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

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

	@Override
	public Affectation getAffectationActiveByIdAgent(Integer idAgent) {

		Affectation res = null;
		TypedQuery<Affectation> query = sirhEntityManager
				.createQuery(
						"select aff from Affectation aff where aff.agent.idAgent=:idAgent and aff.dateDebutAff <= :dateJour and (aff.dateFinAff is null or aff.dateFinAff >= :dateJour)",
						Affectation.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", new Date());

		List<Affectation> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

}
