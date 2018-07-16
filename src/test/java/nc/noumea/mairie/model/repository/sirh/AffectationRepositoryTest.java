package nc.noumea.mairie.model.repository.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.PrimePointageAff;
import nc.noumea.mairie.model.pk.sirh.PrimePointageAffPK;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class AffectationRepositoryTest {

	@Autowired
	IAffectationRepository repository;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;

	// @Test
	@Transactional("sirhTransactionManager")
	public void getAffectationActiveByAgent_returnResult() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		Affectation result = repository.getAffectationActiveByAgent(9005138);

		assertEquals(result.getIdAffectation().intValue(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	// @Test
	@Transactional("sirhTransactionManager")
	public void getAffectationActiveByAgent_returnNull_DateFin() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(cal.getTime());
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		Affectation result = repository.getAffectationActiveByAgent(9005138);

		assertNull(result);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	// @Test
	@Transactional("sirhTransactionManager")
	public void getAffectationActiveByAgent_returnNull_badAgent() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		Affectation result = repository.getAffectationActiveByAgent(9005438);

		assertNull(result);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecService_returnResult() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecService(9005138, 1);

		assertEquals(result.size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecService_noResult_badService() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecService(9005138, fichePoste.getIdFichePoste());

		assertEquals(result.size(), 0);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecService_noResult_badAgent() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecService(9005130, fichePoste.getIdFichePoste());

		assertEquals(result.size(), 0);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecFP_1result() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecFP(9005138, fichePoste.getIdFichePoste());

		assertEquals(result.size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecFP_noResult_badAgent() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecFP(9005118, fichePoste.getIdFichePoste());

		assertEquals(result.size(), 0);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecFP_noResult_badFP() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecFP(9005138, 2);

		assertEquals(result.size(), 0);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentByPeriode_noResult_affectationBeforePeriode() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();
		Date dateDebutAff = new LocalDate(2014, 1, 23).toDate();
		Date dateFinAff = new LocalDate(2014, 1, 31).toDate();

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAff);
		a.setDateFinAff(dateFinAff);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentByPeriode(9005138, dateDebut, dateFin);

		assertEquals(result.size(), 0);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentByPeriode_1Result_affectationBeforePeriode() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();
		Date dateDebutAff = new LocalDate(2014, 1, 23).toDate();

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAff);
		a.setDateFinAff(null);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentByPeriode(9005138, dateDebut, dateFin);

		assertEquals(result.size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentByPeriode_0Result_badAgent() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();
		Date dateDebutAff = new LocalDate(2014, 1, 23).toDate();

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAff);
		a.setDateFinAff(null);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentByPeriode(9005142, dateDebut, dateFin);

		assertEquals(result.size(), 0);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentByPeriode_noResult_affectationAfterPeriode() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();
		Date dateDebutAff = new LocalDate(2014, 3, 1).toDate();
		Date dateFinAff = new LocalDate(2014, 3, 31).toDate();

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAff);
		a.setDateFinAff(dateFinAff);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentByPeriode(9005138, dateDebut, dateFin);

		assertEquals(result.size(), 0);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentByPeriode_1Result_affectationInPeriode() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();
		Date dateDebutAff = new LocalDate(2014, 2, 10).toDate();
		Date dateFinAff = new LocalDate(2014, 2, 20).toDate();

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAff);
		a.setDateFinAff(dateFinAff);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentByPeriode(9005138, dateDebut, dateFin);

		assertEquals(result.size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentByPeriode_1Result_affectationAfterPeriode() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();
		Date dateDebutAff = new LocalDate(2014, 2, 28).toDate();
		Date dateFinAff = new LocalDate(2014, 3, 20).toDate();

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAff);
		a.setDateFinAff(dateFinAff);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentByPeriode(9005138, dateDebut, dateFin);

		assertEquals(result.size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentByPeriode_3Results() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();

		Date dateDebutAff1 = new LocalDate(2014, 1, 10).toDate();
		Date dateFinAff1 = new LocalDate(2014, 1, 20).toDate();
		Date dateDebutAff2 = new LocalDate(2014, 1, 21).toDate();
		Date dateFinAff2 = new LocalDate(2014, 2, 10).toDate();
		Date dateDebutAff3 = new LocalDate(2014, 2, 11).toDate();
		Date dateFinAff3 = new LocalDate(2014, 2, 20).toDate();
		Date dateDebutAff4 = new LocalDate(2014, 2, 21).toDate();

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAff1);
		a.setDateFinAff(dateFinAff1);
		sirhPersistenceUnit.persist(a);

		Affectation a2 = new Affectation();
		a2.setAgent(ag);
		a2.setIdAffectation(2);
		a2.setTempsTravail("tempsTravail");
		a2.setDateDebutAff(dateDebutAff2);
		a2.setDateFinAff(dateFinAff2);
		sirhPersistenceUnit.persist(a2);

		Affectation a3 = new Affectation();
		a3.setAgent(ag);
		a3.setIdAffectation(3);
		a3.setTempsTravail("tempsTravail");
		a3.setDateDebutAff(dateDebutAff3);
		a3.setDateFinAff(dateFinAff3);
		sirhPersistenceUnit.persist(a3);

		Affectation a4 = new Affectation();
		a4.setAgent(ag);
		a4.setIdAffectation(4);
		a4.setTempsTravail("tempsTravail");
		a4.setDateDebutAff(dateDebutAff4);
		a4.setDateFinAff(null);
		sirhPersistenceUnit.persist(a4);

		List<Affectation> result = repository.getListeAffectationsAgentByPeriode(9005138, dateDebut, dateFin);

		assertEquals(result.size(), 3);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentByPeriode_3Results_withDateFinNull() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = null;

		Date dateDebutAff1 = new LocalDate(2014, 1, 10).toDate();
		Date dateFinAff1 = new LocalDate(2014, 1, 20).toDate();
		Date dateDebutAff2 = new LocalDate(2014, 1, 21).toDate();
		Date dateFinAff2 = new LocalDate(2014, 2, 10).toDate();
		Date dateDebutAff3 = new LocalDate(2014, 2, 11).toDate();
		Date dateFinAff3 = new LocalDate(2014, 2, 20).toDate();
		Date dateDebutAff4 = new LocalDate(2014, 2, 21).toDate();

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAff1);
		a.setDateFinAff(dateFinAff1);
		sirhPersistenceUnit.persist(a);

		Affectation a2 = new Affectation();
		a2.setAgent(ag);
		a2.setIdAffectation(2);
		a2.setTempsTravail("tempsTravail");
		a2.setDateDebutAff(dateDebutAff2);
		a2.setDateFinAff(dateFinAff2);
		sirhPersistenceUnit.persist(a2);

		Affectation a3 = new Affectation();
		a3.setAgent(ag);
		a3.setIdAffectation(3);
		a3.setTempsTravail("tempsTravail");
		a3.setDateDebutAff(dateDebutAff3);
		a3.setDateFinAff(dateFinAff3);
		sirhPersistenceUnit.persist(a3);

		Affectation a4 = new Affectation();
		a4.setAgent(ag);
		a4.setIdAffectation(4);
		a4.setTempsTravail("tempsTravail");
		a4.setDateDebutAff(dateDebutAff4);
		a4.setDateFinAff(null);
		sirhPersistenceUnit.persist(a4);

		List<Affectation> result = repository.getListeAffectationsAgentByPeriode(9005138, dateDebut, dateFin);

		assertEquals(result.size(), 3);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAffectationByIdFichePoste_returnListAffectation() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(cal.getTime());
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getAffectationByIdFichePoste(fichePoste.getIdFichePoste());

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(fichePoste.getIdFichePoste(), result.get(0).getFichePoste().getIdFichePoste());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAffectationByIdFichePoste_returnEmptyList() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getAffectationByIdFichePoste(2);

		assertNotNull(result);
		assertEquals(0, result.size());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentOrderByDateAsc_returnResult() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a1 = new Affectation();
		a1.setAgent(ag);
		a1.setIdAffectation(2);
		a1.setTempsTravail("tempsTravail");
		a1.setDateDebutAff(new DateTime(2010, 05, 01, 0, 0, 0).toDate());
		a1.setDateFinAff(null);
		a1.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a1);

		Affectation a2 = new Affectation();
		a2.setAgent(ag);
		a2.setIdAffectation(1);
		a2.setTempsTravail("tempsTravail");
		a2.setDateDebutAff(new DateTime(2010, 01, 01, 0, 0, 0).toDate());
		a2.setDateFinAff(new DateTime(2010, 05, 01, 0, 0, 0).toDate());
		a2.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a2);

		List<Affectation> result = repository.getListeAffectationsAgentOrderByDateAsc(9005138);

		assertEquals(result.size(), 2);
		assertEquals(result.get(0).getIdAffectation(), a2.getIdAffectation());
		assertEquals(result.get(1).getIdAffectation(), a1.getIdAffectation());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherAffectationAgentAvecDateDebut_returnResult() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a1 = new Affectation();
		a1.setAgent(ag);
		a1.setIdAffectation(2);
		a1.setTempsTravail("tempsTravail");
		a1.setDateDebutAff(new DateTime(2010, 05, 01, 0, 0, 0).toDate());
		a1.setDateFinAff(null);
		a1.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a1);

		Affectation a2 = new Affectation();
		a2.setAgent(ag);
		a2.setIdAffectation(1);
		a2.setTempsTravail("tempsTravail");
		a2.setDateDebutAff(new DateTime(2010, 01, 01, 0, 0, 0).toDate());
		a2.setDateFinAff(new DateTime(2010, 05, 01, 0, 0, 0).toDate());
		a2.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a2);

		Affectation result = repository.chercherAffectationAgentAvecDateDebut(9005138, new DateTime(2010, 01, 01, 0, 0, 0).toDate());

		assertNotNull(result);
		assertEquals(result.getIdAffectation(), a2.getIdAffectation());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherAffectationAgentAvecDateDebut_returnNoResult() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a1 = new Affectation();
		a1.setAgent(ag);
		a1.setIdAffectation(2);
		a1.setTempsTravail("tempsTravail");
		a1.setDateDebutAff(new DateTime(2010, 05, 01, 0, 0, 0).toDate());
		a1.setDateFinAff(null);
		a1.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a1);

		Affectation result = repository.chercherAffectationAgentAvecDateDebut(9005138, new DateTime(2010, 01, 01, 0, 0, 0).toDate());

		assertNull(result);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAffectationAgent_ReturnNull() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Affectation a1 = new Affectation();
		a1.setAgent(ag);
		a1.setIdAffectation(2);
		a1.setTempsTravail("tempsTravail");
		a1.setDateDebutAff(new DateTime(2010, 05, 01, 0, 0, 0).toDate());
		a1.setDateFinAff(new DateTime(2010, 06, 01, 0, 0, 0).toDate());
		sirhPersistenceUnit.persist(a1);

		Agent result = repository.getAffectationAgent(9005138, new Date());

		assertNull(result);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAffectationAgent_NoDateFin_ReturnAgent() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Affectation a1 = new Affectation();
		a1.setAgent(ag);
		a1.setIdAffectation(2);
		a1.setTempsTravail("tempsTravail");
		a1.setDateDebutAff(new DateTime(2010, 05, 01, 0, 0, 0).toDate());
		a1.setDateFinAff(null);
		sirhPersistenceUnit.persist(a1);

		Agent result = repository.getAffectationAgent(9005138, new Date());

		assertNotNull(result);
		assertEquals(ag.getIdAgent(), result.getIdAgent());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAffectationAgent_WithDateFin_ReturnAgent() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Affectation a1 = new Affectation();
		a1.setAgent(ag);
		a1.setIdAffectation(2);
		a1.setTempsTravail("tempsTravail");
		a1.setDateDebutAff(new DateTime(2010, 05, 01, 0, 0, 0).toDate());
		a1.setDateFinAff(new DateTime(2028, 05, 01, 0, 0, 0).toDate());
		sirhPersistenceUnit.persist(a1);

		Agent result = repository.getAffectationAgent(9005138, new Date());

		assertNotNull(result);
		assertEquals(ag.getIdAgent(), result.getIdAgent());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListAffectationActiveByIdFichePoste_Noresult() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new DateTime(2015, 01, 01, 00, 00).toDate());
		a.setDateFinAff(new DateTime(2015, 03, 01, 00, 00).toDate());
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListAffectationActiveByIdFichePoste(fichePoste.getIdFichePoste());

		assertEquals(result.size(), 0);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListAffectationActiveByIdFichePoste_Result() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new DateTime(2015, 01, 01, 00, 00).toDate());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListAffectationActiveByIdFichePoste(fichePoste.getIdFichePoste());

		assertEquals(result.size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsForListAgentByPeriode_1Affectation_1Prime() {
		
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);
		
		PrimePointageAffPK primePointageAffPK = new PrimePointageAffPK();
		primePointageAffPK.setNumRubrique(7704);
		
		PrimePointageAff primeAff = new PrimePointageAff();
		primeAff.setPrimePointageAffPK(primePointageAffPK);
		primeAff.setLibelle("INDEMNITE DE PANIER");
		
		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new DateTime(2015, 01, 01, 00, 00).toDate());
		a.setDateFinAff(null);
		a.setIdBaseHoraireAbsence(5);
		a.getPrimePointageAff().add(primeAff);
		
		primeAff.setAffectation(a);
		primePointageAffPK.setIdAffectation(a.getIdAffectation());

		sirhPersistenceUnit.persist(a);
		sirhPersistenceUnit.persist(primeAff);
		
		List<Affectation> result = repository.getListeAffectationsForListAgentByPeriode(
				Arrays.asList(9005138), 
				new DateTime(2015, 01, 01, 00, 00).toDate(), 
				new DateTime(2015, 10, 01, 00, 00).toDate());
		
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getIdBaseHoraireAbsence().intValue(), 5);
		assertEquals(result.get(0).getPrimePointageAff().size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsForListAgentByPeriode_2Affectations_2Primes() {
		
		// 1er affecation/agent
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);
		
		PrimePointageAffPK primePointageAffPK = new PrimePointageAffPK();
		primePointageAffPK.setNumRubrique(7704);
		
		PrimePointageAff primeAff = new PrimePointageAff();
		primeAff.setPrimePointageAffPK(primePointageAffPK);
		primeAff.setLibelle("INDEMNITE DE PANIER");
		
		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new DateTime(2015, 01, 01, 00, 00).toDate());
		a.setDateFinAff(null);
		a.setIdBaseHoraireAbsence(5);
		a.getPrimePointageAff().add(primeAff);
		
		primeAff.setAffectation(a);
		primePointageAffPK.setIdAffectation(a.getIdAffectation());

		sirhPersistenceUnit.persist(a);
		sirhPersistenceUnit.persist(primeAff);
		
		// 2e affecation/agent
		Agent ag2 = new Agent();
		ag2.setIdAgent(9002990);
		ag2.setNomatr(2990);
		ag2.setPrenom("NON");
		ag2.setDateNaissance(new Date());
		ag2.setNomPatronymique("TEST");
		ag2.setNomUsage("USAGE");
		ag2.setPrenomUsage("NONO");
		ag2.setSexe("H");
		ag2.setTitre("Mr");
		sirhPersistenceUnit.persist(ag2);
		
		PrimePointageAffPK primePointageAffPK2 = new PrimePointageAffPK();
		primePointageAffPK2.setNumRubrique(7713);
		
		PrimePointageAff primeAff2 = new PrimePointageAff();
		primeAff2.setPrimePointageAffPK(primePointageAffPK2);
		primeAff2.setLibelle("INDEMNITE DE PANIER DPM");
		
		PrimePointageAffPK primePointageAffPK3 = new PrimePointageAffPK();
		primePointageAffPK3.setNumRubrique(7720);
		
		PrimePointageAff primeAff3 = new PrimePointageAff();
		primeAff3.setPrimePointageAffPK(primePointageAffPK3);
		primeAff3.setLibelle("INDEMNITE TVX INSAL. DANG. 100%");
		
		Affectation a2 = new Affectation();
		a2.setAgent(ag2);
		a2.setIdAffectation(2);
		a2.setTempsTravail("tempsTravail");
		a2.setDateDebutAff(new DateTime(2015, 1, 15, 0, 0).toDate());
		a2.setDateFinAff(new DateTime(2015, 2, 15, 0, 0).toDate());
		a2.setIdBaseHoraireAbsence(3);
		a2.getPrimePointageAff().add(primeAff2);
		a2.getPrimePointageAff().add(primeAff3);

		sirhPersistenceUnit.persist(a2);
		
		List<Affectation> result = repository.getListeAffectationsForListAgentByPeriode(
				Arrays.asList(9005138, 9002990), 
				new DateTime(2015, 1, 1, 0, 0).toDate(), 
				new DateTime(2015, 1, 31, 0, 0).toDate());
		
		assertEquals(result.size(), 2);
		assertEquals(result.get(0).getIdBaseHoraireAbsence().intValue(), 3);
		assertEquals(result.get(0).getPrimePointageAff().size(), 2);
		assertEquals(result.get(1).getIdBaseHoraireAbsence().intValue(), 5);
		assertEquals(result.get(1).getPrimePointageAff().size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}
}
