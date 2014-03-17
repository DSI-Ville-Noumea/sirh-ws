package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Siidma;
import nc.noumea.mairie.model.bean.Utilisateur;
import nc.noumea.mairie.model.pk.SiidmaId;
import nc.noumea.mairie.service.SiidmaService;
import nc.noumea.mairie.service.sirh.AgentService;
import nc.noumea.mairie.service.sirh.UtilisateurService;
import nc.noumea.mairie.web.dto.ReturnMessageDto;

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

		SiidmaService siidmaEMMock = Mockito.mock(SiidmaService.class);
		Mockito.when(siidmaEMMock.chercherSiidmaByIdIndi(9005138)).thenReturn(null);

		UtilisateurService siservService = new UtilisateurService();
		ReflectionTestUtils.setField(siservService, "agentSrv", agSrvEMMock);
		ReflectionTestUtils.setField(siservService, "siidmaSrv", siidmaEMMock);

		// When
		ReturnMessageDto result = siservService.isUtilisateurSIRH("9005138");

		// Then
		assertEquals(1, result.getErrors().size());
		assertEquals("L'agent 9005138 n'existe pas dans SIIDMA.", result.getErrors().get(0));

	}

	@Test
	public void isUtilisateurSIRH_returnEmptyReturmMessageDto_NotUtilisateur() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		SiidmaId id = new SiidmaId();
		id.setCdidut("LOGIN");
		id.setIdIndi(905138);
		Siidma sii = new Siidma();
		sii.setId(id);
		sii.setNomatr(5138);

		List<Utilisateur> listUtil = new ArrayList<>();

		AgentService agSrvEMMock = Mockito.mock(AgentService.class);
		Mockito.when(agSrvEMMock.getAgent(9005138)).thenReturn(ag);

		SiidmaService siidmaEMMock = Mockito.mock(SiidmaService.class);
		Mockito.when(siidmaEMMock.chercherSiidmaByIdIndi(905138)).thenReturn(sii);

		@SuppressWarnings("unchecked")
		TypedQuery<Utilisateur> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listUtil);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Utilisateur.class))).thenReturn(mockQuery);

		UtilisateurService siservService = new UtilisateurService();
		ReflectionTestUtils.setField(siservService, "agentSrv", agSrvEMMock);
		ReflectionTestUtils.setField(siservService, "siidmaSrv", siidmaEMMock);
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

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
		SiidmaId id = new SiidmaId();
		id.setCdidut("LOGIN");
		id.setIdIndi(905138);
		Siidma sii = new Siidma();
		sii.setId(id);
		sii.setNomatr(5138);

		Utilisateur u = new Utilisateur();
		u.setLogin("login");
		List<Utilisateur> listUtil = new ArrayList<>();
		listUtil.add(u);

		AgentService agSrvEMMock = Mockito.mock(AgentService.class);
		Mockito.when(agSrvEMMock.getAgent(9005138)).thenReturn(ag);

		SiidmaService siidmaEMMock = Mockito.mock(SiidmaService.class);
		Mockito.when(siidmaEMMock.chercherSiidmaByIdIndi(905138)).thenReturn(sii);

		@SuppressWarnings("unchecked")
		TypedQuery<Utilisateur> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listUtil);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Utilisateur.class))).thenReturn(mockQuery);

		UtilisateurService siservService = new UtilisateurService();
		ReflectionTestUtils.setField(siservService, "agentSrv", agSrvEMMock);
		ReflectionTestUtils.setField(siservService, "siidmaSrv", siidmaEMMock);
		ReflectionTestUtils.setField(siservService, "sirhEntityManager", sirhEMMock);

		// When
		ReturnMessageDto result = siservService.isUtilisateurSIRH("9005138");

		// Then
		assertEquals(0, result.getErrors().size());

	}
}
