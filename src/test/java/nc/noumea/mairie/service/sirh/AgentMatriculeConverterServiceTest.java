package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AgentMatriculeConverterServiceTest {

	@Test
	public void testTryConvertFromADIdAgentToEAEIdAgent_withIdIs5digits_convertItTo6Digits() {

		// Given
		int theIdToConvert = 906898;
		AgentMatriculeConverterService service = new AgentMatriculeConverterService();

		// When
		int result = service.tryConvertFromADIdAgentToIdAgent(theIdToConvert);

		// Then
		assertEquals(9006898, result);
	}

	@Test
	public void testTryConvertFromADIdAgentToEAEIdAgent_withIdIs6digits() {

		// Given
		int theIdToConvert = 9006898;
		AgentMatriculeConverterService service = new AgentMatriculeConverterService();

		// When
		int result = service.tryConvertFromADIdAgentToIdAgent(theIdToConvert);

		// Then
		assertEquals(9006898, result);
	}
}
