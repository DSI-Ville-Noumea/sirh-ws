package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.SpecialiteDiplome;
import nc.noumea.mairie.model.bean.sirh.TitreDiplome;

import org.junit.Test;

public class DiplomeDtoTest extends AgentDto {

	@Test
	public void testCalculEaeInfosDto_cst() {
		// Given
		SpecialiteDiplome specialiteDiplome = new SpecialiteDiplome();
		specialiteDiplome.setLibSpeDiplome("LIB SPE");
		TitreDiplome titreDiplome = new TitreDiplome();
		titreDiplome.setLibTitreDiplome("LIB");
		DiplomeAgent da = new DiplomeAgent();
		da.setDateObtention(null);
		da.setIdDiplome(1);
		da.setTitreDiplome(titreDiplome);
		da.setSpecialiteDiplome(specialiteDiplome);

		// When
		DiplomeDto dto = new DiplomeDto(da);

		// Then
		assertEquals(da.getDateObtention(), dto.getDateObtention());
		assertEquals(da.getIdDiplome(), dto.getIdDiplome());
		assertEquals(da.getSpecialiteDiplome().getLibSpeDiplome(), dto.getLibSpeDiplome());
		assertEquals(da.getTitreDiplome().getLibTitreDiplome(), dto.getLibTitreDiplome());
	}
}
