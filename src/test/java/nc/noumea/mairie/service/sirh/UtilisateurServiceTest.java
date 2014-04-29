package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Utilisateur;
import nc.noumea.mairie.web.dto.LightUserDto;
import nc.noumea.mairie.web.dto.ReturnMessageDto;
import nc.noumea.mairie.ws.RadiWSConsumer;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class UtilisateurServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void getDirection_returnSiserv() {
		// Given
		Utilisateur u = new Utilisateur();
		u.setLogin("test");
		List<Utilisateur> listUtil = new ArrayList<>();
		listUtil.add(u);

		TypedQuery<Utilisateur> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listUtil);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Utilisateur.class))).thenReturn(mockQuery);

		UtilisateurService siservService = new UtilisateurService();
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		Utilisateur result = siservService.chercherUtilisateurSIRHByLogin("DDCA");

		// Then
		assertEquals("test", result.getLogin());

	}

	@Test
	public void isUtilisateurSIRH_returnEmptyReturmMessageDto_NotAgent() {
		// Given
		AgentService agSrvEMMock = Mockito.mock(AgentService.class);
		Mockito.when(agSrvEMMock.getAgent(9005138)).thenReturn(null);

		UtilisateurService siservService = new UtilisateurService();
		ReflectionTestUtils.setField(siservService, "agentSrv", agSrvEMMock);

		// When
		ReturnMessageDto result = siservService.isUtilisateurSIRH("9005138");

		// Then
		assertEquals(1, result.getErrors().size());
		assertEquals("L'agent 9005138 n'existe pas.", result.getErrors().get(0));

	}

	@Test
	public void isUtilisateurSIRH_returnEmptyReturmMessageDto_NotSiidma() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);

		AgentService agSrvEMMock = Mockito.mock(AgentService.class);
		Mockito.when(agSrvEMMock.getAgent(9005138)).thenReturn(ag);

		RadiWSConsumer radiMock = Mockito.mock(RadiWSConsumer.class);
		Mockito.when(radiMock.getNomatrWithIdAgent(9005138)).thenReturn("5138");
		Mockito.when(radiMock.getAgentCompteAD(5138)).thenReturn(null);

		UtilisateurService siservService = new UtilisateurService();
		ReflectionTestUtils.setField(siservService, "agentSrv", agSrvEMMock);
		ReflectionTestUtils.setField(siservService, "radiWSConsumer", radiMock);

		// When
		ReturnMessageDto result = siservService.isUtilisateurSIRH("9005138");

		// Then
		assertEquals(1, result.getErrors().size());
		assertEquals("L'agent 9005138 n'a pas de compte AD ou n'a pas son login renseigné.", result.getErrors().get(0));

	}

	@Test
	public void isUtilisateurSIRH_returnEmptyReturmMessageDto_NotUtilisateur() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);

		LightUserDto user = new LightUserDto();
		user.setEmployeeNumber(905138);
		user.setsAMAccountName("LOGIN");

		List<Utilisateur> listUtil = new ArrayList<>();

		AgentService agSrvEMMock = Mockito.mock(AgentService.class);
		Mockito.when(agSrvEMMock.getAgent(9005138)).thenReturn(ag);

		RadiWSConsumer radiMock = Mockito.mock(RadiWSConsumer.class);
		Mockito.when(radiMock.getNomatrWithIdAgent(9005138)).thenReturn("5138");
		Mockito.when(radiMock.getAgentCompteAD(5138)).thenReturn(user);

		@SuppressWarnings("unchecked")
		TypedQuery<Utilisateur> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listUtil);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Utilisateur.class))).thenReturn(mockQuery);

		UtilisateurService siservService = new UtilisateurService();
		ReflectionTestUtils.setField(siservService, "agentSrv", agSrvEMMock);
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(siservService, "radiWSConsumer", radiMock);

		// When
		ReturnMessageDto result = siservService.isUtilisateurSIRH("9005138");

		// Then
		assertEquals(1, result.getErrors().size());
		assertEquals("L'agent 9005138 n'est pas un utilisateur SIRH.", result.getErrors().get(0));

	}

	@Test
	public void isUtilisateurSIRH_returnEmptyReturmMessageDto_OK() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);

		LightUserDto user = new LightUserDto();
		user.setEmployeeNumber(905138);
		user.setsAMAccountName("LOGIN");

		Utilisateur u = new Utilisateur();
		u.setLogin("login");
		List<Utilisateur> listUtil = new ArrayList<>();
		listUtil.add(u);

		AgentService agSrvEMMock = Mockito.mock(AgentService.class);
		Mockito.when(agSrvEMMock.getAgent(9005138)).thenReturn(ag);

		@SuppressWarnings("unchecked")
		TypedQuery<Utilisateur> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listUtil);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Utilisateur.class))).thenReturn(mockQuery);

		RadiWSConsumer radiMock = Mockito.mock(RadiWSConsumer.class);
		Mockito.when(radiMock.getNomatrWithIdAgent(9005138)).thenReturn("5138");
		Mockito.when(radiMock.getAgentCompteAD(5138)).thenReturn(user);

		UtilisateurService siservService = new UtilisateurService();
		ReflectionTestUtils.setField(siservService, "agentSrv", agSrvEMMock);
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(siservService, "radiWSConsumer", radiMock);

		// When
		ReturnMessageDto result = siservService.isUtilisateurSIRH("9005138");

		// Then
		assertEquals(0, result.getErrors().size());

	}
}
