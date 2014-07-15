package nc.noumea.mairie.service.sirh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.SuiviMedical;

import org.springframework.stereotype.Service;

@Service
public class SuiviMedicalService implements ISuiviMedicalService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public SuiviMedical getSuiviMedicalById(Integer idSuiviMedical) {

		SuiviMedical res = null;
		TypedQuery<SuiviMedical> query = sirhEntityManager.createQuery(
				"select sm from SuiviMedical sm where sm.idSuiviMedical=:idSuiviMedical", SuiviMedical.class);
		query.setParameter("idSuiviMedical", idSuiviMedical);

		List<SuiviMedical> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

}
