package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.ReferentRh;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class ReferentServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void getListeReferentRH_returnNull() {
		TypedQuery<ReferentRh> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<ReferentRh>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(ReferentRh.class))).thenReturn(mockQuery);

		ReferentService service = new ReferentService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		List<ReferentRh> result = service.getListeReferentRH();

		// Then

		assertEquals(0, result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getListeReferentRH_returnListOf2Referent() {
		// Given
		ReferentRh ref1 = new ReferentRh();
		ref1.setIdAgentReferent(9005138);
		ReferentRh ref2 = new ReferentRh();
		ref2.setIdAgentReferent(9005139);

		TypedQuery<ReferentRh> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(ref1, ref2));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(ReferentRh.class))).thenReturn(mockQuery);

		ReferentService service = new ReferentService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		List<ReferentRh> result = service.getListeReferentRH();

		// Then
		assertEquals(2, result.size());
		assertEquals(new Integer(9005138), result.get(0).getIdAgentReferent());
		assertEquals(new Integer(9005139), result.get(1).getIdAgentReferent());
	}
}
