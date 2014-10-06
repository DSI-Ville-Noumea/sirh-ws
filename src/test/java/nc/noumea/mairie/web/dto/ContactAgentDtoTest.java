package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.sirh.Contact;
import nc.noumea.mairie.model.bean.sirh.TypeContact;

import org.junit.Test;

public class ContactAgentDtoTest {

	@Test
	public void testContactAgentDto_cst() {
		// Given
		TypeContact typeContact = new TypeContact();
		typeContact.setLibelle("lib type");
		Contact c = new Contact();
		c.setContactPrioritaire(1);
		c.setDescription("descr");
		c.setDiffusable("N");
		c.setIdAgent(9005138);
		c.setPrioritaire("0");
		c.setTypeContact(typeContact);

		// When
		ContactAgentDto dto = new ContactAgentDto(c);

		// Then
		assertEquals(c.getDescription(), dto.getDescription());
		assertEquals(c.getDiffusable(), dto.getDiffusable());
		assertEquals(c.getPrioritaire(), dto.getPrioritaire());
		assertEquals("Lib type", dto.getTypeContact());
	}

	@Test
	public void testContactAgentDto_cstNull() {
		// Given
		Contact c = null;

		// When
		ContactAgentDto dto = new ContactAgentDto(c);

		// Then
		assertNull(dto.getDescription());
		assertNull(dto.getDiffusable());
		assertNull(dto.getPrioritaire());
		assertNull(dto.getTypeContact());
	}
}
