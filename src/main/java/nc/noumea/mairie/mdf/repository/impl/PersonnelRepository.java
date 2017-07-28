package nc.noumea.mairie.mdf.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import nc.noumea.mairie.mdf.domain.vdn.FdsMutDet;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutEnt;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutTot;
import nc.noumea.mairie.mdf.repository.IPersonnelRepository;

@Component
public class PersonnelRepository implements IPersonnelRepository {

	@PersistenceContext(unitName = "mdfVdnPersistenceUnit")
	private EntityManager sirhEntityManager;

	private Logger logger = LoggerFactory.getLogger(PersonnelRepository.class);

	@Override
	public FdsMutEnt getEntete() {
		TypedQuery<FdsMutEnt> q = sirhEntityManager.createQuery("select a from FdsMutEnt a", FdsMutEnt.class);

		FdsMutEnt res = null;
		try {
			res = q.getSingleResult();
		} catch (Exception e) {
			logger.error("Plusieurs enregistrements d'en-tête (FDSMUTENT) on été retournés. Le bordereau récapitulatif n'a donc pas pu être généré.");
		}

		return res;
	}

	@Override
	public FdsMutTot getTotal() {
		TypedQuery<FdsMutTot> q = sirhEntityManager.createQuery("select a from FdsMutTot a", FdsMutTot.class);

		FdsMutTot res = null;
		try {
			res = q.getSingleResult();
		} catch (Exception e) {
			logger.error("Plusieurs enregistrements de total (FDSMUTTOT) on été retournés. Le bordereau récapitulatif n'a donc pas pu être généré.");
		}

		return res;
	}

	@Override
	public List<FdsMutDet> getAllDetails() {
		TypedQuery<FdsMutDet> q = sirhEntityManager.createQuery("select a from FdsMutDet a", FdsMutDet.class);
		return q.getResultList();
	}
}
