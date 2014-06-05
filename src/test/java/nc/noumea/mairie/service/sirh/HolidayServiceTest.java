package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.staticmock.MockStaticEntityMethods;
import org.springframework.test.util.ReflectionTestUtils;

@MockStaticEntityMethods
public class HolidayServiceTest {

	@Test
	public void isJourHoliday_True() {
		// Given
		DateTime dayTime = new DateTime(2013, 4, 9, 12, 9, 34);
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);

		@SuppressWarnings("unchecked")
		TypedQuery<Integer> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(list);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("isJourHoliday", Integer.class)).thenReturn(mockQuery);

		HolidayService service = new HolidayService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// Then
		assertEquals(true, service.isHoliday(dayTime.toDate()));
	}

	@Test
	public void isJourHoliday_FAlse() {
		// Given
		DateTime dayTime = new DateTime(2013, 4, 9, 12, 9, 34);

		@SuppressWarnings("unchecked")
		TypedQuery<Integer> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Integer>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("isJourHoliday", Integer.class)).thenReturn(mockQuery);

		HolidayService service = new HolidayService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// Then
		assertEquals(false, service.isHoliday(dayTime.toDate()));
	}

}
