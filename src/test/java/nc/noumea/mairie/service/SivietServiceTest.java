package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.SIVIET;
import nc.noumea.mairie.model.pk.SivietId;
import nc.noumea.mairie.model.service.SivietService;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SivietServiceTest {

	@Test
	public void getLieuNaissEtr_returnSiviet() {
		// Given
		SivietId id = new SivietId();
		id.setCodePays(1);
		id.setSousCodePays(2);

		SIVIET g1 = new SIVIET();
		g1.setId(id);
		g1.setLibCop("TEST VILLE");

		List<SIVIET> listeSiviet = new ArrayList<SIVIET>();
		listeSiviet.add(g1);

		@SuppressWarnings("unchecked")
		TypedQuery<SIVIET> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeSiviet);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(SIVIET.class))).thenReturn(mockQuery);

		SivietService sivietService = new SivietService();
		ReflectionTestUtils.setField(sivietService, "sirhEntityManager", sirhEMMock);

		// When
		SIVIET result = sivietService.getLieuNaissEtr(1, 1);

		// Then
		assertEquals(id.getCodePays(), result.getId().getCodePays());
		assertEquals(id.getSousCodePays(), result.getId().getSousCodePays());
		assertEquals("TEST VILLE", result.getLibCop());

	}
}