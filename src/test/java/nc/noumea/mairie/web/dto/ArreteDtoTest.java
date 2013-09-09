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
import nc.noumea.mairie.model.service.ISiservService;
import nc.noumea.mairie.web.dto.avancements.ArreteDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

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
		avct.setAccAnnee(1);
		avct.setAccMois(0);
		avct.setAccJour(0);
		avct.setNouvAccAnnee(0);
		avct.setNouvAccMois(0);
		avct.setNouvAccJour(0);

		Spcarr carr = new Spcarr();
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);

		Siserv service = new Siserv();
		service.setServi("TATA");
		service.setSigle("S");
		service.setLiServ("TEST DIRECTION SERV");
		service.setDirection("DIRECTION NONO");
		service.setDirectionSigle("DIR");

		Siserv serviceDirection = new Siserv();
		serviceDirection.setServi("DIR");
		serviceDirection.setSigle("SN");
		serviceDirection.setLiServ("TEST");
		serviceDirection.setDirection("DIRECTION");
		serviceDirection.setDirectionSigle("DIRN");

		ISiservService mockSiservService = Mockito.mock(ISiservService.class);
		Mockito.when(mockSiservService.getDirection("TATA")).thenReturn(serviceDirection);
		ReflectionTestUtils.setField(service, "siservSrv", mockSiservService);

		FichePoste fp = new FichePoste();
		fp.setService(service);
		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr);

		// Then
		assertEquals(2013, dto.getAnnee());
		assertEquals("Madame Martine TITIX", dto.getNomComplet());
		assertEquals(false, dto.isRegularisation());
		assertEquals("n° 2009/1310", dto.getDeliberationLabel());
		assertEquals("statut", dto.getDeliberationCapText());
		assertEquals("15/01/2013", sdf.format(dto.getDateCap()));
		assertEquals("15/02/2013", sdf.format(dto.getDateAvct()));
		assertEquals("moyenne", dto.getDureeAvct());
		assertEquals(Integer.valueOf(125), dto.getIna());
		assertEquals("956", dto.getIb());
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

		Siserv service = new Siserv();
		service.setServi("TATA");
		service.setSigle("S");
		service.setLiServ("TEST DIRECTION SERV");
		service.setDirection("DIRECTION NONO");
		service.setDirectionSigle("DIR");

		Siserv serviceDirection = new Siserv();
		serviceDirection.setServi("DIR");
		serviceDirection.setSigle("SN");
		serviceDirection.setLiServ("TEST");
		serviceDirection.setDirection("DIRECTION");
		serviceDirection.setDirectionSigle("DIRN");

		ISiservService mockSiservService = Mockito.mock(ISiservService.class);
		Mockito.when(mockSiservService.getDirection("TATA")).thenReturn(serviceDirection);
		ReflectionTestUtils.setField(service, "siservSrv", mockSiservService);

		FichePoste fp = new FichePoste();
		fp.setService(service);
		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr);

		// Then
		assertEquals(2013, dto.getAnnee());
		assertEquals("Monsieur Max TYY", dto.getNomComplet());
		assertEquals(false, dto.isFeminin());
		assertEquals(true, dto.isChangementClasse());
		assertEquals(false, dto.isRegularisation());
		assertEquals("1 mois", dto.getAcc());
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

		Siserv service = new Siserv();
		service.setServi("TATA");
		service.setSigle("S");
		service.setLiServ("TEST DIRECTION SERV");
		service.setDirection("DIRECTION NONO");
		service.setDirectionSigle("DIR");

		Siserv serviceDirection = new Siserv();
		serviceDirection.setServi("DIR");
		serviceDirection.setSigle("SN");
		serviceDirection.setLiServ("TEST");
		serviceDirection.setDirection("DIRECTION");
		serviceDirection.setDirectionSigle("DIRN");

		ISiservService mockSiservService = Mockito.mock(ISiservService.class);
		Mockito.when(mockSiservService.getDirection("TATA")).thenReturn(serviceDirection);
		ReflectionTestUtils.setField(service, "siservSrv", mockSiservService);

		FichePoste fp = new FichePoste();
		fp.setService(service);
		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr);

		// Then
		assertEquals("", dto.getDeliberationLabel());
		assertEquals("", dto.getDeliberationCapText());
		assertEquals("néant", dto.getAcc());
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
		service.setDirection("DIRECTION NONO");
		service.setDirectionSigle("DIR");

		Siserv serviceDirection = new Siserv();
		serviceDirection.setServi("DIR");
		serviceDirection.setSigle("SN");
		serviceDirection.setLiServ("TEST");
		serviceDirection.setDirection("DIRECTION");
		serviceDirection.setDirectionSigle("DIRN");

		ISiservService mockSiservService = Mockito.mock(ISiservService.class);
		Mockito.when(mockSiservService.getDirection("TATA")).thenReturn(serviceDirection);
		ReflectionTestUtils.setField(service, "siservSrv", mockSiservService);

		FichePoste fp = new FichePoste();
		fp.setService(service);
		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr);

		// Then
		assertEquals(null, dto.getDateArrete());
		assertEquals("2012/125", dto.getNumeroArrete());
		assertEquals("épuisée", dto.getAcc());
	}

	@Test
	public void testArreteDto_ctor_CarriereSansAcc() throws ParseException {
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
		gradeNouveau.setGradeInitial("TITI");
		gradeNouveau.setLiGrad("Grade T123");
		gradeNouveau.setGradeGenerique(gradeGen);
		gradeNouveau.setBarem(barem);

		Spcarr carr = new Spcarr();
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);

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
		service.setDirection("DIRECTION NONO");
		service.setDirectionSigle("DIR");

		Siserv serviceDirection = new Siserv();
		serviceDirection.setServi("DIR");
		serviceDirection.setSigle("SN");
		serviceDirection.setLiServ("TEST");
		serviceDirection.setDirection("DIRECTION");
		serviceDirection.setDirectionSigle("DIRN");

		ISiservService mockSiservService = Mockito.mock(ISiservService.class);
		Mockito.when(mockSiservService.getDirection("TATA")).thenReturn(serviceDirection);
		ReflectionTestUtils.setField(service, "siservSrv", mockSiservService);

		FichePoste fp = new FichePoste();
		fp.setService(service);
		// When
		ArreteDto dto = new ArreteDto(avct, fp, carr);

		// Then
		assertEquals(null, dto.getDateArrete());
		assertEquals("2012/125", dto.getNumeroArrete());
		assertEquals("néant", dto.getAcc());
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

		Spcarr carr = new Spcarr();
		carr.setDateArrete(0);
		carr.setReferenceArrete(12125);

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
		service.setDirection("DIRECTION NONO");
		service.setDirectionSigle("DIR");

		Siserv serviceDirection = new Siserv();
		serviceDirection.setServi("DIR");
		serviceDirection.setSigle("SN");
		serviceDirection.setLiServ("TEST");
		serviceDirection.setDirection("DIRECTION");
		serviceDirection.setDirectionSigle("DIRN");

		ISiservService mockSiservService = Mockito.mock(ISiservService.class);
		Mockito.when(mockSiservService.getDirection("TATA")).thenReturn(serviceDirection);
		ReflectionTestUtils.setField(service, "siservSrv", mockSiservService);

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
		ArreteDto dto = new ArreteDto(avct, fp, carr);

		// Then
		assertEquals(2013, dto.getAnnee());
		assertEquals(" (SN)", dto.getDirectionAgent());
		assertEquals("2 jours ", dto.getAcc());
	}
}
