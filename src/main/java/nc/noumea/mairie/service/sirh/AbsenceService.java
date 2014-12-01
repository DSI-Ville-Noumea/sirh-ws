package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.web.dto.RefTypeSaisiCongeAnnuelDto;

import org.springframework.stereotype.Service;

@Service
public class AbsenceService implements IAbsenceService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public RefTypeSaisiCongeAnnuelDto getBaseHoraireAbsenceByAgent(Integer idAgent, Date date) {

		TypedQuery<Affectation> q = sirhEntityManager
				.createNamedQuery("getAffectationActiveByAgent", Affectation.class);
		q.setParameter("idAgent", idAgent);
		q.setParameter("today", new Date());
		q.setMaxResults(1);

		RefTypeSaisiCongeAnnuelDto result = null;
		try {

			List<Affectation> aff = q.getResultList();
			result = new RefTypeSaisiCongeAnnuelDto();
			result.setIdRefTypeSaisiCongeAnnuel(aff.get(0).getIdBaseHoraireAbsence());
		} catch (Exception e) {
			// on ne fait rien
		}

		return result;
	}
}
