package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.AccueilRh;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class KiosqueRhServiceTest {

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
}
