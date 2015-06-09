package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.AccueilRh;
import nc.noumea.mairie.model.bean.sirh.AlerteRh;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;
import nc.noumea.mairie.ws.ISirhAbsWSConsumer;
import nc.noumea.mairie.ws.ISirhPtgWSConsumer;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class KiosqueRhServiceTest {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;

	@Autowired
	IKiosqueRhService repository;

	@SuppressWarnings("unchecked")
	@Test
	public void getReferentRH_returnNull() {
		TypedQuery<ReferentRh> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(null);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(ReferentRh.class))).thenReturn(mockQuery);

		KiosqueRhService service = new KiosqueRhService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		ReferentRh result = service.getReferentRH("DCAA");

		// Then
		assertNull(result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getReferentRH_returnReferent() {
		// Given
		ReferentRh ref1 = new ReferentRh();
		ref1.setIdAgentReferent(9005138);
		ref1.setServi("DCAA");
		ReferentRh ref2 = new ReferentRh();
		ref2.setIdAgentReferent(9005139);
		ref2.setServi(null);

		TypedQuery<ReferentRh> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(ref1);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(ReferentRh.class))).thenReturn(mockQuery);

		KiosqueRhService service = new KiosqueRhService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		ReferentRh result = service.getReferentRH("DCAA");

		// Then
		assertEquals(new Integer(9005138), result.getIdAgentReferent());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getReferentRH_returnReferentGlobal() {
		// Given
		ReferentRh ref1 = new ReferentRh();
		ref1.setIdAgentReferent(9005138);
		ref1.setServi("DCAA");
		ReferentRh ref2 = new ReferentRh();
		ref2.setIdAgentReferent(9005139);
		ref2.setServi(null);

		TypedQuery<ReferentRh> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(ref2);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(ReferentRh.class))).thenReturn(mockQuery);

		KiosqueRhService service = new KiosqueRhService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		ReferentRh result = service.getReferentRH("DCAA");

		// Then
		assertEquals(new Integer(9005139), result.getIdAgentReferent());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getListeAccueilRh_returnNull() {
		TypedQuery<AccueilRh> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<AccueilRh>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(AccueilRh.class))).thenReturn(mockQuery);

		KiosqueRhService service = new KiosqueRhService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		List<AccueilRh> result = service.getListeAccueilRh();

		// Then

		assertEquals(0, result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getListeAccueilRh_returnListOf2AccueilRh() {
		// Given
		AccueilRh ref1 = new AccueilRh();
		ref1.setTexteAccueilKiosque("texte 1");
		AccueilRh ref2 = new AccueilRh();
		ref2.setTexteAccueilKiosque("texte 2");

		TypedQuery<AccueilRh> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(ref1, ref2));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(AccueilRh.class))).thenReturn(mockQuery);

		KiosqueRhService service = new KiosqueRhService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		List<AccueilRh> result = service.getListeAccueilRh();

		// Then
		assertEquals(2, result.size());
		assertEquals("texte 1", result.get(0).getTexteAccueilKiosque());
		assertEquals("texte 2", result.get(1).getTexteAccueilKiosque());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAlerte_returnListOf2AlerteRh() {
		// Given
		AlerteRh ref1 = new AlerteRh();
		ref1.setIdAlerteKiosque(1);
		ref1.setTexteAlerteKiosque("texte 1");
		ref1.setAgent(true);
		ref1.setApprobateurABS(true);
		ref1.setOperateurABS(true);
		ref1.setDateDebut(new DateTime(2014, 2, 2, 0, 0, 0).toDate());
		ref1.setDateFin(new DateTime(2016, 2, 2, 0, 0, 0).toDate());
		sirhPersistenceUnit.persist(ref1);
		AlerteRh ref2 = new AlerteRh();
		ref2.setIdAlerteKiosque(2);
		ref2.setTexteAlerteKiosque("texte 2");
		ref2.setAgent(true);
		ref2.setDateDebut(new DateTime(2014, 2, 2, 0, 0, 0).toDate());
		ref2.setDateFin(new DateTime(2016, 2, 2, 0, 0, 0).toDate());
		sirhPersistenceUnit.persist(ref2);
		AlerteRh ref3 = new AlerteRh();
		ref3.setIdAlerteKiosque(3);
		ref3.setTexteAlerteKiosque("texte 3");
		ref3.setDateDebut(new DateTime(2014, 2, 2, 0, 0, 0).toDate());
		ref3.setDateFin(new DateTime(2016, 2, 2, 0, 0, 0).toDate());
		sirhPersistenceUnit.persist(ref3);

		// When
		List<AlerteRh> result = repository.getListeAlerte(true, false, true, false, false);

		// Then
		assertEquals(2, result.size());
		assertEquals("texte 1", result.get(0).getTexteAlerteKiosque());
		assertEquals("texte 2", result.get(1).getTexteAlerteKiosque());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAlerteRHByAgent_returnNull() {
		Integer idAgent = 9005138;

		ISirhAbsWSConsumer sirhAbsWSConsumer = Mockito.mock(ISirhAbsWSConsumer.class);
		Mockito.when(sirhAbsWSConsumer.isUserApprobateur(idAgent)).thenReturn(false);
		Mockito.when(sirhAbsWSConsumer.isUserOperateur(idAgent)).thenReturn(false);
		Mockito.when(sirhAbsWSConsumer.isUserViseur(idAgent)).thenReturn(false);

		ISirhPtgWSConsumer sirhPtgWSConsumer = Mockito.mock(ISirhPtgWSConsumer.class);
		Mockito.when(sirhPtgWSConsumer.isUserApprobateur(idAgent)).thenReturn(false);
		Mockito.when(sirhPtgWSConsumer.isUserOperateur(idAgent)).thenReturn(false);

		TypedQuery<AlerteRh> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<AlerteRh>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(AlerteRh.class))).thenReturn(mockQuery);

		KiosqueRhService service = new KiosqueRhService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(service, "sirhAbsWSConsumer", sirhAbsWSConsumer);
		ReflectionTestUtils.setField(service, "sirhPtgWSConsumer", sirhPtgWSConsumer);

		// When
		ReturnMessageDto result = service.getAlerteRHByAgent(idAgent);

		// Then

		assertEquals(0, result.getInfos().size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAlerteRHByAgent_returnListOf2AlerteRh() {
		Integer idAgent = 9005138;

		AlerteRh ref1 = new AlerteRh();
		ref1.setIdAlerteKiosque(1);
		ref1.setTexteAlerteKiosque("texte 1");
		ref1.setAgent(true);
		ref1.setApprobateurABS(true);
		ref1.setOperateurABS(true);
		ref1.setDateDebut(new DateTime(2014, 2, 2, 0, 0, 0).toDate());
		ref1.setDateFin(new DateTime(2016, 2, 2, 0, 0, 0).toDate());
		AlerteRh ref2 = new AlerteRh();
		ref2.setIdAlerteKiosque(2);
		ref2.setTexteAlerteKiosque("texte 2");
		ref2.setAgent(true);
		ref2.setDateDebut(new DateTime(2014, 2, 2, 0, 0, 0).toDate());
		ref2.setDateFin(new DateTime(2016, 2, 2, 0, 0, 0).toDate());

		ISirhAbsWSConsumer sirhAbsWSConsumer = Mockito.mock(ISirhAbsWSConsumer.class);
		Mockito.when(sirhAbsWSConsumer.isUserApprobateur(idAgent)).thenReturn(false);
		Mockito.when(sirhAbsWSConsumer.isUserOperateur(idAgent)).thenReturn(false);
		Mockito.when(sirhAbsWSConsumer.isUserViseur(idAgent)).thenReturn(false);

		ISirhPtgWSConsumer sirhPtgWSConsumer = Mockito.mock(ISirhPtgWSConsumer.class);
		Mockito.when(sirhPtgWSConsumer.isUserApprobateur(idAgent)).thenReturn(false);
		Mockito.when(sirhPtgWSConsumer.isUserOperateur(idAgent)).thenReturn(false);

		TypedQuery<AlerteRh> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(ref1, ref2));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(AlerteRh.class))).thenReturn(mockQuery);

		KiosqueRhService service = new KiosqueRhService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(service, "sirhAbsWSConsumer", sirhAbsWSConsumer);
		ReflectionTestUtils.setField(service, "sirhPtgWSConsumer", sirhPtgWSConsumer);

		// When
		ReturnMessageDto result = service.getAlerteRHByAgent(idAgent);

		// Then
		assertEquals(2, result.getInfos().size());
		assertEquals("texte 1", result.getInfos().get(0));
		assertEquals("texte 2", result.getInfos().get(1));
	}
}
