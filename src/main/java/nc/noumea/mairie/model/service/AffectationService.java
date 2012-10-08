package nc.noumea.mairie.model.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.Affectation;

import org.springframework.stereotype.Service;

@Service
public class AffectationService implements IAffectationService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public Affectation getAffectationFP(Integer idFichePoste) {
		Affectation res = null;
		Query query = sirhEntityManager.createQuery(
				"select aff from Affectation aff "
						+ "where  aff.fichePoste.idFichePoste =:idFichePoste)",
				Affectation.class);
		query.setParameter("idFichePoste", idFichePoste);
		List<Affectation> laff = query.getResultList();

		for (Affectation aff : laff)
			res = aff;

		return res;
	}
}
