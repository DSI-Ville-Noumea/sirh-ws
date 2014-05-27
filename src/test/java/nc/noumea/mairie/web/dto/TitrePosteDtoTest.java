package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.TitrePoste;

import org.junit.Test;

public class TitrePosteDtoTest extends AgentDto {

	@Test
	public void testTitrePosteDto_cst() {
		// Given
		String lib = "Nono";

		// When
		TitrePosteDto dto = new TitrePosteDto();
		dto.setLibTitrePoste(lib);

		// Then
		assertEquals(lib, dto.getLibTitrePoste());
	}

	@Test
	public void testTitrePosteDto_cstWithFichePoste() {
		// Given
		TitrePoste tp = new TitrePoste();
		tp.setLibTitrePoste("lib poste");
		FichePoste fp = new FichePoste();
		fp.setTitrePoste(tp);

		// When
		TitrePosteDto dto = new TitrePosteDto(fp);

		// Then
		assertEquals(tp.getLibTitrePoste(), dto.getLibTitrePoste());
	}
}
