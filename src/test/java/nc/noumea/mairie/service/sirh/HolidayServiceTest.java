package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.JourFerie;
import nc.noumea.mairie.model.repository.sirh.SirhRepository;
import nc.noumea.mairie.web.dto.JourDto;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.staticmock.MockStaticEntityMethods;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

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
	public void isJourHoliday_False() {
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
	
	@Test
	public void isJourFerie_True() {
		// Given
		DateTime dayTime = new DateTime(2013, 4, 9, 12, 9, 34);
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);

		@SuppressWarnings("unchecked")
		TypedQuery<Integer> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(list);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("isJourFerie", Integer.class)).thenReturn(mockQuery);

		HolidayService service = new HolidayService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// Then
		assertEquals(true, service.isJourFerie(dayTime.toDate()));
	}

	@Test
	public void isJourFerie_False() {
		// Given
		DateTime dayTime = new DateTime(2013, 4, 9, 12, 9, 34);

		@SuppressWarnings("unchecked")
		TypedQuery<Integer> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Integer>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("isJourFerie", Integer.class)).thenReturn(mockQuery);

		HolidayService service = new HolidayService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// Then
		assertEquals(false, service.isJourFerie(dayTime.toDate()));
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeJoursFeries_2results() {
		
		List<JourFerie> list = new ArrayList<JourFerie>(); 
		JourFerie jour1 = new JourFerie();
		jour1.setIdJourFerie(1);
		jour1.setDateJour(new DateTime(2014, 12, 25, 0, 0, 0).toDate());
		jour1.setIdTypeJourFerie(1);
		JourFerie jour2 = new JourFerie();
		jour2.setIdJourFerie(2);
		jour2.setDateJour(new DateTime(2014, 12, 26, 0, 0, 0).toDate());
		jour2.setIdTypeJourFerie(2);
		list.addAll(Arrays.asList(jour1, jour2));
		
		SirhRepository sirhRepository = Mockito.mock(SirhRepository.class);
		Mockito.when(sirhRepository.getListeJoursFeries(Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(list);
		
		HolidayService service = new HolidayService();
		ReflectionTestUtils.setField(service, "sirhRepository", sirhRepository);
		
		List<JourDto> result = service.getListeJoursFeries(new DateTime(2014, 12, 1, 0, 0, 0).toDate(), new DateTime(2014, 12, 31, 0, 0, 0).toDate());
		
		assertEquals(result.size(), 2);
		assertEquals(result.get(0).getJour(), new DateTime(2014, 12, 25, 0, 0, 0).toDate());
		assertFalse(result.get(0).isChome());
		assertTrue(result.get(0).isFerie());
		assertEquals(result.get(1).getJour(), new DateTime(2014, 12, 26, 0, 0, 0).toDate());
		assertFalse(result.get(1).isFerie());
		assertTrue(result.get(1).isChome());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeJoursFeries_noResult() {
		
		List<JourFerie> list = new ArrayList<JourFerie>(); 
		
		SirhRepository sirhRepository = Mockito.mock(SirhRepository.class);
		Mockito.when(sirhRepository.getListeJoursFeries(Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(list);
		
		HolidayService service = new HolidayService();
		ReflectionTestUtils.setField(service, "sirhRepository", sirhRepository);
		
		List<JourDto> result = service.getListeJoursFeries(new DateTime(2014, 12, 1, 0, 0, 0).toDate(), new DateTime(2014, 12, 31, 0, 0, 0).toDate());
		
		assertEquals(result.size(), 0);
	}
}
