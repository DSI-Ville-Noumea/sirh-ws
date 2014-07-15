package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.FormationAgent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SirhRepository implements ISirhRepository {

	private Logger logger = LoggerFactory.getLogger(SirhRepository.class);

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public AutreAdministrationAgent chercherAutreAdministrationAgentAncienne(Integer idAgent, boolean isFonctionnaire) {

		StringBuilder sb = new StringBuilder();
		sb.append("select a from AutreAdministrationAgent a where a.autreAdministrationAgentPK.idAgent = :idAgent ");
		if (isFonctionnaire) {
			sb.append(" and a.fonctionnaire = 1 ");
		}
		sb.append(" and a.autreAdministrationAgentPK.dateEntree = ( ");
		sb.append(" select min(aa.autreAdministrationAgentPK.dateEntree) from AutreAdministrationAgent aa where a.autreAdministrationAgentPK.idAgent = aa.autreAdministrationAgentPK.idAgent) ");

		TypedQuery<AutreAdministrationAgent> q = sirhEntityManager.createQuery(sb.toString(),
				AutreAdministrationAgent.class);

		q.setParameter("idAgent", idAgent);

		List<AutreAdministrationAgent> result = q.getResultList();

		if (null != result && !result.isEmpty())
			return q.getSingleResult();

		return null;
	}

	@Override
	public List<AutreAdministrationAgent> getListeAutreAdministrationAgent(Integer idAgent) {

		StringBuilder sb = new StringBuilder();
		sb.append("select a from AutreAdministrationAgent a where a.autreAdministrationAgentPK.idAgent = :idAgent order by a.autreAdministrationAgentPK.dateEntree ");

		TypedQuery<AutreAdministrationAgent> q = sirhEntityManager.createQuery(sb.toString(),
				AutreAdministrationAgent.class);
		q.setParameter("idAgent", idAgent);

		return q.getResultList();
	}

	@Override
	public List<DiplomeAgent> getListDiplomeByAgent(Integer idAgent) {

		TypedQuery<DiplomeAgent> q = sirhEntityManager
				.createQuery("select a from DiplomeAgent a where a.idAgent = :idAgent order by a.dateObtention ",
						DiplomeAgent.class);

		q.setParameter("idAgent", idAgent);

		return q.getResultList();
	}

	@Override
	public List<FormationAgent> getListFormationAgentByAnnee(Integer idAgent, Integer anneeFormation) {

		TypedQuery<FormationAgent> q = sirhEntityManager
				.createQuery(
						"select a from FormationAgent a where a.idAgent = :idAgent and a.anneeFormation = :anneeFormation order by a.idFormation desc ",
						FormationAgent.class);

		q.setParameter("idAgent", idAgent);
		q.setParameter("anneeFormation", anneeFormation);

		return q.getResultList();
	}

	@Override
	public Affectation getAffectationActiveByAgent(int idAgent) {

		TypedQuery<Affectation> q = sirhEntityManager.createNamedQuery("getAffectationActiveByAgentPourCalculEAE",
				Affectation.class);
		q.setParameter("idAgent", idAgent);
		q.setParameter("today", new Date());
		q.setMaxResults(1);

		List<Affectation> result = q.getResultList();

		if (result.size() != 1) {
			logger.warn(
					"Une erreur s'est produite lors de la recherche d'une affectation pour l'agent {}. Le nombre de résultat est {} affectations au lieu de 1.",
					idAgent, result.size());
			return null;
		}

		return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Affectation> getListeAffectationsAgentAvecService(Integer idAgent, String idService) {

		StringBuilder sb = new StringBuilder();

		sb.append("select aff from Affectation aff ");
		sb.append(" inner join aff.fichePoste fp ");
		sb.append(" WHERE aff.agent.idAgent = :idAgent and fp.service.servi = :idService order by aff.dateDebutAff desc ");

		Query query = sirhEntityManager.createQuery(sb.toString(), Affectation.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("idService", idService);

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
}
