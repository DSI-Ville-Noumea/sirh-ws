package nc.noumea.mairie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Sibanq;

import org.springframework.stereotype.Service;

@Service
public class SibanqService implements ISibanqService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public Sibanq getBanque(Integer codeBanque) {

		Sibanq res = null;

		TypedQuery<Sibanq> query = sirhEntityManager.createQuery(
				"select sb from Sibanq sb where sb.idBanque = :idBanque", Sibanq.class);

		query.setParameter("idBanque", codeBanque);
		List<Sibanq> lfp = query.getResultList();

		for (Sibanq fp : lfp)
			res = fp;

		return res;
	}
}
