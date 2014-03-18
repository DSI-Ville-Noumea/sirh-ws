package nc.noumea.mairie.web.dto.avancements;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArreteListDtoTest {

	@Test
	public void testArreteListDto_ctor() {
		// Given

		// When
		ArreteListDto dto = new ArreteListDto();

		// Then
		assertEquals(0, dto.getArretes().size());
	}
}
