package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.sirh.Agent;

import org.junit.Test;

public class CouvertureSocialeDtoTest {

	@Test
	public void testCouvertureSocialeDto_cst() {
		// Given
		Agent ag = new Agent();
		ag.setNumCafat("1");
		ag.setNumRuamm("2");
		ag.setNumMutuelle("3");
		ag.setNumCre("4");
		ag.setNumIrcafex("5");
		ag.setNumClr("6");

		// When
		CouvertureSocialeDto dto = new CouvertureSocialeDto(ag);

		// Then
		assertEquals(ag.getNumCafat(), dto.getNumCafat());
		assertEquals(ag.getNumClr(), dto.getNumClr());
		assertEquals(ag.getNumCre(), dto.getNumCre());
		assertEquals(ag.getNumIrcafex(), dto.getNumIrcafex());
		assertEquals(ag.getNumMutuelle(), dto.getNumMutuelle());
		assertEquals(ag.getNumRuamm(), dto.getNumRuamm());
	}

	@Test
	public void testCouvertureSocialeDto_cstNull() {
		// Given
		Agent ag = null;

		// When
		CouvertureSocialeDto dto = new CouvertureSocialeDto(ag);

		// Then
		assertNull(dto.getNumCafat());
		assertNull(dto.getNumClr());
		assertNull(dto.getNumCre());
		assertNull(dto.getNumIrcafex());
		assertNull(dto.getNumMutuelle());
		assertNull(dto.getNumRuamm());
	}
}
