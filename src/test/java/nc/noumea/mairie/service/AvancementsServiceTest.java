package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.Cap;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementCorpsDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class AvancementsServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testgetCorpsForCadreEmploi_noCorps_returnNull() {
		// Given
		int idCadreEmploi = 99;
		
		TypedQuery<Spgeng> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Spgeng>());
		
		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getSpgengFromCadreEmploi", Spgeng.class)).thenReturn(mockQuery);
		
		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		
		// When
		List<Spgeng> result = service.getCorpsForCadreEmploi(idCadreEmploi);
		
		// Then
		assertNull(result);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCadreEmploi", idCadreEmploi);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testgetCorpsForCadreEmploi_2Corps_returnListOf3Strings() {
		// Given
		int idCadreEmploi = 99;
		Spgeng g1 = new Spgeng();
		g1.setCdgeng("toto");
		Spgeng g2 = new Spgeng();
		g2.setCdgeng("titi");
		
		TypedQuery<Spgeng> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(g1, g2));
		
		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getSpgengFromCadreEmploi", Spgeng.class)).thenReturn(mockQuery);
		
		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		
		// When
		List<Spgeng> result = service.getCorpsForCadreEmploi(idCadreEmploi);
		
		// Then
		assertEquals(2, result.size());
		assertEquals("toto", result.get(0).getCdgeng());
		assertEquals("titi", result.get(1).getCdgeng());
		
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCadreEmploi", idCadreEmploi);
	}
	
	@Test
	public void testcreateCommissionCorps_2AD_fill2AD() {
		// Given
		Cap cap = new Cap();
		Spgeng corps = new Spgeng();
		AvancementFonctionnaire avct1 = new AvancementFonctionnaire();
		avct1.setIdModifAvancement(new Integer(7));
		AvancementFonctionnaire avct2 = new AvancementFonctionnaire();
		avct2.setIdModifAvancement(new Integer(7));
		List<AvancementFonctionnaire> list = Arrays.asList(avct1, avct2);
		
		AvancementsService service = new AvancementsService();
		
		// When
		CommissionAvancementCorpsDto dto = service.createCommissionCorps(cap, corps, list);
		
		// Then
		assertEquals(2, dto.getAvancementsDifferencies().getAvancementsItems().size());
		assertEquals(0, dto.getChangementClasses().getAvancementsItems().size());
	}
	
	@Test
	public void testcreateCommissionCorps_2CC_fill2CC() {
		// Given
		Cap cap = new Cap();
		Spgeng corps = new Spgeng();
		AvancementFonctionnaire avct1 = new AvancementFonctionnaire();
		avct1.setIdModifAvancement(new Integer(4));
		AvancementFonctionnaire avct2 = new AvancementFonctionnaire();
		avct2.setIdModifAvancement(new Integer(4));
		List<AvancementFonctionnaire> list = Arrays.asList(avct1, avct2);
		
		AvancementsService service = new AvancementsService();
		
		// When
		CommissionAvancementCorpsDto dto = service.createCommissionCorps(cap, corps, list);
		
		// Then
		assertEquals(0, dto.getAvancementsDifferencies().getAvancementsItems().size());
		assertEquals(2, dto.getChangementClasses().getAvancementsItems().size());
	}
	
	@Test
	public void testgetStatutFromCap_CapIsCOMMUNAL_Return12() {
		// Given
		Cap cap = new Cap();
		cap.setTypeCap("COMMUNAL");
		
		// When
		List<Integer> result = AvancementsService.getStatutFromCap(cap);
		
		// Then
		assertEquals(new Integer(1), result.get(0));
		assertEquals(new Integer(2), result.get(1));
	}
	
	@Test
	public void testgetStatutFromCap_CapIsTERRITORIAL_Return12() {
		// Given
		Cap cap = new Cap();
		cap.setTypeCap("TERRITORIAL");
		
		// When
		List<Integer> result = AvancementsService.getStatutFromCap(cap);
		
		// Then
		assertEquals(new Integer(18), result.get(0));
		assertEquals(new Integer(20), result.get(1));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testgetCap_1Cap_returnCap() {
		// Given
		int idCap = 99;
		Cap cap = new Cap();
		
		TypedQuery<Cap> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(cap));
		
		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class)).thenReturn(mockQuery);
		
		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		
		// When
		Cap result = service.getCap(idCap);
		
		// Then
		assertEquals(cap, result);
		
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCap", idCap);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testgetCap_0Cap_returnNull() {
		// Given
		int idCap = 99;
		
		TypedQuery<Cap> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Cap>());
		
		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class)).thenReturn(mockQuery);
		
		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		
		// When
		Cap result = service.getCap(idCap);
		
		// Then
		assertNull(result);
		
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCap", idCap);
	}
	
	@Test
	public void testgetAvancementsEaesForCapAndCadreEmploi_CapDoesNotExist_returnEmptyList() {
		// Given
		int idCap = 99;
		
		TypedQuery<Cap> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Cap>());
		
		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class)).thenReturn(mockQuery);
		
		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		
		// When
		List<String> result = service.getAvancementsEaesForCapAndCadreEmploi(idCap, 0);
		
		// Then
		assertEquals(0, result.size());
		
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCap", idCap);
	}
}
