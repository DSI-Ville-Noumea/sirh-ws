package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.eae.Eae;
import nc.noumea.mairie.model.bean.eae.EaeCampagne;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class EaesServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testgetEaesGedIdsForAgents_returnListOfEaeFinalisationGedIds() {
		// Given
		List<Integer> agentIds = Arrays.asList(1, 2);

		TypedQuery<String> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList("uno", "dos"));

		EntityManager eaeEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(eaeEMMock.createQuery(Mockito.anyString(), Mockito.eq(String.class))).thenReturn(mockQuery);

		EaesService service = new EaesService();
		ReflectionTestUtils.setField(service, "eaeEntityManager", eaeEMMock);

		// When
		List<String> result = service.getEaesGedIdsForAgents(agentIds, 2011);

		// Then
		assertEquals(2, result.size());
		assertEquals("uno", result.get(0));
		assertEquals("dos", result.get(1));

		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("annee", 2011);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("agentIds", agentIds);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testgetEaesGedIdsForAgents_AgentListIsEmpty_setPAramAsnull() {
		// Given
		List<Integer> agentIds = new ArrayList<Integer>();

		TypedQuery<String> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<String>());

		EntityManager eaeEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(eaeEMMock.createQuery(Mockito.anyString(), Mockito.eq(String.class))).thenReturn(mockQuery);

		EaesService service = new EaesService();
		ReflectionTestUtils.setField(service, "eaeEntityManager", eaeEMMock);

		// When
		List<String> result = service.getEaesGedIdsForAgents(agentIds, 2011);

		// Then
		assertEquals(0, result.size());

		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("annee", 2011);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("agentIds", null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void compterlistIdEaeByCampagneAndAgent_returnCount() {
		// Given
		EaeCampagne camp = new EaeCampagne();
		camp.setIdCampagneEae(1);

		Integer idAgent = 9005138;
		List<Integer> agentIds = Arrays.asList(1, 2);

		TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn((long) 2);

		EntityManager eaeEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(eaeEMMock.createQuery(Mockito.anyString(), Mockito.eq(Long.class))).thenReturn(mockQuery);

		EaesService service = new EaesService();
		ReflectionTestUtils.setField(service, "eaeEntityManager", eaeEMMock);

		// When
		Integer result = service.compterlistIdEaeByCampagneAndAgent(camp.getIdCampagneEae(), agentIds, idAgent);

		// Then
		assertEquals("2", result.toString());

		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCampagne", camp.getIdCampagneEae());
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAgent", idAgent);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAgents", agentIds);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void compterlistIdEaeByCampagneAndAgent_AgentListIsEmpty_setPAramAsnull() {
		// Given
		EaeCampagne camp = new EaeCampagne();
		camp.setIdCampagneEae(1);

		Integer idAgent = 9005138;

		List<Integer> agentIds = new ArrayList<Integer>();

		TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn((long) 0);

		EntityManager eaeEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(eaeEMMock.createQuery(Mockito.anyString(), Mockito.eq(Long.class))).thenReturn(mockQuery);

		EaesService service = new EaesService();
		ReflectionTestUtils.setField(service, "eaeEntityManager", eaeEMMock);

		// When
		Integer result = service.compterlistIdEaeByCampagneAndAgent(camp.getIdCampagneEae(), agentIds, idAgent);

		// Then
		assertEquals("0", result.toString());

		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCampagne", camp.getIdCampagneEae());
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAgent", idAgent);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAgents", null);
	}

	@Test
	public void findEaeByAgentAndYear_ReturnNull() {
		// Given
		Integer idAgent = 9005138;

		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Eae>());

		EntityManager eaeEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(eaeEMMock.createNativeQuery(Mockito.anyString(), Mockito.eq(Eae.class))).thenReturn(mockQuery);

		EaesService service = new EaesService();
		ReflectionTestUtils.setField(service, "eaeEntityManager", eaeEMMock);

		// When
		Eae result = service.findEaeByAgentAndYear(idAgent, "2014");

		// Then
		assertNull(result);

		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAgent", idAgent);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("annee", "2014");
	}

	@Test
	public void findEaeByAgentAndYear_ReturnEae() {
		// Given
		Integer idAgent = 9005138;
		Eae eae = new Eae();
		eae.setIdEae(1);

		List<Eae> listEae = new ArrayList<Eae>();
		listEae.add(eae);

		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listEae);

		EntityManager eaeEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(eaeEMMock.createNativeQuery(Mockito.anyString(), Mockito.eq(Eae.class))).thenReturn(mockQuery);

		EaesService service = new EaesService();
		ReflectionTestUtils.setField(service, "eaeEntityManager", eaeEMMock);

		// When
		Eae result = service.findEaeByAgentAndYear(idAgent, "2014");

		// Then
		assertNotNull(result);

		assertEquals(eae.getIdEae(), result.getIdEae());

		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAgent", idAgent);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("annee", "2014");
	}
}
