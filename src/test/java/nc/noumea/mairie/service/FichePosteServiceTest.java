package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.service.FichePosteService;

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
}