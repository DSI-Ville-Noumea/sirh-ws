package nc.noumea.mairie.web.dto.avancements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.AvisCap;

import org.joda.time.DateTime;
import org.junit.Test;

public class AvancementItemDtoTest {

	@Test
	public void testAvancementItemDto_Max() {

		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();
		avct.setAgent(new Agent());
		avct.getAgent().setNomUsage("LEPONGE");
		avct.getAgent().setPrenomUsage("Bob");
		avct.setGrade(new Spgradn());
		avct.getGrade().setGradeInitial("EPONGE");
		avct.setDateAvctMoy(new DateTime(2013, 02, 25, 0, 0, 0).toDate());
		avct.setAvisCap(new AvisCap());
		avct.getAvisCap().setIdAvisCap(3);
		avct.setOrdreMerite("UNO");

		// When
		AvancementItemDto dto = new AvancementItemDto(avct, false, null, null);

		// Then
		assertEquals("Bob", dto.getPrenom());
		assertEquals("LEPONGE", dto.getNom());
		assertEquals("EPONGE", dto.getGrade());
		assertEquals(new DateTime(2013, 02, 25, 0, 0, 0).toDate(),
				dto.getDatePrevisionnelleAvancement());
		assertFalse(dto.isDureeMin());
		assertFalse(dto.isDureeMoy());
		assertTrue(dto.isDureeMax());
		assertFalse(dto.isFavorable());
		assertEquals("UNO", dto.getClassement());
	}

	@Test
	public void testAvancementItemDto_Moy() {

		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();
		avct.setAgent(new Agent());
		avct.getAgent().setNomUsage("LEPONGE");
		avct.getAgent().setPrenomUsage("Bob");
		avct.setGrade(new Spgradn());
		avct.getGrade().setGradeInitial("EPONGE");
		avct.setDateAvctMoy(new DateTime(2013, 02, 25, 0, 0, 0).toDate());
		avct.setAvisCap(new AvisCap());
		avct.getAvisCap().setIdAvisCap(2);
		avct.setOrdreMerite("UNO");

		// When
		AvancementItemDto dto = new AvancementItemDto(avct, false, null,
				new DateTime(2014, 1, 1, 0, 0, 0).toDate());

		assertFalse(dto.isDureeMin());
		assertTrue(dto.isDureeMoy());
		assertFalse(dto.isDureeMax());
		assertFalse(dto.isFavorable());
		assertEquals(new DateTime(2014, 1, 1, 0, 0, 0).toDate(),
				dto.getDateAncienAvancementMinimale());
	}

	@Test
	public void testAvancementItemDto_Min() {

		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();
		avct.setAgent(new Agent());
		avct.getAgent().setNomUsage("LEPONGE");
		avct.getAgent().setPrenomUsage("Bob");
		avct.setGrade(new Spgradn());
		avct.getGrade().setGradeInitial("EPONGE");
		avct.setDateAvctMoy(new DateTime(2013, 02, 25, 0, 0, 0).toDate());
		avct.setAvisCap(new AvisCap());
		avct.getAvisCap().setIdAvisCap(1);
		avct.setOrdreMerite("UNO");

		// When
		AvancementItemDto dto = new AvancementItemDto(avct, false, null, null);

		assertTrue(dto.isDureeMin());
		assertFalse(dto.isDureeMoy());
		assertFalse(dto.isDureeMax());
		assertFalse(dto.isFavorable());
		assertNull(dto.getDateAncienAvancementMinimale());
	}

	@Test
	public void testAvancementItemDto_Favorable() {

		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();
		avct.setAgent(new Agent());
		avct.getAgent().setNomUsage("LEPONGE");
		avct.getAgent().setPrenomUsage("Bob");
		avct.setGrade(new Spgradn());
		avct.getGrade().setGradeInitial("EPONGE");
		avct.setDateAvctMoy(new DateTime(2013, 02, 25, 0, 0, 0).toDate());
		avct.setAvisCap(new AvisCap());
		avct.getAvisCap().setIdAvisCap(4);
		avct.setOrdreMerite("UNO");

		// When
		AvancementItemDto dto = new AvancementItemDto(avct, false, null, null);

		assertFalse(dto.isDureeMin());
		assertFalse(dto.isDureeMoy());
		assertFalse(dto.isDureeMax());
		assertTrue(dto.isFavorable());
	}

	@Test
	public void testAvancementItemDto_Defavorable() {

		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();
		avct.setAgent(new Agent());
		avct.getAgent().setNomUsage("LEPONGE");
		avct.getAgent().setPrenomUsage("Bob");
		avct.setGrade(new Spgradn());
		avct.getGrade().setGradeInitial("EPONGE");
		avct.setDateAvctMoy(new DateTime(2013, 02, 25, 0, 0, 0).toDate());
		avct.setAvisCap(new AvisCap());
		avct.getAvisCap().setIdAvisCap(5);
		avct.setOrdreMerite("UNO");

		// When
		AvancementItemDto dto = new AvancementItemDto(avct, false, null, null);

		assertFalse(dto.isDureeMin());
		assertFalse(dto.isDureeMoy());
		assertFalse(dto.isDureeMax());
		assertFalse(dto.isFavorable());
	}
}
