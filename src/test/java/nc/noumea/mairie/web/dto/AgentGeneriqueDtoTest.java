package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.sirh.Agent;

import org.junit.Test;

public class AgentGeneriqueDtoTest {

	@Test
	public void AgentGeneriqueDto_cst() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomUsage("TITI");
		ag.setPrenomUsage("Nono");
		ag.setTitre("0");
		ag.setNomMarital("nom");
		ag.setNomatr(5138);

		// When
		AgentGeneriqueDto dto = new AgentGeneriqueDto(ag);

		// Then
		assertEquals(ag.getNomMarital(), dto.getNomMarital());
		assertEquals(ag.getPrenomUsage(), dto.getPrenomUsage());
		assertEquals(ag.getIdAgent(), dto.getIdAgent());
		assertEquals(ag.getNomatr(), dto.getNomatr());
	}

	@Test
	public void AgentGeneriqueDto_cstNull() {
		// Given
		Agent ag = null;

		// When
		AgentGeneriqueDto dto = new AgentGeneriqueDto(ag);

		// Then
		assertNull(dto.getNomMarital());
		assertNull(dto.getPrenomUsage());
		assertNull(dto.getIdAgent());
		assertNull(dto.getNomatr());
	}
}
