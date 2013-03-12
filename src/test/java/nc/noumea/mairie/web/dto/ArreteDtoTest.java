package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.AvisCap;
import nc.noumea.mairie.model.bean.Deliberation;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.Spbarem;
import nc.noumea.mairie.model.bean.Spcarr;
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
		ArreteDto dto = new ArreteDto(avct, null, null);

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

	@Test
	public void testArreteDto_ctor_Homme() throws ParseException {
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();

		Agent ag = new Agent();
		ag.setNomPatronymique("TOTO");
		ag.setNomMarital("TITI");
		ag.setNomUsage(null);
		ag.setPrenom("Nono");
		ag.setPrenomUsage("Max");
		ag.setTitre("0");

		avct.setAnneeAvancement(2013);
		avct.setAgent(ag);
		avct.setIdModifAvancement(5);
		avct.setRegularisation(false);

		// When
		ArreteDto dto = new ArreteDto(avct, null, null);

		// Then
		assertEquals(2013, dto.getAnnee());
		assertEquals("Monsieur Max TOTO", dto.getNomComplet());
		assertEquals(false, dto.isFeminin());
		assertEquals(true, dto.isChangementClasse());
		assertEquals(false, dto.isRegularisation());
	}

	@Test
	public void testArreteDto_ctor_SansDeliberation() throws ParseException {
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

		Spgeng gradeGen = new Spgeng();
		gradeGen.setCdgeng("TECH");

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
		ArreteDto dto = new ArreteDto(avct, null, null);

		// Then
		assertEquals("", dto.getDeliberationLabel());
		assertEquals("", dto.getDeliberationCapText());
	}

	@Test
	public void testArreteDto_ctor_SansGradeNouveau() throws ParseException {
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();
		avct.setAnneeAvancement(2013);

		// When
		ArreteDto dto = new ArreteDto(avct, null, null);

		// Then
		assertEquals(2013, dto.getAnnee());
		assertEquals("", dto.getDeliberationLabel());
		assertEquals("", dto.getDeliberationCapText());
		assertEquals("", dto.getGradeLabel());
		assertEquals(Integer.valueOf(0), dto.getIna());
		assertEquals("", dto.getIb());
	}

	@Test
	public void testArreteDto_ctor_Carriere() throws ParseException {
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();

		Spcarr carr = new Spcarr();
		carr.setAccAnnee((double) 1);
		carr.setAccMois((double) 0);
		carr.setAccJour((double) 4);
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);

		avct.setAnneeAvancement(2013);

		// When
		ArreteDto dto = new ArreteDto(avct, null, carr);

		// Then
		assertEquals(null, dto.getDateArrete());
		assertEquals("12125", dto.getNumeroArrete());
		assertEquals("1 an(s), 0 mois, 4 jour(s)", dto.getAcc());
	}

	@Test
	public void testArreteDto_ctor_CarriereSansAcc() throws ParseException {
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();

		Spcarr carr = new Spcarr();
		carr.setAccAnnee((double) 0);
		carr.setAccMois((double) 0);
		carr.setAccJour((double) 0);
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);

		avct.setAnneeAvancement(2013);

		// When
		ArreteDto dto = new ArreteDto(avct, null, carr);

		// Then
		assertEquals(null, dto.getDateArrete());
		assertEquals("12125", dto.getNumeroArrete());
		assertEquals("néant", dto.getAcc());
	}

	/*@Test
	public void testArreteDto_ctor_FichePoste() throws ParseException {
		// TODO
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();

		Siserv service = new Siserv();
		service.setDirection("DIRECTION NONO");
		FichePoste fp = new FichePoste();
		fp.setService(service);

		avct.setAnneeAvancement(2013);

		// When
		ArreteDto dto = new ArreteDto(avct, fp, null);

		// Then
		assertEquals(2013, dto.getAnnee());
		assertEquals("DIRECTION NONO", dto.getDirectionAgent());
	}*/
}
