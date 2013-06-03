package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.AgentRecherche;

import org.junit.Test;

public class AgentRechercheDtoTest {

	@Test
	public void testAgentRechercheDto_cst() {
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
}
