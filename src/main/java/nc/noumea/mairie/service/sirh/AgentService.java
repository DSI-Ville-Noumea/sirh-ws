package nc.noumea.mairie.service.sirh;

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
import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.AgentGeneriqueDto;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.EntiteWithAgentWithServiceDto;
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
		String sql = "select a.* from Affectation aff, Agent a where  aff.id_Agent = a.id_Agent and aff.id_Fiche_Poste = "
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
		
		List<Affectation> listAffectations = agentRepository.getListAgentsByServicesAndListAgentsAndDate(idServiceADS, date, idAgents);
		
		if(null == listAffectations
				|| listAffectations.isEmpty()) {
			return result;
		}
		
		// optimisation #18391
		// ce service est appele aussi par le kiosque RH et SHAREPOINT (a
		// verifier)
		// dans leur cas, on recherche qu un seul agent
		// cela ne sert donc a rien d appeler l arbre entier ADS
		EntiteDto root = null;
		if (null == idAgents || idAgents.isEmpty() || 1 < idAgents.size()) {
			root = adsWSConsumer.getWholeTreeLight();
		}
		
		for (Affectation aff : listAffectations) {
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
	public List<AgentWithServiceDto> listAgentsOfServicesWithoutLibelleService(List<Integer> idServiceADS, Date date, List<Integer> idAgents) {

		List<AgentWithServiceDto> result = new ArrayList<AgentWithServiceDto>();
		
		List<Affectation> listAffectations = agentRepository.getListAgentsByServicesAndListAgentsAndDate(idServiceADS, date, idAgents);
		
		if(null != listAffectations) {
			for (Affectation aff : listAffectations) {
				AgentWithServiceDto agDto = new AgentWithServiceDto(aff.getAgent());
				if (aff.getFichePoste() != null && aff.getFichePoste().getIdServiceADS() != null) {
					agDto.setIdServiceADS(aff.getFichePoste().getIdServiceADS());
				}
				result.add(agDto);
			}
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
	public List<AgentWithServiceDto> listAgentsEnActiviteWithServiceAds(String nom, Integer idServiceADS) {

		List<AgentWithServiceDto> result = new ArrayList<AgentWithServiceDto>();

		List<Object[]> list = agentRepository.getListAgentsEnActivite(nom, idServiceADS);

		// optimisation #18176
		EntiteDto root = adsWSConsumer.getWholeTree();

		if (null != list) {
			for (Object[] res : list) {
				AgentWithServiceDto agDto = new AgentWithServiceDto((AgentRecherche) res[0]);
				FichePoste fp = (FichePoste) res[1];
				EntiteDto service = adsService.getEntiteByIdEntiteOptimiseWithWholeTree(fp.getIdServiceADS(), root);

				// on construit le dto de l'agent
				agDto.setIdServiceADS(service == null ? null : service.getIdEntite());
				agDto.setService(service == null ? null : service.getLabel().trim());
				result.add(agDto);
			}
		}

		return result;
	}

	/**
	 * Retourne un arbre d entite avec les agents associés a chaque entite.
	 * On exclut l agent passe en parametre.
	 * On ajoute les agents en plus dans la lliste en parametre listIdsAgentAInclure.
	 * 
	 * @param idServiceADS Integer L entite root que l on recherche
	 * @param idAgent Integer L agent a exclure de l arbre
	 * @param listIdsAgentAInclure List<Integer> Cette liste comprend la liste des agents que l on voir dans l arbre
	 * 		en plus des autres, MEME s ils ne font pas partis de l arbre des entites
	 * 		dans ce cas on rajoute les entites manquantes 
	 * @return EntiteWithAgentWithServiceDto un arbre d entite avec les agents associés a chaque entite
	 */
	@Override
	public EntiteWithAgentWithServiceDto getArbreServicesWithListAgentsByServiceWithoutAgentConnecteAndListAgentHorsService(
			Integer idServiceADS, Integer idAgent, List<Integer> listIdsAgentAInclure, Date dateJour) {

		// on recherche d abord l arbre principal
		// du service ADS passe en parametre
		EntiteWithAgentWithServiceDto entiteWithAgents = getArbreServicesWithListAgentsByServiceWithoutAgentConnecte(idServiceADS, idAgent, listIdsAgentAInclure, dateJour);
		
		if(null == entiteWithAgents) {
			return null;
		}
		
		if(null != listIdsAgentAInclure
				&& !listIdsAgentAInclure.isEmpty()) {
			List<AgentDto> listAgentInTree = adsService.getListeAgentsOfEntiteTree(entiteWithAgents);
			
			List<Integer> listIdsAgentsNotInSameServiceOfApprobateur = new ArrayList<Integer>();
			for(Integer agentNotInTree : listIdsAgentAInclure) {
				AgentDto agentTmp = new AgentDto();
				agentTmp.setIdAgent(agentNotInTree);
				if(!listAgentInTree.contains(agentTmp)) {
					listIdsAgentsNotInSameServiceOfApprobateur.add(agentNotInTree);
				}
			}
			
			// bug #29215
			if(null != listIdsAgentsNotInSameServiceOfApprobateur
					&& !listIdsAgentsNotInSameServiceOfApprobateur.isEmpty()) {
				List<AgentWithServiceDto> listAgentsNotInSameServiceOfApprobateur = listAgentsOfServices(null, dateJour, listIdsAgentsNotInSameServiceOfApprobateur);
				
				// on construit un arbre de EntiteWithAgentWithServiceDto avec ces agents
				List<EntiteWithAgentWithServiceDto> treeWithAgentsOthersServices = getArbrewithAgentsNotInSameServiceOfApprobateur(listAgentsNotInSameServiceOfApprobateur, entiteWithAgents);
				
				// si on a d autres arbres a ajouter
				// alors on cree un noeaud parent ficitif auquel on rattache tous les arbres
				if(null != treeWithAgentsOthersServices
						&& !treeWithAgentsOthersServices.isEmpty()) {
					EntiteWithAgentWithServiceDto root = new  EntiteWithAgentWithServiceDto();
					root.setSigle("Services");
					root.setLabel("Les services");
					root.getEntiteEnfantWithAgents().add(entiteWithAgents);
					root.getEntiteEnfantWithAgents().addAll(treeWithAgentsOthersServices);
					return root;
				}
			}
		}
		
		return entiteWithAgents;
	}
	
	private List<EntiteWithAgentWithServiceDto> getArbrewithAgentsNotInSameServiceOfApprobateur(
			List<AgentWithServiceDto> listAgentsNotInSameServiceOfApprobateur, EntiteWithAgentWithServiceDto originalEntiteWithAgents) {
		
		List<EntiteWithAgentWithServiceDto> result = new ArrayList<EntiteWithAgentWithServiceDto>();
		
		if(null != listAgentsNotInSameServiceOfApprobateur) {
			for(AgentWithServiceDto agent : listAgentsNotInSameServiceOfApprobateur) {
				
				// on cherche d abord si l agent n est pa en PA inactive 
				// mais qu il faut tout de meme l ajouter a une entite existante
				
				
				boolean isTrouve = false;
				for(EntiteWithAgentWithServiceDto entite : result) {
					if(entite.getIdEntite().equals(agent.getIdServiceADS())) {
						entite.getListAgentWithServiceDto().add(agent);
						isTrouve = true;
						break;
					}
				}
				if(!isTrouve) {
					EntiteWithAgentWithServiceDto newEntite = new EntiteWithAgentWithServiceDto();
					newEntite.setIdEntite(agent.getIdServiceADS());
					newEntite.setSigle(agent.getSigleService());
					newEntite.getListAgentWithServiceDto().add(agent);
					result.add(newEntite);
				}
			}
		}
		
		return result;
	}

	/**
	 * Retourne un arbre d entite avec les agents associés a chaque entite.
	 * On exclut l agent passe en parametre
	 */
	@Override
	public EntiteWithAgentWithServiceDto getArbreServicesWithListAgentsByServiceWithoutAgentConnecte(
			Integer idServiceADS, Integer idAgent, List<Integer> listIdsAgentAInclure, Date dateJour) {

		EntiteDto entite = adsWSConsumer.getEntiteWithChildrenByIdEntite(idServiceADS);

		if (null == entite)
			return null;

		EntiteWithAgentWithServiceDto entiteWithAgents = new EntiteWithAgentWithServiceDto(entite);
		List<Object[]> list = agentRepository.getListAgentsEnActivite(null, idServiceADS);

		if (null != list) {
			for (Object[] res : list) {
				AgentWithServiceDto agDto = new AgentWithServiceDto((AgentRecherche) res[0]);
				// #20666 : on n'ajoute pas l'agent lui-meme
				if (idAgent != null && idAgent.toString().equals(agDto.getIdAgent().toString())) {
					continue;
				}
				// on construit le dto de l'agent
				agDto.setIdServiceADS(entite.getIdEntite());
				agDto.setService(entite.getLabel().trim());

				if(!entiteWithAgents.getListAgentWithServiceDto().contains(agDto))
					entiteWithAgents.getListAgentWithServiceDto().add(agDto);
			}
		}

		// #29570
		// dans le cas d agents avec PA inactive non retournes par la requete
		List<AgentWithServiceDto> listAgentDtoAInclure = listAgentsOfServices(null, dateJour, listIdsAgentAInclure);
		if(null != listAgentDtoAInclure) {
			for(AgentWithServiceDto agentDtoAInclure : listAgentDtoAInclure) {
				if(null != agentDtoAInclure.getIdServiceADS()
						&& agentDtoAInclure.getIdServiceADS().equals(entiteWithAgents.getIdEntite())
						&& !entiteWithAgents.getListAgentWithServiceDto().contains(agentDtoAInclure)) {
					entiteWithAgents.getListAgentWithServiceDto().add(agentDtoAInclure);
				}
			}
		}

		getArbreServicesWithListAgentsByService(entiteWithAgents, idAgent, listAgentDtoAInclure);
		
		return entiteWithAgents;
	}

	private void getArbreServicesWithListAgentsByService(EntiteWithAgentWithServiceDto entite, Integer idAgent, List<AgentWithServiceDto> listAgentDtoAInclure) {

		if (null != entite && null != entite.getEnfants()) {
			for (EntiteDto enfant : entite.getEnfants()) {

				EntiteWithAgentWithServiceDto enfantsWithAgents = new EntiteWithAgentWithServiceDto(enfant);

				List<Object[]> list = agentRepository.getListAgentsEnActivite(null, enfant.getIdEntite());
				if (null != list) {
					for (Object[] res : list) {
						AgentWithServiceDto agDto = new AgentWithServiceDto((AgentRecherche) res[0]);
						// #20666 : on n'ajoute pas l'agent lui-meme
						if (idAgent != null && idAgent.toString().equals(agDto.getIdAgent().toString())) {
							continue;
						}
						// on construit le dto de l'agent
						agDto.setIdServiceADS(entite.getIdEntite());
						agDto.setService(entite.getLabel().trim());

						if(!enfantsWithAgents.getListAgentWithServiceDto().contains(agDto))
							enfantsWithAgents.getListAgentWithServiceDto().add(agDto);
					}
				}
				
				// #29570
				// dans le cas d agents avec PA inactive non retournes par la requete
				if(null != listAgentDtoAInclure) {
					for(AgentWithServiceDto agentDtoAInclure : listAgentDtoAInclure) {
						if(null != agentDtoAInclure.getIdServiceADS()
								&& agentDtoAInclure.getIdServiceADS().equals(enfantsWithAgents.getIdEntite())
								&& !enfantsWithAgents.getListAgentWithServiceDto().contains(agentDtoAInclure)) {
							enfantsWithAgents.getListAgentWithServiceDto().add(agentDtoAInclure);
						}
					}
				}

				entite.getEntiteEnfantWithAgents().add(enfantsWithAgents);

				getArbreServicesWithListAgentsByService(enfantsWithAgents, idAgent, listAgentDtoAInclure);
			}
		}
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
		if (null != listAgents) {
			for (Agent agent : listAgents) {
				result.add(new AgentGeneriqueDto(agent));
			}
		}

		return result;
	}

	@Override
	public List<AgentWithServiceDto> listAgentsOfServicesOldAffectation(List<Integer> idServiceADS, List<Integer> listIdsAgent) {

		List<AgentWithServiceDto> result = new ArrayList<AgentWithServiceDto>();

		List<Affectation> listAffectations = agentRepository.getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(idServiceADS, listIdsAgent);
		
		// optimisation #18391
		// ce service est appele aussi par le kiosque RH et SHAREPOINT (a
		// verifier)
		// dans leur cas, on recherche qu un seul agent
		// cela ne sert donc a rien d appeler l arbre entier ADS
		EntiteDto root = null;
		if (null == listIdsAgent || 1 < listIdsAgent.size()) {
			root = adsWSConsumer.getWholeTreeLight();
		}

		for (Affectation aff : listAffectations) {
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
	public List<AgentWithServiceDto> listAgentsOfServicesOldAffectationWithoutLibelleService(List<Integer> idServiceADS, List<Integer> listIdsAgent) {

		List<AgentWithServiceDto> result = new ArrayList<AgentWithServiceDto>();

		List<Affectation> listAffectations = agentRepository.getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(idServiceADS, listIdsAgent);
		
		if(null != listAffectations) {
			for (Affectation aff : listAffectations) {
				AgentWithServiceDto agDto = new AgentWithServiceDto(aff.getAgent());
				if (aff.getFichePoste() != null && aff.getFichePoste().getIdServiceADS() != null) {
					agDto.setIdServiceADS(aff.getFichePoste().getIdServiceADS());
				}
				result.add(agDto);
			}
		}

		return result;
	}
}
