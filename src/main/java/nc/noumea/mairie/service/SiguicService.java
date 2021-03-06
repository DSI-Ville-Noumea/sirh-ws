package nc.noumea.mairie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Siguic;

import org.springframework.stereotype.Service;

@Service
public class SiguicService implements ISiguicService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public Siguic getBanque(Integer codeBanque, Integer codeGuichet) {

		Siguic res = null;

		TypedQuery<Siguic> query = sirhEntityManager.createQuery(
				"select si from Siguic si where si.id.codeBanque = :codeBanque and si.id.codeGuichet = :codeGuichet", Siguic.class);

		query.setParameter("codeBanque", codeBanque);
		query.setParameter("codeGuichet", codeGuichet);
		List<Siguic> lfp = query.getResultList();

		for (Siguic fp : lfp)
			res = fp;

		return res;
	}
}
