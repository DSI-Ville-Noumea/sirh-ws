package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TitrePosteDtoTest extends AgentDto {

	@Test
	public void testTitrePosteDto_cst() {
		// Given
		String lib = "Nono";

		// When
		TitrePosteDto dto = new TitrePosteDto();
		dto.setLibTitrePoste(lib);

		// Then
		assertEquals(lib, dto.getLibTitrePoste());
	}
}
