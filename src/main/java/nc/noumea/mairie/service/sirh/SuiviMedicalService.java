package nc.noumea.mairie.service.sirh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import nc.noumea.mairie.model.bean.sirh.SuiviMedical;
import nc.noumea.mairie.model.bean.sirh.VisiteMedicale;

@Service
public class SuiviMedicalService implements ISuiviMedicalService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public SuiviMedical getSuiviMedicalById(Integer idSuiviMedical) {

		SuiviMedical res = null;
		TypedQuery<SuiviMedical> query = sirhEntityManager.createQuery("select sm from SuiviMedical sm where sm.idSuiviMedical=:idSuiviMedical",
				SuiviMedical.class);
		query.setParameter("idSuiviMedical", idSuiviMedical);

		List<SuiviMedical> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

	@Override
	public VisiteMedicale getVisiteMedicale(int idVisite) {

		TypedQuery<VisiteMedicale> query = sirhEntityManager
				.createQuery("select vm from VisiteMedicale vm where vm.idVisiteMedicale=:idVisiteMedicale", VisiteMedicale.class);
		query.setParameter("idVisiteMedicale", idVisite);

		try {
			VisiteMedicale result = query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}

	}

}
