package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Spbhor;

import org.junit.Test;

public class SpbhorDtoTest {

	@Test
	public void testSpbhorDto() {
		
		Spbhor hor = new Spbhor();
			hor.setCdThor(1);
			hor.setLibHor("libHor");
			hor.setTaux(0.5);
		
		SpbhorDto dto = new SpbhorDto(hor);
		
		assertEquals(hor.getCdThor(), dto.getCdThor());
		assertEquals(hor.getLibHor(), dto.getLabel());
		assertEquals(hor.getTaux(), dto.getTaux());
	}
}
