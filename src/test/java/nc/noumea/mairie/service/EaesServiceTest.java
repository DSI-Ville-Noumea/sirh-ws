package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
}
