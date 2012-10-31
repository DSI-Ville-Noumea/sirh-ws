package nc.noumea.mairie.model.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.Agent;

import org.springframework.stereotype.Service;

@Service
public class AgentService implements IAgentService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public Agent getAgent(Integer id) {
		Agent res = null;

		Query query = sirhEntityManager.createQuery("select ag from Agent ag where ag.idAgent = :idAgent", Agent.class);

		query.setParameter("idAgent", id);
		List<Agent> lfp = query.getResultList();

		for (Agent fp : lfp)
			res = fp;

		return res;
	}

	@Override
	public List<Agent> listAgentServiceSansAgent(String servi, Integer idAgent) {
		Query query = sirhEntityManager.createQuery(
				"select ag from Agent ag , Affectation aff, FichePoste fp where aff.agent.idAgent = ag.idAgent and fp.idFichePoste = aff.fichePoste.idFichePoste "
						+ " and fp.service.servi =:codeServ  and aff.agent.idAgent != :idAgent " + " and aff.dateDebutAff<=:dateJour and "
						+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)", Agent.class);
		query.setParameter("codeServ", servi);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", new Date());
		List<Agent> lag = query.getResultList();

		return lag;
	}

	@Override
	public List<Agent> listAgentPlusieursServiceSansAgentSansSuperieur(Integer idAgent, Integer idAgentResponsable, List<String> listeCodeService) {
		Query query = sirhEntityManager.createQuery(
				"select ag from Agent ag , Affectation aff, FichePoste fp where aff.agent.idAgent = ag.idAgent and fp.idFichePoste = aff.fichePoste.idFichePoste "
						+ " and fp.service.servi in (:listeCodeService)  and aff.agent.idAgent != :idAgent and aff.agent.idAgent != :idAgentResp "
						+ " and aff.dateDebutAff<=:dateJour and "
						+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)", Agent.class);
		query.setParameter("listeCodeService", listeCodeService);
		query.setParameter("idAgent", idAgent);
		query.setParameter("idAgentResp", idAgentResponsable);
		query.setParameter("dateJour", new Date());
		List<Agent> lag = query.getResultList();

		return lag;
	}

	@Override
	public Agent getSuperieurHierarchiqueAgent(Integer idAgent) {
		String sql = "select a.* from sirh.Affectation aff, sirh.Agent a "
				+ "where  aff.id_Agent = a.id_Agent and aff.id_Fiche_Poste = "
				+ "( select fpAgent.id_responsable from sirh.Fiche_Poste fpAgent, sirh.Affectation aff "
				+ "where aff.id_Fiche_Poste = fpAgent.id_Fiche_Poste and aff.id_Agent=:idAgent and aff.date_Debut_Aff<=:dateJour and (aff.date_Fin_Aff is null or aff.date_Fin_Aff='01/01/0001' or aff.date_Fin_Aff>=:dateJour)) "
				+ "and aff.date_Debut_Aff<=:dateJour and (aff.date_Fin_Aff is null or aff.date_Fin_Aff='01/01/0001' or aff.date_Fin_Aff>=:dateJour)";
		Query query = sirhEntityManager.createNativeQuery(sql,Agent.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", new Date());
		List<Agent> lag = query.getResultList();

		Agent res = null;
		if (!lag.isEmpty()) {
			res = (Agent) lag.get(0);
		}

		return res;
	}

}
