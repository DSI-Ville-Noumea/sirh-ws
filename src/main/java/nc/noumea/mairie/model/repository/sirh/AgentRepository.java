package nc.noumea.mairie.model.repository.sirh;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.FichePoste;

import org.springframework.stereotype.Repository;

@Repository
public class AgentRepository implements IAgentRepository {

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
		sb.append(" and (aff.date_Fin_Aff is null or aff.date_Fin_Aff >= :dateJourSIRH ) ");
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
	public List<Agent> getListAgents(List<Integer> listIdsAgent) {

		TypedQuery<Agent> query = sirhEntityManager.createNamedQuery("getListAgents", Agent.class);

		query.setParameter("listIdsAgent", listIdsAgent);
		return query.getResultList();
	}

	@Override
	public List<Object[]> getListAgentsEnActivite(String nom, Integer idServiceADS) {

		StringBuilder sb = new StringBuilder();

		sb.append("select ag, fp from AgentRecherche ag , Affectation aff, FichePoste fp, Spadmn pa ");
		sb.append("where  fp.idFichePoste = aff.fichePoste.idFichePoste ");
		sb.append("and ag.nomatr=pa.id.nomatr ");
		sb.append("and ag.idAgent=aff.agentrecherche.idAgent ");
		sb.append("and pa.positionAdministrative.cdpAdm in ('01', '02', '03', '04', '23', '24', '60', '61', '62', '63', '64', '65', '66') ");
		sb.append("and pa.id.datdeb <= :dateJourMairie and (pa.datfin = 0 or pa.datfin >= :dateJourMairie) ");
		sb.append("and aff.dateDebutAff<=:dateJour and (aff.dateFinAff is null or aff.dateFinAff>=:dateJour) ");

		// if we're restraining search with service codes
		if (idServiceADS != null)
			sb.append("and fp.idServiceADS= :idServiceADS ");
		// if we're restraining search with nomAgent...
		if (null != nom && !nom.equals(""))
			sb.append("and ag.nomUsage like :nom ");

		Query query = sirhEntityManager.createQuery(sb.toString());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		query.setParameter("dateJourMairie", Integer.valueOf(sdf.format(new Date())));
		query.setParameter("dateJour", new Date());

		// if we're restraining search with service codes
		if (idServiceADS != null)
			query.setParameter("idServiceADS", idServiceADS);

		// if we're restraining search with nomAgent...
		if (null != nom && !nom.equals(""))
			query.setParameter("nom", nom + "%");

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();

		return list;
	}

	@Override
	public List<Affectation> getListAgentsByServicesAndListAgentsAndDate(List<Integer> idServiceADS, Date date, List<Integer> idAgents) {

		StringBuilder sb = new StringBuilder();
		
		sb.append("select aff.id_affectation , ag.nom_usage, ag.prenom_usage, ag.id_agent, ag.civilite, fp.id_service_ads from affectation aff ");
		sb.append("inner join agent ag on aff.id_agent=ag.id_agent ");
		sb.append("inner join fiche_poste fp on aff.id_fiche_poste=fp.id_fiche_poste ");
		sb.append("where aff.date_debut_aff <= :dateJour and (aff.date_fin_aff is null or aff.date_fin_aff >= :dateJour) ");
		// if we're restraining search with idAgents...
		if (idAgents != null && idAgents.size() != 0)
			sb.append("and aff.id_agent in (:idAgents) ");
		// if we're restraining search with service codes
		if (idServiceADS != null && idServiceADS.size() != 0)
			sb.append("and fp.id_service_ads in (:idServiceADS) ");
		
		Query query = sirhEntityManager.createNativeQuery(sb.toString());
		query.setParameter("dateJour", date);

		// if we're restraining search with service codes
		if (idServiceADS != null && idServiceADS.size() != 0)
			query.setParameter("idServiceADS", idServiceADS);

		// if we're restraining search with idAgent...
		if (idAgents != null && idAgents.size() != 0)
			query.setParameter("idAgents", idAgents);

		@SuppressWarnings("unchecked")
		List<Object[]> l = (List<Object[]>) query.getResultList();
		
		List<Affectation> result = new ArrayList<Affectation>();
		for (Object[] r : l) {
			Integer idAffectation = (Integer) r[0];
			String nomUsage = (String) r[1];
			String prenomUsage = (String) r[2];
			Integer idAgent = (Integer) r[3];
			String civilite = (String) r[4];
			Integer idServiceAds = (Integer) r[5];
			
			Agent agent = new Agent();
			agent.setIdAgent(idAgent);
			agent.setNomUsage(nomUsage);
			agent.setPrenomUsage(prenomUsage);
			agent.setTitre(civilite);
			
			Affectation affectation = new Affectation();
			affectation.setIdAffectation(idAffectation);
			affectation.setAgent(agent);
			
			FichePoste fichePoste = new FichePoste();
			fichePoste.setIdServiceADS(idServiceAds);
			
			affectation.setFichePoste(fichePoste);
			
			result.add(affectation);
		}
		
		return result;
	}
	
	@Override
	public List<Affectation> getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(List<Integer> idServiceADS, List<Integer> idAgents) {

		StringBuilder sb = new StringBuilder();
		
		sb.append("select aff.id_affectation , ag.nom_usage, ag.prenom_usage, ag.id_agent, ag.civilite, fp.id_service_ads from affectation aff ");
		sb.append("inner join agent ag on aff.id_agent=ag.id_agent ");
		sb.append("inner join fiche_poste fp on aff.id_fiche_poste=fp.id_fiche_poste ");
		sb.append("where aff.date_debut_aff = (select max(aff2.date_debut_aff) from affectation aff2 where aff2.id_agent=aff.id_agent group by aff2.id_agent ) ");
		// if we're restraining search with idAgents...
		if (idAgents != null && idAgents.size() != 0)
			sb.append("and aff.id_agent in (:idAgents) ");
		// if we're restraining search with service codes
		if (idServiceADS != null && idServiceADS.size() != 0)
			sb.append("and fp.id_service_ads in (:idServiceADS) ");
		
		Query query = sirhEntityManager.createNativeQuery(sb.toString());

		// if we're restraining search with service codes
		if (idServiceADS != null && idServiceADS.size() != 0)
			query.setParameter("idServiceADS", idServiceADS);

		// if we're restraining search with idAgent...
		if (idAgents != null && idAgents.size() != 0)
			query.setParameter("idAgents", idAgents);

		@SuppressWarnings("unchecked")
		List<Object[]> l = (List<Object[]>) query.getResultList();
		
		List<Affectation> result = new ArrayList<Affectation>();
		for (Object[] r : l) {
			Integer idAffectation = (Integer) r[0];
			String nomUsage = (String) r[1];
			String prenomUsage = (String) r[2];
			Integer idAgent = (Integer) r[3];
			String civilite = (String) r[4];
			Integer idServiceAds = (Integer) r[5];
			
			Agent agent = new Agent();
			agent.setIdAgent(idAgent);
			agent.setNomUsage(nomUsage);
			agent.setPrenomUsage(prenomUsage);
			agent.setTitre(civilite);
			
			Affectation affectation = new Affectation();
			affectation.setIdAffectation(idAffectation);
			affectation.setAgent(agent);
			
			FichePoste fichePoste = new FichePoste();
			fichePoste.setIdServiceADS(idServiceAds);
			
			affectation.setFichePoste(fichePoste);
			
			result.add(affectation);
		}
		
		return result;
	}
}
