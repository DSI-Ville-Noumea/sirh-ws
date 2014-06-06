package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Contrat;
import nc.noumea.mairie.model.bean.sirh.SuiviMedical;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SuiviMedicalServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void getSuiviMedicalById_returnNull() {
		// Given
		int id = 1;

		TypedQuery<SuiviMedical> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(null);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(SuiviMedical.class))).thenReturn(mockQuery);

		SuiviMedicalService service = new SuiviMedicalService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		SuiviMedical result = service.getSuiviMedicalById(id);

		// Then
		assertNull(result);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idSuiviMedical", id);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getSuiviMedicalById_returnContrat() {
		// Given
		int id = 1;

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		SuiviMedical c = new SuiviMedical();
		c.setAgent(ag);
		c.setIdSuiviMedical(id);

		TypedQuery<SuiviMedical> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(c));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(SuiviMedical.class))).thenReturn(mockQuery);

		SuiviMedicalService service = new SuiviMedicalService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		SuiviMedical result = service.getSuiviMedicalById(id);

		// Then
		assertNotNull(result);
		assertEquals(c.getIdSuiviMedical(), result.getIdSuiviMedical());
		assertEquals(c.getAgent().getIdAgent(), result.getAgent().getIdAgent());
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idSuiviMedical", id);

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
}
