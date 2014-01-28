package nc.noumea.mairie.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import nc.noumea.mairie.model.service.SpadmnService;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SpadmnServiceTest {

	@Test
	public void estPAActive_returnTrue() {
		// Given
		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(1);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNativeQuery(Mockito.anyString())).thenReturn(mockQuery);

		SpadmnService paService = new SpadmnService();
		ReflectionTestUtils.setField(paService, "sirhEntityManager", sirhEMMock);

		// When
		boolean result = paService.estPAActive(5138, 20131215);

		// Then
		assertTrue(result);

	}

	@Test
	public void estPAActive_returnFalse() {
		// Given
		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(0);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNativeQuery(Mockito.anyString())).thenReturn(mockQuery);

		SpadmnService paService = new SpadmnService();
		ReflectionTestUtils.setField(paService, "sirhEntityManager", sirhEMMock);

		// When
		boolean result = paService.estPAActive(5138, 20131215);

		// Then
		assertFalse(result);

	}
}