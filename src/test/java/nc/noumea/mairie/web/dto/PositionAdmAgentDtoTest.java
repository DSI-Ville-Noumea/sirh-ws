package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.pk.SpadmnId;

import org.junit.Test;

public class PositionAdmAgentDtoTest extends AgentDto {

	@Test
	public void testPositionAdmAgentDto_cst() {
		// Given
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140101);
		id.setNomatr(5138);
		Spadmn spadmn = new Spadmn();
		spadmn.setId(id);
		spadmn.setCdpadm("COD");

		// When
		PositionAdmAgentDto dto = new PositionAdmAgentDto(spadmn);

		// Then
		assertEquals(spadmn.getCdpadm(), dto.getCdpadm());
		assertEquals(id.getDatdeb(), dto.getDatdeb());
		assertNull(dto.getDatfin());
		assertEquals(id.getNomatr(), dto.getNomatr());
	}
}
