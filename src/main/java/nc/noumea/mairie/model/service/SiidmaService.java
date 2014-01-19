package nc.noumea.mairie.model.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Siidma;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SiidmaService implements ISiidmaService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	private Logger logger = LoggerFactory.getLogger(SiidmaService.class);

	@Override
	public Siidma chercherSiidmaByIdIndi(Integer idIndi) {
		Siidma res = null;
		TypedQuery<Siidma> query = sirhEntityManager.createQuery(
				"select siid from Siidma siid where  siid.id.idIndi=:idIndi ", Siidma.class);
		query.setParameter("idIndi", idIndi);
		List<Siidma> lsiidma = query.getResultList();

		for (Siidma siid : lsiidma)
			res = siid;
		return res;
	}
}
