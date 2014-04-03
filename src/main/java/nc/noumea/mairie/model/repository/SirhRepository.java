package nc.noumea.mairie.model.repository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.AvancementDetache;
import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.Budget;
import nc.noumea.mairie.model.bean.DiplomeAgent;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.FormationAgent;
import nc.noumea.mairie.model.bean.NiveauEtude;
import nc.noumea.mairie.model.bean.PMotifAvct;
import nc.noumea.mairie.model.bean.Silieu;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.TitrePoste;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SirhRepository implements ISirhRepository {

	private Logger logger = LoggerFactory.getLogger(SirhRepository.class);

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public Agent getAgentWithListNomatr(Integer noMatr) {

		StringBuilder sb = new StringBuilder();

		sb.append("select ag.* from Agent ag where ag.nomatr = :noMatr ");

		Query query = sirhEntityManager.createNativeQuery(sb.toString(), Agent.class);
		query.setParameter("noMatr", noMatr);

		@SuppressWarnings("unchecked")
		List<Agent> list = query.getResultList();

		Agent agent = null;
		if (!list.isEmpty()) {
			agent = (Agent) list.get(0);
		}

		return agent;
	}

	@Override
	public Agent getAgentEligibleEAESansAffectes(Integer noMatr) {

		StringBuilder sb = new StringBuilder();

		sb.append("select a.* from Agent a ");
		sb.append(" inner join Affectation aff on a.id_agent = aff.id_agent ");
		sb.append(" inner join Fiche_Poste fp on aff.ID_FICHE_POSTE = fp.ID_FICHE_POSTE ");
		sb.append(" where aff.date_Debut_Aff <= :dateJourSIRH ");
		sb.append(" and (aff.date_Fin_Aff is null or aff.date_Fin_Aff = '0001-01-01' or aff.date_Fin_Aff >= :dateJourSIRH ) ");
		sb.append(" and a.nomatr = :noMatr ");

		Query query = sirhEntityManager.createNativeQuery(sb.toString(), Agent.class);

		query.setParameter("dateJourSIRH", new Date());
		query.setParameter("noMatr", noMatr);

		@SuppressWarnings("unchecked")
		List<Agent> list = query.getResultList();

		Agent agent = null;
		if (!list.isEmpty()) {
			agent = (Agent) list.get(0);
		}

		return agent;
	}

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
	public AvancementFonctionnaire getAvancement(Integer idAgent, Integer anneeAvancement, boolean isFonctionnaire) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT avct FROM AvancementFonctionnaire avct ");
		sb.append(" WHERE avct.anneeAvancement = :anneeAvancement ");
		sb.append(" AND avct.agent.idAgent = :idAgent ");
		if (isFonctionnaire) {
			sb.append(" AND (avct.codeCategporie = 1 or avct.codeCategporie = 2 or avct.codeCategporie = 18 or avct.codeCategporie = 20) ");
		}

		TypedQuery<AvancementFonctionnaire> qA = sirhEntityManager.createQuery(sb.toString(),
				AvancementFonctionnaire.class);
		qA.setParameter("anneeAvancement", anneeAvancement);
		qA.setParameter("idAgent", idAgent);

		List<AvancementFonctionnaire> result = qA.getResultList();
		if (null != result && !result.isEmpty())
			return result.get(0);

		return null;
	}

	@Override
	public AvancementDetache getAvancementDetache(Integer idAgent, Integer anneeAvancement) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT avct, grade FROM AvancementDetache avct ");
		sb.append(" left outer join avct.grade grade ");
		sb.append(" WHERE avct.anneeAvancement = :anneeAvancement ");
		sb.append(" AND avct.agent.idAgent = :idAgent ");

		Query qA = sirhEntityManager.createQuery(sb.toString());
			qA.setParameter("anneeAvancement", anneeAvancement);
			qA.setParameter("idAgent", idAgent);
		
		List<Object[]> result = qA.getResultList();
		
		if(null == result || result.isEmpty()) {
			return null;
		}

		AvancementDetache avct = (AvancementDetache) result.get(0)[0];
		Spgradn grade = (Spgradn) result.get(0)[1];
		
		avct.setGrade(grade);

		return avct;
	}

	@Override
	public Affectation getAffectationActiveByAgent(int idAgent) {

		TypedQuery<Affectation> q = sirhEntityManager.createNamedQuery("getAffectationActiveByAgentPourCalculEAE", Affectation.class);
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
	
	@Override
	public Agent getAgent(Integer idAgent) {

		StringBuilder sb = new StringBuilder();

		sb.append("select a from Agent a ");
		sb.append(" WHERE a.idAgent = :idAgent ");

		Query query = sirhEntityManager.createQuery(sb.toString(), Agent.class);
			query.setParameter("idAgent", idAgent);

		@SuppressWarnings("unchecked")
		List<Agent> result = query.getResultList();
		if (null == result || result.size() == 0)
			return null;

		return result.get(0);
	}
	
	@Override
	public PMotifAvct getMotifAvct(Integer idMotifAvct) {
		
		StringBuilder sb = new StringBuilder();

		sb.append("select a from PMotifAvct a ");
		sb.append(" WHERE a.idMotifAvct = :idMotifAvct ");

		Query query = sirhEntityManager.createQuery(sb.toString(), PMotifAvct.class);
			query.setParameter("idMotifAvct", idMotifAvct);

		@SuppressWarnings("unchecked")
		List<PMotifAvct> result = query.getResultList();
		
		if (null == result || result.size() == 0)
			return null;

		return result.get(0);
	}
}
