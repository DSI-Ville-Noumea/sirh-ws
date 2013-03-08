package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.AvisCap;
import nc.noumea.mairie.model.bean.Deliberation;
import nc.noumea.mairie.model.bean.Spbarem;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.web.dto.avancements.ArreteDto;

import org.junit.Test;

public class ArreteDtoTest {

	@Test
	public void testArreteDto_ctor_FemmeMarie() throws ParseException {
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();

		Agent ag = new Agent();
		ag.setNomPatronymique("TOTO");
		ag.setNomMarital("TITI");
		ag.setNomUsage("TITIX");
		ag.setPrenom("Nono");
		ag.setPrenomUsage("Martine");
		ag.setTitre("1");

		Spbarem barem = new Spbarem();
		barem.setIban("0000956");
		barem.setIna(125);

		Deliberation delib = new Deliberation();
		delib.setIdDeliberation(10);
		delib.setLibDeliberation("n° 2009/1310");
		delib.setTexteCap("statut");

		Spgeng gradeGen = new Spgeng();
		gradeGen.setCdgeng("TECH");
		gradeGen.setDeliberationCommunale(delib);

		Spgradn gradeNouveau = new Spgradn();
		gradeNouveau.setCdgrad("T123");
		gradeNouveau.setLiGrad("Grade T123");
		gradeNouveau.setGradeGenerique(gradeGen);
		gradeNouveau.setBarem(barem);

		AvisCap avis = new AvisCap();
		avis.setIdAvisCap(2);
		avis.setLibLong("Moyenne");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		avct.setAnneeAvancement(2013);
		avct.setAgent(ag);
		avct.setGradeNouveau(gradeNouveau);
		avct.setRegularisation(false);
		avct.setDateCap(sdf.parse("15/01/2013"));
		avct.setDateAvctMini(sdf.parse("15/01/2013"));
		avct.setDateAvctMoy(sdf.parse("15/02/2013"));
		avct.setDateAvctMaxi(sdf.parse("15/03/2013"));
		avct.setAvisCapEmployeur(avis);
		avct.setIdModifAvancement(7);

		// When
		ArreteDto dto = new ArreteDto(avct);

		// Then
		assertEquals(2013, dto.getAnnee());
		assertEquals("Madame Martine TITIX épouse TITI", dto.getNomComplet());
		assertEquals(false, dto.isRegularisation());
		assertEquals("n° 2009/1310", dto.getDeliberationLabel());
		assertEquals("statut", dto.getDeliberationCapText());
		assertEquals("15/01/2013", sdf.format(dto.getDateCap()));
		assertEquals("15/02/2013", sdf.format(dto.getDateAvct()));
		assertEquals("moyenne", dto.getDureeAvct());
		assertEquals("Grade T123", dto.getGradeLabel());
		assertEquals(Integer.valueOf(125), dto.getIna());
		assertEquals("0000956", dto.getIb());
		assertEquals(false, dto.isChangementClasse());
		assertEquals(true, dto.isFeminin());
	}
}
