package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.SpSold;
import nc.noumea.mairie.model.service.SoldeService;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SoldeServiceTest {

	@Test
	public void getSoldeConge_returnSiviet() {
		// Given
		SpSold g1 = new SpSold();
		g1.setNomatr(5138);
		g1.setSoldeAnneeEnCours(12.5);
		g1.setSoldeAnneeEnCours(21.7);

		List<SpSold> listeSpsold = new ArrayList<SpSold>();
		listeSpsold.add(g1);

		@SuppressWarnings("unchecked")
		TypedQuery<SpSold> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeSpsold);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(SpSold.class))).thenReturn(mockQuery);

		SoldeService soldeService = new SoldeService();
		ReflectionTestUtils.setField(soldeService, "sirhEntityManager", sirhEMMock);

		// When
		SpSold result = soldeService.getSoldeConge(5138);

		// Then
		assertEquals(g1.getNomatr(), result.getNomatr());
		assertEquals(g1.getSoldeAnneePrec(), result.getSoldeAnneePrec());
		assertEquals(g1.getSoldeAnneeEnCours(), result.getSoldeAnneeEnCours());

	}
}