package nc.noumea.mairie.model.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AgentRecherche;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService implements IAgentService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private ISiservService siservSrv;

	@Override
	public Agent getAgent(Integer id) {
		Agent res = null;

		TypedQuery<Agent> query = sirhEntityManager.createQuery("select ag from Agent ag where ag.idAgent = :idAgent", Agent.class);

		query.setParameter("idAgent", id);
		List<Agent> lfp = query.getResultList();

		for (Agent fp : lfp)
			res = fp;

		return res;
	}

	@Override
	public List<Agent> listAgentServiceSansAgent(String servi, Integer idAgent) {
		TypedQuery<Agent> query = sirhEntityManager.createQuery(
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
		TypedQuery<Agent> query = sirhEntityManager.createQuery(
				"select ag from Agent ag , Affectation aff, FichePoste fp where aff.agent.idAgent = ag.idAgent and fp.idFichePoste = aff.fichePoste.idFichePoste "
						+ " and fp.service.servi in (:listeCodeService)  and aff.agent.idAgent != :idAgent and aff.agent.idAgent != :idAgentResp "
						+ " and aff.dateDebutAff<=:dateJour and "
						+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour) order by ag.nomUsage ", Agent.class);
		query.setParameter("listeCodeService", listeCodeService);
		query.setParameter("idAgent", idAgent);
		query.setParameter("idAgentResp", idAgentResponsable);
		query.setParameter("dateJour", new Date());
		List<Agent> lag = query.getResultList();

		return lag;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Agent getSuperieurHierarchiqueAgent(Integer idAgent) {
		String sql = "select a.* from Affectation aff, Agent a "
				+ "where  aff.id_Agent = a.id_Agent and aff.id_Fiche_Poste = "
				+ "( select fpAgent.id_responsable from Fiche_Poste fpAgent, Affectation aff "
				+ "where aff.id_Fiche_Poste = fpAgent.id_Fiche_Poste and aff.id_Agent=:idAgent and aff.date_Debut_Aff<=:dateJour and (aff.date_Fin_Aff is null or aff.date_Fin_Aff='01/01/0001' or aff.date_Fin_Aff>=:dateJour)) "
				+ "and aff.date_Debut_Aff<=:dateJour and (aff.date_Fin_Aff is null or aff.date_Fin_Aff='01/01/0001' or aff.date_Fin_Aff>=:dateJour)";
		Query query = sirhEntityManager.createNativeQuery(sql, Agent.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", new Date());
		List<Agent> lag = query.getResultList();

		Agent res = null;
		if (!lag.isEmpty()) {
			res = (Agent) lag.get(0);
		}

		return res;
	}

	@Override
	public List<AgentWithServiceDto> listAgentsOfServices(List<String> servis, Date date, List<Integer> idAgents) {

		List<AgentWithServiceDto> result = new ArrayList<AgentWithServiceDto>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT aff FROM Affectation aff JOIN FETCH aff.agent AS ag JOIN FETCH aff.fichePoste AS fp ");
		sb.append("WHERE aff.agent.idAgent = ag.idAgent and fp.idFichePoste = aff.fichePoste.idFichePoste ");
		// if we're restraining search with service codes
		if (servis != null && servis.size() != 0)
			sb.append("and fp.service.servi in (:listeCodeService) ");
		// if we're restraining search with idAgents...
		if (idAgents != null && idAgents.size() != 0)
			sb.append("and aff.agent.idAgent in (:idAgents) ");
		sb.append("and aff.dateDebutAff<=:dateJour and ");
		sb.append("(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)");

		TypedQuery<Affectation> query = sirhEntityManager.createQuery(sb.toString(), Affectation.class);
		query.setParameter("dateJour", date);

		// if we're restraining search with service codes
		if (servis != null && servis.size() != 0)
			query.setParameter("listeCodeService", servis);

		// if we're restraining search with idAgent...
		if (idAgents != null && idAgents.size() != 0)
			query.setParameter("idAgents", idAgents);

		for (Affectation aff : query.getResultList()) {
			AgentWithServiceDto agDto = new AgentWithServiceDto(aff.getAgent());
			agDto.setService(aff.getFichePoste().getService().getLiServ().trim());
			agDto.setCodeService(aff.getFichePoste().getService().getServi());
			result.add(agDto);
		}

		return result;
	}

	@Override
	public Agent findAgentWithName(Integer idAgent, String nom) {
		Agent res = null;

		TypedQuery<Agent> query = sirhEntityManager.createQuery("select ag from Agent ag where ag.idAgent = :idAgent and ag.nomUsage like :nom",
				Agent.class);

		query.setParameter("idAgent", idAgent);
		query.setParameter("nom", nom + "%");
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			// agent nom trouvé avec ce nom
		}
		return res;
	}

	@Override
	public List<AgentWithServiceDto> listAgentsEnActivite(String nom, String codeService) {

		List<AgentWithServiceDto> result = new ArrayList<AgentWithServiceDto>();

		StringBuilder sb = new StringBuilder();

		sb.append("select ag from AgentRecherche ag , Affectation aff, FichePoste fp, Spadmn pa ");
		sb.append("where  fp.idFichePoste = aff.fichePoste.idFichePoste ");
		sb.append("and ag.nomatr=pa.id.nomatr ");
		sb.append("and ag.idAgent=aff.agentrecherche.idAgent ");
		sb.append("and pa.cdpadm in ('01', '02', '03', '04', '23', '24', '60', '61', '62', '63', '64', '65', '66') ");
		sb.append("and pa.id.datdeb <= :dateJourMairie and (pa.datfin = 0 or pa.datfin >= :dateJourMairie) ");
		sb.append("and aff.dateDebutAff<=:dateJour and (aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour) ");

		// if we're restraining search with service codes
		if (!codeService.equals(""))
			sb.append("and fp.service.servi= :codeService ");
		// if we're restraining search with nomAgent...
		if (!nom.equals(""))
			sb.append("and ag.nomUsage like :nom ");

		TypedQuery<AgentRecherche> query = sirhEntityManager.createQuery(sb.toString(), AgentRecherche.class);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		query.setParameter("dateJourMairie", Integer.valueOf(sdf.format(new Date())));
		query.setParameter("dateJour", new Date());

		// if we're restraining search with service codes
		if (!codeService.equals(""))
			query.setParameter("codeService", codeService);

		// if we're restraining search with nomAgent...
		if (!nom.equals(""))
			query.setParameter("nom", nom + "%");

		List<AgentRecherche> list = query.getResultList();
		for (AgentRecherche ag : list) {
			AgentWithServiceDto agDto = new AgentWithServiceDto(ag);
			System.out.println("ici" + ag.getIdAgent());
			Siserv service = siservSrv.getServiceAgent(ag.getIdAgent());

			// on construit le dto de l'agent
			agDto.setCodeService(service.getServi().trim());
			agDto.setService(service.getLiServ().trim());
			result.add(agDto);
		}

		return result;
	}
}
