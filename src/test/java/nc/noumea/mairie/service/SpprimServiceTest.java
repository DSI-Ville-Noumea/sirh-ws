package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.repository.ISpprimRepository;
import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.service.sirh.IFichePosteService;
import nc.noumea.mairie.ws.dto.EasyVistaDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SpprimServiceTest {

	@Test
	public void getChefServiceAgent_chefService_ReturnChefService() {
		// Given
		EasyVistaDto result = new EasyVistaDto();
		Agent agent = new Agent();
		agent.setIdAgent(9002990);
		agent.setNomatr(2990);
		Affectation aff = new Affectation();
		aff.setAgent(agent);

		ISpprimRepository primeRepo = Mockito.mock(ISpprimRepository.class);
		Mockito.when(primeRepo.getListChefsService()).thenReturn(Arrays.asList(9002990, 9008756));

		SpprimService service = new SpprimService();
		ReflectionTestUtils.setField(service, "primeRepo", primeRepo);

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

		ISpprimRepository primeRepo = Mockito.mock(ISpprimRepository.class);
		Mockito.when(primeRepo.getListChefsService()).thenReturn(Arrays.asList(9002990, 9008756));
		Mockito.when(primeRepo.getListDirecteur()).thenReturn(Arrays.asList(9008787, 9008755));

		SpprimService service = new SpprimService();
		ReflectionTestUtils.setField(service, "primeRepo", primeRepo);

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

		ISpprimRepository primeRepo = Mockito.mock(ISpprimRepository.class);
		Mockito.when(primeRepo.getListChefsService()).thenReturn(Arrays.asList(9002990, 9008756));
		Mockito.when(primeRepo.getListDirecteur()).thenReturn(Arrays.asList(9008787, 9008755));

		IFichePosteService fpSrv = Mockito.mock(IFichePosteService.class);
		Mockito.when(fpSrv.getListShdAgents(agent.getIdAgent(), 6)).thenReturn(Arrays.asList(9002990, 9008754));

		IAgentService agentSrv = Mockito.mock(IAgentService.class);
		Mockito.when(agentSrv.getAgent(9002990)).thenReturn(agChef);

		SpprimService service = new SpprimService();
		ReflectionTestUtils.setField(service, "primeRepo", primeRepo);
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

		ISpprimRepository primeRepo = Mockito.mock(ISpprimRepository.class);
		Mockito.when(primeRepo.getListChefsService()).thenReturn(Arrays.asList(9002990, 9008756));
		Mockito.when(primeRepo.getListDirecteur()).thenReturn(Arrays.asList(9008787, 9008755));

		IFichePosteService fpSrv = Mockito.mock(IFichePosteService.class);
		Mockito.when(fpSrv.getListShdAgents(agent.getIdAgent(), 6)).thenReturn(Arrays.asList(9002994, 9008754));

		SpprimService service = new SpprimService();
		ReflectionTestUtils.setField(service, "primeRepo", primeRepo);
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