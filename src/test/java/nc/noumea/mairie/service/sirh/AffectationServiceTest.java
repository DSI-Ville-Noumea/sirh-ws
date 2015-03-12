package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
import nc.noumea.mairie.ws.dto.EasyVistaDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class AffectationServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void getAffectationById_returnNull() {
		// Given
		int idAffectation = 1;

		TypedQuery<Affectation> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(null);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Affectation.class))).thenReturn(mockQuery);

		AffectationService service = new AffectationService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Affectation result = service.getAffectationById(idAffectation);

		// Then
		assertNull(result);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAffectation", idAffectation);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAffectationById_returnAffectation() {
		// Given
		int idAffectation = 1;

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setAgent(ag);

		TypedQuery<Affectation> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(aff));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Affectation.class))).thenReturn(mockQuery);

		AffectationService service = new AffectationService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Affectation result = service.getAffectationById(idAffectation);

		// Then
		assertNotNull(result);
		assertEquals(aff.getIdAffectation(), result.getIdAffectation());
		assertEquals(aff.getAgent().getIdAgent(), result.getAgent().getIdAgent());
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idAffectation", idAffectation);

	}

	@Test
	public void getChefServiceAgent_chefService_ReturnChefService() {
		// Given
		EasyVistaDto result = new EasyVistaDto();
		Agent agent = new Agent();
		agent.setIdAgent(9002990);
		agent.setNomatr(2990);
		Affectation aff = new Affectation();
		aff.setAgent(agent);

		IAffectationRepository affRepo = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affRepo.getListChefsService()).thenReturn(Arrays.asList(9002990, 9008756));

		AffectationService service = new AffectationService();
		ReflectionTestUtils.setField(service, "affRepo", affRepo);

		// When
		result = service.getChefServiceAgent(aff, result);

		// Then
		assertNotNull(result.getErrors());
		assertNotNull(result.getInfos());
		assertEquals(0, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals(agent.getNomatr(), result.getNomatrChef());
	}

	@Test
	public void getChefServiceAgent_directeur_ReturnDirecteur() {
		// Given
		EasyVistaDto result = new EasyVistaDto();
		Agent agent = new Agent();
		agent.setIdAgent(9008787);
		agent.setNomatr(8787);
		Affectation aff = new Affectation();
		aff.setAgent(agent);

		IAffectationRepository affRepo = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affRepo.getListChefsService()).thenReturn(Arrays.asList(9002990, 9008756));
		Mockito.when(affRepo.getListDirecteur()).thenReturn(Arrays.asList(9008787, 9008755));

		AffectationService service = new AffectationService();
		ReflectionTestUtils.setField(service, "affRepo", affRepo);

		// When
		result = service.getChefServiceAgent(aff, result);

		// Then
		assertNotNull(result.getErrors());
		assertNotNull(result.getInfos());
		assertEquals(0, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals(agent.getNomatr(), result.getNomatrChef());
	}

	@Test
	public void getChefServiceAgent_agent_ReturnChefService() {
		// Given
		EasyVistaDto result = new EasyVistaDto();
		Agent agent = new Agent();
		agent.setIdAgent(9005138);
		agent.setNomatr(5138);
		Affectation aff = new Affectation();
		aff.setAgent(agent);

		Agent agChef = new Agent();
		agChef.setIdAgent(9002990);
		agChef.setNomatr(2990);

		IAffectationRepository affRepo = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affRepo.getListChefsService()).thenReturn(Arrays.asList(9002990, 9008756));
		Mockito.when(affRepo.getListDirecteur()).thenReturn(Arrays.asList(9008787, 9008755));

		IFichePosteService fpSrv = Mockito.mock(IFichePosteService.class);
		Mockito.when(fpSrv.getListShdAgents(agent.getIdAgent(), 6)).thenReturn(Arrays.asList(9002990, 9008754));

		IAgentService agentSrv = Mockito.mock(IAgentService.class);
		Mockito.when(agentSrv.getAgent(9002990)).thenReturn(agChef);

		AffectationService service = new AffectationService();
		ReflectionTestUtils.setField(service, "affRepo", affRepo);
		ReflectionTestUtils.setField(service, "fpSrv", fpSrv);
		ReflectionTestUtils.setField(service, "agentSrv", agentSrv);

		// When
		result = service.getChefServiceAgent(aff, result);

		// Then
		assertNotNull(result.getErrors());
		assertNotNull(result.getInfos());
		assertEquals(0, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals(agChef.getNomatr(), result.getNomatrChef());
	}

	@Test
	public void getChefServiceAgent_agent_ReturnNoBody() {
		// Given
		EasyVistaDto result = new EasyVistaDto();
		Agent agent = new Agent();
		agent.setIdAgent(9005138);
		agent.setNomatr(5138);
		Affectation aff = new Affectation();
		aff.setAgent(agent);

		Agent agChef = new Agent();
		agChef.setIdAgent(9002990);
		agChef.setNomatr(2990);

		IAffectationRepository affRepo = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affRepo.getListChefsService()).thenReturn(Arrays.asList(9002990, 9008756));
		Mockito.when(affRepo.getListDirecteur()).thenReturn(Arrays.asList(9008787, 9008755));

		IFichePosteService fpSrv = Mockito.mock(IFichePosteService.class);
		Mockito.when(fpSrv.getListShdAgents(agent.getIdAgent(), 6)).thenReturn(Arrays.asList(9002994, 9008754));

		AffectationService service = new AffectationService();
		ReflectionTestUtils.setField(service, "affRepo", affRepo);
		ReflectionTestUtils.setField(service, "fpSrv", fpSrv);

		// When
		result = service.getChefServiceAgent(aff, result);

		// Then
		assertNotNull(result.getErrors());
		assertNotNull(result.getInfos());
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Aucun chef de service trouvé.", result.getErrors().get(0));
	}
}
