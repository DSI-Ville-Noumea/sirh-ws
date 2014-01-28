package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.PrimePointageFP;
import nc.noumea.mairie.model.pk.PrimePointageFPPK;
import nc.noumea.mairie.model.service.FichePosteService;
import nc.noumea.mairie.ws.ISirhPtgWSConsumer;
import nc.noumea.mairie.ws.dto.RefPrimeDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class FichePosteServiceTest {

	@Test
	public void getFichePostePrimaireAgentAffectationEnCours_returnFichePoste() {
		// Given
		FichePoste fp = new FichePoste();
		fp.setIdFichePoste(12);

		List<FichePoste> listeFDP = new ArrayList<FichePoste>();
		listeFDP.add(fp);

		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		FichePoste result = ficheService.getFichePostePrimaireAgentAffectationEnCours(9005138, new Date());

		// Then
		assertEquals(fp.getIdFichePoste(), result.getIdFichePoste());

	}

	@Test
	public void estResponsable_returnTrue() {
		// Given
		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(1);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNativeQuery(Mockito.anyString())).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		boolean result = ficheService.estResponsable(9005138);

		// Then
		assertTrue(result);
	}

	@Test
	public void estResponsable_returnFAlse() {
		// Given
		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(0);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNativeQuery(Mockito.anyString())).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		boolean result = ficheService.estResponsable(9005138);

		// Then
		assertFalse(result);
	}

	@Test
	public void getFichePosteSecondaireAgentAffectationEnCours_returnNull() {
		// Given
		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<FichePoste>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		FichePoste result = ficheService.getFichePosteSecondaireAgentAffectationEnCours(9005138, new Date());

		// Then
		assertNull(result);

	}

	@Test
	public void getFichePosteSecondaireAgentAffectationEnCours_returnFichePoste() {
		// Given
		FichePoste fp = new FichePoste();
		fp.setIdFichePoste(12);

		List<FichePoste> listeFDP = new ArrayList<FichePoste>();
		listeFDP.add(fp);

		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		FichePoste result = ficheService.getFichePosteSecondaireAgentAffectationEnCours(9005138, new Date());

		// Then
		assertEquals(fp.getIdFichePoste(), result.getIdFichePoste());

	}

	@Test
	public void getTitrePosteAgent_returnTitre() {
		// Given
		String titre1 = "TEST1";
		String titre2 = "TEST2";

		List<String> listeFDP = new ArrayList<String>();
		listeFDP.add(titre1);
		listeFDP.add(titre2);

		@SuppressWarnings("unchecked")
		TypedQuery<String> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(String.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		String result = ficheService.getTitrePosteAgent(9005138, new Date());

		// Then
		assertEquals(titre1, result);
	}

	@Test
	public void getIdFichePostePrimaireAgentAffectationEnCours_return0() {
		// Given
		@SuppressWarnings("unchecked")
		TypedQuery<Integer> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Integer>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		Integer result = ficheService.getIdFichePostePrimaireAgentAffectationEnCours(9005138, new Date());

		// Then
		assertEquals("0", result.toString());

	}

	@Test
	public void getIdFichePostePrimaireAgentAffectationEnCours_returnIdFDP() {
		// Given
		List<Integer> listeFDP = new ArrayList<Integer>();
		listeFDP.add(12);

		@SuppressWarnings("unchecked")
		TypedQuery<Integer> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		Integer result = ficheService.getIdFichePostePrimaireAgentAffectationEnCours(9005138, new Date());

		// Then
		assertEquals("12", result.toString());

	}

	@Test
	public void getFichePosteById_returnFDP() {
		// Given
		FichePoste fdp1 = new FichePoste();
		fdp1.setIdFichePoste(12);
		fdp1.setAnnee(2013);

		List<FichePoste> listeFDP = new ArrayList<FichePoste>();
		listeFDP.add(fdp1);

		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		FichePoste result = ficheService.getFichePosteById(12);

		// Then
		assertEquals(fdp1.getIdFichePoste(), result.getIdFichePoste());
		assertEquals(fdp1.getAnnee(), result.getAnnee());

	}

	@Test
	public void getFichePosteDetailleSIRHByIdWithRefPrime_returnFDP() {
		// Given
		PrimePointageFPPK primePointageFPPK1 = new PrimePointageFPPK();
		primePointageFPPK1.setNumRubrique(1010);
		PrimePointageFP pp1 = new PrimePointageFP();
		pp1.setPrimePointageFPPK(primePointageFPPK1);
		pp1.setLibelle("prime 1");

		FichePoste fdp1 = new FichePoste();
		fdp1.setIdFichePoste(12);
		fdp1.setAnnee(2013);
		pp1.setIdFichePoste(fdp1);
		fdp1.getPrimePointageFP().add(pp1);

		RefPrimeDto refDto1 = new RefPrimeDto();
		refDto1.setLibelle("Nono1");

		List<FichePoste> listeFDP = new ArrayList<FichePoste>();
		listeFDP.add(fdp1);

		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQuery);

		ISirhPtgWSConsumer sirhPtgConsumerMock = Mockito.mock(ISirhPtgWSConsumer.class);
		Mockito.when(sirhPtgConsumerMock.getPrime(1010)).thenReturn(refDto1);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(ficheService, "sirhPtgWSConsumer", sirhPtgConsumerMock);

		// When
		FichePoste result = ficheService.getFichePosteDetailleSIRHByIdWithRefPrime(12);

		// Then
		assertEquals(fdp1.getIdFichePoste(), result.getIdFichePoste());
		assertEquals(fdp1.getAnnee(), result.getAnnee());
		assertEquals(1, result.getPrimePointageFP().size());

		Iterator<PrimePointageFP> i = result.getPrimePointageFP().iterator();
		while (i.hasNext()) {
			PrimePointageFP r = i.next();
			assertEquals(refDto1.getLibelle(), r.getLibelle());

		}
	}
}