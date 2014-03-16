package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.Agent;

import org.joda.time.DateTime;
import org.junit.Test;

public class CalculEaeInfosDtoTest extends AgentDto {

	@Test
	public void testCalculEaeInfosDto_cst() {
		// Given
		Agent agent = new Agent();
		agent.setIdAgent(9005138);
		Affectation aff = new Affectation();
		aff.setAgent(agent);
		aff.setDateDebutAff(new DateTime(2014, 01, 01, 0, 0, 0).toDate());
		aff.setDateFinAff(null);

		// When
		CalculEaeInfosDto dto = new CalculEaeInfosDto(aff);

		// Then
		assertEquals(aff.getDateDebutAff(), dto.getDateDebut());
		assertNull(dto.getDateFin());

	}
}
