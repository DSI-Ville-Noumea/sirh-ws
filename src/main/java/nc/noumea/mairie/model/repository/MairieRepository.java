package nc.noumea.mairie.model.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spmtsr;

import org.springframework.stereotype.Repository;

@Repository
public class MairieRepository implements IMairieRepository {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public List<Spmtsr> getListSpmtsr(Integer noMatricule) {

		TypedQuery<Spmtsr> q = sirhEntityManager.createQuery(
				"select a from Spmtsr a where a.id.nomatr = :noMatricule order by a.id.datdeb ", Spmtsr.class);

		q.setParameter("noMatricule", noMatricule);

		return q.getResultList();
	}

	@Override
	public List<Spbhor> getListSpbhor() {

		return sirhEntityManager.createNamedQuery("Spbhor.whereCdTauxNotZero", Spbhor.class).getResultList();
	}

	@Override
	public Spbhor getSpbhorById(Integer idSpbhor) {

		return sirhEntityManager.find(Spbhor.class, idSpbhor);
	}
}
