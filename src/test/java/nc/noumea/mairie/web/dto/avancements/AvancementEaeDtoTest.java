package nc.noumea.mairie.web.dto.avancements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AvancementDetache;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.AvisCap;

import org.joda.time.DateTime;
import org.junit.Test;

public class AvancementEaeDtoTest {

	@Test
	public void testAvancementEaeDto_cstor_Fonctionnaire() {

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
			avct.setEtat("A");
			avct.setIdAvct(1);

		// When
		AvancementEaeDto dto = new AvancementEaeDto(avct);

		// Then
		assertEquals(new DateTime(2013, 02, 25, 0, 0, 0).toDate(), dto.getDateAvctMoy());
		assertEquals("A", dto.getEtat());
		assertNull(dto.getGrade());
		assertEquals(1, (int) dto.getIdAvct());
	}

	@Test
	public void testAvancementEaeDto_cstor_Detache() {

		// Given
		Spgradn gradeNouveau = new Spgradn();
			gradeNouveau.setCdgrad("cdgrad");
		AvancementDetache avct = new AvancementDetache();
			avct.setAgent(new Agent());
			avct.getAgent().setNomUsage("LEPONGE");
			avct.getAgent().setPrenomUsage("Bob");
			avct.setGrade(new Spgradn());
			avct.getGrade().setGradeInitial("EPONGE");
			avct.setDateAvctMoy(new DateTime(2013, 02, 25, 0, 0, 0).toDate());
			avct.setEtat("A");
			avct.setIdAvct(1);
			avct.setGradeNouveau(gradeNouveau);

		// When
		AvancementEaeDto dto = new AvancementEaeDto(avct);

		// Then
		assertEquals(new DateTime(2013, 02, 25, 0, 0, 0).toDate(), dto.getDateAvctMoy());
		assertEquals("A", dto.getEtat());
		assertEquals(gradeNouveau.getCdgrad(), dto.getGrade().getCodeGrade());
		assertEquals(1, (int) dto.getIdAvct());
	}
}
