package nc.noumea.mairie.service.sirh;

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
import nc.noumea.mairie.model.bean.sirh.AgentRecherche;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.repository.sirh.IAgentRepository;
import nc.noumea.mairie.service.ads.IAdsService;
import nc.noumea.mairie.web.dto.AgentGeneriqueDto;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService implements IAgentService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private IUtilisateurService utilisateurSrv;

	@Autowired
	private IADSWSConsumer adsWSConsumer;

	@Autowired
	private IAdsService adsService;

	@Autowired
	private IAgentRepository agentRepository;

	@Override
	public Agent getAgent(Integer id) {
		Agent res = null;

		TypedQuery<Agent> query = sirhEntityManager.createNamedQuery("getAgent", Agent.class);

		query.setParameter("idAgent", id);
		List<Agent> lfp = query.getResultList();

		for (Agent fp : lfp)
			res = fp;

		return res;
	}

	@Override
	public List<Agent> listAgentServiceSansAgent(Integer idServiceADS, Integer idAgent) {
		TypedQuery<Agent> query = sirhEntityManager
				.createQuery(
						"select ag from Agent ag , Affectation aff, FichePoste fp where aff.agent.idAgent = ag.idAgent and fp.idFichePoste = aff.fichePoste.idFichePoste "
								+ " and fp.idServiceADS =:idServiceADS  and aff.agent.idAgent != :idAgent " + " and aff.dateDebutAff<=:dateJour and "
								+ "(aff.dateFinAff is null or aff.dateFinAff>=:dateJour)", Agent.class);
		query.setParameter("idServiceADS", idServiceADS);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", new Date());
		List<Agent> lag = query.getResultList();

		return lag;
	}

	@Override
	public List<Agent> listAgentPlusieursServiceSansAgentSansSuperieur(Integer idAgent, Integer idAgentResponsable, List<Integer> listeIdServiceADS) {
		TypedQuery<Agent> query = sirhEntityManager.createQuery(
				"select ag from Agent ag , Affectation aff, FichePoste fp where aff.agent.idAgent = ag.idAgent and fp.idFichePoste = aff.fichePoste.idFichePoste "
						+ " and fp.idServiceADS in (:listeIdServiceADS)  and aff.agent.idAgent != :idAgent and aff.agent.idAgent != :idAgentResp " + " and aff.dateDebutAff<=:dateJour and "
						+ "(aff.dateFinAff is null or aff.dateFinAff>=:dateJour) order by ag.nomUsage ", Agent.class);
		query.setParameter("listeIdServiceADS", listeIdServiceADS);
		query.setParameter("idAgent", idAgent);
		query.setParameter("idAgentResp", idAgentResponsable);
		query.setParameter("dateJour", new Date());
		List<Agent> lag = query.getResultList();

		return lag;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Agent getSuperieurHierarchiqueAgent(Integer idAgent) {
		String sql = "select a.* from Affectation aff, Agent a " + "where  aff.id_Agent = a.id_Agent and aff.id_Fiche_Poste = "
				+ "( select fpAgent.id_responsable from Fiche_Poste fpAgent, Affectation aff "
				+ "where aff.id_Fiche_Poste = fpAgent.id_Fiche_Poste and aff.id_Agent=:idAgent and aff.date_Debut_Aff<=:dateJour and (aff.date_Fin_Aff is null or aff.date_Fin_Aff>=:dateJour)) "
				+ "and aff.date_Debut_Aff<=:dateJour and (aff.date_Fin_Aff is null or aff.date_Fin_Aff>=:dateJour)";
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
	public List<AgentWithServiceDto> listAgentsOfServices(List<Integer> idServiceADS, Date date, List<Integer> idAgents) {

		List<AgentWithServiceDto> result = new ArrayList<AgentWithServiceDto>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT aff FROM Affectation aff JOIN FETCH aff.agent AS ag JOIN FETCH aff.fichePoste AS fp ");
		sb.append("WHERE aff.agent.idAgent = ag.idAgent and fp.idFichePoste = aff.fichePoste.idFichePoste ");
		// if we're restraining search with service codes
		if (idServiceADS != null && idServiceADS.size() != 0)
			sb.append("and fp.idServiceADS in (:idServiceADS) ");
		// if we're restraining search with idAgents...
		if (idAgents != null && idAgents.size() != 0)
			sb.append("and aff.agent.idAgent in (:idAgents) ");
		sb.append("and aff.dateDebutAff<=:dateJour and ");
		sb.append("(aff.dateFinAff is null or aff.dateFinAff>=:dateJour)");

		TypedQuery<Affectation> query = sirhEntityManager.createQuery(sb.toString(), Affectation.class);
		query.setParameter("dateJour", date);

		// if we're restraining search with service codes
		if (idServiceADS != null && idServiceADS.size() != 0)
			query.setParameter("idServiceADS", idServiceADS);

		// if we're restraining search with idAgent...
		if (idAgents != null && idAgents.size() != 0)
			query.setParameter("idAgents", idAgents);

		// optimisation #18391
		// ce service est appele aussi par le kiosque RH et SHAREPOINT (a
		// verifier)
		// dans leur cas, on recherche qu un seul agent
		// cela ne sert donc a rien d appeler l arbre entier ADS
		EntiteDto root = null;
		if (null == idAgents || 1 < idAgents.size()) {
			root = adsWSConsumer.getWholeTree();
		}

		for (Affectation aff : query.getResultList()) {
			AgentWithServiceDto agDto = new AgentWithServiceDto(aff.getAgent());
			EntiteDto service = null;
			if (aff.getFichePoste() != null && aff.getFichePoste().getIdServiceADS() != null) {

				if (null != root) {
					service = adsService.getEntiteByIdEntiteOptimiseWithWholeTree(aff.getFichePoste().getIdServiceADS(), root);
				} else {
					service = adsWSConsumer.getEntiteByIdEntite(aff.getFichePoste().getIdServiceADS());
				}

				// adsWSConsumer.getEntiteByIdEntite(aff.getFichePoste().getIdServiceADS());
				EntiteDto direction = adsService.getAffichageDirectionWithoutCallADS(service);
				agDto.setDirection(direction == null ? "" : direction.getLabel());
				agDto.setSigleDirection(direction == null ? "" : direction.getSigle());
			}

			agDto.setService(service == null ? "" : service.getLabel().trim());
			agDto.setIdServiceADS(service == null ? null : service.getIdEntite());
			agDto.setSigleService(service == null ? "" : service.getSigle());
			result.add(agDto);
		}

		return result;
	}

	@Override
	public Agent findAgentWithName(Integer idAgent, String nom) {
		Agent res = null;

		TypedQuery<Agent> query = sirhEntityManager.createQuery("select ag from Agent ag where ag.idAgent = :idAgent and upper(ag.nomUsage) like :nom", Agent.class);

		query.setParameter("idAgent", idAgent);
		query.setParameter("nom", nom.toUpperCase() + "%");
		try {
			res = query.getSingleResult();
		} catch (Exception e) {
			// agent nom trouvé avec ce nom
		}
		return res;
	}

	@Override
	public List<AgentWithServiceDto> listAgentsEnActivite(String nom, Integer idServiceADS) {

		List<AgentWithServiceDto> result = new ArrayList<AgentWithServiceDto>();

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
		if (!nom.equals(""))
			sb.append("and ag.nomUsage like :nom ");

		Query query = sirhEntityManager.createQuery(sb.toString());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		query.setParameter("dateJourMairie", Integer.valueOf(sdf.format(new Date())));
		query.setParameter("dateJour", new Date());

		// if we're restraining search with service codes
		if (idServiceADS != null)
			query.setParameter("idServiceADS", idServiceADS);

		// if we're restraining search with nomAgent...
		if (!nom.equals(""))
			query.setParameter("nom", nom + "%");

		// optimisation #18176
		EntiteDto root = adsWSConsumer.getWholeTree();

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		for (Object[] res : list) {
			AgentWithServiceDto agDto = new AgentWithServiceDto((AgentRecherche) res[0]);
			FichePoste fp = (FichePoste) res[1];
			EntiteDto service = adsService.getEntiteByIdEntiteOptimiseWithWholeTree(fp.getIdServiceADS(), root);

			// on construit le dto de l'agent
			agDto.setIdServiceADS(service == null ? null : service.getIdEntite());
			agDto.setService(service == null ? null : service.getLabel().trim());
			result.add(agDto);
		}

		return result;
	}

	@Override
	public EntiteDto getServiceAgent(Integer idAgent, Date dateDonnee) {
		String hql = "select fp from FichePoste fp, Affectation aff " + "where aff.fichePoste.idFichePoste = fp.idFichePoste and  aff.agent.idAgent =:idAgent and aff.dateDebutAff<=:dateJour "
				+ "and (aff.dateFinAff is null or aff.dateFinAff>=:dateJour)";
		Query query = sirhEntityManager.createQuery(hql, FichePoste.class);
		query.setParameter("idAgent", idAgent);

		if (null != dateDonnee) {
			query.setParameter("dateJour", dateDonnee);
		} else {
			query.setParameter("dateJour", new Date());
		}
		try {
			FichePoste fp = (FichePoste) query.getSingleResult();
			if (fp.getIdServiceADS() != null) {
				return adsWSConsumer.getEntiteByIdEntite(fp.getIdServiceADS());
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public EntiteDto getDirectionOfAgent(Integer idAgent, Date dateDonnee) {
		String hql = "select fp from FichePoste fp ,Affectation aff " + "where aff.fichePoste.idFichePoste = fp.idFichePoste and  aff.agent.idAgent =:idAgent and aff.dateDebutAff<=:dateJour "
				+ "and (aff.dateFinAff is null or aff.dateFinAff >= :dateJour)";
		Query query = sirhEntityManager.createQuery(hql, FichePoste.class);
		query.setParameter("idAgent", idAgent);

		if (null != dateDonnee) {
			query.setParameter("dateJour", dateDonnee);
		} else {
			query.setParameter("dateJour", new Date());
		}
		try {
			FichePoste fp = (FichePoste) query.getSingleResult();
			if (fp.getIdServiceADS() != null) {
				return adsWSConsumer.getAffichageDirection(fp.getIdServiceADS());
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<AgentGeneriqueDto> getListAgents(List<Integer> listIdsAgent) {
		
		List<Agent> listAgents = agentRepository.getListAgents(listIdsAgent);
		
		List<AgentGeneriqueDto> result = new ArrayList<AgentGeneriqueDto>();
		if(null != listAgents) {
			for(Agent agent : listAgents) {
				result.add(new AgentGeneriqueDto(agent));
			}
		}
		
		return result;
	}
}
