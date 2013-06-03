package nc.noumea.mairie.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.AgentRecherche;
import nc.noumea.mairie.model.service.AgentService;
import nc.noumea.mairie.web.dto.AgentDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class AgentServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testlistAgentsEnActivite_returnListOfAgentDto() {
		// Given
		List<AgentRecherche> listeAgentRecherche = new ArrayList<AgentRecherche>();
		AgentRecherche ag1 = new AgentRecherche();
		ag1.setIdAgent(9005138);
		AgentRecherche ag2 = new AgentRecherche();
		ag2.setNomUsage("TEST NOM");
		listeAgentRecherche.add(ag1);
		listeAgentRecherche.add(ag2);

		TypedQuery<AgentRecherche> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeAgentRecherche);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(AgentRecherche.class))).thenReturn(mockQuery);

		AgentService service = new AgentService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		List<AgentDto> result = service.listAgentsEnActivite("QUIN", "");

		// Then
		assertEquals(2, result.size());
		assertEquals(ag1.getIdAgent(), result.get(0).getIdAgent());
		assertEquals("TEST NOM", result.get(1).getNom());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testlistAgentsEnActivite_AgentDtoListIsEmpty_setPAramAsnull() {
		// Given
		TypedQuery<AgentRecherche> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<AgentRecherche>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(AgentRecherche.class))).thenReturn(mockQuery);

		AgentService service = new AgentService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		List<AgentDto> result = service.listAgentsEnActivite("", "");

		// Then
		assertEquals(0, result.size());
	}
}
