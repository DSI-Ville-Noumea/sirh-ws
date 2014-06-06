package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ListVMDtoTest {

	@Test
	public void ListVMDto_cst() {
		// Given

		// When
		ListVMDto dto = new ListVMDto();

		// Then
		assertNotNull(dto.getAccompagnements());
		assertNotNull(dto.getConvocations());
	}
}
