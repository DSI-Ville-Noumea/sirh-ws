package nc.noumea.mairie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.SIVIET;

import org.springframework.stereotype.Service;

@Service
public class SivietService implements ISivietService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public SIVIET getLieuNaissEtr(Integer codePays, Integer codeCommune) {

		SIVIET res = null;

		TypedQuery<SIVIET> query = sirhEntityManager.createQuery(
				"select si from SIVIET si where si.id.codePays = :codePays and si.id.sousCodePays = :sousCodePays", SIVIET.class);

		query.setParameter("codePays", codePays);
		query.setParameter("sousCodePays", codeCommune);
		List<SIVIET> lfp = query.getResultList();

		for (SIVIET fp : lfp)
			res = fp;

		return res;
	}
}
