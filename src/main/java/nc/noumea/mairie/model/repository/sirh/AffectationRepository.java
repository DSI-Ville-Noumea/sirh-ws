package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AffectationRepository implements IAffectationRepository {

	private Logger logger = LoggerFactory.getLogger(AffectationRepository.class);

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public Affectation getAffectationActiveByAgent(int idAgent) {

		TypedQuery<Affectation> q = sirhEntityManager.createNamedQuery("getAffectationActiveByAgentPourCalculEAE", Affectation.class);
		q.setParameter("idAgent", idAgent);
		q.setParameter("today", new Date());
		q.setMaxResults(1);

		List<Affectation> result = q.getResultList();

		if (result.size() != 1) {
			logger.warn("Une erreur s'est produite lors de la recherche d'une affectation pour l'agent {}. Le nombre de résultat est {} affectations au lieu de 1.", idAgent, result.size());
			return null;
		}

		return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Affectation> getListeAffectationsAgentAvecService(Integer idAgent, Integer idServiceADS) {

		StringBuilder sb = new StringBuilder();

		sb.append("select aff from Affectation aff ");
		sb.append(" inner join aff.fichePoste fp ");
		sb.append(" WHERE aff.agent.idAgent = :idAgent and fp.idServiceADS = :idServiceADS order by aff.dateDebutAff desc ");

		Query query = sirhEntityManager.createQuery(sb.toString(), Affectation.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("idServiceADS", idServiceADS);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Affectation> getListeAffectationsAgentAvecFP(Integer idAgent, Integer idFichePoste) {

		StringBuilder sb = new StringBuilder();

		sb.append("select aff from Affectation aff ");
		sb.append(" WHERE aff.agent.idAgent = :idAgent and aff.fichePoste.idFichePoste = :idFichePoste ");

		Query query = sirhEntityManager.createQuery(sb.toString(), Affectation.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("idFichePoste", idFichePoste);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Affectation> getListeAffectationsAgentByPeriode(Integer idAgent, Date dateDebut, Date dateFin) {

		StringBuilder sb = new StringBuilder();

		sb.append("select a from Affectation a " + "where a.agent.idAgent = :idAgent ");
		if (null != dateFin) {
			sb.append("and a.dateDebutAff <= :dateFin ");
		}
		sb.append("and (a.dateFinAff is null or a.dateFinAff >= :dateDebut) ");

		Query query = sirhEntityManager.createQuery(sb.toString(), Affectation.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateDebut", dateDebut);
		if (null != dateFin) {
			query.setParameter("dateFin", dateFin);
		}

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Affectation> getListeAffectationsAgentOrderByDateDesc(Integer idAgent) {

		StringBuilder sb = new StringBuilder();

		sb.append("select a from Affectation a ");
		sb.append("where a.agent.idAgent = :idAgent ");
		sb.append("order by a.dateDebutAff desc ");

		Query query = sirhEntityManager.createQuery(sb.toString(), Affectation.class);
		query.setParameter("idAgent", idAgent);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Affectation> getAffectationByIdFichePoste(Integer idFichePoste) {

		StringBuilder sb = new StringBuilder();

		sb.append("select aff from Affectation aff ");
		sb.append(" WHERE aff.fichePoste.idFichePoste = :idFichePoste ");

		Query query = sirhEntityManager.createQuery(sb.toString(), Affectation.class);
		query.setParameter("idFichePoste", idFichePoste);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Affectation> getListeAffectationsAgentOrderByDateAsc(Integer idAgent) {

		StringBuilder sb = new StringBuilder();

		sb.append("select a from Affectation a ");
		sb.append("where a.agent.idAgent = :idAgent ");
		sb.append("order by a.dateDebutAff asc ");

		Query query = sirhEntityManager.createQuery(sb.toString(), Affectation.class);
		query.setParameter("idAgent", idAgent);

		return query.getResultList();
	}

	@Override
	public Affectation chercherAffectationAgentAvecDateDebut(Integer idAgent, Date date) {

		StringBuilder sb = new StringBuilder();

		sb.append("select a from Affectation a ");
		sb.append("where a.agent.idAgent = :idAgent ");
		sb.append("and a.dateDebutAff = :date ");

		Query query = sirhEntityManager.createQuery(sb.toString(), Affectation.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("date", date);
		Affectation res = null;
		try {
			res = (Affectation) query.getSingleResult();
		} catch (Exception e) {
		}

		return res;
	}

	@Override
	public Agent getAffectationAgent(Integer idAgent, Date dateDonnee) {

		StringBuilder sb = new StringBuilder();

		sb.append("select aff.agent from Affectation aff ");
		sb.append("where aff.agent.idAgent =:idAgent ");
		sb.append("and aff.dateDebutAff<=:dateJour  and (aff.dateFinAff is null or aff.dateFinAff >= :dateJour) ");

		Query query = sirhEntityManager.createQuery(sb.toString(), Agent.class);
		query.setParameter("idAgent", idAgent);
		if (null != dateDonnee) {
			query.setParameter("dateJour", dateDonnee);
		} else {
			query.setParameter("dateJour", new Date());
		}
		Agent res = null;
		try {
			res = (Agent) query.getSingleResult();
		} catch (Exception e) {
		}

		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Affectation> getListAffectationActiveByIdFichePoste(Integer idFichePoste) {

		StringBuilder sb = new StringBuilder();
		sb.append("select aff from Affectation aff ");
		sb.append("where aff.dateDebutAff<=:dateJour  and (aff.dateFinAff is null or aff.dateFinAff >= :dateJour) ");
		sb.append("and aff.fichePoste.idFichePoste = :idFichePoste ");

		Query q = sirhEntityManager.createQuery(sb.toString(), Affectation.class);
		q.setParameter("dateJour", new Date());
		q.setParameter("idFichePoste", idFichePoste);

		return q.getResultList();
	}

}
