package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import nc.noumea.mairie.model.bean.Spmtsr;

import org.joda.time.DateTime;
import org.junit.Test;

public class ParcoursProDtoTest extends AgentDto {

	@Test
	public void testParcoursProDto_cst() {
		// Given
		Spmtsr spmtsr = new Spmtsr();
		spmtsr.setDatdeb(20140101);

		Date spmtsrDate = new DateTime(2014, 01, 01, 0, 0, 0).toDate();

		// When
		ParcoursProDto dto = new ParcoursProDto(spmtsr);

		// Then
		assertEquals(spmtsrDate, dto.getDateDebut());
		assertNull(dto.getDateFin());
	}
}
