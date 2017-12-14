package nc.noumea.mairie.mdf.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import nc.noumea.mairie.mdf.domain.StatusJob;
import nc.noumea.mairie.mdf.repository.IStatusJobRepository;

@Repository
public class StatusJobRepository implements IStatusJobRepository {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager entityManager;

	@Override
	public List<StatusJob> getAllStatusByDateAndEntite(String date, String entite) {
		
		TypedQuery<StatusJob> q = entityManager.createQuery("select s from StatusJob s where s.entite = :entite and s.moisTraitement = :date", StatusJob.class);

		q.setParameter("entite", entite);
		q.setParameter("date", date);

		return q.getResultList();
	}
}
