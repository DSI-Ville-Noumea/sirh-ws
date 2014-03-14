package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.CentreFormation;
import nc.noumea.mairie.model.bean.FormationAgent;
import nc.noumea.mairie.model.bean.TitreFormation;

import org.junit.Test;

public class FormationDtoTest {

	@Test
	public void testFormationDto_ctor() {
		// Given
		CentreFormation centreFormation = new CentreFormation();
		centreFormation.setLibCentreFormation("lib centre");
		TitreFormation titreFormation = new TitreFormation();
		titreFormation.setLibTitreFormation("lib titre");
		FormationAgent fa = new FormationAgent();
		fa.setAnneeFormation(2013);
		fa.setTitreFormation(titreFormation);
		fa.setCentreFormation(centreFormation);
		fa.setDureeFormation(null);
		fa.setUniteformation(null);

		// When
		FormationDto dto = new FormationDto(fa);

		// Then
		assertEquals(fa.getAnneeFormation(), dto.getAnneeFormation());
		assertEquals(fa.getCentreFormation().getLibCentreFormation(), dto.getCentreFormation());
		assertEquals(fa.getDureeFormation(), dto.getDureeFormation());
		assertEquals(fa.getTitreFormation().getLibTitreFormation(), dto.getTitreFormation());
		assertEquals(fa.getUniteformation(), dto.getUniteDuree());
	}
}
