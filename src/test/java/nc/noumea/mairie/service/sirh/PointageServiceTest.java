package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.BaseHorairePointage;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.web.dto.BaseHorairePointageDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class PointageServiceTest {

	@Test
	public void getBaseHorairePointageByAgent_returnDtoNotNull() {
		// Given
		BaseHorairePointage base = new BaseHorairePointage();
		base.setBaseCalculee(42.0);

		@SuppressWarnings("unchecked")
		TypedQuery<BaseHorairePointage> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(base);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNativeQuery(Mockito.anyString(), Mockito.eq(BaseHorairePointage.class)))
				.thenReturn(mockQuery);

		PointageService ptgService = new PointageService();
		ReflectionTestUtils.setField(ptgService, "sirhEntityManager", sirhEMMock);

		// When
		BaseHorairePointageDto result = ptgService.getBaseHorairePointageByAgent(9005138, new Date());

		// Then
		assertEquals(base.getBaseCalculee(), result.getBaseCalculee());

	}

	@Test
	public void getBaseHorairePointageByAgent_returnDtoNull() {
		// Given

		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(null);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNativeQuery(Mockito.anyString(), Mockito.eq(BaseHorairePointage.class)))
				.thenReturn(mockQuery);

		PointageService ptgService = new PointageService();
		ReflectionTestUtils.setField(ptgService, "sirhEntityManager", sirhEMMock);

		// When
		BaseHorairePointageDto result = ptgService.getBaseHorairePointageByAgent(9005138, new Date());

		// Then
		assertNull(result.getIdBaseHorairePointage());

	}
}