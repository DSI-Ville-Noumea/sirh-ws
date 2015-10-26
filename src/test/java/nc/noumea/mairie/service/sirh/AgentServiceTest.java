package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;

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
import nc.noumea.mairie.service.ads.IAdsService;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;
import nc.noumea.mairie.web.dto.EntiteDto;
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
	public void testlistAgentsEnActivite_returnListOfAgentDto() {
		// Given
		List<AgentRecherche> listeAgentRecherche = new ArrayList<AgentRecherche>();
		AgentRecherche ag1 = new AgentRecherche();
		ag1.setIdAgent(9005138);
		ag1.setNomatr(5138);
		AgentRecherche ag2 = new AgentRecherche();
		ag2.setIdAgent(9005131);
		ag2.setNomUsage("TEST NOM");
		listeAgentRecherche.add(ag1);
		listeAgentRecherche.add(ag2);

		List<Object[]> listResult = new ArrayList<Object[]>();
		Object[] obj1 = new Object[2];
		obj1[0] = ag1;
		obj1[1] = new FichePoste();
		Object[] obj2 = new Object[2];
		obj2[0] = ag2;
		obj2[1] = new FichePoste();

		listResult.add(obj1);
		listResult.add(obj2);

		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listResult);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString())).thenReturn(mockQuery);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		IAdsService adsService = Mockito.mock(IAdsService.class);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(agtService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(agtService, "adsService", adsService);

		// When
		List<AgentWithServiceDto> result = agtService.listAgentsEnActivite("QUIN", null);

		// Then
		assertEquals(2, result.size());
		assertEquals(ag1.getIdAgent(), result.get(0).getIdAgent());
		assertEquals("TEST NOM", result.get(1).getNom());

	}

	@Test
	public void testlistAgentsEnActivite_AgentDtoListIsEmpty_setPAramAsnull() {
		// Given
		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Object[]>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString())).thenReturn(mockQuery);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		IAdsService adsService = Mockito.mock(IAdsService.class);

		AgentService service = new AgentService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(service, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(service, "adsService", adsService);

		// When
		List<AgentWithServiceDto> result = service.listAgentsEnActivite("", null);

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

		@SuppressWarnings("unchecked")
		TypedQuery<Affectation> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeAffectation);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Affectation.class))).thenReturn(mockQuery);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(noeud1);
		Mockito.when(adsWSConsumer.getAffichageDirection(1)).thenReturn(noeudDirection);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getAffichageDirectionWithoutCallADS(noeud1)).thenReturn(noeudDirection);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(agtService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(agtService, "adsService", adsService);

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

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(agtService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(agtService, "adsService", adsService);

		// When
		List<AgentWithServiceDto> result = agtService.listAgentsOfServicesOldAffectation(null, Arrays.asList(9005138));

		// Then
		assertEquals(1, result.size());
		assertEquals(ag.getIdAgent(), result.get(0).getIdAgent());
		assertEquals(service.getLabel(), result.get(0).getService());
		assertEquals(direction.getLabel(), result.get(0).getDirection());

	}

}
