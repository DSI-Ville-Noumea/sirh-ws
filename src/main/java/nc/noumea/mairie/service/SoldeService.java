package nc.noumea.mairie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.SpSold;

import org.springframework.stereotype.Service;

@Service
public class SoldeService implements ISoldeService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public SpSold getSoldeConge(Integer nomatr) {
		SpSold res = null;

		TypedQuery<SpSold> query = sirhEntityManager.createQuery("select ag from SpSold ag where ag.nomatr = :nomatr", SpSold.class);

		query.setParameter("nomatr", nomatr);
		List<SpSold> ls = query.getResultList();

		for (SpSold solde : ls)
			res = solde;

		return res;
	}

}
