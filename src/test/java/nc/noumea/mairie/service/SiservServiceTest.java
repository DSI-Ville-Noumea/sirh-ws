package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Siserv;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SiservServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void getDirection_returnSiserv() {
		// Given
		Siserv s = new Siserv();
		s.setServi("T");
		List<Siserv> listServ = new ArrayList<>();
		listServ.add(s);
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listServ);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Siserv result = siservService.getDirection("DDCA");

		// Then
		assertEquals("T", result.getServi());

	}

	@Test
	public void estAlphabetique_ParamNull_returnFalse() {
		// Given
		SiservService siservService = new SiservService();

		// When
		@SuppressWarnings("static-access")
		boolean result = siservService.estAlphabetique(null);

		// Then
		assertFalse(result);
	}

	@Test
	public void estAlphabetique_returnFalse() {
		// Given
		SiservService siservService = new SiservService();

		// When
		@SuppressWarnings("static-access")
		boolean result = siservService.estAlphabetique("1");

		// Then
		assertFalse(result);
	}

	@Test
	public void estAlphabetique_returnTrue() {
		// Given
		SiservService siservService = new SiservService();

		// When
		@SuppressWarnings("static-access")
		boolean result = siservService.estAlphabetique("DCCA");

		// Then
		assertTrue(result);
	}

	@Test
	public void getSection_returnNull() {
		// Given
		Siserv s = new Siserv();
		s.setServi("T");
		List<Siserv> listServ = new ArrayList<>();
		listServ.add(s);

		@SuppressWarnings("unchecked")
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listServ);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Siserv result = siservService.getSection("DDCA");

		// Then
		assertNull(result);

	}

	@Test
	public void getSection_returnSiserv() {
		// Given
		Siserv s = new Siserv();
		s.setServi("T");
		List<Siserv> listServ = new ArrayList<>();
		listServ.add(s);

		@SuppressWarnings("unchecked")
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listServ);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Siserv result = siservService.getSection("DDCB");

		// Then
		assertEquals("T", result.getServi());

	}

	@Test
	public void getDivision_returnNull() {
		// Given
		Siserv s = new Siserv();
		s.setServi("T");
		List<Siserv> listServ = new ArrayList<>();
		listServ.add(s);

		@SuppressWarnings("unchecked")
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listServ);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Siserv result = siservService.getDivision("DDAA");

		// Then
		assertNull(result);

	}

	@Test
	public void getDivision_returnSiserv() {
		// Given
		Siserv s = new Siserv();
		s.setServi("T");
		List<Siserv> listServ = new ArrayList<>();
		listServ.add(s);

		@SuppressWarnings("unchecked")
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listServ);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Siserv result = siservService.getDivision("DDCA");

		// Then
		assertEquals("T", result.getServi());

	}

	@Test
	public void getService_returnNull() {
		// Given
		@SuppressWarnings("unchecked")
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Siserv>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Siserv result = siservService.getService("DDAA");

		// Then
		assertNull(result);

	}

	@Test
	public void getService_returnSiserv() {
		// Given
		Siserv s = new Siserv();
		s.setServi("T");
		List<Siserv> listServ = new ArrayList<>();
		listServ.add(s);

		@SuppressWarnings("unchecked")
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listServ);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Siserv result = siservService.getService("DDCA");

		// Then
		assertEquals("T", result.getServi());

	}

	@Test
	public void getServiceAgent_returnSiserv() {
		// Given
		Siserv s = new Siserv();
		s.setServi("T");

		@SuppressWarnings("unchecked")
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(s);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Siserv result = siservService.getServiceAgent(9005138, null);

		// Then
		assertEquals("T", result.getServi());

	}

	@Test
	public void getListServiceActif_returnSiserv() {
		// Given
		Siserv s = new Siserv();
		s.setServi("T");

		@SuppressWarnings("unchecked")
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(s));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		List<Siserv> result = siservService.getListServiceActif();

		// Then
		assertEquals(1, result.size());

	}

	@Test
	public void getgetServiceBySigle_returnSiserv() {
		// Given
		Siserv s = new Siserv();
		s.setServi("T");

		@SuppressWarnings("unchecked")
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(s));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Siserv result = siservService.getServiceBySigle("S");

		// Then
		assertEquals("T", result.getServi());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void getDirectionPourEAE_returnSiserv() {
		// Given
		Siserv s = new Siserv();
		s.setServi("T");
		List<Siserv> listServ = new ArrayList<>();
		listServ.add(s);
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listServ);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Siserv result = siservService.getDirectionPourEAE("DDCA");

		// Then
		assertEquals("T", result.getServi());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void getDirectionPourEAE_DAG_returnSiserv() {
		// Given
		Siserv s = new Siserv();
		s.setServi("T");
		List<Siserv> listServ = new ArrayList<>();
		listServ.add(s);
		TypedQuery<Siserv> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listServ);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siserv.class))).thenReturn(mockQuery);

		SiservService siservService = new SiservService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Siserv result = siservService.getDirectionPourEAE("DAGA");

		// Then
		assertEquals("T", result.getServi());

	}
}
