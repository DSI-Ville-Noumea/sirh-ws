package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class AffectationServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void getAffectationById_returnNull() {
		// Given
		int idAffectation = 1;

		TypedQuery<Affectation> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(null);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Affectation.class))).thenReturn(mockQuery);

		AffectationService service = new AffectationService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Affectation result = service.getAffectationById(idAffectation);

		// Then
		assertNull(result);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAffectation", idAffectation);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAffectationById_returnAffectation() {
		// Given
		int idAffectation = 1;

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setAgent(ag);

		TypedQuery<Affectation> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(aff));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Affectation.class))).thenReturn(mockQuery);

		AffectationService service = new AffectationService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Affectation result = service.getAffectationById(idAffectation);

		// Then
		assertNotNull(result);
		assertEquals(aff.getIdAffectation(), result.getIdAffectation());
		assertEquals(aff.getAgent().getIdAgent(), result.getAgent().getIdAgent());
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAffectation", idAffectation);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAffectationByIdFichePoste_returnNull() {
		// Given
		int idFDP = 1;

		TypedQuery<Affectation> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(null);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Affectation.class))).thenReturn(mockQuery);

		AffectationService service = new AffectationService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Affectation result = service.getAffectationByIdFichePoste(idFDP);

		// Then
		assertNull(result);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idFichePoste", idFDP);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAffectationByIdFichePoste_returnAffectation() {
		// Given
		int idFDP = 1;

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setAgent(ag);

		TypedQuery<Affectation> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(aff));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Affectation.class))).thenReturn(mockQuery);

		AffectationService service = new AffectationService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Affectation result = service.getAffectationByIdFichePoste(idFDP);

		// Then
		assertNotNull(result);
		assertEquals(aff.getIdAffectation(), result.getIdAffectation());
		assertEquals(aff.getAgent().getIdAgent(), result.getAgent().getIdAgent());
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idFichePoste", idFDP);

	}
}
