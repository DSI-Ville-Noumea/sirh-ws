package nc.noumea.mairie.model.repository.sirh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Droits;

import org.springframework.stereotype.Repository;

@Repository
public class DroitsRepository implements IDroitsRepository {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public List<Droits> getDroitsByElementAndAgent(Integer idElement, String login) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select d from Droits d ");
		sb.append("inner join fetch d.droitsGroupe dg ");
		sb.append("inner join fetch dg.utilisateurs u ");
		sb.append("inner join fetch d.element e ");
		sb.append("where e.idElement = :idElement ");
		sb.append("and u.login = :login ");

		TypedQuery<Droits> query = sirhEntityManager.createQuery(sb.toString(), Droits.class);
		query.setParameter("idElement", idElement);
		query.setParameter("login", login);

		return query.getResultList();
	}
}
