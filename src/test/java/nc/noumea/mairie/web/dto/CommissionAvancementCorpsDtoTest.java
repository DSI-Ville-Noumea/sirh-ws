package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementCorpsDto;

import org.junit.Test;

public class CommissionAvancementCorpsDtoTest {

	@Test
	public void CommissionAvancementCorpsDto() {
		// Given
		Spgeng spgeng = new Spgeng();
		spgeng.setCdgeng("CORPS");
		
		// When
		CommissionAvancementCorpsDto dto = new CommissionAvancementCorpsDto(spgeng);
		
		// Then
		assertEquals("CORPS", dto.getCorps());
	}
}
