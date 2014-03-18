package nc.noumea.mairie.ws.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class RefPrimeDtoTest {

	@Test
	public void testRefPrimeDto_cst() {
		// Given

		// When
		RefPrimeDto dto = new RefPrimeDto();
		dto.setAide("Aide");
		dto.setCalculee(false);
		dto.setDescription("Description");
		dto.setIdRefPrime(1);
		dto.setLibelle("lib");
		dto.setNumRubrique(2045);
		dto.setStatut("A");
		dto.setTypeSaisie("nb");

		// Then
		assertEquals("Aide", dto.getAide());
		assertFalse(dto.isCalculee());
		assertEquals("Description", dto.getDescription());
		assertEquals(1, (int) dto.getIdRefPrime());
		assertEquals("lib", dto.getLibelle());
		assertEquals(2045, (int) dto.getNumRubrique());
		assertEquals("A", dto.getStatut());
		assertEquals("nb", dto.getTypeSaisie());
	}
}
