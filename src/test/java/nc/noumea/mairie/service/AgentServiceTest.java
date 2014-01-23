package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AgentRecherche;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.service.AgentService;
import nc.noumea.mairie.model.service.ISiservService;
import nc.noumea.mairie.model.service.SiservService;
import nc.noumea.mairie.web.dto.AgentWithServiceDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class AgentServiceTest {

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

		Siserv service = new Siserv();
		service.setServi("N");
		service.setLiServ("NONO");

		@SuppressWarnings("unchecked")
		TypedQuery<AgentRecherche> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeAgentRecherche);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(AgentRecherche.class))).thenReturn(
				mockQuery);

		ISiservService mockSiservService = Mockito.mock(ISiservService.class);
		Mockito.when(mockSiservService.getServiceAgent(9005138)).thenReturn(service);
		Mockito.when(mockSiservService.getServiceAgent(9005131)).thenReturn(service);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(agtService, "siservSrv", mockSiservService);

		// When
		List<AgentWithServiceDto> result = agtService.listAgentsEnActivite("QUIN", "");

		// Then
		assertEquals(2, result.size());
		assertEquals(ag1.getIdAgent(), result.get(0).getIdAgent());
		assertEquals("TEST NOM", result.get(1).getNom());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testlistAgentsEnActivite_AgentDtoListIsEmpty_setPAramAsnull() {
		// Given
		TypedQuery<AgentRecherche> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<AgentRecherche>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(AgentRecherche.class))).thenReturn(
				mockQuery);

		AgentService service = new AgentService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		List<AgentWithServiceDto> result = service.listAgentsEnActivite("", "");

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
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Agent.class))).thenReturn(mockQuery);

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

		TypedQuery<Agent> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(ag));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Agent.class))).thenReturn(mockQuery);

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

		Siserv service = new Siserv();
		service.setServi("N");
		service.setLiServ("NONO");

		@SuppressWarnings("unchecked")
		TypedQuery<Agent> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeAgent);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Agent.class))).thenReturn(mockQuery);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);

		// When
		List<Agent> result = agtService.listAgentServiceSansAgent("DDAA", 9005138);

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

		Siserv service = new Siserv();
		service.setServi("N");
		service.setLiServ("NONO");

		List<String> listService = new ArrayList<>();
		listService.add("DDAA");

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
		Siserv serviceDirection = new Siserv();
		serviceDirection.setServi("DIR");
		serviceDirection.setLiServ("DIRECTION");

		Agent ag1 = new Agent();
		ag1.setIdAgent(9005138);
		ag1.setNomUsage("NOM USAGE");
		Siserv serv1 = new Siserv();
		serv1.setServi("DDAA");
		serv1.setLiServ("TEST");
		FichePoste fp1 = new FichePoste();
		fp1.setIdFichePoste(1);
		fp1.setAnnee(2014);
		fp1.setService(serv1);
		Affectation aff1 = new Affectation();
		aff1.setAgent(ag1);
		aff1.setFichePoste(fp1);

		List<Affectation> listeAffectation = new ArrayList<Affectation>();
		listeAffectation.add(aff1);

		@SuppressWarnings("unchecked")
		TypedQuery<Affectation> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeAffectation);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Affectation.class))).thenReturn(mockQuery);

		SiservService siservSrvMock = Mockito.mock(SiservService.class);
		Mockito.when(siservSrvMock.getDirection("DDAA")).thenReturn(serviceDirection);

		AgentService agtService = new AgentService();
		ReflectionTestUtils.setField(agtService, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(agtService, "siservSrv", siservSrvMock);

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

}
