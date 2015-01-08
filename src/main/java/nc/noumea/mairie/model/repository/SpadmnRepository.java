package nc.noumea.mairie.model.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spadmn;

import org.springframework.stereotype.Repository;

@Repository
public class SpadmnRepository implements ISpadmnRepository {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public Spadmn chercherPositionAdmAgentAncienne(Integer noMatr) {

		TypedQuery<Spadmn> q = sirhEntityManager
				.createQuery(
						"select a from Spadmn a where a.id.nomatr = :noMatr and a.id.datdeb = (select min(pa.id.datdeb) from Spadmn pa where pa.id.nomatr = a.id.nomatr )",
						Spadmn.class);
		q.setParameter("noMatr", noMatr);

		List<Spadmn> result = q.getResultList();
		if (null != result && !result.isEmpty())
			return result.get(0);

		return null;
	}

	@Override
	public Spadmn chercherPositionAdmAgentEnCours(Integer noMatr) {

		TypedQuery<Spadmn> q = sirhEntityManager
				.createQuery(
						"select a from Spadmn a where a.id.nomatr = :noMatr and ( (:dateJourMairie between a.id.datdeb and a.datfin) or (a.id.datdeb <= :dateJourMairie and a.datfin = 0 ))",
						Spadmn.class);
		q.setParameter("noMatr", noMatr);

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		q.setParameter("dateJourMairie", Integer.valueOf(sdfMairie.format(new Date())));

		List<Spadmn> result = q.getResultList();
		if (null != result && !result.isEmpty())
			return result.get(0);

		return null;
	}
	
	@Override
	public List<Spadmn> chercherListPositionAdmAgentSurPeriodeDonnee(Integer noMatr, Date dateDebut, Date dateFin) {

		TypedQuery<Spadmn> q = sirhEntityManager
				.createQuery(
						"select a from Spadmn a where a.id.nomatr = :noMatr and "
						+ " ( (a.id.datdeb between :datdeb and :datfin) or (a.datfin between :datdeb and :datfin)) ",
						Spadmn.class);
		q.setParameter("noMatr", noMatr);

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		q.setParameter("datdeb", Integer.valueOf(sdfMairie.format(dateDebut)));
		q.setParameter("datfin", Integer.valueOf(sdfMairie.format(dateFin)));
		
		return q.getResultList();
	}
	
	@Override
	public List<Spadmn> chercherListPositionAdmAgentAncienne(Integer noMatr, Integer dateLimite) {
		
		TypedQuery<Spadmn> q = sirhEntityManager
				.createQuery(
						"select a from Spadmn a where a.id.nomatr = :noMatr and a.id.datdeb < :dateLimite order by a.id.datdeb desc ",
						Spadmn.class);
		q.setParameter("noMatr", noMatr);
		q.setParameter("dateLimite", dateLimite);
		
		return q.getResultList();
	}
}
