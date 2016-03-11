package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Contrat;

import org.joda.time.DateTime;
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

	@Test
	public void getNbJoursPeriodeEssai_returnNull() {
		// Given
		Date dateDeb = new Date();
		Date dateFin = null;

		ContratService service = new ContratService();

		// When
		Integer result = service.getNbJoursPeriodeEssai(dateDeb, dateFin);

		// Then
		assertNull(result);

	}

	@Test
	public void getNbJoursPeriodeEssai_returnNb() {
		// Given
		Date dateDeb = new DateTime(2011, 03, 01, 0, 0, 0, 0).toDate();
		Date dateFin = new DateTime(2011, 05, 01, 0, 0, 0, 0).toDate();

		ContratService service = new ContratService();

		// When
		Integer result = service.getNbJoursPeriodeEssai(dateDeb, dateFin);

		// Then
		assertNotNull(result);
		assertEquals(new Integer(61), result);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void getContratById_returnContrat() {
		// Given
		int idContrat = 1;

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		Contrat c = new Contrat();
		c.setAgent(ag);
		c.setIdContrat(idContrat);

		TypedQuery<Contrat> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(c));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Contrat.class))).thenReturn(mockQuery);

		ContratService service = new ContratService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Contrat result = service.getContratById(idContrat);

		// Then
		assertNotNull(result);
		assertEquals(c.getIdContrat(), result.getIdContrat());
		assertEquals(c.getAgent().getIdAgent(), result.getAgent().getIdAgent());
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idContrat", idContrat);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void getContratById_returnNull() {
		// Given
		int idContrat = 1;

		TypedQuery<Contrat> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Contrat>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Contrat.class))).thenReturn(mockQuery);

		ContratService service = new ContratService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Contrat result = service.getContratById(idContrat);

		// Then
		assertNull(result);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idContrat", idContrat);
	}
	
	@Test
	public void isPeriodeEssai_pasContrat() {
		
		Integer idAgent = 9005138;
		Date dateRecherche = new Date();
		
		TypedQuery<Contrat> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(null);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Contrat.class))).thenReturn(mockQuery);

		ContratService service = new ContratService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		
		assertFalse(service.isPeriodeEssai(idAgent, dateRecherche));
	}
	
	@Test
	public void isPeriodeEssai_false() {
		
		Integer idAgent = 9005138;
		Date dateRecherche = new Date();
		
		List<Contrat> listContrats = new ArrayList<Contrat>();
		Contrat contrat = new Contrat();
		contrat.setDateFinPeriodeEssai(new DateTime(dateRecherche).minusDays(1).toDate());
		
		listContrats.add(contrat);
		
		TypedQuery<Contrat> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listContrats);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Contrat.class))).thenReturn(mockQuery);

		ContratService service = new ContratService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		
		assertFalse(service.isPeriodeEssai(idAgent, dateRecherche));
	}
	
	@Test
	public void isPeriodeEssai_true() {
		
		Integer idAgent = 9005138;
		Date dateRecherche = new Date();
		
		List<Contrat> listContrats = new ArrayList<Contrat>();
		Contrat contrat = new Contrat();
		contrat.setDateFinPeriodeEssai(new DateTime(dateRecherche).plusDays(1).toDate());
		
		listContrats.add(contrat);
		
		TypedQuery<Contrat> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listContrats);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Contrat.class))).thenReturn(mockQuery);

		ContratService service = new ContratService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		
		assertTrue(service.isPeriodeEssai(idAgent, dateRecherche));
	}
}
