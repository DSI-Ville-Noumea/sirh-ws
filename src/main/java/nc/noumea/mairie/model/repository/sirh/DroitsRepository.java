package nc.noumea.mairie.model.repository.sirh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Droits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DroitsRepository implements IDroitsRepository {

	private Logger logger = LoggerFactory.getLogger(DroitsRepository.class);
	
	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public List<Droits> getDroitsByElementAndAgent(Integer idElement, String login) {

		logger.debug("getDroitsByElementAndAgent with parameter idElement = {}, login = {}",
				idElement, login);
		
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

	@Override
	public List<Droits> getDroitsByGroupeAndAgent(Integer idGroupe, String login) {

		logger.debug("getDroitsByGroupeAndAgent with parameter idGroupe = {}, login = {}",
				idGroupe, login);
		
		StringBuilder sb = new StringBuilder();
		sb.append("select d from Droits d ");
		sb.append("inner join fetch d.droitsGroupe dg ");
		sb.append("inner join fetch dg.utilisateurs u ");
		sb.append("where dg.idGroupe = :idGroupe ");
		sb.append("and u.login = :login ");

		TypedQuery<Droits> query = sirhEntityManager.createQuery(sb.toString(), Droits.class);
		query.setParameter("idGroupe", idGroupe);
		query.setParameter("login", login);

		return query.getResultList();
	}
}
