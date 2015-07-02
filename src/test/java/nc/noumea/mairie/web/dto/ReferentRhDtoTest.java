package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;

import org.junit.Test;

public class ReferentRhDtoTest {

	@Test
	public void testReferentRhDto_cst() {
		// Given
		ReferentRh c = new ReferentRh();
		c.setIdAgentReferent(21);
		c.setNumeroTelephone(22);
		// c.setServi("toto");
		Agent ag = new Agent();
		ag.setPrenomUsage("prenom usage");
		EntiteDto serv = new EntiteDto();
		serv.setSigle("sigle");

		// When
		ReferentRhDto dto = new ReferentRhDto(c, ag, serv);

		// Then
		assertEquals(c.getIdAgentReferent(), dto.getIdAgentReferent());
		assertEquals(c.getNumeroTelephone(), dto.getNumeroTelephone());
		assertEquals(ag.getPrenomUsage(), dto.getPrenomAgentReferent());
		assertEquals(serv.getSigle(), dto.getSigleService());
	}

	@Test
	public void testReferentRhDto_cstNull() {
		// Given
		ReferentRh c = null;
		Agent ag = null;
		EntiteDto serv = null;

		// When
		ReferentRhDto dto = new ReferentRhDto(c, ag, serv);

		// Then
		assertNull(dto.getIdAgentReferent());
		assertNull(dto.getNumeroTelephone());
		assertNull(dto.getPrenomAgentReferent());
		assertNull(dto.getSigleService());
	}
}
