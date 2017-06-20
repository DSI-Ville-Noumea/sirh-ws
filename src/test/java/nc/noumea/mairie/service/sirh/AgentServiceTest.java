package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
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
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.EntiteWithAgentWithServiceDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class AgentServiceTest {

	@Autowired
	IAgentService repository;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;

	@Test
	public void testlistAgentsEnActivite_AgentDtoListIsEmpty_setPAramAsnull() {
		// Given
		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Object[]>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString())).thenReturn(mockQuery);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		IAdsService adsService = Mockito.mock(IAdsService.class);
		
		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		
		AgentService service = new AgentService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(service, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(service, "adsService", adsService);
		ReflectionTestUtils.setField(service, "agentRepository", agentRepository);

		// When
		List<AgentWithServiceDto> result = service.listAgentsEnActiviteWithServiceAds("", null);

		// Then
		assertEquals(0, result.size());
	}

	@Test
	public void getAgent_ReturnNull() {
		// Given
		@SuppressWarnings("unchecked")
		TypedQuery<Agent> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Agent>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery(Mockito.anyString(), Mockito.eq(Agent.class))).thenReturn(mockQuery);

		AgentService service = new AgentService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Agent result = service.getAgent(9005138);

		// Then
		assertEquals(null, result);
	}

	@Test
	public void getAgent_ReturnAgent() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);

		@SuppressWarnings("unchecked")
		TypedQuery<Agent> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(ag));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery(Mockito.anyString(), Mockito.eq(Agent.class))).thenReturn(mockQuery);

		AgentService service = new AgentService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Agent result = service.getAgent(9005138);

		// Then
		assertEquals(ag.getIdAgent(), result.getIdAgent());
	}

	@Test
	public void listAgentServiceSansAgent_returnListOfAgent() {
		// Given
		List<Agent> listeAgent = new ArrayList<Agent>();
		Agent ag1 = new Agent();
		ag1.setIdAgent(9005138);
		Agent ag2 = new Agent();
		ag2.setIdAgent(9005131);
		ag2.setNomUsage("TEST NOM");
		listeAgent.add(ag1);
		listeAgent.add(ag2);

		@SuppressWarnings("unchecked")
		TypedQuery<Agent> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeAgent);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Agent.class))).thenReturn(mockQuery);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);

		// When
		List<Agent> result = agtService.listAgentServiceSansAgent(1, 9005138);

		// Then
		assertEquals(2, result.size());
		assertEquals(ag1.getIdAgent(), result.get(0).getIdAgent());
		assertEquals("TEST NOM", result.get(1).getNomUsage());

	}

	@Test
	public void listAgentPlusieursServiceSansAgentSansSuperieur_returnListOfAgent() {
		// Given
		List<Agent> listeAgent = new ArrayList<Agent>();
		Agent ag1 = new Agent();
		ag1.setIdAgent(9005138);
		Agent ag2 = new Agent();
		ag2.setIdAgent(9005131);
		ag2.setNomUsage("TEST NOM");
		listeAgent.add(ag1);
		listeAgent.add(ag2);

		List<Integer> listService = new ArrayList<Integer>();
		listService.add(1);

		@SuppressWarnings("unchecked")
		TypedQuery<Agent> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeAgent);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Agent.class))).thenReturn(mockQuery);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);

		// When
		List<Agent> result = agtService.listAgentPlusieursServiceSansAgentSansSuperieur(9005138, 9002990, listService);

		// Then
		assertEquals(2, result.size());
		assertEquals(ag1.getIdAgent(), result.get(0).getIdAgent());
		assertEquals("TEST NOM", result.get(1).getNomUsage());

	}

	@Test
	public void getSuperieurHierarchiqueAgent_returnAgent() {
		// Given
		List<Agent> listeAgent = new ArrayList<Agent>();
		Agent ag2 = new Agent();
		ag2.setIdAgent(9005131);
		ag2.setNomUsage("TEST NOM");
		listeAgent.add(ag2);

		List<String> listService = new ArrayList<>();
		listService.add("DDAA");

		@SuppressWarnings("unchecked")
		TypedQuery<Agent> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeAgent);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNativeQuery(Mockito.anyString(), Mockito.eq(Agent.class))).thenReturn(mockQuery);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);

		// When
		Agent result = agtService.getSuperieurHierarchiqueAgent(9005138);

		// Then
		assertEquals(ag2.getIdAgent(), result.getIdAgent());
		assertEquals("TEST NOM", result.getNomUsage());

	}

	@Test
	public void listAgentsOfServices_returnNoAgent() {
		// Given
		List<Affectation> listeAffectation = new ArrayList<Affectation>();

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsByServicesAndListAgentsAndDate(Mockito.anyListOf(Integer.class), 
				Mockito.any(Date.class), Mockito.anyListOf(Integer.class)))
			.thenReturn(listeAffectation);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);

		// When
		List<AgentWithServiceDto> result = agtService.listAgentsOfServices(null, new Date(), null);

		// Then
		assertEquals(0, result.size());
	}

	@Test
	public void listAgentsOfServices_returnListOfAgentWithServiceDto() {
		// Given

		Agent ag1 = new Agent();
		ag1.setIdAgent(9005138);
		ag1.setNomUsage("NOM USAGE");
		ag1.setTitre("0");

		FichePoste fp1 = new FichePoste();
		fp1.setAnnee(2014);
		fp1.setIdServiceADS(1);
		Affectation aff1 = new Affectation();
		aff1.setAgent(ag1);
		aff1.setFichePoste(fp1);
		EntiteDto noeudDirection = new EntiteDto();
		noeudDirection.setLabel("DIRECTION");
		EntiteDto noeud1 = new EntiteDto();
		noeud1.setLabel("TEST");

		List<Affectation> listeAffectation = new ArrayList<Affectation>();
		listeAffectation.add(aff1);

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsByServicesAndListAgentsAndDate(Mockito.anyListOf(Integer.class), Mockito.any(Date.class), Mockito.anyListOf(Integer.class)))
			.thenReturn(listeAffectation);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(noeud1);
		Mockito.when(adsWSConsumer.getAffichageDirection(1)).thenReturn(noeudDirection);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getAffichageDirectionWithoutCallADS(noeud1)).thenReturn(noeudDirection);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(agtService, "adsService", adsService);
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);

		// When
		List<AgentWithServiceDto> result = agtService.listAgentsOfServices(null, new Date(), null);

		// Then
		assertEquals(1, result.size());
		assertEquals(ag1.getIdAgent(), result.get(0).getIdAgent());
		assertEquals("NOM USAGE", result.get(0).getNom());
		assertEquals("TEST", result.get(0).getService());
		assertEquals("DIRECTION", result.get(0).getDirection());
	}

	@Test
	public void listAgentsOfServicesWithoutLibelleService_returnNoAgent() {
		// Given
		List<Affectation> listeAffectation = new ArrayList<Affectation>();

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsByServicesAndListAgentsAndDate(Mockito.anyListOf(Integer.class), 
				Mockito.any(Date.class), Mockito.anyListOf(Integer.class)))
			.thenReturn(listeAffectation);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);

		// When
		List<AgentWithServiceDto> result = agtService.listAgentsOfServicesWithoutLibelleService(null, new Date(), null);

		// Then
		assertEquals(0, result.size());
	}

	@Test
	public void listAgentsOfServicesWithoutLibelleService_returnListOfAgentWithServiceDto() {
		// Given
		Agent ag1 = new Agent();
		ag1.setIdAgent(9005138);
		ag1.setNomUsage("NOM USAGE");
		ag1.setTitre("0");

		FichePoste fp1 = new FichePoste();
		fp1.setAnnee(2014);
		fp1.setIdServiceADS(1);
		
		Affectation aff1 = new Affectation();
		aff1.setAgent(ag1);
		aff1.setFichePoste(fp1);
		
		EntiteDto noeudDirection = new EntiteDto();
		noeudDirection.setLabel("DIRECTION");
		
		EntiteDto noeud1 = new EntiteDto();
		noeud1.setLabel("TEST");

		List<Affectation> listeAffectation = new ArrayList<Affectation>();
		listeAffectation.add(aff1);

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsByServicesAndListAgentsAndDate(Mockito.anyListOf(Integer.class), 
				Mockito.any(Date.class), Mockito.anyListOf(Integer.class)))
			.thenReturn(listeAffectation);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);

		// When
		List<AgentWithServiceDto> result = agtService.listAgentsOfServicesWithoutLibelleService(null, new Date(), null);

		// Then
		assertEquals(1, result.size());
		assertEquals(ag1.getIdAgent(), result.get(0).getIdAgent());
		assertEquals("NOM USAGE", result.get(0).getNom());
		assertNull(result.get(0).getService());
		assertNull(result.get(0).getDirection());
		assertEquals(result.get(0).getIdServiceADS(), fp1.getIdServiceADS());
	}

	@Test
	public void findAgentWithName_returnAgent() {
		// Given

		Agent ag1 = new Agent();
		ag1.setIdAgent(9005138);
		ag1.setNomUsage("NOM USAGE");

		@SuppressWarnings("unchecked")
		TypedQuery<Agent> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(ag1);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Agent.class))).thenReturn(mockQuery);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);

		// When
		Agent result = agtService.findAgentWithName(9005138, "nono");

		// Then
		assertEquals(ag1.getIdAgent(), result.getIdAgent());
	}

	@Test
	public void listAgentsOfServicesOldAffectation_returnNoAgent() {
		// Given
		List<Affectation> listeAgent = new ArrayList<Affectation>();
		
		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdServiceADS(1);

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(null, Arrays.asList(9005138))).thenReturn(listeAgent);
		
		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);

		// When
		List<AgentWithServiceDto> result = agtService.listAgentsOfServicesOldAffectation(null, Arrays.asList(9005138));

		// Then
		assertEquals(0, result.size());
	}

	@Test
	public void listAgentsOfServicesOldAffectation_returnListOfAgent() {
		// Given
		List<Affectation> listeAgent = new ArrayList<Affectation>();
		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdServiceADS(1);
		Agent ag = new Agent();
		ag.setTitre("0");
		ag.setIdAgent(9005138);
		Affectation ag1 = new Affectation();
		ag1.setFichePoste(fichePoste);
		ag1.setAgent(ag);
		listeAgent.add(ag1);
		EntiteDto direction = new EntiteDto();
		direction.setIdEntite(3);
		direction.setLabel("direction");
		EntiteDto service = new EntiteDto();
		service.setIdEntite(1);
		service.setSigle("SIGLE");
		service.setLabel("Label");

		@SuppressWarnings("unchecked")
		TypedQuery<Affectation> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeAgent);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Affectation.class))).thenReturn(mockQuery);
		
		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);		
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(fichePoste.getIdServiceADS())).thenReturn(service);
		
		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getAffichageDirectionWithoutCallADS(service)).thenReturn(direction);

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(null, Arrays.asList(9005138))).thenReturn(listeAgent);
		
		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(agtService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(agtService, "adsService", adsService);
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);

		// When
		List<AgentWithServiceDto> result = agtService.listAgentsOfServicesOldAffectation(null, Arrays.asList(9005138));

		// Then
		assertEquals(1, result.size());
		assertEquals(ag.getIdAgent(), result.get(0).getIdAgent());
		assertEquals(service.getLabel(), result.get(0).getService());
		assertEquals(direction.getLabel(), result.get(0).getDirection());
	}

	@Test
	public void listAgentsOfServicesOldAffectationWithoutLibelleService_returnNoAgent() {
		// Given
		List<Affectation> listeAgent = new ArrayList<Affectation>();
		
		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdServiceADS(1);

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(null, Arrays.asList(9005138))).thenReturn(listeAgent);
		
		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);

		// When
		List<AgentWithServiceDto> result = agtService.listAgentsOfServicesOldAffectationWithoutLibelleService(null, Arrays.asList(9005138));

		// Then
		assertEquals(0, result.size());
	}

	@Test
	public void listAgentsOfServicesOldAffectationWithoutLibelleService_returnListOfAgent() {
		// Given
		List<Affectation> listeAgent = new ArrayList<Affectation>();
		
		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdServiceADS(1);
		
		Agent ag = new Agent();
		ag.setTitre("0");
		ag.setIdAgent(9005138);
		
		Affectation ag1 = new Affectation();
		ag1.setFichePoste(fichePoste);
		ag1.setAgent(ag);
		listeAgent.add(ag1);

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(null, Arrays.asList(9005138))).thenReturn(listeAgent);
		
		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);

		// When
		List<AgentWithServiceDto> result = agtService.listAgentsOfServicesOldAffectationWithoutLibelleService(null, Arrays.asList(9005138));

		// Then
		assertEquals(1, result.size());
		assertEquals(ag.getIdAgent(), result.get(0).getIdAgent());
		assertNull(result.get(0).getService());
		assertNull(result.get(0).getDirection());
		assertEquals(fichePoste.getIdServiceADS(), result.get(0).getIdServiceADS());
	}
	
	@Test
	public void getArbreServicesWithListAgentsByServiceWithoutAgentConnecte_EntiteNotExist() {

		Integer idServiceADS = 1;
		
		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteWithChildrenByIdEntite(idServiceADS)).thenReturn(null);
		
		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "adsWSConsumer", adsWSConsumer);
		
		EntiteWithAgentWithServiceDto result = agtService.getArbreServicesWithListAgentsByServiceWithoutAgentConnecte(idServiceADS, null, null, new Date());
		
		assertNull(result);
	}
	
	@Test
	public void getArbreServicesWithListAgentsByServiceWithoutAgentConnecte_3niveaux_3agents() {

		Integer idServiceADS = 1;
		
		EntiteDto entiteRoot = new EntiteDto();
		entiteRoot.setIdEntite(idServiceADS);
		entiteRoot.setLabel("label root");
		
		EntiteDto entiteEnfant = new EntiteDto();
		entiteEnfant.setIdEntite(idServiceADS+1);
		entiteEnfant.setLabel("label enfant");
		entiteRoot.getEnfants().add(entiteEnfant);
		
		EntiteDto entitePetitEnfant = new EntiteDto();
		entitePetitEnfant.setIdEntite(entiteEnfant.getIdEntite()+1);
		entitePetitEnfant.setLabel("label petit enfant");
		entiteEnfant.getEnfants().add(entitePetitEnfant);
		
		AgentRecherche agentRecherche1 = new AgentRecherche();
		agentRecherche1.setIdAgent(9005138);
		Object[] agent1 = new Object[1];
		agent1[0] = agentRecherche1;

		List<Object[]> listAgent1 = new ArrayList<Object[]>();
		listAgent1.add(agent1);
		
		AgentRecherche agentRecherche2 = new AgentRecherche();
		agentRecherche2.setIdAgent(9005110);
		Object[] agent2 = new Object[1];
		agent2[0] = agentRecherche2;

		List<Object[]> listAgent2 = new ArrayList<Object[]>();
		listAgent2.add(agent2);
		
		AgentRecherche agentRecherche3 = new AgentRecherche();
		agentRecherche3.setIdAgent(9005142);
		Object[] agent3 = new Object[1];
		agent3[0] = agentRecherche3;

		List<Object[]> listAgent3 = new ArrayList<Object[]>();
		listAgent3.add(agent3);
		
		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteWithChildrenByIdEntite(idServiceADS)).thenReturn(entiteRoot);
		
		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsEnActivite(null, entiteRoot.getIdEntite())).thenReturn(listAgent1);
		Mockito.when(agentRepository.getListAgentsEnActivite(null, entiteEnfant.getIdEntite())).thenReturn(listAgent2);
		Mockito.when(agentRepository.getListAgentsEnActivite(null, entitePetitEnfant.getIdEntite())).thenReturn(listAgent3);
				
		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);
		
		EntiteWithAgentWithServiceDto result = agtService.getArbreServicesWithListAgentsByServiceWithoutAgentConnecte(idServiceADS, null, null, new Date());
		
		assertEquals(result.getIdEntite(), entiteRoot.getIdEntite());
		assertEquals(result.getListAgentWithServiceDto().size(), 1);
		assertEquals(result.getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche1.getIdAgent());
		// entite enfant
		assertEquals(result.getEntiteEnfantWithAgents().size(), 1);
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getIdEntite(), entiteEnfant.getIdEntite());
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().size(), 1);
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche2.getIdAgent());
		// petit enfant
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().size(), 1);
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getIdEntite(), entitePetitEnfant.getIdEntite());
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().size(), 1);
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche3.getIdAgent());
	}
	
	@Test
	public void getArbreServicesWithListAgentsByServiceWithoutAgentConnecte_3niveaux_3agents_And_oneOtherService() {

		Date dateJour = Mockito.spy(new Date());
		List<Integer> listOtherAgents = new ArrayList<Integer>();
		listOtherAgents.add(9004999);
		
		Integer idServiceADS = 1;
		
		EntiteDto entiteRoot = new EntiteDto();
		entiteRoot.setIdEntite(idServiceADS);
		entiteRoot.setLabel("label root");
		
		EntiteDto entiteEnfant = new EntiteDto();
		entiteEnfant.setIdEntite(idServiceADS+1);
		entiteEnfant.setLabel("label enfant");
		entiteRoot.getEnfants().add(entiteEnfant);
		
		EntiteDto entitePetitEnfant = new EntiteDto();
		entitePetitEnfant.setIdEntite(entiteEnfant.getIdEntite()+1);
		entitePetitEnfant.setLabel("label petit enfant");
		entiteEnfant.getEnfants().add(entitePetitEnfant);
		
		AgentRecherche agentRecherche1 = new AgentRecherche();
		agentRecherche1.setIdAgent(9005138);
		Object[] agent1 = new Object[1];
		agent1[0] = agentRecherche1;

		List<Object[]> listAgent1 = new ArrayList<Object[]>();
		listAgent1.add(agent1);
		
		AgentRecherche agentRecherche2 = new AgentRecherche();
		agentRecherche2.setIdAgent(9005110);
		Object[] agent2 = new Object[1];
		agent2[0] = agentRecherche2;

		List<Object[]> listAgent2 = new ArrayList<Object[]>();
		listAgent2.add(agent2);
		
		AgentRecherche agentRecherche3 = new AgentRecherche();
		agentRecherche3.setIdAgent(9005142);
		Object[] agent3 = new Object[1];
		agent3[0] = agentRecherche3;

		List<Object[]> listAgent3 = new ArrayList<Object[]>();
		listAgent3.add(agent3);
		
		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteWithChildrenByIdEntite(idServiceADS)).thenReturn(entiteRoot);
		
		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsEnActivite(null, entiteRoot.getIdEntite())).thenReturn(listAgent1);
		Mockito.when(agentRepository.getListAgentsEnActivite(null, entiteEnfant.getIdEntite())).thenReturn(listAgent2);
		Mockito.when(agentRepository.getListAgentsEnActivite(null, entitePetitEnfant.getIdEntite())).thenReturn(listAgent3);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);
		
		EntiteWithAgentWithServiceDto result = agtService.getArbreServicesWithListAgentsByServiceWithoutAgentConnecte(idServiceADS, null, null, dateJour);

		assertEquals(result.getIdEntite(), entiteRoot.getIdEntite());
		assertEquals(result.getListAgentWithServiceDto().size(), 1);
		assertEquals(result.getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche1.getIdAgent());
		// entite enfant
		assertEquals(result.getEntiteEnfantWithAgents().size(), 1);
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getIdEntite(), entiteEnfant.getIdEntite());
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().size(), 1);
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche2.getIdAgent());
		// petit enfant
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().size(), 1);
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getIdEntite(), entitePetitEnfant.getIdEntite());
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().size(), 1);
		assertEquals(result.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche3.getIdAgent());
		
		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getListeAgentsOfEntiteTree(Mockito.isA(EntiteWithAgentWithServiceDto.class))).thenReturn(
				Arrays.asList(new AgentDto(result.getListAgentWithServiceDto().get(0)),
						new AgentDto(result.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0)),
						new AgentDto(result.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0))));

		ReflectionTestUtils.setField(agtService, "adsService", adsService);
		
		Agent agent = new Agent();
		agent.setTitre("Mr");
		agent.setIdAgent(9004999);
		FichePoste fp = new FichePoste();
		fp.setIdServiceADS(99);
		Affectation aff = new Affectation();
		aff.setFichePoste(fp);
		aff.setAgent(agent);

		List<Integer> listSameAgents = new ArrayList<Integer>();
		listSameAgents.add(agentRecherche1.getIdAgent());
		
		Mockito.when(agentRepository.getListAgentsByServicesAndListAgentsAndDate(null, dateJour, listSameAgents))
			.thenReturn(Arrays.asList(aff));
		
		/////////////////////
		// bug #29215
		Mockito.when(adsService.getListeAgentsOfEntiteTree(result)).thenReturn(
				Arrays.asList(new AgentDto(result.getListAgentWithServiceDto().get(0)),
						new AgentDto(result.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0)),
						new AgentDto(result.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0))));
		EntiteWithAgentWithServiceDto result3 = agtService.getArbreServicesWithListAgentsByServiceWithoutAgentConnecte(idServiceADS, null, listSameAgents, dateJour);
		
		assertEquals(result3.getIdEntite(), entiteRoot.getIdEntite());
		assertEquals(result3.getListAgentWithServiceDto().size(), 1);
		assertEquals(result3.getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche1.getIdAgent());
		// entite enfant
		assertEquals(result3.getEntiteEnfantWithAgents().size(), 1);
		assertEquals(result3.getEntiteEnfantWithAgents().get(0).getIdEntite(), entiteEnfant.getIdEntite());
		assertEquals(result3.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().size(), 1);
		assertEquals(result3.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche2.getIdAgent());
		// petit enfant
		assertEquals(result3.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().size(), 1);
		assertEquals(result3.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getIdEntite(), entitePetitEnfant.getIdEntite());
		assertEquals(result3.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().size(), 1);
		assertEquals(result3.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche3.getIdAgent());
		
		//////////////////////////////////////////////////////
		///// SUITE TEST : avec un agent d un autre service
		EntiteDto otherEntite = new EntiteDto();
		otherEntite.setIdEntite(fp.getIdServiceADS());
		otherEntite.setLabel("other service");
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(fp.getIdServiceADS())).thenReturn(otherEntite);

		// bug #29570
		Agent agent12 = new Agent();
		agent12.setTitre("Mr");
		agent12.setIdAgent(9005195);
		FichePoste fp2 = new FichePoste();
		fp2.setIdServiceADS(idServiceADS);
		Affectation aff2 = new Affectation();
		aff2.setFichePoste(fp2);
		aff2.setAgent(agent12);
		
		AgentDto agentDto5195 = new AgentDto();
		agentDto5195.setIdAgent(agent12.getIdAgent());

		Mockito.when(agentRepository.getListAgentsByServicesAndListAgentsAndDate(null, dateJour, listOtherAgents))
			.thenReturn(Arrays.asList(aff));
		List<Integer> listOtherAgents2 = new ArrayList<Integer>();
		listOtherAgents2.addAll(listOtherAgents);
		listOtherAgents2.add(9005195);
		Mockito.when(agentRepository.getListAgentsByServicesAndListAgentsAndDate(null, dateJour, listOtherAgents2))
			.thenReturn(Arrays.asList(aff, aff2));
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(fp2.getIdServiceADS())).thenReturn(entiteRoot);
		
		Mockito.when(adsService.getListeAgentsOfEntiteTree(Mockito.isA(EntiteWithAgentWithServiceDto.class))).thenReturn(
				Arrays.asList(new AgentDto(result.getListAgentWithServiceDto().get(0)),
						new AgentDto(result.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0)),
						new AgentDto(result.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0)),
						agentDto5195));
		// fin bug #29570
		
		EntiteWithAgentWithServiceDto result2 = agtService.getArbreServicesWithListAgentsByServiceWithoutAgentConnecteAndListAgentHorsService(idServiceADS, null, listOtherAgents2, dateJour);
		
		// entite fictive
		assertEquals("Services", result2.getSigle());
		assertEquals("Les services", result2.getLabel());
		assertEquals(result2.getListAgentWithServiceDto().size(), 0);
		assertEquals(result2.getEntiteEnfantWithAgents().size(), 2);
		// entite root
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getIdEntite(), entiteRoot.getIdEntite());
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().size(), 2);
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche1.getIdAgent());
		// bug #29570
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(1).getIdAgent(), agent12.getIdAgent());
		// entite other service
		assertEquals(result2.getEntiteEnfantWithAgents().get(1).getIdEntite(), otherEntite.getIdEntite());
		assertEquals(result2.getEntiteEnfantWithAgents().get(1).getListAgentWithServiceDto().size(), 1);
		assertEquals(result2.getEntiteEnfantWithAgents().get(1).getListAgentWithServiceDto().get(0).getIdAgent(), agent.getIdAgent());
		// enfant
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().size(), 1);
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getIdEntite(), entiteEnfant.getIdEntite());
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().size(), 1);
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche2.getIdAgent());

		// petit enfant
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().size(), 1);
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getIdEntite(), entitePetitEnfant.getIdEntite());
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().size(), 1);
		assertEquals(result2.getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getEntiteEnfantWithAgents().get(0).getListAgentWithServiceDto().get(0).getIdAgent(), agentRecherche3.getIdAgent());
	}
	
	@Test
	public void getListeAgentEnNonCycleWithIndemniteForfaitTravailDPM_noResult() {
		
		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsEnActiviteByPrimeSurAffectation(
				Arrays.asList(AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_SAMEDI_DPM, AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_DJF_DPM), null))
			.thenReturn(null);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);
		
		assertEquals(0, agtService.getListeAgentWithIndemniteForfaitTravailDPM(null).size());
	}
	
	@Test
	public void getListeAgentEnNonCycleWithIndemniteForfaitTravailDPM_2Results() {
		
		AgentRecherche agent1 = new AgentRecherche();
		AgentRecherche agent2 = new AgentRecherche();
		
		List<AgentRecherche> listAgents = new ArrayList<AgentRecherche>();
		listAgents.add(agent1);
		listAgents.add(agent2);
		
		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getListAgentsEnActiviteByPrimeSurAffectation(
				Arrays.asList(AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_SAMEDI_DPM, AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_DJF_DPM), null))
			.thenReturn(listAgents);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "agentRepository", agentRepository);
		
		assertEquals(2, agtService.getListeAgentWithIndemniteForfaitTravailDPM(null).size());
	}
	


	@Test
	public void getAgentByIdTitreRepas_returnAgent() {
		// Given

		Agent ag1 = new Agent();
		ag1.setIdAgent(9005138);
		ag1.setNomUsage("NOM USAGE");

		@SuppressWarnings("unchecked")
		TypedQuery<Agent> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(ag1);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Agent.class))).thenReturn(mockQuery);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);

		// When
		Agent result = agtService.getAgentByIdTitreRepas(2323);

		// Then
		assertEquals(ag1.getIdAgent(), result.getIdAgent());
	}
	


	@Test
	public void getAgentByIdTitreRepas_noResult() {
		// Given

		@SuppressWarnings("unchecked")
		TypedQuery<Agent> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(null);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Agent.class))).thenReturn(mockQuery);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);

		// When
		Agent result = agtService.getAgentByIdTitreRepas(2323);

		// Then
		assertNull(result);
	}

}
