package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.AvisCap;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.web.dto.avancements.AvancementDifferencieItemDto;

import org.joda.time.DateTime;
import org.junit.Test;

public class AvancementDifferencieItemDtoTest {

	@Test
	public void testAvancementDifferencieItemDto_Max() {
		
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();
		avct.setAgent(new Agent());
		avct.getAgent().setNomUsage("LEPONGE");
		avct.getAgent().setPrenomUsage("Bob");
		avct.setGrade(new Spgradn());
		avct.getGrade().setGradeInitial("EPONGE");
		avct.setDateAvctMoy(new DateTime(2013, 02,  25, 0, 0, 0).toDate());
		avct.setAvisCap(new AvisCap());
		avct.getAvisCap().setIdAvisCap(3);
		avct.setOrdreMerite("UNO");
		
		// When
		AvancementDifferencieItemDto dto = new AvancementDifferencieItemDto(avct);
		
		// Then
		assertEquals("Bob", dto.getPrenom());
		assertEquals("LEPONGE", dto.getNom());
		assertEquals("EPONGE", dto.getGrade());
		assertEquals(new DateTime(2013, 02,  25, 0, 0, 0).toDate(), dto.getDatePrevisionnelleAvancement());
		assertFalse(dto.isDureeMin());
		assertFalse(dto.isDureeMoy());
		assertTrue(dto.isDureeMax());
		assertEquals("UNO", dto.getClassement());
	}
	
	@Test
	public void testAvancementDifferencieItemDto_Moy() {
		
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();
		avct.setAgent(new Agent());
		avct.getAgent().setNomUsage("LEPONGE");
		avct.getAgent().setPrenomUsage("Bob");
		avct.setGrade(new Spgradn());
		avct.getGrade().setGradeInitial("EPONGE");
		avct.setDateAvctMoy(new DateTime(2013, 02,  25, 0, 0, 0).toDate());
		avct.setAvisCap(new AvisCap());
		avct.getAvisCap().setIdAvisCap(2);
		avct.setOrdreMerite("UNO");
		
		// When
		AvancementDifferencieItemDto dto = new AvancementDifferencieItemDto(avct);
		
		assertFalse(dto.isDureeMin());
		assertTrue(dto.isDureeMoy());
		assertFalse(dto.isDureeMax());
	}
	
	@Test
	public void testAvancementDifferencieItemDto_Min() {
		
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();
		avct.setAgent(new Agent());
		avct.getAgent().setNomUsage("LEPONGE");
		avct.getAgent().setPrenomUsage("Bob");
		avct.setGrade(new Spgradn());
		avct.getGrade().setGradeInitial("EPONGE");
		avct.setDateAvctMoy(new DateTime(2013, 02,  25, 0, 0, 0).toDate());
		avct.setAvisCap(new AvisCap());
		avct.getAvisCap().setIdAvisCap(1);
		avct.setOrdreMerite("UNO");
		
		// When
		AvancementDifferencieItemDto dto = new AvancementDifferencieItemDto(avct);
		
		assertTrue(dto.isDureeMin());
		assertFalse(dto.isDureeMoy());
		assertFalse(dto.isDureeMax());
	}
}
