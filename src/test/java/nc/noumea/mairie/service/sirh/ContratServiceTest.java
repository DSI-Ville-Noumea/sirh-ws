package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Contrat;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class ContratServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void getContratBetweenDate_returnNull() {
		// Given
		int idAgent = 1;
		Date dateDeb = new Date();

		TypedQuery<Contrat> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(null);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Contrat.class))).thenReturn(mockQuery);

		ContratService service = new ContratService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Contrat result = service.getContratBetweenDate(idAgent, dateDeb);

		// Then
		assertNull(result);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAgent", idAgent);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("dateDeb", dateDeb);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getContratBetweenDate_returnContrat() {
		// Given
		int idAgent = 1;
		Date dateDeb = new Date();

		Agent ag = new Agent();
		ag.setIdAgent(idAgent);
		Contrat c = new Contrat();
		c.setAgent(ag);
		c.setIdContrat(2);

		TypedQuery<Contrat> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(c));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Contrat.class))).thenReturn(mockQuery);

		ContratService service = new ContratService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Contrat result = service.getContratBetweenDate(idAgent, dateDeb);

		// Then
		assertNotNull(result);
		assertEquals(c.getIdContrat(), result.getIdContrat());
		assertEquals(c.getAgent().getIdAgent(), result.getAgent().getIdAgent());
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAgent", idAgent);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("dateDeb", dateDeb);

	}
}
