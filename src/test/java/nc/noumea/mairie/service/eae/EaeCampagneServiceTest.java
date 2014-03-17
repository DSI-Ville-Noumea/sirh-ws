package nc.noumea.mairie.service.eae;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.eae.EaeCampagne;
import nc.noumea.mairie.service.eae.EaeCampagneService;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class EaeCampagneServiceTest {

	@Test
	public void getEaeCampagneOuverte_returnCampagne() {
		// Given
		EaeCampagne camp = new EaeCampagne();
		camp.setAnnee(2013);
		camp.setIdCampagneEae(1);

		@SuppressWarnings("unchecked")
		TypedQuery<EaeCampagne> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(camp);

		EntityManager eaeEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(eaeEMMock.createQuery(Mockito.anyString(), Mockito.eq(EaeCampagne.class))).thenReturn(mockQuery);

		EaeCampagneService campService = new EaeCampagneService();
		ReflectionTestUtils.setField(campService, "eaeEntityManager", eaeEMMock);

		// When
		EaeCampagne result = campService.getEaeCampagneOuverte();

		// Then
		assertEquals(camp.getIdCampagneEae(), result.getIdCampagneEae());
		assertEquals(camp.getAnnee(), result.getAnnee());

	}
}