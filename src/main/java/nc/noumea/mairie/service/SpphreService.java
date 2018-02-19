package nc.noumea.mairie.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.noumea.mairie.model.bean.Spphre;
import nc.noumea.mairie.service.sirh.HelperService;

@Service
public class SpphreService implements ISpphreService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private HelperService helperService;
	
	@Override
	public Spphre getSpphreForDayAndAgent(Integer idAgent, Date day) {

		String jpql = "from Spphre h where h.id.nomatr = :nomatr and h.id.datJour = :datJour";
		TypedQuery<Spphre> q = sirhEntityManager.createQuery(jpql, Spphre.class);
		q.setParameter("nomatr", helperService.getMairieMatrFromIdAgent(idAgent));
		q.setParameter("datJour", helperService.getIntegerDateMairieFromDate(day));
		
		List<Spphre> result = q.getResultList();
		
		if (result.size() == 0)
			return null;
		
		return result.get(0);
	}
}
