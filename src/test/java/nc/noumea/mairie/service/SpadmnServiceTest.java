package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.pk.SpadmnId;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.service.SpadmnService;
import nc.noumea.mairie.web.dto.PositionAdmAgentDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SpadmnServiceTest {

	@Test
	public void estPAActive_returnTrue() {
		// Given
		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(1);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNativeQuery(Mockito.anyString())).thenReturn(mockQuery);

		SpadmnService paService = new SpadmnService();
		ReflectionTestUtils.setField(paService, "sirhEntityManager", sirhEMMock);

		// When
		boolean result = paService.estPAActive(5138, 20131215);

		// Then
		assertTrue(result);

	}

	@Test
	public void estPAActive_returnFalse() {
		// Given
		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(0);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNativeQuery(Mockito.anyString())).thenReturn(mockQuery);

		SpadmnService paService = new SpadmnService();
		ReflectionTestUtils.setField(paService, "sirhEntityManager", sirhEMMock);

		// When
		boolean result = paService.estPAActive(5138, 20131215);

		// Then
		assertFalse(result);

	}
	
	@Test
	public void chercherPositionAdmAgentAncienne_return1result() {
		
		SpadmnId id = new SpadmnId();
			id.setDatdeb(2000);
			id.setNomatr(1111);
		Spadmn spAdm = new Spadmn();
			spAdm.setCdpadm("CODE");
			spAdm.setDatfin(2001);
			spAdm.setId(id);
		
		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.chercherPositionAdmAgentAncienne(Mockito.anyInt())).thenReturn(spAdm);
		
		SpadmnService paService = new SpadmnService();
		ReflectionTestUtils.setField(paService, "mairieRepository", mairieRepository);
		
		PositionAdmAgentDto result = paService.chercherPositionAdmAgentAncienne(1);
		
		assertNotNull(result);
		
		assertEquals("CODE", result.getCdpadm());
		assertEquals(2000, result.getDatdeb().intValue());
		assertEquals(2001, result.getDatfin().intValue());
		assertEquals(1111, result.getNomatr().intValue());
	}
	
	@Test
	public void chercherPositionAdmAgentAncienne_returnNull() {
		
		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.chercherPositionAdmAgentAncienne(Mockito.anyInt())).thenReturn(null);
		
		SpadmnService paService = new SpadmnService();
		ReflectionTestUtils.setField(paService, "mairieRepository", mairieRepository);
		
		PositionAdmAgentDto result = paService.chercherPositionAdmAgentAncienne(1);
		
		assertNull(result);
	}
	
	@Test
	public void chercherPositionAdmAgentEnCours_return1result() {
		
		SpadmnId id = new SpadmnId();
			id.setDatdeb(2000);
			id.setNomatr(1111);
		Spadmn spAdm = new Spadmn();
			spAdm.setCdpadm("CODE");
			spAdm.setDatfin(2001);
			spAdm.setId(id);
		
		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.chercherPositionAdmAgentEnCours(Mockito.anyInt())).thenReturn(spAdm);
		
		SpadmnService paService = new SpadmnService();
		ReflectionTestUtils.setField(paService, "mairieRepository", mairieRepository);
		
		PositionAdmAgentDto result = paService.chercherPositionAdmAgentEnCours(1);
		
		assertNotNull(result);
		
		assertEquals("CODE", result.getCdpadm());
		assertEquals(2000, result.getDatdeb().intValue());
		assertEquals(2001, result.getDatfin().intValue());
		assertEquals(1111, result.getNomatr().intValue());
	}
	
	@Test
	public void chercherPositionAdmAgentEnCours_returnNull() {
		
		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.chercherPositionAdmAgentEnCours(Mockito.anyInt())).thenReturn(null);
		
		SpadmnService paService = new SpadmnService();
		ReflectionTestUtils.setField(paService, "mairieRepository", mairieRepository);
		
		PositionAdmAgentDto result = paService.chercherPositionAdmAgentEnCours(1);
		
		assertNull(result);
	}
}