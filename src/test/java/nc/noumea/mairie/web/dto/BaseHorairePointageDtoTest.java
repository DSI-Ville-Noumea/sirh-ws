package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.sirh.BaseHorairePointage;

import org.junit.Test;

public class BaseHorairePointageDtoTest {

	@Test
	public void testBaseHorairePointageDto_cst() {
		// Given
		BaseHorairePointage ag = new BaseHorairePointage();
		ag.setCodeBaseHorairePointage("A");
		ag.setLibelleBaseHorairePointage("lib");
		ag.setDescriptionBaseHorairePointage("desc");
		ag.setHeureDimanche(7.48);
		ag.setBaseCalculee(39.0);

		// When
		BaseHorairePointageDto dto = new BaseHorairePointageDto(ag);

		// Then
		assertEquals(ag.getCodeBaseHorairePointage(), dto.getCodeBaseHorairePointage());
		assertEquals(ag.getLibelleBaseHorairePointage(), dto.getLibelleBaseHorairePointage());
		assertEquals(ag.getDescriptionBaseHorairePointage(), dto.getDescriptionBaseHorairePointage());
		assertEquals(ag.getHeureDimanche(), dto.getHeureDimanche());
		assertEquals(ag.getBaseCalculee(), dto.getBaseCalculee());
		assertEquals(ag.getBaseLegale(), dto.getBaseLegale());
	}

	@Test
	public void testBaseHorairePointageDto_cstNull() {
		// Given
		BaseHorairePointage ag = null;

		// When
		BaseHorairePointageDto dto = new BaseHorairePointageDto(ag);

		// Then
		assertNull(dto.getCodeBaseHorairePointage());
		assertNull(dto.getLibelleBaseHorairePointage());
		assertNull(dto.getDescriptionBaseHorairePointage());
		assertNull(dto.getHeureDimanche());
		assertNull(dto.getBaseCalculee());
		assertNull(dto.getBaseLegale());
	}
}
