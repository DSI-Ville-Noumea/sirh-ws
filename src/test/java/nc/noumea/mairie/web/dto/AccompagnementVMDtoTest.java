package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AccompagnementVMDtoTest {

	@Test
	public void AccompagnementVMDto_cstVide() {
		// Given

		// When
		AccompagnementVMDto dto = new AccompagnementVMDto();

		// Then
		assertNotNull(dto.getAgents());
	}

	@Test
	public void AccompagnementVMDto_cst() {
		// Given
		AgentWithServiceDto ag = new AgentWithServiceDto();
		ag.setIdAgent(9005138);
		ag.setNom("NOM");

		// When
		AccompagnementVMDto dto = new AccompagnementVMDto(ag);

		// Then
		assertNotNull(dto.getAgents());
		assertEquals(ag.getNom(), dto.getAgentResponsable().getNom());
	}
}
