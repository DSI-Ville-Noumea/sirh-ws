package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.SiBanqGuichet;
import nc.noumea.mairie.model.bean.Sibanq;
import nc.noumea.mairie.model.pk.SiguicId;
import nc.noumea.mairie.web.dto.BanqueGuichetDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SibanqServiceTest {

	@Test
	public void getBanque_returnSibanq() {
		// Given
		Sibanq g1 = new Sibanq();
		g1.setIdBanque(1);
		g1.setLiBanque("LIB BANQUE");

		List<Sibanq> listeBanque = new ArrayList<Sibanq>();
		listeBanque.add(g1);

		@SuppressWarnings("unchecked")
		TypedQuery<Sibanq> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeBanque);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Sibanq.class))).thenReturn(mockQuery);

		SibanqService service = new SibanqService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Sibanq result = service.getBanque(1);

		// Then
		assertEquals(new Integer(1), result.getIdBanque());
		assertEquals("LIB BANQUE", result.getLiBanque());

	}

	@Test
	public void getBanque_returnNull() {
		// Given
		Sibanq g1 = new Sibanq();
		g1.setIdBanque(1);
		g1.setLiBanque("LIB BANQUE");

		@SuppressWarnings("unchecked")
		TypedQuery<Sibanq> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Sibanq>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Sibanq.class))).thenReturn(mockQuery);

		SibanqService service = new SibanqService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Sibanq result = service.getBanque(1);

		// Then
		assertNull(result);

	}

	@Test
	public void getListBanqueAvecGuichet_noResult() {

		@SuppressWarnings("unchecked")
		TypedQuery<SiBanqGuichet> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<SiBanqGuichet>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(SiBanqGuichet.class))).thenReturn(mockQuery);

		SibanqService service = new SibanqService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		List<BanqueGuichetDto> result = service.getListBanqueAvecGuichet();

		assertEquals(0, result.size());
	}

	@Test
	public void getListBanqueAvecGuichet_1result() {
		SiguicId id = new SiguicId();
		id.setCodeBanque(1);
		id.setCodeGuichet(20);
		SiBanqGuichet banq1 = new SiBanqGuichet();
		banq1.setId(id);
		banq1.setLiBanque("banque");
		banq1.setLiGuichet("guichet");

		List<SiBanqGuichet> listBanqu = new ArrayList<SiBanqGuichet>();
		listBanqu.add(banq1);

		@SuppressWarnings("unchecked")
		TypedQuery<SiBanqGuichet> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listBanqu);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(SiBanqGuichet.class))).thenReturn(mockQuery);

		SibanqService service = new SibanqService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		List<BanqueGuichetDto> result = service.getListBanqueAvecGuichet();

		assertEquals(1, result.size());
		assertEquals("00001", result.get(0).getCodeBanque());
		assertEquals("banque", result.get(0).getLibelleBanque());
		assertEquals("00020", result.get(0).getCodeGuichet());
		assertEquals("guichet", result.get(0).getLibelleGuichet());
	}
}