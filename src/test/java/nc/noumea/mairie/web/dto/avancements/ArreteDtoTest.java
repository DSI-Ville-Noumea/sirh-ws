package nc.noumea.mairie.web.dto.avancements;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AvancementDetache;
import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.AvisCap;
import nc.noumea.mairie.model.bean.Deliberation;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.Spbarem;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spclas;
import nc.noumea.mairie.model.bean.Speche;
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
		ag.setNomatr(5138);
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
		gradeNouveau.setGradeInitial("TITI");
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
		avct.setDateAvctMini(sdf.parse("01/01/2013"));
		avct.setDateAvctMoy(sdf.parse("01/02/2013"));
		avct.setDateAvctMaxi(sdf.parse("01/03/2013"));
		avct.setAvisCapEmployeur(avis);
		avct.setIdModifAvancement(7);
		avct.setAccAnnee(1);
		avct.setAccMois(0);
		avct.setAccJour(0);
		avct.setNouvAccAnnee(0);
		avct.setNouvAccMois(0);
		avct.setNouvAccJour(0);

		Spcarr carr = new Spcarr();
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);
		carr.setModReg("I");

		Siserv service = new Siserv();
		service.setServi("TATA");
		service.setSigle("S");
		service.setLiServ("TEST DIRECTION SERV");
		service.setDirection("TEST");
		service.setDirectionSigle("SN");

		Spclas classeGrade = new Spclas();
		classeGrade.setCodcla("1");
		classeGrade.setLibCla("nono 1");

		Speche echelonGrade = new Speche();
		echelonGrade.setCodEch("e");
		echelonGrade.setLibEch("Nono e");

		FichePoste fp = new FichePoste();
		fp.setService(service);
		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr, classeGrade, echelonGrade);

		// Then
		assertEquals(2013, dto.getAnnee());
		assertEquals("Madame Martine TITIX", dto.getNomComplet());
		assertEquals(false, dto.isRegularisation());
		assertEquals("n° 2009/1310", dto.getDeliberationLabel());
		assertEquals("statut", dto.getDeliberationCapText());
		assertEquals("15/01/2013", sdf.format(dto.getDateCap()));
		assertEquals("1er février 2013", dto.getDateAvct());
		assertEquals("moyenne", dto.getDureeAvct());
		assertEquals(Integer.valueOf(125), dto.getIna());
		assertEquals("956", dto.getIb());
		assertEquals(false, dto.isChangementClasse());
		assertEquals(true, dto.isFeminin());
		assertEquals("I", dto.getBaseReglement());
		assertEquals("de titi nono 1 nono e", dto.getGradeLabel());
	}

	@Test
	public void testArreteDto_ctor_Homme() throws ParseException {
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();

		Agent ag = new Agent();
		ag.setNomatr(5138);
		ag.setNomPatronymique("TOTO");
		ag.setNomMarital("TITI");
		ag.setNomUsage("TYY");
		ag.setPrenom("Nono");
		ag.setPrenomUsage("Max");
		ag.setTitre("0");

		Spbarem barem = new Spbarem();
		barem.setIban("0000956");
		barem.setIna(125);

		Spgeng gradeGen = new Spgeng();
		gradeGen.setCdgeng("TECH");

		Spgradn gradeNouveau = new Spgradn();
		gradeNouveau.setCdgrad("T123");
		gradeNouveau.setGradeInitial("TITI");
		gradeNouveau.setLiGrad("Grade T123");
		gradeNouveau.setGradeGenerique(gradeGen);
		gradeNouveau.setBarem(barem);

		avct.setAnneeAvancement(2013);
		avct.setAgent(ag);
		avct.setIdModifAvancement(5);
		avct.setRegularisation(false);
		avct.setGradeNouveau(gradeNouveau);
		avct.setAccAnnee(1);
		avct.setAccMois(0);
		avct.setAccJour(0);
		avct.setNouvAccAnnee(0);
		avct.setNouvAccMois(1);
		avct.setNouvAccJour(0);

		Spcarr carr = new Spcarr();
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);
		carr.setModReg("I");

		Siserv service = new Siserv();
		service.setServi("TATA");
		service.setSigle("S");
		service.setLiServ("TEST DIRECTION SERV");
		service.setDirection("TEST");
		service.setDirectionSigle("SN");

		Spclas classeGrade = new Spclas();
		classeGrade.setCodcla("1");
		classeGrade.setLibCla("nono 1");

		Speche echelonGrade = new Speche();
		echelonGrade.setCodEch("e");
		echelonGrade.setLibEch("Nono e");

		FichePoste fp = new FichePoste();
		fp.setService(service);
		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr, classeGrade, echelonGrade);

		// Then
		assertEquals(2013, dto.getAnnee());
		assertEquals("Monsieur Max TYY", dto.getNomComplet());
		assertEquals(false, dto.isFeminin());
		assertEquals(true, dto.isChangementClasse());
		assertEquals(false, dto.isRegularisation());
		assertEquals("1 mois", dto.getAcc());
		assertEquals("5138", dto.getMatriculeAgent());
		assertEquals("I", dto.getBaseReglement());
		assertEquals("de titi nono 1 nono e", dto.getGradeLabel());
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
		ag.setNomatr(5138);

		Spbarem barem = new Spbarem();
		barem.setIban("0000956");
		barem.setIna(125);

		Spgeng gradeGen = new Spgeng();
		gradeGen.setCdgeng("TECH");

		Spgradn gradeNouveau = new Spgradn();
		gradeNouveau.setCdgrad("T123");
		gradeNouveau.setGradeInitial("TITI");
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
		avct.setAccAnnee(0);
		avct.setAccMois(0);
		avct.setAccJour(0);
		avct.setNouvAccAnnee(0);
		avct.setNouvAccMois(0);
		avct.setNouvAccJour(0);

		Spcarr carr = new Spcarr();
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);
		carr.setModReg("M");

		Siserv service = new Siserv();
		service.setServi("TATA");
		service.setSigle("S");
		service.setLiServ("TEST DIRECTION SERV");
		service.setDirection("TEST");
		service.setDirectionSigle("SN");

		Spclas classeGrade = new Spclas();
		classeGrade.setCodcla("1");
		classeGrade.setLibCla("nono 1");

		Speche echelonGrade = null;

		FichePoste fp = new FichePoste();
		fp.setService(service);
		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr, classeGrade, echelonGrade);

		// Then
		assertEquals("", dto.getDeliberationLabel());
		assertEquals("", dto.getDeliberationCapText());
		assertEquals("néant", dto.getAcc());
		assertEquals("M", dto.getBaseReglement());
		assertEquals("de titi nono 1", dto.getGradeLabel());
	}

	@Test
	public void testArreteDto_ctor_Carriere() throws ParseException {
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();

		Agent ag = new Agent();
		ag.setNomPatronymique("TOTO");
		ag.setNomMarital("TITI");
		ag.setNomUsage("TITIX");
		ag.setPrenom("Nono");
		ag.setPrenomUsage("Martine");
		ag.setTitre("1");
		ag.setNomatr(5138);

		Spbarem barem = new Spbarem();
		barem.setIban("0000956");
		barem.setIna(125);

		Spgeng gradeGen = new Spgeng();
		gradeGen.setCdgeng("TECH");

		Spgradn gradeNouveau = new Spgradn();
		gradeNouveau.setCdgrad("T123");
		gradeNouveau.setGradeInitial("TITI");
		gradeNouveau.setLiGrad("Grade T123");
		gradeNouveau.setGradeGenerique(gradeGen);
		gradeNouveau.setBarem(barem);

		Spcarr carr = new Spcarr();
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);
		carr.setModReg("M");

		avct.setAnneeAvancement(2013);
		avct.setAgent(ag);
		avct.setGradeNouveau(gradeNouveau);
		avct.setAccAnnee(1);
		avct.setAccMois(0);
		avct.setAccJour(4);
		avct.setNouvAccAnnee(0);
		avct.setNouvAccMois(0);
		avct.setNouvAccJour(0);

		Siserv service = new Siserv();
		service.setServi("TATA");
		service.setSigle("S");
		service.setLiServ("TEST DIRECTION SERV");
		service.setDirection("TEST");
		service.setDirectionSigle("SN");

		Spclas classeGrade = null;

		Speche echelonGrade = new Speche();
		echelonGrade.setCodEch("e");
		echelonGrade.setLibEch("Nono e");

		FichePoste fp = new FichePoste();
		fp.setService(service);
		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr, classeGrade, echelonGrade);

		// Then
		assertEquals(null, dto.getDateArrete());
		assertEquals("2012/125", dto.getNumeroArrete());
		assertEquals("épuisée", dto.getAcc());
		assertEquals("M", dto.getBaseReglement());
		assertEquals("SN (S)", dto.getServiceAgent());
		assertEquals("de titi nono e", dto.getGradeLabel());
	}

	@Test
	public void testArreteDto_ctor_CarriereSansAcc() throws ParseException {
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();

		Agent ag = new Agent();
		ag.setNomatr(5138);
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
		gradeNouveau.setGradeInitial("TITI");
		gradeNouveau.setLiGrad("Grade T123");
		gradeNouveau.setGradeGenerique(gradeGen);
		gradeNouveau.setBarem(barem);

		Spcarr carr = new Spcarr();
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);
		carr.setModReg("I");

		avct.setAnneeAvancement(2013);
		avct.setAgent(ag);
		avct.setGradeNouveau(gradeNouveau);
		avct.setAccAnnee(0);
		avct.setAccMois(0);
		avct.setAccJour(0);
		avct.setNouvAccAnnee(0);
		avct.setNouvAccMois(0);
		avct.setNouvAccJour(0);

		Siserv service = new Siserv();
		service.setServi("TATA");
		service.setSigle("S");
		service.setLiServ("TEST DIRECTION SERV");
		service.setDirection("TEST");
		service.setDirectionSigle("SN");

		Spclas classeGrade = new Spclas();
		classeGrade.setCodcla("1");
		classeGrade.setLibCla("nono 1");

		Speche echelonGrade = new Speche();
		echelonGrade.setCodEch("e");
		echelonGrade.setLibEch("Nono e");

		FichePoste fp = new FichePoste();
		fp.setService(service);
		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr, classeGrade, echelonGrade);

		// Then
		assertEquals(null, dto.getDateArrete());
		assertEquals("2012/125", dto.getNumeroArrete());
		assertEquals("néant", dto.getAcc());
		assertEquals("I", dto.getBaseReglement());
		assertEquals("de titi nono 1 nono e", dto.getGradeLabel());
	}

	@Test
	public void testArreteDto_ctor_FichePoste() throws ParseException {
		// Given
		AvancementFonctionnaire avct = new AvancementFonctionnaire();

		Agent ag = new Agent();
		ag.setNomPatronymique("TOTO");
		ag.setNomMarital("TITI");
		ag.setNomUsage("TITIX");
		ag.setPrenom("Nono");
		ag.setPrenomUsage("Martine");
		ag.setTitre("1");
		ag.setNomatr(5138);

		Spcarr carr = new Spcarr();
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);
		carr.setModReg("I");

		Spbarem barem = new Spbarem();
		barem.setIban("0000956");
		barem.setIna(125);

		Spgeng gradeGen = new Spgeng();
		gradeGen.setCdgeng("TECH");

		Spgradn gradeNouveau = new Spgradn();
		gradeNouveau.setCdgrad("T123");
		gradeNouveau.setGradeInitial("TITI");
		gradeNouveau.setLiGrad("Grade T123");
		gradeNouveau.setGradeGenerique(gradeGen);
		gradeNouveau.setBarem(barem);

		Siserv service = new Siserv();
		service.setServi("TATA");
		service.setSigle("S");
		service.setLiServ("TEST DIRECTION SERV");
		service.setDirection("TEST");
		service.setDirectionSigle("SN");

		Spclas classeGrade = new Spclas();
		classeGrade.setCodcla("1");
		classeGrade.setLibCla("nono 1");

		Speche echelonGrade = new Speche();
		echelonGrade.setCodEch("e");
		echelonGrade.setLibEch("Nono e");

		FichePoste fp = new FichePoste();
		fp.setService(service);

		avct.setAnneeAvancement(2013);
		avct.setAgent(ag);
		avct.setGradeNouveau(gradeNouveau);
		avct.setAccAnnee(1);
		avct.setAccMois(0);
		avct.setAccJour(0);
		avct.setNouvAccAnnee(0);
		avct.setNouvAccMois(0);
		avct.setNouvAccJour(2);

		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr, classeGrade, echelonGrade);

		// Then
		assertEquals(2013, dto.getAnnee());
		assertEquals(" (SN)", dto.getDirectionAgent());
		assertEquals("2 jours ", dto.getAcc());
		assertEquals("I", dto.getBaseReglement());
		assertEquals("de titi nono 1 nono e", dto.getGradeLabel());
	}

	@Test
	public void testArreteDto_ctor_AvctDetache() throws ParseException {
		// Given
		AvancementDetache avct = new AvancementDetache();

		Agent ag = new Agent();
		ag.setNomPatronymique("TOTO");
		ag.setNomMarital("TITI");
		ag.setNomUsage("TITIX");
		ag.setPrenom("Nono");
		ag.setPrenomUsage("Martine");
		ag.setTitre("1");
		ag.setNomatr(5138);

		Spcarr carr = new Spcarr();
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);
		carr.setModReg("I");

		Spbarem barem = new Spbarem();
		barem.setIban("0000956");
		barem.setIna(125);

		Spgeng gradeGen = new Spgeng();
		gradeGen.setCdgeng("TECH");

		Spgradn gradeNouveau = new Spgradn();
		gradeNouveau.setCdgrad("T123");
		gradeNouveau.setGradeInitial("TITI");
		gradeNouveau.setLiGrad("Grade T123");
		gradeNouveau.setGradeGenerique(gradeGen);
		gradeNouveau.setBarem(barem);

		Siserv service = new Siserv();
		service.setServi("TATA");
		service.setSigle("S");
		service.setLiServ("TEST DIRECTION SERV");
		service.setDirection("TEST");
		service.setDirectionSigle("SN");

		Spclas classeGrade = new Spclas();
		classeGrade.setCodcla("1");
		classeGrade.setLibCla("nono 1");

		Speche echelonGrade = new Speche();
		echelonGrade.setCodEch("e");
		echelonGrade.setLibEch("Nono e");

		FichePoste fp = new FichePoste();
		fp.setService(service);

		avct.setAnneeAvancement(2013);
		avct.setAgent(ag);
		avct.setGradeNouveau(gradeNouveau);
		avct.setAccAnnee(1);
		avct.setAccMois(0);
		avct.setAccJour(0);
		avct.setNouvAccAnnee(0);
		avct.setNouvAccMois(0);
		avct.setNouvAccJour(2);

		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr, classeGrade, echelonGrade);

		// Then
		assertEquals(2013, dto.getAnnee());
		assertEquals(" (SN)", dto.getDirectionAgent());
		assertEquals("2 jours ", dto.getAcc());
		assertEquals("I", dto.getBaseReglement());
		assertEquals(null, dto.getDateCap());
		assertEquals("", dto.getDureeAvct());
		assertEquals("de titi nono 1 nono e", dto.getGradeLabel());
	}
}