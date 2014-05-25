package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AgentRecherche;

import org.junit.Test;

public class AgentDtoTest {

	@Test
	public void testAgentDto_cst() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomUsage("TITI");
		ag.setPrenomUsage("Nono");

		// When
		AgentDto dto = new AgentDto(ag);

		// Then
		assertEquals(ag.getDisplayNom(), dto.getNom());
		assertEquals(ag.getDisplayPrenom(), dto.getPrenom());
		assertEquals(ag.getIdAgent(), dto.getIdAgent());
	}

	@Test
	public void testAgentDto_cstNull() {
		// Given
		Agent ag = null;

		// When
		AgentDto dto = new AgentDto(ag);

		// Then
		assertNull(dto.getNom());
		assertNull(dto.getPrenom());
		assertNull(dto.getIdAgent());
	}

	@Test
	public void testAgentDto_cst_withAgentRecherche() {
		// Given
		AgentRecherche ag = new AgentRecherche();
		ag.setIdAgent(9005138);
		ag.setNomUsage("TITI");
		ag.setPrenomUsage("Nono");

		// When
		AgentDto dto = new AgentDto(ag);

		// Then
		assertEquals(ag.getDisplayNom(), dto.getNom());
		assertEquals(ag.getDisplayPrenom(), dto.getPrenom());
		assertEquals(ag.getIdAgent(), dto.getIdAgent());
	}

	@Test
	public void testAgentDto_cstNull_withAgentRecherche() {
		// Given
		AgentRecherche ag = null;

		// When
		AgentDto dto = new AgentDto(ag);

		// Then
		assertNull(dto.getNom());
		assertNull(dto.getPrenom());
		assertNull(dto.getIdAgent());
	}
}
