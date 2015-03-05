package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.sirh.AvancementDetache;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.MotifAvct;

import org.springframework.stereotype.Repository;

@Repository
public class AvancementRepository implements IAvancementRepository {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public AvancementFonctionnaire getAvancement(Integer idAgent,
			Integer anneeAvancement, boolean isFonctionnaire) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT avct FROM AvancementFonctionnaire avct ");
		sb.append(" WHERE avct.anneeAvancement = :anneeAvancement ");
		sb.append(" AND avct.agent.idAgent = :idAgent ");
		if (isFonctionnaire) {
			sb.append(" AND (avct.codeCategporie = 1 or avct.codeCategporie = 2 or avct.codeCategporie = 18 or avct.codeCategporie = 20) ");
		}

		TypedQuery<AvancementFonctionnaire> qA = sirhEntityManager.createQuery(
				sb.toString(), AvancementFonctionnaire.class);
		qA.setParameter("anneeAvancement", anneeAvancement);
		qA.setParameter("idAgent", idAgent);

		List<AvancementFonctionnaire> result = qA.getResultList();
		if (null != result && !result.isEmpty())
			return result.get(0);

		return null;
	}

	@Override
	public AvancementDetache getAvancementDetache(Integer idAgent,
			Integer anneeAvancement) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT avct, grade FROM AvancementDetache avct ");
		sb.append(" left outer join avct.grade grade ");
		sb.append(" WHERE avct.anneeAvancement = :anneeAvancement ");
		sb.append(" AND avct.agent.idAgent = :idAgent ");

		Query qA = sirhEntityManager.createQuery(sb.toString());
		qA.setParameter("anneeAvancement", anneeAvancement);
		qA.setParameter("idAgent", idAgent);

		@SuppressWarnings("unchecked")
		List<Object[]> result = qA.getResultList();

		if (null == result || result.isEmpty()) {
			return null;
		}

		AvancementDetache avct = (AvancementDetache) result.get(0)[0];
		Spgradn grade = (Spgradn) result.get(0)[1];

		avct.setGrade(grade);

		return avct;
	}

	@Override
	public MotifAvct getMotifAvct(Integer idMotifAvct) {

		StringBuilder sb = new StringBuilder();

		sb.append("select a from MotifAvct a ");
		sb.append(" WHERE a.idMotifAvct = :idMotifAvct ");

		Query query = sirhEntityManager.createQuery(sb.toString(),
				MotifAvct.class);
		query.setParameter("idMotifAvct", idMotifAvct);

		@SuppressWarnings("unchecked")
		List<MotifAvct> result = query.getResultList();

		if (null == result || result.size() == 0)
			return null;

		return result.get(0);
	}

	@Override
	public Date getDateAvancementsMinimaleAncienne(Integer idAgent) {

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT avct.dateAvctMini FROM AvancementFonctionnaire avct ");
		sb.append("where avct.agent.idAgent = :idAgent ");
		sb.append("and avct.avisCapEmployeur.idAvisCap = 1 ");
		sb.append("and avct.anneeAvancement = (select max(avct2.anneeAvancement) FROM AvancementFonctionnaire avct2 where avct2.avisCapEmployeur.idAvisCap = 1 and avct2.agent.idAgent = :idAgent ) ");

		TypedQuery<Date> qA = sirhEntityManager.createQuery(sb.toString(),
				Date.class);
		qA.setParameter("idAgent", idAgent);
		try {
			return qA.getSingleResult();
		} catch (Exception e) {
			// rien trouvé
			return null;
		}
	}
}
