package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spcong;
import nc.noumea.mairie.service.SpcongService;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SpcongServiceTest {

	@Test
	public void getHistoriqueCongeAnnee_returnListSpcong() {
		// Given
		Spcong g1 = new Spcong();
		g1.setStatut("F");
		Spcong g2 = new Spcong();
		g2.setStatut("C");

		List<Spcong> listeSpcong = new ArrayList<Spcong>();
		listeSpcong.add(g1);
		listeSpcong.add(g2);

		@SuppressWarnings("unchecked")
		TypedQuery<Spcong> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeSpcong);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Spcong.class))).thenReturn(mockQuery);

		SpcongService spcongService = new SpcongService();
		ReflectionTestUtils.setField(spcongService, "sirhEntityManager", sirhEMMock);

		// When
		ArrayList<Spcong> result = (ArrayList<Spcong>) spcongService.getHistoriqueCongeAnnee((long) 5138);

		// Then
		assertEquals(2, result.size());
		assertEquals(g1.getStatut(), result.get(0).getStatut());
		assertEquals(g2.getStatut(), result.get(1).getStatut());

	}

	@Test
	public void getToutHistoriqueConge_returnListSpcong() {
		// Given
		Spcong g1 = new Spcong();
		g1.setStatut("F");
		Spcong g2 = new Spcong();
		g2.setStatut("C");

		List<Spcong> listeSpcong = new ArrayList<Spcong>();
		listeSpcong.add(g1);
		listeSpcong.add(g2);

		@SuppressWarnings("unchecked")
		TypedQuery<Spcong> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeSpcong);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Spcong.class))).thenReturn(mockQuery);

		SpcongService spcongService = new SpcongService();
		ReflectionTestUtils.setField(spcongService, "sirhEntityManager", sirhEMMock);

		// When
		ArrayList<Spcong> result = (ArrayList<Spcong>) spcongService.getToutHistoriqueConge((long) 5138);

		// Then
		assertEquals(2, result.size());
		assertEquals(g1.getStatut(), result.get(0).getStatut());
		assertEquals(g2.getStatut(), result.get(1).getStatut());

	}
}