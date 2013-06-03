package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Agent;

import org.junit.Test;

public class AgentDtoTest  {

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
}
