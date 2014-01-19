package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Siidma;
import nc.noumea.mairie.model.pk.SiidmaId;
import nc.noumea.mairie.model.service.SiidmaService;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SiidmaServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void chercherSiidmaByIdIndi_returnNull() {
		// Given
		TypedQuery<Siidma> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Siidma>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siidma.class))).thenReturn(mockQuery);

		SiidmaService siidmaService = new SiidmaService();
		ReflectionTestUtils.setField(siidmaService, "sirhEntityManager", sirhEMMock);

		// When
		Siidma result = siidmaService.chercherSiidmaByIdIndi(905138);

		// Then
		assertEquals(null, result);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void chercherSiidmaByIdIndi_returnSiidma() {
		// Given
		SiidmaId idSiidma = new SiidmaId();
		idSiidma.setCdidut("");
		idSiidma.setIdIndi(905138);

		Siidma siidma = new Siidma();
		siidma.setId(idSiidma);
		siidma.setNomatr(5138);

		ArrayList<Siidma> list = new ArrayList<>();
		list.add(siidma);

		TypedQuery<Siidma> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(list);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siidma.class))).thenReturn(mockQuery);

		SiidmaService siidmaService = new SiidmaService();
		ReflectionTestUtils.setField(siidmaService, "sirhEntityManager", sirhEMMock);

		// When
		Siidma result = siidmaService.chercherSiidmaByIdIndi(905138);

		// Then
		assertEquals(siidma.getNomatr(), result.getNomatr());
	}
}
