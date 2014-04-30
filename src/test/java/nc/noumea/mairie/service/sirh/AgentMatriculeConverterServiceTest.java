package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AgentMatriculeConverterServiceTest {

	@Test
	public void testfromADIdAgentToEAEIdAgent_withIdNot5digits_throwException() {

		// Given
		int theIdToConvert = 89;
		AgentMatriculeConverterService service = new AgentMatriculeConverterService();

		// When
		int res = service.fromADIdAgentToEAEIdAgent(theIdToConvert);

		assertEquals(0, res);

	}

	@Test
	public void testfromADIdAgentToEAEIdAgent_withIdIs5digits_convertItTo6Digits() {

		// Given
		int theIdToConvert = 906898;
		AgentMatriculeConverterService service = new AgentMatriculeConverterService();

		// When
		int result = service.fromADIdAgentToEAEIdAgent(theIdToConvert);

		// Then
		assertEquals(9006898, result);
	}

	@Test
	public void testTryConvertFromADIdAgentToEAEIdAgent_withIdIs5digits_convertItTo6Digits() {

		// Given
		int theIdToConvert = 906898;
		AgentMatriculeConverterService service = new AgentMatriculeConverterService();

		// When
		int result = service.tryConvertFromADIdAgentToEAEIdAgent(theIdToConvert);

		// Then
		assertEquals(9006898, result);
	}

	@Test
	public void testTryConvertFromADIdAgentToEAEIdAgent_withIdIs6digits() {

		// Given
		int theIdToConvert = 9006898;
		AgentMatriculeConverterService service = new AgentMatriculeConverterService();

		// When
		int result = service.tryConvertFromADIdAgentToEAEIdAgent(theIdToConvert);

		// Then
		assertEquals(9006898, result);
	}
}