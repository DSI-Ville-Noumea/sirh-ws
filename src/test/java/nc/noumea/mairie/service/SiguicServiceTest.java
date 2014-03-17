package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Siguic;
import nc.noumea.mairie.model.pk.SiguicId;
import nc.noumea.mairie.service.SiguicService;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SiguicServiceTest {

	@Test
	public void getBanque_returnSiguic() {
		// Given
		SiguicId id = new SiguicId();
		id.setCodeBanque(1);
		id.setCodeGuichet(2);

		Siguic g1 = new Siguic();
		g1.setId(id);
		g1.setLiGuic("LIB GUICHET");

		List<Siguic> listeGuichet = new ArrayList<Siguic>();
		listeGuichet.add(g1);

		@SuppressWarnings("unchecked")
		TypedQuery<Siguic> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeGuichet);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Siguic.class))).thenReturn(mockQuery);

		SiguicService guichetService = new SiguicService();
		ReflectionTestUtils.setField(guichetService, "sirhEntityManager", sirhEMMock);

		// When
		Siguic result = guichetService.getBanque(1, 1);

		// Then
		assertEquals(id.getCodeBanque(), result.getId().getCodeBanque());
		assertEquals(id.getCodeGuichet(), result.getId().getCodeGuichet());
		assertEquals("LIB GUICHET", result.getLiGuic());

	}
}